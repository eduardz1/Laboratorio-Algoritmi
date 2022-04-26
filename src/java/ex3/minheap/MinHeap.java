package minheap;

import java.util.ArrayList;
import java.util.Comparator;
import java.lang.Math;
import java.util.Collections;

/**
 * MinHeap
 * 
 * @param T type of the element in the array
 */
public class MinHeap<T> {
  private ArrayList<T> heap = null;
  private Comparator<? super T> comparator = null;

  /**
   * Creates an empty MinHeap
   * Accepts a comparator as input implementing
   * a previous relation between T elements
   * 
   * @param comparator: comparator implementing previous relation between T
   *                    elements
   * @throws MinHeapException throws an Exception when comparator is null
   */
  public MinHeap(Comparator<? super T> comparator) throws MinHeapException {
    if (comparator == null)
      throw new MinHeapException("MinHeap Constructor:" + " parameter comparator cannot be null");
    this.heap = new ArrayList();
    this.comparator = comparator;
  }

  /**
   * @return TRUE if this.heap is empty
   */
  public boolean isEmpty() { // cannot be null because of how the constructor is declared
    return this.heap.isEmpty();
  }

  /**
   * @return number of elements in this.heap
   */
  public int size() {
    return this.heap.size();
  }

  /**
   * @param i index of the element requested
   * @return element at i position in the ArrayList
   */
  public T getElementAt(int i) throws MinHeapException {
    if ((i < 0) || (i >= this.heap.size()))
      throw new MinHeapException("MinHeap getElementAt" + " index of array out of bound");
    return this.heap.get(i);
  }

  /**
   * @param element to be added to the heap
   * @throws MinHeapException throws an Exception when element is null
   */
  public void insert(T element) throws MinHeapException {
    if (element == null)
      throw new MinHeapException("MinHeap add" + " parameter element cannot be null");
    this.heap.add(element);

    int i = this.heap.size() - 1;

    while (i > 0 && this.comparator.compare(this.heap.get(this.parent(i)), this.heap.get(i)) > 0) {
      Collections.swap(this.heap, this.parent(i), i);
      i = this.parent(i);
    }
  }

  /**
   * @param i
   * @return index of the parent of the element at i position
   */
  public int parent(int i) {
    return i / 2;
  }

  /**
   * @param i
   * @return index of the left element of the parent at position i
   */
  public int left(int i) {
    return 2 * i;
  }

  /**
   * @param i
   * @return index of the right element of the parent at position i
   */
  public int right(int i){
    return 2 * i + 1;
  }

  /**
   * extracts minimum element of the MinHeap
   * 
   * @return T element extracted
   * @throws MinHeapException
   */
  public T extractMinimum() throws MinHeapException {
    if(this.heap == null)
      throw new MinHeapException("MinHeap extract" + " heap is empty");
    T res = this.heap.get(0);
    this.heap.add(0, this.heap.remove(this.heap.size() - 1));
    this.minHeapify(0);
  }

  /**
   * preserves the min-heap property
   * 
   * @param i current node index
   */
  private void minHeapify(int i) {
    int smallest;
    int left = this.left(i);
    int right = this.right(i);

    if(left < this.heap.size() && this.comparator.compare(this.heap.get(left), this.heap.get(i)) < 0) {
      smallest = left;
    } else {
      smallest = i;
    }
    if(right < this.heap.size() && this.comparator.compare(this.heap.get(right), this.heap.get(smallest)) < 0) {
      smallest = right;
    }

    if(smallest != i) {
      Collections.swap(this.heap, i, smallest);
      minHeapify(smallest);
    }
  }

  //public void reduce(int i, )
}