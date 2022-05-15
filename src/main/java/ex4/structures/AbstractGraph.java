package ex4.structures;

import java.util.HashMap;
import java.util.Map;

import ex4.exceptions.GraphException;

public abstract class AbstractGraph<V, E> {

  /**
   * can be viewed as an adjacency list but functions more akin to an adjacency
   * matrix
   */
  private final Map<V, Map<V, E>> adjacencyMap;

  public AbstractGraph() {
    this.adjacencyMap = new HashMap<>();
  }

  public void addVertex(V vertex) throws GraphException {
    if (vertex == null)
      throw new GraphException("addVertex:" + " vertex cannot be null");

    this.adjacencyMap.put(vertex, new HashMap<>());
  }

}