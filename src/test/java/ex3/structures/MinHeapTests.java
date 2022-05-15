package ex3.structures;
import org.junit.Test;

import ex3.exceptions.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MinHeapTests {
  
  @Test(expected = MinHeapException.class)
    public void createHeapWithComparatorNullThrowsException() throws MinHeapException {
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
    assertEquals("a", heap.parent("c"));
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
  public void peekReturnsExpectedValue() throws MinHeapException, ElementNotFoundException {
    Comparator<String> comp = Comparator.comparing((String x) -> x);
    MinHeap<String> heap = new MinHeap<String>(comp);

    assertThrows(MinHeapException.class, () -> heap.peek());
    
    heap.insert("d");
    assertEquals("d", heap.peek());

    heap.insert("c");
    heap.insert("b");
    heap.insert("e");
    assertEquals("b", heap.peek());

    heap.increaseKey("b", "a");
    assertEquals("a", heap.peek());
    
    heap.remove();
    assertEquals("c", heap.peek());
  }



  @Test
  public void isMinHeapifiedAfterInsertSortedArray() throws MinHeapException, ElementNotFoundException {

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
  public void isMinHeapifiedAfterInsertUnsortedArray() throws MinHeapException, ElementNotFoundException {

    Comparator<String> comp = Comparator.comparing((String x) -> x);
    MinHeap<String> heap = new MinHeap<String>(comp);

    List<String> els = Arrays.asList("abcdefghijklmnopqrstuvz".split(""));
    Collections.shuffle(els);

    assertTrue(heap.isHeapified());
    for (String el : els) {
      heap.insert(el);
      assertTrue(heap.isHeapified());
    }
  }

  @Test
  public void isMinHeapifiedAfterRemove() throws MinHeapException, ElementNotFoundException {

    Comparator<String> comp = Comparator.comparing((String x) -> x);
    MinHeap<String> heap = new MinHeap<String>(comp);

    List<String> els = Arrays.asList("abcdefghijklmnopqrstuvz".split(""));
    Collections.shuffle(els);

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
  public void isMinHeapfiedAfterincreaseKey() throws MinHeapException, ElementNotFoundException {
    Comparator<String> comp = Comparator.comparing((String x) -> x);
    MinHeap<String> heap = new MinHeap<String>(comp);

    String[] els = {"aa", "bb", "cc", "dd", "ee"};

    for (String el : els) {
      heap.insert(el);
    }

    assertTrue(heap.isHeapified());
    for (String el : els) {
      heap.increaseKey(el, el.substring(1));
      assertTrue(heap.isHeapified());
    }

  }

  @Test 
  public void increaseKeyDecrementKeyValue() throws MinHeapException, ElementNotFoundException {
    Comparator<String> comp = Comparator.comparing((String x) -> x);
    MinHeap<String> heap = new MinHeap<String>(comp);
    
    heap.insert("d");	
    
    heap.increaseKey("d", "c");
    assertTrue(comp.compare("c", heap.peek()) == 0);

    heap.increaseKey("c", "b");
    assertTrue(comp.compare("b", heap.peek()) == 0);

    heap.increaseKey("b", "a");
    assertTrue(comp.compare("a", heap.peek()) == 0);
  }

  @Test
  public void isEmptyAfterRemoveLastElement() throws MinHeapException {
    Comparator<String> comp = Comparator.comparing((String x) -> x);
    MinHeap<String> heap = new MinHeap<String>(comp);
    heap.insert("a");
    heap.remove();
    assertTrue(heap.size() == 0);
    assertTrue(heap.isEmpty());
  }
}