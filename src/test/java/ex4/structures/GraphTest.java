package ex4.structures;

import org.junit.Test;

import ex4.exceptions.*;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphTest {

  @Test
  public void isEmptyAfterCreate() {
    Graph<Integer, Integer> g = new Graph<>(true);
    assertEquals(0, g.getEdgeCount());
    assertEquals(0, g.getVertexCount());
  }

  @Test(expected = GraphException.class)
  public void addVertexNullThrowsException() throws GraphException {
    Graph<Integer, Double> g = new Graph<Integer, Double>(true);
    g.addVertex(null);
  }

  @Test(expected = GraphException.class)
  public void addAllVertexNullThrowsException() throws GraphException {
    Graph<Integer, Double> g = new Graph<Integer, Double>(true);
    g.addAllVertexes(null);
  }

  @Test(expected = GraphException.class)
  public void addAllVertexContainsNullThrowsException() throws GraphException {
    Graph<Integer, Double> g = new Graph<Integer, Double>(true);
    List<Integer> els = Arrays.asList(-1, 0, 1, 2, null, 4, 5, 6 );
    g.addAllVertexes(els);
  }

  @Test
  public void addEdgeNullThrowsException() throws GraphException, ElementNotFoundException {
    Graph<Integer, Double> g = new Graph<Integer, Double>(true);
    g.addVertex(1);
    g.addVertex(2);
    assertThrows(GraphException.class, () -> g.addEdge(1, null, 0.0));
    assertThrows(GraphException.class, () -> g.addEdge(null, 2, 0.0));
  }

  // @Test FIXME: bisogna differenziare tra addEdge e makeEdge forse oppure addEdge da sempre successo o bom?
  // public void addEdgeBetweenInvalidVertexThrowsException() throws GraphException, ElementNotFoundException {
  //   Graph<Integer, Double> g = new Graph<Integer, Double>(true);
  //   g.addVertex(1);
  //   g.addVertex(2);
  //   assertThrows(ElementNotFoundException.class, () -> g.addEdge(1, 4, 0.0));
  //   assertThrows(ElementNotFoundException.class, () -> g.addEdge(4, 2, 0.0));
  // }

  @Test
  public void removeEdgeNullOrInvalidThrowsException() throws GraphException, ElementNotFoundException {
    Graph<Integer, Double> g = new Graph<Integer, Double>(true);
    g.addVertex(1);
    g.addVertex(2);
    assertThrows(ElementNotFoundException.class, () -> g.removeEdge(0, 0));
    assertThrows(ElementNotFoundException.class, () -> g.removeEdge(1, 2));
    assertThrows(ElementNotFoundException.class, () -> g.removeEdge(2, 1));
    assertThrows(GraphException.class, () -> g.removeEdge(null, 1));
    assertThrows(GraphException.class, () -> g.removeEdge(2, null));
  }

  @Test
  public void removeVertexNullOrInvalidThrowsException() throws GraphException, ElementNotFoundException {
    Graph<Integer, Double> g = new Graph<Integer, Double>(true);
    assertThrows(ElementNotFoundException.class, () -> g.removeVertex(0));
    assertThrows(GraphException.class, () -> g.removeVertex(null));
  }

  @Test
  public void getNeighborsFromNullOrInvalidVertexThrowsException() throws GraphException, ElementNotFoundException {
    Graph<Integer, Double> g = new Graph<Integer, Double>(true);
    assertThrows(ElementNotFoundException.class, () -> g.getNeighbors(1));
    assertThrows(GraphException.class, () -> g.getNeighbors(null));
  }

  @Test
  public void addVertexHandleExpectedResult() throws GraphException {
    Graph<Integer, Double> g = new Graph<Integer, Double>(true);
    
    int count = 0;
    assertEquals(0, g.getVertexCount());

    for (int el : new int[] {-1, 0, 1, 2, 3, 4, 5, 6}) {
      g.addVertex(el);
      count++;
      assertEquals(count, g.getVertexCount());
    }
  }

  @Test
  public void addAllVertexHandleExpectedResult() throws GraphException {
    Graph<Integer, Double> g = new Graph<Integer, Double>(true);
    
    List<Integer> els = Arrays.asList(-1, 0, 1, 2, 3, 4, 5, 6 );
    g.addAllVertexes(els);
    assertEquals(els.size(), g.getVertexCount());
  }

  @Test
  public void addEdgeHandleExpectedResult() throws GraphException, ElementNotFoundException {
    Graph<Integer, Double> g = new Graph<Integer, Double>(true);
    List<Integer> els = Arrays.asList(-1, 0, 1, 2, 3, 4, 5, 6 );
    g.addAllVertexes(els);

    for (int i = 0; i < els.size() - 1; i++) {
      g.addEdge(els.get(i), els.get(i + 1), 0.0);
      assertEquals(i + 1, g.getEdgeCount());
    }
  }

  @Test
  public void addEdgeOnIndirectGraphAddReversedEdge() throws GraphException, ElementNotFoundException {
    Graph<Integer, Double> g = new Graph<Integer, Double>(false);

    g.addVertex(1);
    g.addVertex(2);

    g.addEdge(1, 2, 0.0);
    assertEquals(1, g.getEdgeCount());
    assertNotNull(g.getEdge(1, 2));
    assertNotNull(g.getEdge(2, 1));
    assertFalse(g.isDirected());
  }

  @Test
  public void addEdgeOnDirectGraphDoesNotAddReversedEdge() throws GraphException, ElementNotFoundException {
    Graph<Integer, Double> g = new Graph<Integer, Double>(true);

    g.addVertex(1);
    g.addVertex(2);

    g.addEdge(1, 2, 0.0);
    assertEquals(1, g.getEdgeCount());
    assertNotNull(g.getEdge(1, 2));
    assertThrows(ElementNotFoundException.class, () -> g.getEdge(2, 1));
    assertTrue(g.isDirected());
  }

  @Test
  public void removeEdgeHandleExpectedResult() throws GraphException, ElementNotFoundException {

    Graph<Integer, Double> g = new Graph<Integer, Double>(false);
    g.addVertex(1);
    g.addVertex(2);
    g.addEdge(1, 2, 0.0);
    assertEquals(1, g.getEdgeCount());
    assertNotNull(g.getEdge(1, 2));
    assertNotNull(g.getEdge(2, 1));
    g.removeEdge(1, 2);
    assertEquals(0, g.getEdgeCount());
    assertThrows(ElementNotFoundException.class, () -> g.getEdge(1, 2));
    assertThrows(ElementNotFoundException.class, () -> g.getEdge(2, 1));

  }

  @Test
  public void removeVertexHandleExpectedResult() throws GraphException, ElementNotFoundException {

    Graph<Integer, Double> g = new Graph<Integer, Double>(true);
    g.addVertex(1);
    assertEquals(1, g.getVertexCount());
    g.removeVertex(1);
    assertEquals(0, g.getVertexCount());

  }

  @Test
  public void removeVertexDeletesEdgesLinkedToRemovedVertex() throws GraphException, ElementNotFoundException {

    Graph<Integer, Double> g = new Graph<Integer, Double>(true);
    g.addVertex(1);
    g.addVertex(2);
    g.addVertex(3);

    g.addEdge(1, 2, 0.0);
    g.addEdge(2, 3, 0.0);
    assertNotNull(g.getEdge(1, 2));
    assertNotNull(g.getEdge(2, 3));

    g.removeVertex(2);
    assertThrows(ElementNotFoundException.class, () -> g.getEdge(1, 2));
    assertThrows(ElementNotFoundException.class, () -> g.getEdge(2, 3));
  }

  @Test
  public void getNeighborsOnDirectGraphHandleExpectedResult() throws GraphException, ElementNotFoundException {
    Graph<Integer, Double> g = new Graph<Integer, Double>(true);
    g.addVertex(1);
    g.addVertex(2);
    g.addVertex(3);
    g.addVertex(4);

    g.addEdge(1, 3, 0.0);
    g.addEdge(1, 4, 0.0);

    assertArrayEquals(Arrays.asList(3, 4).toArray(), g.getNeighbors(1).toArray());
    assertEquals(0, g.getNeighbors(3).size());
    assertEquals(0, g.getNeighbors(4).size());
  }

  @Test
  public void getNeighborsOnIndirectGraphHandleExpectedResult() throws GraphException, ElementNotFoundException {
    Graph<Integer, Double> g = new Graph<Integer, Double>(false);
    g.addVertex(1);
    g.addVertex(2);
    g.addVertex(3);
    g.addVertex(4);

    g.addEdge(1, 3, 0.0);
    g.addEdge(1, 4, 0.0);

    assertArrayEquals(Arrays.asList(3, 4).toArray(), g.getNeighbors(1).toArray());
    assertArrayEquals(Arrays.asList(1).toArray(), g.getNeighbors(3).toArray());
    assertArrayEquals(Arrays.asList(1).toArray(), g.getNeighbors(4).toArray());
  }

  @Test
  public void getVerticesHandleExpectedResult() throws GraphException, ElementNotFoundException {
   
    Graph<Integer, Double> g = new Graph<Integer, Double>(true);

    // Check for empty array
    assertNotNull(g.getVertices());
    assertEquals(0, g.getVertices().size());
    
    List<Integer> els = Arrays.asList(-1, 0, 1, 2, 3, 4, 5, 6 );
    g.addAllVertexes(els);
    
    // Check after insert
    ArrayList<Integer> vxs =  g.getVertices();
    assertEquals(els.size(),vxs.size());
    for (Integer el : els) {
      assertNotEquals(-1, vxs.indexOf(el));
    }

    // Check after delete
    int deleted = 0;
    for (Integer el : els) {
      g.removeVertex(el);
      deleted++;
      ArrayList<Integer> vertices =  g.getVertices();
      assertEquals(-1, vertices.indexOf(el));
      assertEquals(els.size() - deleted, vertices.size());
    }
  }
}