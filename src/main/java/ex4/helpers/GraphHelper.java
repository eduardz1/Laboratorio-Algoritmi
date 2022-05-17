package ex4.helpers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ex3.structures.*;
import ex4.exceptions.ArgumentException;
import ex4.exceptions.DijkstraException;
import ex4.structures.Graph;

/**
 * // TODO
 */
public class GraphHelper {

  /**
   * Finds the shortest path in a graph using the Dijkstra algorithm
   * 
   * @param <V>         Type of the elements in the graph
   * @param <E>         Type of the edges in the graph (aka type of the weight),
   *                    must extend {@code}Number{@code}
   * @param graph       {@link Graph Graph} of generic type, can be either
   *                    directed or undirected
   * @param comparator  {@code}Comparator{@code} for a genric
   *                    {@link Node Node} of vertices to edges
   * @param min         {@code}MIN VALUE{@code} of the specified number type
   * @param max         {@code}MAX VALUE{@code} of the specified number type
   * @param source      source node for the path search
   * @param destination destination of the path search
   * @return returns a new {@link Pair Pair} where the first element
   *         is a {@code}List{@code} of the calculated path and the second element
   *         is the path length
   * @throws Exception
   */
  public static <V, E extends Number> Pair<List<V>, E> dijkstra(
      Graph<V, E> graph, Comparator<? super Node<V, E>> comparator, E min, E max, V source, V destination)
      throws Exception {

    if (!graph.containsVertex(source) || !graph.containsVertex(destination))
      throw new ArgumentException("Source or destination are invalid");

    PriorityQueue<Node<V, E>> queue = new MinHeap<>(comparator);
    Map<V, Node<V, E>> references = new HashMap<>(); // Used to mark visited vertices
    Map<V, E> distances = new HashMap<>(); // use null to mark infinity
    Map<V, V> prevs = new HashMap<>(); // use null to mark undefined

    distances.put(source, min);

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

    // Get shortest part to destination
    List<V> path = new ArrayList<V>();
    path.add(destination);
    V currentV = prevs.get(destination);
    if (currentV == null)
      throw new DijkstraException("Path between source and destination does not exist");

    do {
      path.add(currentV);
      currentV = prevs.get(currentV);
    } while (currentV != null);
    Collections.reverse(path);

    if (!path.get(0).equals(source))
      throw new DijkstraException("Path between source and destination does not exist");

    return new Pair<List<V>, E>(path, distances.get(destination));
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
  public static <E extends Number> E addNumbers(E a, E b) {
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
   * comparison of two generic objects extending {@code}Number{@code}
   * 
   * @param a first element of comparison
   * @param b second element of comparison
   * @return {@code}true{@code} if {@code}a < b{@code},
   *         {@code}false{@code} otherwise
   */
  public static boolean isLower(Number a, Number b) {
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
  }

  /**
   * Pair of two generic objects
   * 
   * @param <V> Type of the first object
   * @param <E> Type of the second object
   */
  public static class Pair<V, E> { // TODO: check se si può tenere statica

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
  public static class Node<V, E extends Number> { // TODO: check se si può tenere statica

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