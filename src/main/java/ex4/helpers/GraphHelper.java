package ex4.helpers;

import ex3.structures.MinHeap;
import ex3.structures.PriorityQueue;
import ex4.exceptions.ArgumentException;
import ex4.exceptions.GraphHelperException;
import ex4.structures.Graph;

import java.util.*;


/**
 * Class that contains some useful methods for the Graph class. 
 */
public class GraphHelper {
  private static Graph<String, Float> currentGraphSF = null;
  private static Pair<Map<String, String>, Map<String, Float>> currentPairSF = null;

  /**
   * Dijkstra's algorithm.
   * 
   * @param <V>    Type of the element in the graph
   * @param <E>    Type of the edges in the graph (aka type of the weight),
   *               must extend {@code}Number{@code}
   * @param graph  {@link Graph Graph} of generic type, can be either
   *               directed or undirected
   * @param max    {@code}MAX VALUE{@code} of the specified number type
   * @param source source node for the path search
   * @return {@link GraphHelper.Pair Pair} a {@code}Map{@code} of the shortest
   *         path from {@code}source{@code} to each vertex and a
   *         {@code}Map{@code} of the distances from each vertex
   * @throws Exception when it encounters an edge with negative weight
   */
  private static Pair<Map<String, String>, Map<String, Float>> dijkstra(
      Graph<String, Float> graph,
      Float max,
      String source) throws Exception {

    Comparator<Float> comp = Comparator.comparing((Float x) -> x);
    PriorityQueue<Node<String, Float>> queue = new MinHeap<>((n1, n2) -> comp.compare(n1.key, n2.key));
    Map<String, Float> distances = new HashMap<>(); // used to track distances from source to each node
    Map<String, String> prevs = new HashMap<>(); // uses null to mark undefined

    for (String v : graph.getVertices()) {
      distances.put(v, max);
      prevs.put(v, null);
    }
    queue.insert(new Node<>(source, 0f));
    distances.put(source, 0f);

    while (!queue.isEmpty()) {
      Node<String, Float> u = queue.remove();
      if(distances.get(u.item) < u.key) continue; // ignores stale nodes when we already found a shorter path

      for (String neigh : graph.getNeighbors(u.item)) { // no need to check for visited given that the algorithm is greedy
        Float edgeWeight = graph.getEdge(u.item, neigh);
        if(edgeWeight < 0f) throw new GraphHelperException("Encountered an edge with a negative weight");
        Float newDist = distances.get(u.item) + edgeWeight;

        if (newDist < distances.get(neigh)) { // new shortest path found, relax the edge and update the queue
          Node<String, Float> newNode = new Node<>(neigh, newDist);
          if (!queue.contains(newNode)) {
            queue.insert(newNode);
          } else {
            queue.increaseKeyPriority(new Node<>(neigh, distances.get(neigh)), newNode);
          }
          distances.put(neigh, newDist);
          prevs.put(neigh, u.item);
        }
      }
    }
    return new Pair<>(prevs, distances);
  }

  /**
   * Finds the shortest path in a graph starting from the shortest path tree return by {@code}dijkstra{@code}'s algorithm.
   * 
   * @param graph       {@link Graph Graph} of generic type, can be either
   *                    directed or undirected
   * @param max         {@code}MAX VALUE{@code} of the specified number type
   * @param source      source node for the path search
   * @param destination destination of the path search
   * @return returns a new {@link Pair Pair} where the first element
   *         is a {@code}List{@code} of the calculated path and the second element
   *         is the path length, if there is no path between the two nodes then the
   *         first element is an empty array and the first element is zero
   * @throws ArgumentException when either graph is null, source and destination are the same or one of the former is not in the graph
   * @throws GraphHelperException when the graph contains an edge with negative weight
   */
  public static Pair<ArrayList<String>, Float> findShortestPath(
      Graph<String, Float> graph,
      Float max,
      String source,
      String destination) throws Exception {

    if (graph == null)
      throw new ArgumentException("Graph is null");
    if (!graph.containsVertex(source) || !graph.containsVertex(destination))
      throw new ArgumentException("Source or destination are not in the graph");
    if (source.equals(destination))
      throw new ArgumentException("Source and destination are the same");
    
    if(currentGraphSF == null || !currentGraphSF.equals(graph)) {
      currentGraphSF = graph;
      currentPairSF = dijkstra(graph, max, source);
    }
    Map<String, String> prevs = currentPairSF.first;
    Map<String, Float> distances = currentPairSF.second;

    ArrayList<String> path = new ArrayList<>();
    if(prevs.get(destination) == null) return new Pair<>(path, 0f);
    path.add(destination);

    for(String currentV = prevs.get(destination); currentV != null; currentV = prevs.get(currentV)) {
      path.add(currentV);
    }
    Collections.reverse(path);

    return new Pair<>(path, distances.get(destination));
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
    public Node {
      Objects.requireNonNull(item);
    }
  }
}