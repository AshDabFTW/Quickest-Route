package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Graph {
  private Map<Country, List<Country>> adjNodes;

  public Graph() {
    this.adjNodes = new HashMap<>();
  }

  public void addNode(Country node) {
    adjNodes.putIfAbsent(node, new ArrayList<>());
  }

  public void addEdge(Country node1, Country node2) {
    adjNodes.get(node1).add(node2);
  }

  public List<Country> bfsRoute(Country startCountry, Country endCountry) {
    List<Country> visited = new ArrayList<>();
    Queue<Country> queue = new LinkedList<>();
    Map<Country, Country> parentMap = new HashMap<>();

    if (startCountry.equals(endCountry)) {
      return new ArrayList<>();
    }

    queue.add(startCountry);
    visited.add(startCountry);
    parentMap.put(startCountry, null);

    while (!queue.isEmpty()) {
      Country node = queue.poll();

      if (node.equals(endCountry)) {
        List<Country> path = new ArrayList<>();
        path.add(endCountry);
        while(!node.equals(startCountry)) {
          node = parentMap.get(node);
          path.add(node);
        }
        Collections.reverse(path);
        return path;
      }

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
