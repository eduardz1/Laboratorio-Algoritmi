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
   * can be viewed as an adjacency list but functions more akin to an adjacency
   * matrix
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

  public void addVertex(V vertex) throws GraphException {
    if (vertex == null)
      throw new GraphException("addVertex:" + " vertex cannot be null");

    this.adjacencyMap.put(vertex, new HashMap<>());
  }

  public void addAllVertexes(Collection<V> vertexes) throws GraphException {
    if (vertexes == null)
      throw new GraphException("addAllVertexes:" + " vertexes cannot be null");

    for (V vertex : vertexes)
      this.addVertex(vertex);
  }

  public void addEdge(Edge<V, E> edge) throws GraphException {
    if (edge == null)
      throw new GraphException("addEdge:" + " edge cannot be null");

    this.addEdge(edge.getFrom(), edge.getTo(), edge.getWeight());
  }

  public void addAllEdges(Collection<Edge<V, E>> edges) throws GraphException, ElementNotFoundException {
    if (edges == null)
      throw new GraphException("addAllEdges:" + " edges cannot be null");

    for (Edge<V, E> edge : edges)
      this.addEdge(edge);
  }

  public void addEdge(V from, V to, E weight) throws GraphException {
    if (!adjacencyMap.containsKey(to))
      this.addVertex(to);
    if (!adjacencyMap.containsKey(from))
      this.addVertex(from);

    adjacencyMap.get(from).put(to, weight);
    this.type.makeEdgeStrategy(adjacencyMap, to, from, weight); 
    //FIXME: Questa implemntazione del metodo vuoto non mi convince per niente, 
    // piuttosto modificare la suddivisione delle classi
  }

  public boolean isDirected() {
    return this.type instanceof DirectedGraphType;
  }

  public boolean containsVertex(V vertex) {
    return this.adjacencyMap.containsKey(vertex);
  }

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
    if (vertex == null)
      throw new GraphException("getNeighbors:" + " vertex cannot be null");
    if (!this.adjacencyMap.containsKey(vertex))
      throw new ElementNotFoundException("getNeighbors:" + " vertex does not exist");

    return new ArrayList<>(this.adjacencyMap.get(vertex).keySet());
  }

  public E getEdge(V from, V to) throws GraphException, ElementNotFoundException {
    if (from == null || to == null)
      throw new GraphException("getEdge:" + " from and to cannot be null");
    if (!adjacencyMap.containsKey(from))
      throw new ElementNotFoundException("getEdge:" + " from does not exist");

    if (!adjacencyMap.get(from).containsKey(to))
      throw new ElementNotFoundException("getEdge:" + " to does not exist");

    return adjacencyMap.get(from).get(to);
  }

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