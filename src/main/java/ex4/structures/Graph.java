package ex4.structures;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import ex4.exceptions.*;

/**
 * A class representing a Graph with generic Vertexes and Edges.
 * 
 * @param <V> type of the elements in the Graph
 * @param <E> type of the edges in the Graph
 */
public class Graph<V, E> {
  private final GraphType<V, E> type;
  private final Map<V, Map<V, E>> adjacencyMatrix;

  /**
   * Creates an empty Graph.
   * 
   * @param isDirected set to {@code}true{@code} if the Graph should be directed,
   *                   {@code}false{@code} otherwise
   */
  public Graph(boolean isDirected) {
    this.type = (isDirected) ? new DirectedGraphType<>() : new UndirectedGraphType<>();
    this.adjacencyMatrix = new HashMap<>();
  }

  public void addVertex(V vertex) throws GraphException {
    if (vertex == null)
      throw new GraphException("addVertex:" + " vertex cannot be null");

    this.adjacencyMatrix.put(vertex, new HashMap<>());
  }

  public void makeEdge(V to, V from, E weight) throws GraphException, ElementNotFoundException {
    if (to == null || from == null)
      throw new GraphException("makeEdge:" + " to and from cannot be null");
    if (!adjacencyMatrix.containsKey(to))
      throw new ElementNotFoundException("makeEdge:" + " to does not exist");
    if (!adjacencyMatrix.containsKey(from))
      throw new ElementNotFoundException("makeEdge:" + " from does not exist");

    adjacencyMatrix.get(from).put(to, weight);
    this.type.makeEdge(adjacencyMatrix, to, from, weight);
  }

  public boolean isDirected() {
    return this.type instanceof DirectedGraphType;
  }

  public boolean containsVertex(V vertex) {
    return this.adjacencyMatrix.containsKey(vertex);
  }

  public boolean containsEdge(V vertex, E edge) {
    return this.adjacencyMatrix.get(vertex).containsValue(edge);
  }

  /**
   * Removes the specified vertex from the Graph.
   * 
   * @param vertex element ot be removed
   * @throws GraphException           when input vertex is null
   * @throws ElementNotFoundException when input vertex is not found
   */
  public void removeVertex(V vertex) throws GraphException, ElementNotFoundException {
    if (vertex == null)
      throw new GraphException("removeVertex:" + " vertex cannot be null");
    if (!this.adjacencyMatrix.containsKey(vertex))
      throw new ElementNotFoundException("removeVertex:" + " vertex does not exist");

    this.adjacencyMatrix.values().forEach(map -> map.remove(vertex));
  }

  public void removeEdge(V from, V to) throws GraphException, ElementNotFoundException {
    if (from == null || to == null)
      throw new GraphException("removeEdge:" + " from and to cannot be null");
    if (!this.adjacencyMatrix.containsKey(from))
      throw new ElementNotFoundException("removeEdge:" + " from does not exist");

    this.adjacencyMatrix.get(from).put(to, null);
  }

  public int getVertexCount() {
    return this.adjacencyMatrix.size();
  }

  /**
   * @return number of edges in the Graph
   */
  public int getEdgeCount() {
    int count = 0;
    for (Map<V, E> map : adjacencyMatrix.values())
      count += map.size();
    return count;
  }

  public ArrayList<E> getEdges() {
    ArrayList<E> edges = new ArrayList<>();
    for (Map<V, E> map : adjacencyMatrix.values())
      edges.addAll(map.values());
    return edges;
  }

  public ArrayList<V> getVertices() {
    return new ArrayList<>(this.adjacencyMatrix.keySet());
  }

  public ArrayList<V> getNeighbors(V vertex) throws GraphException, ElementNotFoundException {
    if (vertex == null)
      throw new GraphException("getNeighbors:" + " vertex cannot be null");
    if (!this.adjacencyMatrix.containsKey(vertex))
      throw new ElementNotFoundException("getNeighbors:" + " vertex does not exist");

    return new ArrayList<>(this.adjacencyMatrix.get(vertex).keySet());
  }

  public E getEdge(V from, V to) throws GraphException, ElementNotFoundException {
    if (from == null || to == null)
      throw new GraphException("getEdge:" + " from and to cannot be null");
    if (!adjacencyMatrix.containsKey(from))
      throw new ElementNotFoundException("getEdge:" + " from does not exist");

    return adjacencyMatrix.get(from).get(to);
  }

  public void print() {
    for (V vertex : this.adjacencyMatrix.keySet()) {
      System.out.print(vertex + ": ");
      for (V neighbor : this.adjacencyMatrix.get(vertex).keySet()) {
        System.out.print(neighbor + " ");
      }
      System.out.println();
    }
  }
}