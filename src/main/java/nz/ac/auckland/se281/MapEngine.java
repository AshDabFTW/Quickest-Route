package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.List;

/** This class is the main entry point. */
public class MapEngine {
  Graph map = new Graph();
  List<Country> countryList = new ArrayList<Country>();

  public MapEngine() {
    // add other code here if you want
    loadMap(); // keep this mehtod invocation
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {
    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();
    // add code here to create your data structures

    for (String countryInList : countries) {
      String[] countryInfo = countryInList.split(",");
      Country country = new Country(countryInfo[0], countryInfo[1], countryInfo[2]);

      map.addNode(country);
      countryList.add(country);
    }

    for (String adjCountriesTogether : adjacencies) {
      String[] adjCountriesSplit = adjCountriesTogether.split(",");

      Country mainCountry = new Country(adjCountriesSplit[0], "null", "null");
      for (int i = 1; i < adjCountriesSplit.length; i++) {
        Country adjCountry = new Country(adjCountriesSplit[i], "null", "null");
        map.addEdge(mainCountry, adjCountry);
      }
    }
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    Country infoCountry = getValidCountryInput(MessageCli.INSERT_COUNTRY.getMessage());
    MessageCli.COUNTRY_INFO.printMessage(
        infoCountry.getName(), infoCountry.getContinent(), infoCountry.getTax());
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {
    Country startCountry = getValidCountryInput(MessageCli.INSERT_SOURCE.getMessage());
    Country endCountry = getValidCountryInput(MessageCli.INSERT_DESTINATION.getMessage());

    List<Country> bfsRoute = map.bfsRoute(startCountry, endCountry);
    if(bfsRoute.isEmpty()) {
      MessageCli.NO_CROSSBORDER_TRAVEL.printMessage();
      return;
    }
    // MessageCli.ROUTE_INFO.printMessage(bfsRoute);
  }

  public Country getValidCountryInput(String repeatMessage) {
    boolean validInput = false;

    while (!validInput) {
      System.out.println(repeatMessage);
      String input = Utils.capitalizeFirstLetterOfEachWord(Utils.scanner.nextLine());
      Country searchCountry = new Country(input, "null", "null");

      try {
        int indexSearchCountry = countryList.indexOf(searchCountry);
        if (indexSearchCountry == -1) {
          throw new InvalidCountryInputException();
        } else {
          Country searchedCountry = countryList.get(indexSearchCountry);
          return searchedCountry;
        }
      } catch (Exception InvalidCountryInputException) {
        MessageCli.INVALID_COUNTRY.printMessage(input);
      }
    }
    return null;
  }
  ;
}
