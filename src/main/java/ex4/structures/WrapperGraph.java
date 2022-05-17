package ex4.structures;

import java.util.ArrayList;
import java.util.Collection;

import ex4.exceptions.ElementNotFoundException;
import ex4.exceptions.GraphException;

public class WrapperGraph<V, E> {
  
  private final AbstractGraph<V, E> internalGraph;

  /**
   * Creates an empty graph.
   */
  public WrapperGraph(boolean isDirected) {
    internalGraph = isDirected ? new NewDirectGraph() : new NewIndirectedGraph();
  }

  /**
   * @return {@code}true{@code} if is directed or {@code}false{@code} otherwise
   */
  public boolean isDirected() {
    return this.internalGraph instanceof WrapperGraph.NewDirectGraph;
  } 

  /**
   * {@inheritDoc ex4.structures.AbstractGraph#addVertex(V)}
   * @throws GraphException when vertices are null
   */
  public void addAllVertices(Collection<V> vertices) throws GraphException {
    if (vertices == null)
      throw new GraphException("addAllVertexes:" + " vertexes cannot be null");

    this.internalGraph.addAllVertices(vertices);
  }

  /**
   * {@inheritDoc ex4.structures.AbstractGraph#addVertex(V)}
   * @throws GraphException when vertex is null.
   */
  public void addVertex(V vertex) throws GraphException {
    if (vertex == null)
      throw new GraphException("addVertex:" + " vertex cannot be null");

    this.internalGraph.addVertex(vertex);
  }

  /**
   * {@inheritDoc ex4.structures.AbstractGraph#addEdge(V, V, E)}
   */
  public boolean containsEdge(V from, V to) {
    return this.internalGraph.containsEdge(from, to);
  }

  /**
   * {@inheritDoc ex4.structures.AbstractGraph#containsVertex(V)}
   */
  public boolean containsVertex(V vertex) {
    return this.internalGraph.containsVertex(vertex);
  }

  /**
   * {@inheritDoc ex4.structures.AbstractGraph#getEdge(V, V)}
   * @throws GraphException when edge is null.
   * @throws ElementNotFoundException when either one of the vertices is not present in the graph.
   */
  public E getEdge(V from, V to) throws GraphException, ElementNotFoundException {
    
    if (from == null || to == null)
      throw new GraphException("getEdge:" + " from and to cannot be null");
    
    if (!internalGraph.containsVertex(from))
      throw new ElementNotFoundException("getEdge:" + " from does not exist");

    if (!internalGraph.containsEdge(from, to))
      throw new ElementNotFoundException("getEdge:" + " to does not exist");

    
    return this.internalGraph.getEdge(from, to);
  }

  @Override
  public int getEdgeCount() {
    return this.internalGraph.getEdgeCount();
  }

  @Override
  public ArrayList<E> getEdges() {
    return this.internalGraph.getEdges();
  }

  @Override
  public ArrayList<V> getNeighbors(V vertex) throws GraphException, ElementNotFoundException {
    if (vertex == null)
      throw new GraphException("getNeighbors:" + " vertex cannot be null");
    
    if (!this.internalGraph.containsVertex(vertex))
      throw new ElementNotFoundException("getNeighbors:" + " vertex does not exist");

      return this.internalGraph.getNeighbors(vertex);
  }

  @Override
  public int getVertexCount() {
    return this.internalGraph.getVertexCount();
  }

  @Override
  public ArrayList<V> getVertices() {
    return this.internalGraph.getVertices();
  }

  @Override
  public void addEdge(V from, V to, E weight) throws GraphException, ElementNotFoundException {
    if (to == null || from == null)
      throw new GraphException("makeEdge:" + " to and from cannot be null");
 
    if (!internalGraph.containsVertex(to))
      throw new ElementNotFoundException("addEdge:" + " to does not exist");
    if (!internalGraph.containsVertex(from))
      throw new ElementNotFoundException("addEdge:" + " from does not exist");

      this.internalGraph.addEdge(from, to, weight);
  }

  @Override
  public void removeEdge(V from, V to) throws GraphException, ElementNotFoundException {
    if (to == null || from == null)
      throw new GraphException("makeEdge:" + " to and from cannot be null");
 
    if (!internalGraph.containsVertex(to))
      throw new ElementNotFoundException("addEdge:" + " to does not exist");
    if (!internalGraph.containsVertex(from))
      throw new ElementNotFoundException("addEdge:" + " from does not exist");

    this.internalGraph.removeEdge(from, to);
  }

  private class NewIndirectedGraph extends AbstractGraph<V, E> {

    @Override
    public int getEdgeCount() {
      return super.getEdgeCount() / 2;
    }
  
    @Override
    public void addEdge(V from, V to, E weight) {
      super.addEdge(from, to, weight);
      super.addEdge(to, from, weight);
    }
  
    @Override
    public void removeEdge(V from, V to) throws GraphException, ElementNotFoundException {
      super.removeEdge(from, to);
      super.removeEdge(to, from);
    }
  
  }

  private class NewDirectGraph extends AbstractGraph<V, E> {

    @Override
    public int getEdgeCount() {
      return super.getEdgeCount();
    }
  
    @Override
    public void addEdge(V from, V to, E weight) {
      super.addEdge(from, to, weight);
    }
  
    @Override
    public void removeEdge(V from, V to) throws GraphException, ElementNotFoundException {
      super.removeEdge(from, to);
    }
    
  }
}
