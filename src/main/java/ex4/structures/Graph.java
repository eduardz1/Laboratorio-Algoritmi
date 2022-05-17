package ex4.structures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

import ex4.exceptions.*;

//TODO: Suddividere le exception, i paremtri null in argument dovrebbero throware un ArgumentExcpetion

/**
 * A class representing a Graph with generic Vertexes and Edges.
 * 
 * @param <V> type of the elements in the Graph
 * @param <E> type of the edges in the Graph
 */
public class Graph<V, E> {

  private final GraphType<V, E> type;
  /**
   * can be viewed as an adjacency list but functions more akin to a matrix
   */
  private final Map<V, Map<V, E>> adjacencyMap;

  /**
   * Creates an empty Graph.
   * 
   * @param isDirected set to {@code}true{@code} if the Graph should be directed,
   *                   {@code}false{@code} otherwise
   */
  public Graph(boolean isDirected) {
    this.type = (isDirected) ? new DirectedGraphType<>() : new UndirectedGraphType<>();
    this.adjacencyMap = new HashMap<>();
  }

  /**
   * Add vertex to the Graph.
   *
   * @param vertex is the vertex to be added.
   * @throws GraphException when vertex is null.
   */
  public void addVertex(V vertex) throws GraphException {
    if (vertex == null)
      throw new GraphException("addVertex:" + " vertex cannot be null");

    this.adjacencyMap.put(vertex, new HashMap<>());
  }

  /**
   * Add a {@code}Collection{@code} of vertex to the Graph.
   *
   * @param vertices is the collection of the vertices to be added.
   * @throws GraphException when vertices are null
   */
  public void addAllVertices(Collection<V> vertices) throws GraphException {
    if (vertices == null)
      throw new GraphException("addAllVertices:" + " vertices cannot be null");

    for (V vertex : vertices)
      this.addVertex(vertex);
  }

  /**
   * Add edge to the Graph.
   *
   * @param edge is the edge as a object to be added.
   * @throws GraphException when edge is null.
   */
  public void addEdge(Edge<V, E> edge) throws GraphException {
    if (edge == null)
      throw new GraphException("addEdge:" + " edge cannot be null");

    this.addEdge(edge.getFrom(), edge.getTo(), edge.getWeight());
  }

  /**
   * Add a {@code}Collection{@code} of edges to the Graph.
   *
   * @param edges is the collection of the edges to be added.
   * @throws GraphException when edges are null.
   */
  public void addAllEdges(Collection<Edge<V, E>> edges) throws GraphException {
    if (edges == null)
      throw new GraphException("addAllEdges:" + " edges cannot be null");

    for (Edge<V, E> edge : edges)
      this.addEdge(edge);
  }

  /**
   * Add a edge to the graph.
   *
   * @param from   is the source vertex
   * @param to     is the destination vertex
   * @param weight is the weight of the edge
   * @throws GraphException when {@code}to{@code} or {@code}from{@code} are null
   */
  public void addEdge(V from, V to, E weight) throws GraphException {
    if (!adjacencyMap.containsKey(to))
      this.addVertex(to);
    if (!adjacencyMap.containsKey(from))
      this.addVertex(from);

    adjacencyMap.get(from).put(to, weight);
    this.type.makeEdgeStrategy(adjacencyMap, to, from, weight);
    // FIXME: Questa implemntazione del metodo vuoto non mi convince per niente,
    // piuttosto modificare la suddivisione delle classi
  }

  /**
   * @return {@code}true{@code} if is directed or {@code}false{@code} otherwise
   */
  public boolean isDirected() {
    return this.type instanceof DirectedGraphType;
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
   *
   * @param vertex element associated to the edge
   * @param edge   element to find
   * @return {@code}true{@code} if contains edge or {@code}false{@code} otherwise
   */
  public boolean containsEdge(V vertex, E edge) {
    return this.adjacencyMap.get(vertex).containsValue(edge);
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
   * 
   * @param from is the source vertex of the edge to be removed
   * @param to is the destination vertex of the edge to be removed 
   * @throws GraphException when {@code}from{@code} or {@code}to{@code} are null
   * @throws ElementNotFoundException  when {@code}from{@code} or {@code}to{@code} are not found
   */
  public void removeEdge(V from, V to) throws GraphException, ElementNotFoundException {
    if (from == null || to == null)
      throw new GraphException("removeEdge:" + " from and to cannot be null");
    if (!this.adjacencyMap.containsKey(from))
      throw new ElementNotFoundException("removeEdge:" + " from does not exist");
    if (!this.adjacencyMap.get(from).containsKey(to))
      throw new ElementNotFoundException("removeEdge:" + " to does not exist");

    this.adjacencyMap.get(from).remove(to);
    this.type.removeEdgeStrategy(this.adjacencyMap, from, to);
  }

  /**
   * 
   * @return number of the vertices in the Graph
   */
  public int getVertexCount() {
    return this.adjacencyMap.size();
  }

  /**
   * @return number of edges in the Graph
   */
  public int getEdgeCount() {
    int count = 0;
    for (Map<V, E> map : adjacencyMap.values())
      count += map.size();

    return this.type.getEdgeCountStrategy(count);
  }

  /**
   * 
   * @return list of edges in the Graph.
   */
  public ArrayList<E> getEdges() {
    ArrayList<E> edges = new ArrayList<>();
    for (Map<V, E> map : adjacencyMap.values())
      edges.addAll(map.values());
    // Here in reality we could only check half of the edges in a undirected graph
    // as it is we are returning equivalent pairs which does not make too much sense
    // for example (3,1) and (1,3) are the same edge in an undirected graph
    return edges;
  }

  /**
   * 
   * @return list of vertices in the Graph.
   */
  public ArrayList<V> getVertices() {
    return new ArrayList<>(this.adjacencyMap.keySet());
  }

  /**
   * 
   * @param vertex element of which the neighbors are to be found
   * @return list of neighbors
   * @throws GraphException when vertex is null
   * @throws ElementNotFoundException when vertex is not found
   */
  public ArrayList<V> getNeighbors(V vertex) throws GraphException, ElementNotFoundException {
    if (vertex == null)
      throw new GraphException("getNeighbors:" + " vertex cannot be null");
    if (!this.adjacencyMap.containsKey(vertex))
      throw new ElementNotFoundException("getNeighbors:" + " vertex does not exist");

    return new ArrayList<>(this.adjacencyMap.get(vertex).keySet());
  }

  /**
   * 
   * @param from is the source vertex of the edge 
   * @param to  is the destination vertex of the edge
   * @return edge
   * @throws GraphException when {@code}from{@code} or {@code}to{@code} are null
   * @throws ElementNotFoundException  when {@code}from{@code} or {@code}to{@code} are not found
   */
  public E getEdge(V from, V to) throws GraphException, ElementNotFoundException {
    if (from == null || to == null)
      throw new GraphException("getEdge:" + " from and to cannot be null");
    if (!adjacencyMap.containsKey(from))
      throw new ElementNotFoundException("getEdge:" + " from does not exist");

    if (!adjacencyMap.get(from).containsKey(to))
      throw new ElementNotFoundException("getEdge:" + " to does not exist");

    return adjacencyMap.get(from).get(to);
  }

  /**
   * prints a Graph formatted
   */
  public void print() {
    for (V vertex : this.adjacencyMap.keySet()) {
      System.out.print(vertex + ": ");
      for (V neighbor : this.adjacencyMap.get(vertex).keySet()) {
        System.out.print(neighbor + " ");
      }
      System.out.println();
    }
  }
}