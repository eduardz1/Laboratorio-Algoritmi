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
  private Map<V, Map<V, E>> adjacencyMatrix;

  /**
   * Creates an empty Graph.
   * 
   * @param isDirected set to {@code}true{@code} if the Graph should be directed, {@code}false{@code} otherwise
   */
  public Graph(boolean isDirected) {
    this.type = (isDirected) ? new DirectedGraphType<>() : new UndirectedGraphType<>();
    this.adjacencyMatrix = new HashMap<>();
  }

  public void addVertex(V vertex) throws GraphException {
    if(vertex == null)
      throw new GraphException("addVertex:" + " vertex cannot be null");
    
    Map<V, E> temp = new HashMap<>();
    temp.put(vertex, null);
    this.adjacencyMatrix.put(vertex, temp);
  }

  public void makeEdge(V to, V from, int weight) throws GraphException {
    this.type.makeEdge(to, from, weight);
  }

  public boolean isDirected() {
    return this.type instanceof DirectedGraphType;
  }

  public boolean containsVertex(V vertex) {
    return this.adjacencyMatrix.containsKey(vertex);
  }

  public boolean containsEdge(E edge) {
    return this.adjacencyMatrix.containsValue(edge);
  }

  public void removeVertex(V vertex) throws GraphException {
    if(vertex == null)
      throw new GraphException("removeVertex:" + " vertex cannot be null");
    // TODO:
  }

  public void removeEdge(E edge) throws GraphException {
    if(edge == null)
      throw new GraphException("removeEdge:" + " edge cannot be null");
    // TODO:
  }

  public int getVertexCount() {
    return this.adjacencyMatrix.size();
  }

  /**
   * @return number of edges in the Graph
   */
  public int getEdgeCount() {
    return this.type.getEdgeCount();
  }

  public ArrayList<E> getEdges() {
    return this.type.getEdges();
  }

  public ArrayList<V> getVertices() {
    return new ArrayList<>(this.adjacencyMatrix.keySet());
  }

  public ArrayList<V> getNeighbors(V vertex) throws GraphException {
    return this.type.getNeighbors(vertex);
  }

  public E getEdge(V from, V to) throws GraphException {
    return this.type.getEdge(from, to);
  }

  public void print() {
    for(V vertex : this.adjacencyMatrix.keySet()) {
      System.out.print(vertex + ": ");
      for(V neighbor : this.adjacencyMatrix.get(vertex).keySet()) {
        System.out.print(neighbor + " ");
      }
      System.out.println();
    }
  }
}