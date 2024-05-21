package nz.ac.auckland.se281;

import java.util.List;

/** This class is the main entry point. */
public class MapEngine {
  Graph map = new Graph();

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
    // add code here
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {}
}
