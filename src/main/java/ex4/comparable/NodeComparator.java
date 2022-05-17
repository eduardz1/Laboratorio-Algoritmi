package ex4.comparable;

import java.util.Comparator;

import ex4.helpers.GraphHelper.Node;

/** // TODO
 * @param <V>
 * @param <E>
 */
public class NodeComparator<V, E extends Number> implements Comparator<Node<V,E>> {

  private Comparator<? super E> comparator;
  
  /**
   * // TODO
   * @param internalComparator
   */
  public NodeComparator(Comparator<? super E> internalComparator) {
    this.comparator = internalComparator;
  }

  @Override
  public int compare(Node<V, E> arg0, Node<V, E> arg1) {
    return this.comparator.compare(arg0.priority, arg1.priority);
  }
}