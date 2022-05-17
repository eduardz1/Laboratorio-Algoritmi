package ex4.structures;

import java.util.Map;

/**
 * // TODO
 * @param <V>
 * @param <E>
 */
public class UndirectedGraphType<V, E> implements GraphType<V, E> {

  public void makeEdgeStrategy(Map<V, Map<V, E>> adjacencyMatrix, V to, V from, E weight) {
    adjacencyMatrix.get(to).put(from, weight);
  }

  @Override
  public void removeEdgeStrategy(Map<V, Map<V, E>> adjacencyMatrix, V from, V to) {
    adjacencyMatrix.get(to).remove(from);
  }

  @Override
  public int getEdgeCountStrategy(int edges) {
    return edges / 2;
  }

}
