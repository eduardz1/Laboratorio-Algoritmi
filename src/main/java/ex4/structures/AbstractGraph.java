package ex4.structures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ex4.exceptions.ElementNotFoundException;
import ex4.exceptions.GraphException;

/**
 * An abstract class representing a Graph with generic Vertexes and Edges.
 * Needs to be extended to either be a directed or undirected graph.
 * 
 * @param <V> type of the elements in the Graph
 * @param <E> type of the edges in the Graph
 */
public abstract class AbstractGraph<V, E> {

  /**
   * can be viewed as an adjacency list but functions more akin to a matrix
   */
  private final Map<V, Map<V, E>> adjacencyMap;

  /**
   * Creates an empty Graph.
   */
  public AbstractGraph() {
    this.adjacencyMap = new HashMap<>();
  }

  /**
   * Add vertex to the Graph.
   *
   * @param vertex is the vertex to be added.
   */
  public void addVertex(V vertex) {
    this.adjacencyMap.put(vertex, new HashMap<>());
  }

  /**
   * Add a {@code}Collection{@code} of vertex to the Graph.
   *
   * @param vertices is the collection of the vertices to be added.
   */
  public void addAllVertices(Collection<V> vertexes) {
    for (V vertex : vertexes)
      this.addVertex(vertex);
  }

  /**
   * Add edge to the Graph.
   *
   * @param edge is the edge as a object to be added.
   */
  public void addEdge(V from, V to, E weight) {
    adjacencyMap.get(from).put(to, weight);
  }

  /**
   * @param vertex element to find
   * @return {@code}true{@code} if contains vertex or
   *         {@code}false{@code} otherwise
   */
  public boolean containsVertex(V vertex) {
    return this.adjacencyMap.containsKey(vertex);
  }

  /**
   * Checks whether the graph contains an edge between two vertexes.
   *
   * @param vertex element associated to the source of the edge.
   * @param to element associated to the destination of the edge.
   * @return {@code}true{@code} if contains edge or {@code}false{@code} otherwise
   */
  public boolean containsEdge(V from, V to) {
    return this.adjacencyMap.get(from).containsKey(to);
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
    if (!this.adjacencyMap.containsKey(vertex))
      throw new ElementNotFoundException("removeVertex:" + " vertex does not exist");

    this.adjacencyMap.values().forEach(map -> map.remove(vertex));
    this.adjacencyMap.remove(vertex);
  }

  /**
   * Removes the specified edge from the Graph.
   *
   * @param from is the source vertex of the edge to be removed
   * @param to is the destination vertex of the edge to be removed 
   * @throws GraphException when {@code}from{@code} or {@code}to{@code} are null
   * @throws ElementNotFoundException  when {@code}from{@code} or {@code}to{@code} are not found
   */
  public void removeEdge(V from, V to) throws GraphException, ElementNotFoundException {
    this.adjacencyMap.get(from).remove(to);
  }

  public int getVertexCount() {
    return this.adjacencyMap.size();
  }

  /**
   * @return number of edges in the Graph
   */
  public int getEdgeCount() {
    return adjacencyMap
      .values()
      .stream()
      .reduce(0, (acc, curr) -> acc + curr.size(), Integer::sum);
  }

  public ArrayList<E> getEdges() {
    ArrayList<E> edges = new ArrayList<>();
    for (Map<V, E> map : adjacencyMap.values())
      edges.addAll(map.values());
    // Here in reality we could only check half of the edges in a undirected graph
    // as it is we are returning equivalent pairs which does not make too much sense
    // for example (3,1) and (1,3) are the same edge in an undirected graph
    return edges;
  }

  public ArrayList<V> getVertices() {
    return new ArrayList<>(this.adjacencyMap.keySet());
  }

  public ArrayList<V> getNeighbors(V vertex) throws GraphException, ElementNotFoundException {
    return new ArrayList<>(this.adjacencyMap.get(vertex).keySet());
  }

  public E getEdge(V from, V to) throws GraphException, ElementNotFoundException {
    return adjacencyMap.get(from).get(to);
  }

}