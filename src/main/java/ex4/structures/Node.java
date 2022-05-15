package ex4.structures;

import ex4.exceptions.ArgumentException;

public class Node<V, E> {

  public V item;
  public E priority;

  public Node(V item, E priority) throws ArgumentException {

    if (item == null)
    throw new ArgumentException("Item cannot be null");

    this.item = item;
    this.priority = priority;
  }
}