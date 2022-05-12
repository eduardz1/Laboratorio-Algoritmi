package ex4.structures;

import java.util.Map;

import ex4.exceptions.*;

public class UndirectedGraphType<V, E> implements GraphType<V, E> {

  public void makeEdge(Map<V, Map<V, E>> adjacencyMatrix, V to, V from, E weight)
      throws GraphException, ElementNotFoundException {
    adjacencyMatrix.get(to).put(from, weight);
  }

}
