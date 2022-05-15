package ex4.structures;

import org.junit.Test;

import ex4.exceptions.*;

import ex4.structures.GraphHelper;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class GraphHelperTest {

  @Test
  public void testDijkstra() throws Exception {
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

    // Comparator<Node<String, Integer>> comp = new NodeComparator<>(Comparator.comparingInt((Integer x) -> x));
    // Pair<List<String>, Number> path = GraphHelper.<String>dijkstra(graph,
    //     comp,
    //     (Integer)Integer.MIN_VALUE,
    //     (Integer)Integer.MAX_VALUE,
    //     "a",
    //     "z");
    // assertArrayEquals(Arrays.asList("a", "b", "e", "z").toArray(), path.getFirst().toArray());
    // assertEquals(7, path.getSecond().intValue());

    GraphHelper.addNumbers(1, 2);
  }
}