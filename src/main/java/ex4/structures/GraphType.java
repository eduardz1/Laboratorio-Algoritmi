package main.java.ex4.structures;

import java.util.ArrayList;

import main.java.ex4.exceptions.*;

public interface GraphType<V, E> {
  public ArrayList<V> getNeighbors(V vertex) throws GraphException;
  public void makeEdge(V to, V from, int weight) throws GraphException;
  public int getEdgeCount();
  public ArrayList<E> getEdges();
  public E getEdge(V from, V to) throws GraphException;
}
