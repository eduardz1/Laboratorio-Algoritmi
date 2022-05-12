package ex4.structures;

import java.util.Map;

public interface GraphType<V, E> {
  public void makeEdgeStrategy(Map<V, Map<V, E>> adjacencyMatrix, V to, V from, E weight);

  public void removeEdgeStrategy(Map<V, Map<V, E>> adjacencyMatrix, V from, V to);

  public int getEdgeCountStrategy(int edges);
}
