package ex4.structures;

import ex4.exceptions.ArgumentException;

/**
 * Node for a {@link ex3.structures.PriorityQueue PriorityQueue} composed by
 * an item with associated priority
 * 
 * @param <V> Type of the item
 * @param <E> Type of the priority, must extend {@code}Number{@code}
 */
public class Node<V, E extends Number> {

  public V item;
  public E priority;

  /**
   * Creates a new {@code}Node{@code} object
   * 
   * @param item element
   * @param priority associated priority
   * @throws ArgumentException when item is null
   */
  public Node(V item, E priority) throws ArgumentException {
    if (item == null)
      throw new ArgumentException("Item cannot be null");

    this.item = item;
    this.priority = priority;
  }
}