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
import ex4.helpers.GraphHelper;

public class NodeTest {

  @Test(expected = ArgumentException.class)
  public void createNodeWithItemNullThrowsException() throws ArgumentException {
    new GraphHelper.Node<String, Integer>(null, 0);
  }

  @Test
  public void nodeComparatorHandleExpectedResult() throws ArgumentException {
    Comparator<Integer> comp = Comparator.comparingInt((Integer x) -> x);
    NodeComparator<String, Integer> comparator = new NodeComparator<>(comp);

    GraphHelper.Node<String, Integer> node = new GraphHelper.Node<>("a", 1);
    GraphHelper.Node<String, Integer> node2 = new GraphHelper.Node<>("b", 0);

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
    List<GraphHelper.Node<String, Integer>> nodes = new ArrayList<GraphHelper.Node<String,Integer>>();    
    for (int i = 0; i < els.size(); i++) {
      GraphHelper.Node<String, Integer> node = new GraphHelper.Node<>(els.get(i), i);
      nodes.add(node);
    }

    Collections.shuffle(nodes);
    nodes.sort(comparator);

    for (int i = 0; i < els.size() - 1; i++) {
      assertTrue(nodes.get(i).priority < nodes.get(i + 1).priority);
    }
   }     
}
