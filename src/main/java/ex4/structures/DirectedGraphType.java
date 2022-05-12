package ex4.structures;

import java.util.Map;

import ex4.exceptions.*;

public class DirectedGraphType<V, E> implements GraphType<V, E> {

  @Override
  public void makeEdge(Map<V, Map<V, E>> adjacencyMatrix, V to, V from, E weight)
      throws GraphException, ElementNotFoundException { }
}