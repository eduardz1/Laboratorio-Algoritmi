package ex4.structures;

import java.util.Map;

/**
 * // TODO
 * @param <V>
 * @param <E>
 */
public interface GraphType<V, E> {

  /**
   * // TODO
   * 
   * @param adjacencyMatrix
   * @param to
   * @param from
   * @param weight
   */
  public void makeEdgeStrategy(Map<V, Map<V, E>> adjacencyMatrix, V to, V from, E weight);

  /**
   * // TODO
   * 
   * @param adjacencyMatrix
   * @param from
   * @param to
   */
  public void removeEdgeStrategy(Map<V, Map<V, E>> adjacencyMatrix, V from, V to);

  /**
   * // TODO
   * @param edges
   * @return
   */
  public int getEdgeCountStrategy(int edges);
}
