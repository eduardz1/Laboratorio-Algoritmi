package test.java;
import org.junit.Test;

import main.java.ex3.exceptions.ElementNotFoundException;
import main.java.ex3.exceptions.MinHeapException;
import main.java.ex3.structures.MinHeap;
import static org.junit.Assert.*;

import java.util.Comparator;

public class MinHeapTests {
  
  @Test(expected = MinHeapException.class)
    public void createHeapWithoutComparatorThrowsException() throws MinHeapException {
    MinHeap<String> heap = new MinHeap<String>(null);
  }

  @Test(expected = MinHeapException.class)
  public void insertNullElementThrowsException() throws MinHeapException {
    Comparator<String> comp = Comparator.comparing((String x) -> x);
    MinHeap<String> heap = new MinHeap<String>(comp);
    heap.insert(null);
  }

  @Test()
  public void getParentOrChildrenOnInvalidKeyThrowsException() throws MinHeapException, ElementNotFoundException {
    Comparator<String> comp = Comparator.comparing((String x) -> x);
    MinHeap<String> heap = new MinHeap<String>(comp);
    
    assertThrows(ElementNotFoundException.class, () -> heap.parent(null));
    assertThrows(ElementNotFoundException.class, () -> heap.parent(""));
    assertThrows(ElementNotFoundException.class, () -> heap.parent("abc"));
    assertThrows(ElementNotFoundException.class, () -> heap.left(null));
    assertThrows(ElementNotFoundException.class, () -> heap.left(""));
    assertThrows(ElementNotFoundException.class, () -> heap.left("abc"));
    assertThrows(ElementNotFoundException.class, () -> heap.right(null));
    assertThrows(ElementNotFoundException.class, () -> heap.right(""));
    assertThrows(ElementNotFoundException.class, () -> heap.right("abc"));
  } 

  @Test
  public void isEmptyAfterCreate() throws MinHeapException {
    Comparator<String> comp = Comparator.comparing((String x) -> x);
    MinHeap<String> heap = new MinHeap<String>(comp);
    assertTrue(heap.size() == 0);
    assertTrue(heap.isEmpty());
  }

  @Test
  public void getParentReturnsExpectedValue() throws MinHeapException, ElementNotFoundException {
    Comparator<String> comp = Comparator.comparing((String x) -> x);
    MinHeap<String> heap = new MinHeap<String>(comp);

    heap.insert("a");
    heap.insert("b");
    heap.insert("c");
    heap.insert("d");
    
    assertNull(heap.parent("a"));
    assertEquals("a", heap.parent("b"));
    assertEquals("a", heap.parent("c")); //FIXME: Parent method is not i/2 but i/2-1, because we work with 0pos index
    assertEquals("b", heap.parent("d"));
  }

  @Test
  public void getLeftReturnsExpectedValue() throws MinHeapException, ElementNotFoundException {
    Comparator<String> comp = Comparator.comparing((String x) -> x);
    MinHeap<String> heap = new MinHeap<String>(comp);

    heap.insert("a");
    heap.insert("b");
    heap.insert("c");
    heap.insert("d");
    heap.insert("e");
    
    assertEquals("b", heap.left("a"));
    assertEquals("d", heap.left("b")); 
    assertNull(heap.left("c"));
    assertNull(heap.left("d"));
    assertNull(heap.left("e"));
  }

  @Test
  public void getRightReturnsExpectedValue() throws MinHeapException, ElementNotFoundException {
    Comparator<String> comp = Comparator.comparing((String x) -> x);
    MinHeap<String> heap = new MinHeap<String>(comp);

    heap.insert("a");
    heap.insert("b");
    heap.insert("c");
    heap.insert("d");
    heap.insert("e");
    
    assertEquals("c", heap.right("a"));
    assertEquals("e", heap.right("b")); 
    assertNull(heap.right("c"));
    assertNull(heap.right("d"));
    assertNull(heap.right("e"));
  }


  @Test
  public void isMinHeapifiedAfterInsert() throws MinHeapException {

    Comparator<String> comp = Comparator.comparing((String x) -> x);
    MinHeap<String> heap = new MinHeap<String>(comp);

    assertTrue(heap.isHeapified());
    String[] els = "abcdefghijklmnopqrstuvz".split("");
    for (String el : els) {
      heap.insert(el);
      assertTrue(heap.isHeapified());
    }
  }

  @Test
  public void isMinHeapifiedAfterRemove() throws MinHeapException {

    Comparator<String> comp = Comparator.comparing((String x) -> x);
    MinHeap<String> heap = new MinHeap<String>(comp);

    String[] els = "abcdefghijklmnopqrstuvz".split("");
    for (String el : els) {
      heap.insert(el);
    }

    assertTrue(heap.isHeapified());
    for (int i = heap.size(); i > 0; i--) {
      heap.remove();
      assertTrue(heap.isHeapified());
    }
  }

  @Test
  public void isMinHeapfiedAfterdecreaseKey() throws MinHeapException, ElementNotFoundException {
    Comparator<String> comp = Comparator.comparing((String x) -> x);
    MinHeap<String> heap = new MinHeap<String>(comp);

    String[] els = {"aa", "bb", "cc", "dd", "ee"};

    for (String el : els) {
      heap.insert(el);
    }

    assertTrue(heap.isHeapified());
    for (String el : els) {
      heap.decreaseKey(el, el.substring(1)); // FIXME: String comparator seems to not works, check
      assertTrue(heap.isHeapified());
    }

  }

  @Test
  public void isEmptyAfterRemoveLastElement() {

  }
}
