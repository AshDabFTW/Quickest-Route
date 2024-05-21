package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
  private Map<Country, List<Country>> adjNodes;

  public Graph() {
    this.adjNodes = new HashMap<>();
  }

  public void addNode(Country node) {
    adjNodes.putIfAbsent(node, new ArrayList<>());
  }

  public void addEdge(Country node1, Country node2) {
    if (!adjNodes.get(node1).contains(node2)) {
      adjNodes.get(node1).add(node2);
      adjNodes.get(node2).add(node1);
    }
  }
}
