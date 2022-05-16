package ex3.structures;
import org.junit.Test;

import ex3.exceptions.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MinHeapTests {

  /**
   * Class used for testing purpose inside MinHeap
   */
  public class TestObject<A, B> {
    public A field1;
    public B field2;
    
    TestObject(A a, B b) {
      this.field1 = a;
      this.field2 = b;
    }
  }

  public class TestObjectComparator<A, B> implements Comparator<TestObject<A,B>> {

    public Comparator<? super B> comparator;
    
    public TestObjectComparator(Comparator<? super B> comp) {
      this.comparator = comp;
    }
  
    @Override
    public int compare(TestObject<A,B> arg0, TestObject<A,B> arg1) {
      return this.comparator.compare(arg0.field2, arg1.field2);
    }
  }
  
  @Test(expected = MinHeapException.class)
  public void createHeapWithComparatorNullThrowsException() throws MinHeapException {
    new MinHeap<String>(null);
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
    assertEquals(0, comp.compare("c", heap.peek()));

    heap.increaseKey("c", "b");
    assertEquals(0, comp.compare("b", heap.peek()));

    heap.increaseKey("b", "a");
    assertEquals(0, comp.compare("a", heap.peek()));
  }

  @Test
  public void isEmptyAfterRemoveLastElement() throws MinHeapException {
    Comparator<String> comp = Comparator.comparing((String x) -> x);
    MinHeap<String> heap = new MinHeap<String>(comp);
    heap.insert("a");
    heap.remove();
    assertEquals(0, heap.size());
    assertTrue(heap.isEmpty());
  }

  @Test
  public void isMinHeapifiedAfterInsertObject() throws MinHeapException, ElementNotFoundException {
    Comparator<Integer> comp = Comparator.comparingInt((Integer x) -> x);
    TestObjectComparator<String, Integer> comparator = new TestObjectComparator<>(comp);
    MinHeap<TestObject<String, Integer>> queue = new MinHeap<>(comparator);

    List<String> els = Arrays.asList("abcdefg".split(""));
    List<TestObject<String, Integer>> objs = new ArrayList<TestObject<String,Integer>>();    
    for (int i = 0; i < els.size(); i++) {
      TestObject<String, Integer> obj = new TestObject<>(els.get(i), i);
      objs.add(obj);
    }
    Collections.shuffle(objs);

    assertTrue(queue.isHeapified());
    int inserted = 0;
    for (TestObject<String, Integer> el : objs) {
      queue.insert(el);
      inserted++;
      assertTrue(queue.isHeapified());
      assertEquals(inserted, queue.size());
    }
  }

  @Test
  public void increaseKeyOfObjectHandleExpectedResult() throws MinHeapException, ElementNotFoundException {

    Comparator<Integer> comp = Comparator.comparingInt((Integer x) -> x);
    TestObjectComparator<String, Integer> comparator = new TestObjectComparator<>(comp);
    MinHeap<TestObject<String, Integer>> queue = new MinHeap<>(comparator);

    var prev = new TestObject<>("d", 4);
    queue.insert(prev);	

    var newT = new TestObject<>("c", 3);
    queue.increaseKey(prev, newT);
    assertEquals(0, comparator.compare(newT, queue.peek()));
    assertEquals("c", queue.peek().field1);
    assertTrue(queue.peek().field2 == 3);
    prev = newT;

    newT = new TestObject<>("b", 2);
    queue.increaseKey(prev, newT);
    assertEquals(0, comparator.compare(newT, queue.peek()));
    assertEquals("b", queue.peek().field1);
    assertTrue(queue.peek().field2 == 2);
    prev = newT;

    newT = new TestObject<>("a", 1);
    queue.increaseKey(prev, newT);
    assertEquals(0, comparator.compare(newT, queue.peek()));
    assertEquals("a", queue.peek().field1);
    assertTrue(queue.peek().field2 == 1);

  }
}