package ex4.helpers;

import ex3.structures.MinHeap;
import ex3.structures.PriorityQueue;
import ex4.exceptions.ArgumentException;
import ex4.exceptions.GraphHelperException;
import ex4.structures.Edge;
import ex4.structures.Graph;

import java.util.*;

/**
 * // TODO
 */
public class GraphHelper {

  /**
   * Dijkstra's algorithm.
   * 
   * @param <V>    Tyype of the element in the graph
   * @param <E>    Type of the edges in the graph (aka type of the weight),
   *               must extend {@code}Number{@code}
   * @param graph  {@link Graph Graph} of generic type, can be either
   *               directed or undirected
   * @param source source node for the path search
   * @param max    {@code}MAX VALUE{@code} of the specified number type
   * @param queue  {@link PriorityQueue PriorityQueue} to use
   * @return {@link GraphHelper.Pair Pair} a {@code}Map{@code} of the shortest
   *         path from {@code}source{@code} to each verted and a
   *         {@code}Map{@code} of the distances from each vertex
   * @throws Exception
   */
  private static <V, E extends Number> Pair<Map<V, V>, Map<V, E>> dijkstra(
      Graph<V, E> graph,
      V source,
      E max,
      PriorityQueue<Node<V, E>> queue) throws Exception {

    Map<V, Node<V, E>> references = new HashMap<>(); // used to mark visited vertices
    Map<V, E> distances = new HashMap<>();
    Map<V, V> prevs = new HashMap<>(); // uses null to mark undefined

    distances.put(source, getZero(max));

    for (V v : graph.getVertices()) {

      if (!v.equals(source)) {
        distances.put(v, max);
      }

      Node<V, E> tmp = new Node<>(v, distances.get(v));
      queue.insert(tmp);
      references.put(v, tmp);
      prevs.put(v, null);
    }

    while (!queue.isEmpty()) {
      Node<V, E> u = queue.remove();
      references.remove(u.item);

      for (V neigh : graph.getNeighbors(u.item)) {
        if (!references.containsKey(neigh))
          continue;
        E alt = addNumbers(distances.get(u.item), graph.getEdge(u.item, neigh));
        if (isLower(alt, distances.get(neigh))) {
          distances.put(neigh, alt);
          Node<V, E> newVal = new Node<>(neigh, alt);
          queue.increaseKeyPriority(references.get(neigh), newVal);
          references.put(neigh, newVal);
          prevs.put(neigh, u.item);
        }
      }
    }

    return new Pair<>(prevs, distances);
  }

  /**
   * Finds the shortest path in a graph
   * 
   * @param <V>         Type of the elements in the graph
   * @param <E>         Type of the edges in the graph (aka type of the weight),
   *                    must extend {@code}Number{@code}
   * @param graph       {@link Graph Graph} of generic type, can be either
   *                    directed or undirected
   * @param comp  {@code}Comparator{@code} for a genric
   *                    {@link Node Node} of vertices to edges
   * @param max         {@code}MAX VALUE{@code} of the specified number type
   * @param source      source node for the path search
   * @param destination destination of the path search
   * @return returns a new {@link Pair Pair} where the first element
   *         is a {@code}List{@code} of the calculated path and the second element
   *         is the path length
   * @throws Exception
   */
  public static <V, E extends Number> Pair<List<V>, E> findShortestPath(
      Graph<V, E> graph,
      Comparator<? super E> comp,
      E max,
      V source,
      V destination) throws Exception {

    if (graph == null)
      throw new ArgumentException("Graph is null");
    if (!graph.containsVertex(source) || !graph.containsVertex(destination))
      throw new ArgumentException("Source or destination are not in the graph");
    if (containsNegativeWeight(graph))
      throw new GraphHelperException("Graph contains negative weights"); // TODO: test
    if (source.equals(destination))
      throw new ArgumentException("Source and destination are the same");

    PriorityQueue<Node<V, E>> queue = new MinHeap<>((n1, n2) -> comp.compare(n1.key, n2.key));

    Pair<Map<V, V>, Map<V, E>> res = dijkstra(graph, source, max, queue);
    Map<V, V> prevs = res.first;
    Map<V, E> distances = res.second;

    // Get the shortest part to destination
    List<V> path = new ArrayList<>();
    path.add(destination);
    V currentV = prevs.get(destination);
    if (currentV == null)
      throw new GraphHelperException("Path between source and destination does not exist");

    do {
      path.add(currentV);
      currentV = prevs.get(currentV);
    } while (currentV != null);
    Collections.reverse(path);

    if (!path.get(0).equals(source))
      throw new GraphHelperException("Path between source and destination does not exist");

    return new Pair<>(path, distances.get(destination));
  }

  private static <V, E extends Number> boolean containsNegativeWeight(Graph<V, E> graph) {
    for (Edge<V, E> edge : graph.getEdges()) {
      if (isLower(edge.weight(), getZero(edge.weight())))
        return true;
    }
    return false;
  }

  /**
   * add two generic {@code}Number{@code} objects
   * 
   * @param <E> Type of the element, must extend {@code}Number{@code}
   * @param a   first element
   * @param b   ssecond element
   * @return sum of the two elements
   */
  @SuppressWarnings("unchecked")
  private static <E extends Number> E addNumbers(E a, E b) {
    if (a instanceof Double || b instanceof Double) {
      return (E) (Double) (a.doubleValue() + b.doubleValue());
    } else if (a instanceof Float || b instanceof Float) {
      return (E) (Float) (a.floatValue() + b.floatValue());
    } else if (a instanceof Long || b instanceof Long) {
      return (E) (Long) (a.longValue() + b.longValue());
    } else {
      return (E) (Integer) (a.intValue() + b.intValue());
    }
  }

  /**
   * @return zero as generic
   */
  @SuppressWarnings("unchecked")
  private static <E extends Number> E getZero(E clazz) {
    if (clazz instanceof Double) {
      return (E) (Double) 0d;
    } else if (clazz instanceof Float) {
      return (E) (Float) 0f;
    } else if (clazz instanceof Long) {
      return (E) (Long) 0L;
    } else {
      return (E) (Integer) 0;
    }
  }

  /**
   * comparison of two generic objects extending {@code}Number{@code}
   * 
   * @param a first element of comparison
   * @param b second element of comparison
   * @return {@code}true{@code} if {@code}a < b{@code},
   *         {@code}false{@code} otherwise
   */
  private static boolean isLower(Number a, Number b) {
    if (a instanceof Double || b instanceof Double) {
      return a.doubleValue() < b.doubleValue();
    } else if (a instanceof Float || b instanceof Float) {
      return a.floatValue() < b.floatValue();
    } else if (a instanceof Long || b instanceof Long) {
      return a.longValue() < b.longValue();
    } else {
      return a.intValue() < b.intValue();
    }
  }

  /**
   * Pair of two generic objects
   *
   * @param <V> Type of the first object
   * @param <E> Type of the second object
   */
  public record Pair<V, E> (V first, E second) {
  }

  /**
   * Node for a {@link PriorityQueue PriorityQueue} composed by
   * an item with associated priority
   *
   * @param <V> Type of the item
   * @param <E> Type of the key, must extend {@code}Number{@code}
   */
  public record Node<V, E extends Number> (V item, E key) {
  }
}