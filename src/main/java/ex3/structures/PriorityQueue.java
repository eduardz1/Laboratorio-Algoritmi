package ex3.structures;

import java.util.Collection;

public interface PriorityQueue<T> {
  /**
   * @return true if queue is empty
   */
  boolean isEmpty();

  /**
   * @return number of elements in the queue
   */
  int size();

  /**
   * inserts {@code}element{@code} to the queue
   * @param element to be added to the queue
   * @throws Exception throws an Exception when element is null
   */
  void insert(T element) throws Exception;

  /**
   * // TODO:
   * @param elements
   * @throws Exception
   */
  void insertAll(Collection<T> elements) throws Exception;

  /**
   * extracts first element of the queue
   * 
   * @return T element extracted
   * @throws Exception
   */
  T remove() throws Exception;

  /**
   * returns, without extracting first element of the queue
   * @return T element
   * @throws Exception
   */
  T peek() throws Exception;

  /**
   * incresease the priority of an element
   * 
   * @param key key to be increased in priority
   * @param newKey new value of {@code}key{@code}
   * @throws Exception
   */
  void increaseKeyPriority(T key, T newKey) throws Exception;
}
