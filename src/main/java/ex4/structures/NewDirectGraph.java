package ex4.structures;

import ex4.exceptions.ElementNotFoundException;
import ex4.exceptions.GraphException;

public class NewDirectGraph<V, E> extends AbstractGraph<V, E> {

  @Override
  public int getEdgeCount() {
    return super.getEdgeCount();
  }

  @Override
  public void makeEdge(V from, V to, E weight) throws GraphException, ElementNotFoundException {
    super.makeEdge(from, to, weight);
  }

  @Override
  public void removeEdge(V from, V to) throws GraphException, ElementNotFoundException {
    super.removeEdge(from, to);
  }
  
}
