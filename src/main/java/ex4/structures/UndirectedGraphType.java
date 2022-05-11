package main.java.ex4.structures;

import java.util.ArrayList;

import main.java.ex4.exceptions.GraphException;

public class UndirectedGraphType<V, E> implements GraphType<V, E> {

  @Override
  public ArrayList<V> getNeighbors(V vertex) throws GraphException {
    if(vertex == null)
      throw new GraphException("getNeighbors:" + " vertex cannot be null");
    // TODO Auto-generated method stub
    return null;
  }

	@Override
	public void makeEdge(V to, V from, int weight) throws GraphException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getEdgeCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<E> getEdges() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E getEdge(V from, V to) throws GraphException {
		// TODO Auto-generated method stub
		return null;
	}
    
}
