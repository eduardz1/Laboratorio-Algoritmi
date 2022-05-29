package ex4.helpers;

import ex3.structures.MinHeap;
import ex3.structures.PriorityQueue;
import ex4.comparable.NodeComparator;
import ex4.exceptions.ArgumentException;
import ex4.exceptions.GraphHelperException;
import ex4.structures.Graph;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
  private static <V, E extends Number> Pair<Map<V, V>, Map<V, E>> dijkstra
  (
    Graph<V, E> graph,
    V source,
    E max,
    PriorityQueue<Node<V, E>> queue
  ) throws Exception {

    Map<V, Node<V, E>> references = new HashMap<>(); // Used to mark visited vertices
    Map<V, E> distances = new HashMap<>(); // use null to mark infinity
    Map<V, V> prevs = new HashMap<>(); // use null to mark undefined

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
          queue.increaseKey(references.get(neigh), newVal);
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
   * @param comparator  {@code}Comparator{@code} for a genric
   *                    {@link Node Node} of vertices to edges
   * @param max         {@code}MAX VALUE{@code} of the specified number type
   * @param source      source node for the path search
   * @param destination destination of the path search
   * @return returns a new {@link Pair Pair} where the first element
   *         is a {@code}List{@code} of the calculated path and the second element
   *         is the path length
   * @throws Exception
   */
  public static <V, E extends Number> Pair<List<V>, E> findShortestPath
  (
    Graph<V, E> graph,
    Comparator<? super E> comparator,
    E max,
    V source,
    V destination
  ) throws Exception {

    if (graph == null)
      throw new ArgumentException("Graph is null");
    if (!graph.containsVertex(source) || !graph.containsVertex(destination))
      throw new ArgumentException("Source or destination are not in the graph");
    if (containsNegativeWeight(graph))
      throw new GraphHelperException("Graph contains negative weights"); // TODO: test
    if (source.equals(destination))
      throw new ArgumentException("Source and destination are the same");

    Comparator<? super Node<V, E>> comp = NodeComparator.<V, E>getComparator(comparator);
    PriorityQueue<Node<V, E>> queue = new MinHeap<>(comp);
    
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
    for (Graph<V, E>.Edge edge : graph.getEdges()) {
      if (isLower(edge.getWeight(), getZero(edge.getWeight())))
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
   * print a graph formatted in the dot language
   * 
   * @param <E>   Type of the edges, must extend {@code}Number{@code}
   * @param graph {@link Graph Graph} of generic type, can be either
   *              directed or undirected
   * @param file  {@code}File{@code} object where the output needs to be written
   * @throws Exception
   */
  public static <E extends Number> void dotPrint(Graph<String, E> graph, File file) throws Exception {
    FileWriter writer = new FileWriter(file);
    // TODO: check if it's Directed or not and print accordingly
    try {
      writer.write("digraph G {");

      for (String vertex : graph.getVertices()) {
        for (String neigh : graph.getNeighbors(vertex)) {
          writer.write(vertex + " -> " + neigh + " [label=" + graph.getEdge(vertex, neigh) + "];");
        }
      }
      writer.write("}");
    } catch (IOException e) {
      System.out.println("Error writing to file");
      e.printStackTrace();
    }
    writer.close();
  }

  /**
   * Pair of two generic objects
   * 
   * @param <V> Type of the first object
   * @param <E> Type of the second object
   */
  public static class Pair<V, E> {

    private V first;
    private E second;

    /**
     * Creates a new {@code}Pair{@code} object
     * 
     * @param first  first element
     * @param second first element
     */
    public Pair(V first, E second) {
      this.first = first;
      this.second = second;
    }

    /**
     * @return first object
     */
    public V getFirst() {
      return first;
    }

    /**
     * @return second object
     */
    public E getSecond() {
      return second;
    }

    /**
     * sets first object
     * 
     * @param first first element
     */
    public void setFirst(V first) {
      this.first = first;
    }

    /**
     * sets second object
     * 
     * @param second second element
     */
    public void setSecond(E second) {
      this.second = second;
    }

  }

  /**
   * Node for a {@link ex3.structures.PriorityQueue PriorityQueue} composed by
   * an item with associated priority
   * 
   * @param <V> Type of the item
   * @param <E> Type of the priority, must extend {@code}Number{@code}
   */
  public static class Node<V, E extends Number> {

    public V item;
    public E priority;

    /**
     * Creates a new {@code}Node{@code} object
     * 
     * @param item     element
     * @param priority associated priority
     * @throws ArgumentException when item is null
     */
    public Node(V item, E priority) throws ArgumentException {
      if (item == null)
        throw new ArgumentException("Item cannot be null");

      this.item = item;
      this.priority = priority;
    }
  }
}