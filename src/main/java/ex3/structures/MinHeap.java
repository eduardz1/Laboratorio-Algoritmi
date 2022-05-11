package main.java.ex3.structures;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import main.java.ex3.exceptions.*;

/**
 * Class that implements a Minimum Heap data structure.
 * 
 * @param <T> type of the element in the Heap
 */
public class MinHeap<T> implements PriorityQueue<T> {
  private ArrayList<T> heap;
  private Map<T, Integer> lookup;
  private final Comparator<? super T> comparator;

  /**
   * Creates an empty MinHeap
   * Accepts a comparator as input implementing
   * a previous relation between T elements
   * 
   * @param comparator: comparator implementing previous relation between T elements
   * @throws MinHeapException throws an Exception when comparator is null
   */
  public MinHeap(Comparator<? super T> comparator) throws MinHeapException {
    if (comparator == null)
      throw new MinHeapException("MinHeap:" + " parameter comparator cannot be null");
    this.heap = new ArrayList<>();
    this.comparator = comparator;
    this.lookup = new HashMap<>();
  }

  @Override
  public boolean isEmpty() {
    return this.heap.isEmpty();
  }

  @Override
  public int size() {
    return this.heap.size();
  }

  @Override
  public void insert(T elem) throws MinHeapException {
    if (elem == null)
      throw new MinHeapException("insert:" + " elem cannot be null"); 
    if (this.lookup.containsKey(elem))
      throw new MinHeapException("insert:" + " elem already present");

    this.heap.add(elem);
    this.lookup.put(elem, this.heap.size() - 1);

    int i = this.heap.size() - 1;
    botHeapify(i);
  }

  /**
   * @param i
   * @return index of the parent of the element at i position
   */
  private int parent(int i) {
    if (i == 0) return i;
    return (i - 1) / 2;
  }
  
  /**
   * @param elem
   * @return parent of {@code}elem{@code} in O(1) time
   * @throws ElementNotFoundException when {@code}elem{@code} is not present in the Heap
   */
  public T parent (T elem) throws ElementNotFoundException {
    if (!this.lookup.containsKey(elem))
      throw new ElementNotFoundException("parent:" + " Heap does not contain " + elem);
    int i = this.lookup.get(elem);
    if (i == 0) return null;
    return this.heap.get((i - 1) / 2);
  }

  /**
   * @param i
   * @return index of the left element of the parent at position i
   */
  private int left(int i) {
    int res = 2 * i + 1;
    if (res >= this.heap.size()) return i;
    return res;
  }

  /**
   * @param elem
   * @return left child of {@code}elem{@code} in O(1) time
   * @throws ElementNotFoundException when {@code}elem{@code} is not present in the Heap
   */
  public T left(T elem) throws ElementNotFoundException {
    if (!this.lookup.containsKey(elem))
      throw new ElementNotFoundException("left:" + " Heap does not contain " + elem);
    int i = this.lookup.get(elem);
    int res = 2 * i + 1;
    if (res >= this.heap.size()) return null;
    return this.heap.get(res);
  }

  /**
   * @param i
   * @return index of the right element of the parent at position i
   */
  private int right(int i){
    int res = 2 * i + 2;
    if (res >= this.heap.size()) return i;
    return res;
  }

  /**
   * @param elem
   * @return right child of {@code}elem{@code} in O(1) time
   * @throws ElementNotFoundException when {@code}elem{@code} is not present in the Heap
   */
  public T right(T elem) throws ElementNotFoundException{
    if (!this.lookup.containsKey(elem))
      throw new ElementNotFoundException("right:" + " Heap does not contain " + elem);
    int i = this.lookup.get(elem);
    int res = 2 * i + 2;
    if (res >= this.heap.size()) return null;
    return this.heap.get(res);
  }

  @Override
  public T remove() throws MinHeapException {
    if(this.heap.isEmpty())
      throw new MinHeapException("remove:" + " heap is empty");

    T res = this.heap.get(0);
    T newRoot = this.heap.remove(this.heap.size() - 1);

    this.heap.add(0, newRoot);
    this.lookup.remove(res);
    this.lookup.put(newRoot, 0);
    topHeapify(0);
    return res;
  }

  @Override
  public T peek() throws MinHeapException {
    if (this.heap.isEmpty())
      throw new MinHeapException("peek:" + " heap is empty");
    return this.heap.get(0);
  }

  /**
   * Preserves the min-heap property from top to bottom
   * 
   * @param i current node index
   */
  private void topHeapify(int i) {
    int smallest;
    int left  = this.left(i);
    int right = this.right(i);

    boolean cond = this.comparator.compare(this.heap.get(left), this.heap.get(i)) < 0;
    smallest = (cond) ? left : i;
    
    cond = this.comparator.compare(this.heap.get(right), this.heap.get(smallest)) < 0;
    if(right != i && cond) smallest = right;

    if(smallest != i) {
      this.lookup.put(this.heap.get(i), smallest);
      this.lookup.put(this.heap.get(smallest), i);
      Collections.swap(this.heap, i, smallest);
      topHeapify(smallest);
    }
  }

  /**
   * Decreases {@code}key{@code} to the new value {@code}newKey{@code}
   * 
   * @param key key to be decreased
   * @param newKey new value to be set
   * @throws ElementNotFoundException when {@code}key{@code} is not present in the Heap
   * @throws MinHeapException when {@code}newKey{@code} is not smaller than {@code}key{@code}
   */
  @Override
  public void decreaseKey(T key, T newKey) throws MinHeapException, ElementNotFoundException {
    if (!this.lookup.containsKey(key))
      throw new ElementNotFoundException("decreaseKey:" + key + " key not found in the heap");

    int i = this.lookup.get(key);
    if(this.comparator.compare(newKey, this.heap.get(i)) > 0)
      throw new MinHeapException(String.format("decreaseKey:" + " new key {%s} is not smaller than current key {%s}", newKey, key));
    
    this.heap.add(i, key);
    botHeapify(i);
  }
  
  /**
   * Preserves min-heap property from the bottom to the top
   * 
   * @param i current node index
   */
  private void botHeapify(int i) {
    while(i > 0 && this.comparator.compare(this.heap.get(parent(i)), this.heap.get(i)) > 0) {
      int parent = this.parent(i);
      this.lookup.put(this.heap.get(parent), i);
      this.lookup.put(this.heap.get(i), parent);
      Collections.swap(this.heap, i, parent(i));
      i = parent(i);
    }
  }

  

  // public boolean isPartialFilled() {

  //   return true;
  // }

  
  
  public boolean isHeapified() {
  //   if(this.heap.isEmpty())
  //     return true;
  //   T root = this.heap.get(0);
  // TODO: Completare  
  return true;
  }

  // private boolean isHeapified(T elem) {
    
  // }


}