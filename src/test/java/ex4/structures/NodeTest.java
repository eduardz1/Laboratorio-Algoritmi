package ex4.structures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

import ex4.comparable.NodeComparator;
import ex4.exceptions.ArgumentException;

public class NodeTest {

  @Test(expected = ArgumentException.class)
  public void createNodeWithItemNullThrowsException() throws ArgumentException {
    Node<String, Integer> node = new Node<>(null, 0);
  }

  @Test
  public void nodeComparatorHandleExpectedResult() throws ArgumentException {
    Comparator<Integer> comp = Comparator.comparingInt((Integer x) -> x);
    NodeComparator<String, Integer> comparator = new NodeComparator<>(comp);

    Node<String, Integer> node = new Node<>("a", 1);
    Node<String, Integer> node2 = new Node<>("b", 0);

    assertTrue(comparator.compare(node, node2) > 0);
    assertTrue(comparator.compare(node2, node) < 0);

    node.priority = 0;
    assertEquals(0, comparator.compare(node2, node));
  }

  @Test
  public void nodeComparatorInsideSortingFunctionHandleExpectedResult() throws ArgumentException {
    Comparator<Integer> comp = Comparator.comparingInt((Integer x) -> x);
    NodeComparator<String, Integer> comparator = new NodeComparator<>(comp);

    List<String> els = Arrays.asList("abcdefg".split(""));
    List<Node<String, Integer>> nodes = new ArrayList<Node<String,Integer>>();    
    for (int i = 0; i < els.size(); i++) {
      Node<String, Integer> node = new Node<>(els.get(i), i);
      nodes.add(node);
    }

    Collections.shuffle(nodes);
    nodes.sort(comparator);

    for (int i = 0; i < els.size() - 1; i++) {
      assertTrue(nodes.get(i).priority < nodes.get(i + 1).priority);
    }
   }     
}
