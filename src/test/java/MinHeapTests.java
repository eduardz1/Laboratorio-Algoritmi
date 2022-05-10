package minheap;

import org.junit.Test;
import static org.junit.Assert.*;

import java.beans.Transient;

public class MinHeapTests {
  
  @Test
  public void isEmptyAfterCreate() {
    Comparator<String> comp = Comparator.comparing((String x) -> x);
    MinHeap heap = new MinHeap(comp);
    assertTrue(heap.size() == 0);
  }

  @Test
  public void isMinHeapifiedAfterInsert() {

  }

  @Test
  public void isMinHeapifiedAfterRemove() {

  }

  @Test
  public void isMinHeapfiedAfterIncreaseKey() {

  }

  @Test
  public void isEmptyAfterRemove() {

  }
}
