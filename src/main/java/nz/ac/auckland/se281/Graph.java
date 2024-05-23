package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/** Class for the map of countries. */
public class Graph {
  // initialising the map
  private Map<Country, List<Country>> adjNodes;

  /** Contructor to create the map. */
  public Graph() {
    this.adjNodes = new HashMap<>();
  }

  /**
   * Method that creates the nodes on the graph (the countries).
   *
   * @param node is the country.
   */
  public void addNode(Country node) {
    adjNodes.putIfAbsent(node, new ArrayList<>());
  }

  /**
   * Adds edges between the country nodes.
   *
   * @param node1 is the node of the first country.
   * @param node2 is the node of the second country.
   */
  public void addEdge(Country node1, Country node2) {
    // will change the country so all of the information gets filled
    Set<Country> keyset = adjNodes.keySet();
    for (Country country : keyset) {
      if (country.equals(node1)) {
        node1 = country;
      } else if (country.equals(node2)) {
        node2 = country;
      }
    }

    // makes the connection
    adjNodes.get(node1).add(node2);
  }

  /**
   * finds the fastest route between two countries using the BFS method.
   *
   * @param startCountry is the starting country.
   * @param endCountry is the ending country.
   * @return an arraylist of the route followed.
   */
  public List<Country> bfsRoute(Country startCountry, Country endCountry) {
    // initilising the lists and map to show the parent of each node
    List<Country> visited = new ArrayList<>();
    Queue<Country> queue = new LinkedList<>();
    Map<Country, Country> parentMap = new HashMap<>();

    // if start and end country the same, return an empty array
    if (startCountry.equals(endCountry)) {
      return new ArrayList<>();
    }

    // add the start countrys to the lists
    queue.add(startCountry);
    visited.add(startCountry);
    parentMap.put(startCountry, null);

    // will keep looping until queue is empty
    while (!queue.isEmpty()) {
      // gets the country at the start of the queue
      Country node = queue.poll();

      // checks if it has arrived to end country then removes any countries that were not in that
      // direct path to that country using the parent map and reverses the array
      if (node.equals(endCountry)) {
        List<Country> path = new ArrayList<>();
        path.add(endCountry);
        while (!node.equals(startCountry)) {
          node = parentMap.get(node);
          path.add(node);
        }
        Collections.reverse(path);
        return path;
      }

      // does the bfs method and 
      for (Country n : adjNodes.get(node)) {
        if (!visited.contains(n)) {
          visited.add(n);
          queue.add(n);
          parentMap.put(n, node);
        }
      }
    }
    return visited;
  }
}
