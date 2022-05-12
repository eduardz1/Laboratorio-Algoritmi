package ex4.structures;

import java.util.Map;

import ex4.exceptions.*;

public interface GraphType<V, E> {
  public void makeEdge(Map<V, Map<V, E>> adjacencyMatrix, V to, V from, E weight)
      throws GraphException, ElementNotFoundException;
}
