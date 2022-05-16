package ex4.helpers;

import org.junit.Test;

import ex4.comparable.NodeComparator;
import ex4.exceptions.ArgumentException;
import ex4.exceptions.DijkstraException;
import ex4.structures.Graph;
import ex4.structures.Node;
import ex4.structures.Pair;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class GraphHelperTest {

  @Test
  public void dijkstraOnDirectGraphHandleExpectedResult() throws Exception {
    Graph<String, Integer> graph = new Graph<>(true);

    String[] vertexes = { "a", "b", "c", "d", "e", "f", "z" };

    for (String el : vertexes) {
      graph.addVertex(el);
    }

    graph.makeEdge("a", "b", 4);
    graph.makeEdge("a", "c", 6);
    graph.makeEdge("a", "f", 4);
    graph.makeEdge("b", "e", 2);
    graph.makeEdge("b", "d", 8);
    graph.makeEdge("b", "c", 7);
    graph.makeEdge("c", "d", 2);
    graph.makeEdge("d", "e", 4);
    graph.makeEdge("e", "z", 1);
    graph.makeEdge("f", "b", 1);
    graph.makeEdge("f", "z", 9);

    Comparator<Node<String, Integer>> comp = new NodeComparator<>(Comparator.comparingInt((Integer x) -> x));
    Pair<List<String>, Integer> path = GraphHelper.<String, Integer>dijkstra(graph,
        comp,
        0,
        Integer.MAX_VALUE,
        "a",
        "z");
    assertArrayEquals(Arrays.asList("a", "b", "e", "z").toArray(), path.getFirst().toArray());
    assertEquals(7, path.getSecond().intValue());
  }

  @Test
  public void dijkstraOnIndirectGraphHandleExpectedResult() throws Exception {
    Graph<String, Integer> graph = new Graph<>(false);

    String[] vertexes = { "a", "b", "c", "d", "e"};

    for (String el : vertexes) {
      graph.addVertex(el);
    }

    graph.makeEdge("a", "b", 2);
    graph.makeEdge("b", "c", 2);
    graph.makeEdge("c", "d", 2);
    graph.makeEdge("d", "e", 2);
    graph.makeEdge("a", "e", 7);

    Comparator<Node<String, Integer>> comp = new NodeComparator<>(Comparator.comparingInt((Integer x) -> x));
    Pair<List<String>, Integer> path = GraphHelper.<String, Integer>dijkstra(graph,
        comp,
        0,
        Integer.MAX_VALUE,
        "a",
        "e");
    assertArrayEquals(Arrays.asList("a", "e").toArray(), path.getFirst().toArray());
    assertEquals(7, path.getSecond().intValue());
  }

  @Test
  public void dijkstraOnGraphWithInternalLoopHandleExpectedResult() throws Exception {
    Graph<String, Integer> graph = new Graph<>(false);

    String[] vertexes = { "a", "b", "c", "d", "e"};

    for (String el : vertexes) {
      graph.addVertex(el);
    }

    graph.makeEdge("a", "b", 1);
    graph.makeEdge("b", "c", 1);
    graph.makeEdge("c", "d", 1);
    graph.makeEdge("d", "b", 1);
    graph.makeEdge("c", "e", 10);

    Comparator<Node<String, Integer>> comp = new NodeComparator<>(Comparator.comparingInt((Integer x) -> x));
    Pair<List<String>, Integer> path = GraphHelper.<String, Integer>dijkstra(graph,
        comp,
        0,
        Integer.MAX_VALUE,
        "a",
        "e");
    assertArrayEquals(Arrays.asList("a", "b", "c", "e").toArray(), path.getFirst().toArray());
    assertEquals(12, path.getSecond().intValue());   
  }

  @Test(expected = DijkstraException.class)
  public void dijkstraWithUnreachableDestinationThrowsException() throws Exception {
    Graph<String, Integer> graph = new Graph<>(false);

    String[] vertexes = { "a", "b", "c", "d", "e"};

    for (String el : vertexes) {
      graph.addVertex(el);
    }

    graph.makeEdge("a", "b", 1);
    graph.makeEdge("b", "c", 1);
    graph.makeEdge("c", "a", 1);
    graph.makeEdge("d", "e", 1);

    Comparator<Node<String, Integer>> comp = new NodeComparator<>(Comparator.comparingInt((Integer x) -> x));
    GraphHelper.<String, Integer>dijkstra(graph,
        comp,
        0,
        Integer.MAX_VALUE,
        "a",
        "e");
  }

  @Test(expected = DijkstraException.class)
  public void dijkstraWithDestinationWithoutEdgesThrowsException() throws Exception {
    Graph<String, Integer> graph = new Graph<>(false);

    String[] vertexes = { "a", "b", "c", "d", "e"};

    for (String el : vertexes) {
      graph.addVertex(el);
    }

    graph.makeEdge("a", "b", 1);
    graph.makeEdge("b", "c", 1);
    graph.makeEdge("c", "d", 1);
    graph.makeEdge("d", "b", 1);

    Comparator<Node<String, Integer>> comp = new NodeComparator<>(Comparator.comparingInt((Integer x) -> x));
    GraphHelper.<String, Integer>dijkstra(graph,
        comp,
        0,
        Integer.MAX_VALUE,
        "a",
        "e");
  }

  @Test
  public void dijkstraWithInvalidDestinationThrowsException() throws Exception {
    Graph<String, Integer> graph = new Graph<>(false);
    String[] vertexes = { "a", "b"};
    for (String el : vertexes) {
      graph.addVertex(el);
    }
    graph.makeEdge("a", "b", 1);

    Comparator<Node<String, Integer>> comp = new NodeComparator<>(Comparator.comparingInt((Integer x) -> x));
    assertThrows(
      ArgumentException.class, 
      () -> GraphHelper.<String, Integer>dijkstra(graph,
        comp,
        0,
        Integer.MAX_VALUE,
        "a",
        null)
    );

    assertThrows(
      ArgumentException.class, 
      () -> GraphHelper.<String, Integer>dijkstra(graph,
        comp,
        0,
        Integer.MAX_VALUE,
        "a",
        "z")
    );
  }

  @Test
  public void dijkstraWithInvalidSourceThrowsException() throws Exception {
    Graph<String, Integer> graph = new Graph<>(false);
    String[] vertexes = { "a", "b"};
    for (String el : vertexes) {
      graph.addVertex(el);
    }
    graph.makeEdge("a", "b", 1);

    Comparator<Node<String, Integer>> comp = new NodeComparator<>(Comparator.comparingInt((Integer x) -> x));
    assertThrows(
      ArgumentException.class, 
      () -> GraphHelper.<String, Integer>dijkstra(graph,
        comp,
        0,
        Integer.MAX_VALUE,
        null,
        "e")
    );

    assertThrows(
      ArgumentException.class, 
      () -> GraphHelper.<String, Integer>dijkstra(graph,
        comp,
        0,
        Integer.MAX_VALUE,
        "z",
        "b")
    );
  }



}