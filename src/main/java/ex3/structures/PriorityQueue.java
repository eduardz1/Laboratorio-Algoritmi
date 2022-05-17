package ex3.structures;

import java.util.Collection;

public interface PriorityQueue<T> {
  /**
   * @return true if queue is empty
   */
  public boolean isEmpty();

  /**
   * @return number of elements in the queue
   */
  public int size();

  /**
   * inserts {@code}element{@code} to the queue
   * @param element to be added to the queue
   * @throws MinHeapException throws an Exception when element is null
   */
  public void insert(T element) throws Exception;

  /**
   * // TODO:
   * @param elements
   * @throws Exception
   */
  public void insertAll(Collection<T> elements) throws Exception;

  /**
   * extracts first element of the queue
   * 
   * @return T element extracted
   * @throws Exception
   */
  public T remove() throws Exception;

  /**
   * returns, without extracting first element of the queue
   * @return T element
   * @throws Exception
   */
  public T peek() throws Exception;

  /**
   * incresease the priority of an element
   * 
   * @param key key to be increased in priority
   * @param newKey new value of {@code}key{@code}
   * @throws Exception
   */
  public void increaseKey(T key, T newKey) throws Exception;
}
