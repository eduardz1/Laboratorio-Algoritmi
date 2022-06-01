package ex4.helpers;

import ex4.exceptions.ElementNotFoundException;
import ex4.exceptions.GraphException;
import ex4.structures.Graph;

import java.util.HashMap;

public class GraphBuilder<V, E> {

  private final HashMap<V, HashMap<V, E>> map;
  private boolean directed;

  public GraphBuilder() {
    this.map = new HashMap<>();
  }

  public GraphBuilder<V, E> addVertex(V vertex) {
    map.put(vertex, new HashMap<>());
    return this;
  }

  public GraphBuilder<V, E> addEdge(V from, V to, E weight) {
    if (!map.containsKey(from))
      this.addVertex(from);
    
    if (!map.containsKey(to))
      this.addVertex(to);

    map.get(from).put(to, weight);
    return this;
  }
  
  public GraphBuilder<V, E> buildDiagraph(boolean isDirected) {
    directed = isDirected;
    return this;
  }

  public Graph<V, E> build() throws GraphException, ElementNotFoundException {
    Graph<V, E> graph = new Graph<>(directed);

    for (V v : this.map.keySet()) {
      graph.addVertex(v);
    }

    for (var entry : this.map.entrySet()) {
      for (var edge : entry.getValue().entrySet()) {
        graph.addEdge(entry.getKey(), edge.getKey(), edge.getValue());
      }
    }

    return graph;
  }

}
