package ex4.structures;

import java.util.ArrayList;
import java.util.Collection;

import ex4.exceptions.ElementNotFoundException;
import ex4.exceptions.GraphException;

public class WrapperGraph<V, E> extends AbstractGraph<V, E> {
  
  private AbstractGraph<V, E> internalGraph;
  private Boolean directed;

  public WrapperGraph(boolean isDirected) {
    internalGraph = isDirected ? new NewDirectGraph<V, E>() : new NewIndirectedGraph<>();
    directed = isDirected;
  }

  public boolean isDirected() {
    return this.directed;
  } 

  @Override
  public void addAllVertexes(Collection<V> vertexes) throws GraphException {
    this.internalGraph.addAllVertexes(vertexes);
  }

  @Override
  public void addVertex(V vertex) throws GraphException {
    this.internalGraph.addVertex(vertex);
  }

  @Override
  public boolean containsEdge(V vertex, E edge) {
    return this.internalGraph.containsEdge(vertex, edge);
  }

  @Override
  public boolean containsVertex(V vertex) {
    return this.internalGraph.containsVertex(vertex);
  }

  @Override
  public E getEdge(V from, V to) throws GraphException, ElementNotFoundException {
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
  public void makeEdge(V from, V to, E weight) throws GraphException, ElementNotFoundException {
    this.internalGraph.makeEdge(from, to, weight);
  }

  @Override
  public void removeEdge(V from, V to) throws GraphException, ElementNotFoundException {
    this.internalGraph.removeEdge(from, to);
  }

}
