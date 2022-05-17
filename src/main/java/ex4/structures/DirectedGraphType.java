package ex4.structures;

import java.util.Map;

/**
 * // TODO
 * @param <V>
 * @param <E>
 */
public class DirectedGraphType<V, E> implements GraphType<V, E> {

  @Override
  public void makeEdgeStrategy(Map<V, Map<V, E>> adjacencyMatrix, V to, V from, E weight) {
  }

  @Override
  public void removeEdgeStrategy(Map<V, Map<V, E>> adjacencyMatrix, V from, V to) {
  }

  @Override
  public int getEdgeCountStrategy(int edges) {
    return edges;
  }
}