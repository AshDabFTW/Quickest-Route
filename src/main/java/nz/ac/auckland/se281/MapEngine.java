package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/** This class is the main entry point. */
public class MapEngine {
  private Graph map = new Graph();
  private List<Country> countryList = new ArrayList<Country>();

  /** This is the method that will run and loard the map. */
  public MapEngine() {
    // add other code here if you want
    loadMap(); // keep this mehtod invocation
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {
    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();
    // add code here to create your data structures

    // creates the nodes for the map
    for (String countryInList : countries) {
      // splits the string array from the csv read
      String[] countryInfo = countryInList.split(",");
      Country country = new Country(countryInfo[0], countryInfo[1], countryInfo[2]);

      // adds nodes to map and to a country list
      map.addNode(country);
      countryList.add(country);
    }

    // creates the edges between countries for the map
    for (String adjCountriesTogether : adjacencies) {
      // splits the string array from the csv read
      String[] adjCountriesSplit = adjCountriesTogether.split(",");

      // adds edges between countries
      Country mainCountry = new Country(adjCountriesSplit[0], "null", "null");
      for (int i = 1; i < adjCountriesSplit.length; i++) {
        Country adjCountry = new Country(adjCountriesSplit[i], "null", "null");
        map.addEdge(mainCountry, adjCountry);
      }
    }
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    // calls method to get a valid country input
    Country infoCountry = getValidCountryInput(MessageCli.INSERT_COUNTRY.getMessage());

    // prints the information about the country
    MessageCli.COUNTRY_INFO.printMessage(
        infoCountry.getName(), infoCountry.getContinent(), infoCountry.getTax());
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {
    // gets valid country inputs for start and end countries
    Country startCountry = getValidCountryInput(MessageCli.INSERT_SOURCE.getMessage());
    Country endCountry = getValidCountryInput(MessageCli.INSERT_DESTINATION.getMessage());

    // calls graph method to find route using bfs method between start and end country
    List<Country> bfsRoute = map.bfsRoute(startCountry, endCountry);

    // if start and end country are the same, print out no borders crossed
    if (bfsRoute.isEmpty()) {
      MessageCli.NO_CROSSBORDER_TRAVEL.printMessage();
      return;
    }

    // building a string to display the route and counts the tax of each country
    StringBuilder nameStringBuilder = new StringBuilder();
    Set<String> continents = new LinkedHashSet<>();
    int totalTax = 0;
    nameStringBuilder.append("[");
    for (Country country : bfsRoute) {
      nameStringBuilder.append(country.getName()).append(", ");
      continents.add(country.getContinent());
      totalTax = totalTax + Integer.valueOf(country.getTax());
    }
    nameStringBuilder.setLength(nameStringBuilder.length() - 2);
    nameStringBuilder.append("]");
    MessageCli.ROUTE_INFO.printMessage(nameStringBuilder.toString());

    // building the string for the continents visitied in the route
    StringBuilder continentStringBuilder = new StringBuilder();
    continentStringBuilder.append("[");
    for (String continent : continents) {
      continentStringBuilder.append(continent).append(", ");
    }
    continentStringBuilder.setLength(continentStringBuilder.length() - 2);
    continentStringBuilder.append("]");
    MessageCli.CONTINENT_INFO.printMessage(continentStringBuilder.toString());

    // removing the tax of the starting country as you do not have to pay that
    totalTax = totalTax - Integer.valueOf(bfsRoute.get(0).getTax());
    MessageCli.TAX_INFO.printMessage(String.valueOf(totalTax));
  }

  /**
   * Method for getting a valid country input from the user.
   *
   * @param repeatMessage the message to ask the user everytime it loops.
   * @return the valid country which was inputed.
   */
  public Country getValidCountryInput(String repeatMessage) {
    // loop to keep asking for a valid input
    while (true) {
      // print the starting question
      System.out.println(repeatMessage);

      // gets the input from the user and capitilises the start of each word
      String input = Utils.capitalizeFirstLetterOfEachWord(Utils.scanner.nextLine());

      // creates a counrty based on the input
      Country searchCountry = new Country(input, "null", "null");

      // will try to find the index of the country, if it doesnt exist, it will throw an exception
      // which is caught with an error message
      try {
        int indexSearchCountry = countryList.indexOf(searchCountry);
        if (indexSearchCountry == -1) {
          throw new InvalidCountryInputException(input);
        } else {
          Country searchedCountry = countryList.get(indexSearchCountry);
          return searchedCountry;
        }
      } catch (Exception InvalidCountryInputException) {
        MessageCli.INVALID_COUNTRY.printMessage(input);
      }
    }
  }
  ;
}
