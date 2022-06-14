package ex4.helpers;

import ex4.exceptions.ArgumentException;
import ex4.structures.Graph;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class GraphHelperTests {

  @Test
  public void dijkstraOnDirectGraphHandleExpectedResult() throws Exception {

    GraphBuilder<String, Float> builder = new GraphBuilder<>();

    // Vertex "a", "b", "c", "d", "e", "f", "z"
    Graph<String, Float> graph = builder
        .addEdge("a", "b", 4f)
        .addEdge("a", "c", 6f)
        .addEdge("a", "f", 4f)
        .addEdge("b", "e", 2f)
        .addEdge("b", "d", 8f)
        .addEdge("b", "c", 7f)
        .addEdge("c", "d", 2f)
        .addEdge("d", "e", 4f)
        .addEdge("e", "z", 1f)
        .addEdge("f", "b", 1f)
        .addEdge("f", "z", 9f)
        .build();

    GraphHelper.Pair<ArrayList<String>, Float> path = GraphHelper.findShortestPath(graph,
        Float.MAX_VALUE,
        "a",
        "z");
    assertArrayEquals(Arrays.asList("a", "b", "e", "z").toArray(), path.first().toArray());
    assertEquals(7, path.second().intValue());
  }

  @Test
  public void dijkstraOnIndirectGraphHandleExpectedResult() throws Exception {

    GraphBuilder<String, Float> builder = new GraphBuilder<>();

    // "a", "b", "c", "d", "e"
    Graph<String, Float> graph = builder
        .buildDiagraph(false)
        .addEdge("a", "b", 2f)
        .addEdge("b", "c", 2f)
        .addEdge("c", "d", 2f)
        .addEdge("d", "e", 2f)
        .addEdge("a", "e", 7f)
        .build();

    GraphHelper.Pair<ArrayList<String>, Float> path = GraphHelper.findShortestPath(graph,
        Float.MAX_VALUE,
        "a",
        "e");
    assertArrayEquals(Arrays.asList("a", "e").toArray(), path.first().toArray());
    assertEquals(7, path.second().intValue());
  }

  @Test
  public void dijkstraOnGraphWithInternalLoopHandleExpectedResult() throws Exception {
    GraphBuilder<String, Float> builder = new GraphBuilder<>();

    // "a", "b", "c", "d", "e"
    Graph<String, Float> graph = builder
        .addEdge("a", "b", 1f)
        .addEdge("b", "c", 1f)
        .addEdge("c", "d", 1f)
        .addEdge("d", "b", 1f)
        .addEdge("c", "e", 10f)
        .build();

    GraphHelper.Pair<ArrayList<String>, Float> path = GraphHelper.findShortestPath(graph,
        Float.MAX_VALUE,
        "a",
        "e");
    assertArrayEquals(Arrays.asList("a", "b", "c", "e").toArray(), path.first().toArray());
    assertEquals(12, path.second().intValue());
  }

  @Test
  public void dijkstraWithUnreachableDestinationReturnsEmptyArray() throws Exception {
    Graph<String, Float> graph = new Graph<>(false);

    String[] vertexes = { "a", "b", "c", "d", "e" };

    for (String el : vertexes) {
      graph.addVertex(el);
    }

    graph.addEdge("a", "b", 1f);
    graph.addEdge("b", "c", 1f);
    graph.addEdge("c", "a", 1f);
    graph.addEdge("d", "e", 1f);

    GraphHelper.Pair<ArrayList<String>, Float> empty = GraphHelper.findShortestPath(graph,
        Float.MAX_VALUE,
        "a",
        "e");

    assertArrayEquals(new ArrayList<String>().toArray(), empty.first().toArray());
    assertEquals(0, empty.second().intValue());
  }

  public void dijkstraWithDestinationWithoutEdgesReturnsEmptyArray() throws Exception {
    Graph<String, Float> graph = new Graph<>(false);

    String[] vertexes = { "a", "b", "c", "d", "e" };

    for (String el : vertexes) {
      graph.addVertex(el);
    }

    graph.addEdge("a", "b", 1f);
    graph.addEdge("b", "c", 1f);
    graph.addEdge("c", "d", 1f);
    graph.addEdge("d", "b", 1f);

     GraphHelper.Pair<ArrayList<String>, Float> empty = GraphHelper.findShortestPath(graph,
        Float.MAX_VALUE,
        "a",
        "e");

    assertArrayEquals(new ArrayList<String>().toArray(), empty.first().toArray());
    assertEquals(0, empty.second().intValue());
  }

  @Test
  public void dijkstraWithInvalidDestinationThrowsException() throws Exception {
    Graph<String, Float> graph = new Graph<>(false);
    String[] vertexes = { "a", "b" };
    for (String el : vertexes) {
      graph.addVertex(el);
    }
    graph.addEdge("a", "b", 1f);

    assertThrows(
        ArgumentException.class,
        () -> GraphHelper.findShortestPath(graph,
            Float.MAX_VALUE,
            "a",
            null));

    assertThrows(
        ArgumentException.class,
        () -> GraphHelper.findShortestPath(graph,
            Float.MAX_VALUE,
            "a",
            "z"));
  }

  @Test
  public void dijkstraWithInvalidSourceThrowsException() throws Exception {
    Graph<String, Float> graph = new Graph<>(false);
    String[] vertexes = { "a", "b" };
    for (String el : vertexes) {
      graph.addVertex(el);
    }
    graph.addEdge("a", "b", 1f);

    assertThrows(
        ArgumentException.class,
        () -> GraphHelper.findShortestPath(graph,
            Float.MAX_VALUE,
            null,
            "e"));

    assertThrows(
        ArgumentException.class,
        () -> GraphHelper.findShortestPath(graph,
            Float.MAX_VALUE,
            "z",
            "b"));
  }

  @Test(expected = NullPointerException.class)
  public void createNodeWithItemNullThrowsException() throws NullPointerException {
    new GraphHelper.Node<String, Float>(null, 0f);
  }

}