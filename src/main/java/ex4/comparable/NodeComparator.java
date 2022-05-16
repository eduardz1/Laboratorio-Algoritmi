package ex4.comparable;

import java.util.Comparator;

import ex4.structures.Node;

public class NodeComparator<V, E> implements Comparator<Node<V,E>> {

  private Comparator<? super E> comparator;
  
  public NodeComparator(Comparator<? super E> internalComparator) {
    this.comparator = internalComparator;
  }

  @Override
  public int compare(Node<V, E> arg0, Node<V, E> arg1) {
    return this.comparator.compare(arg0.priority, arg1.priority);
  }
}