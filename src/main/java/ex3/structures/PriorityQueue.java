package ex3.structures;

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
   * increases value of generic element {@code}key{@code} to {@code}newKey{@code}
   * 
   * @param key element to be incremented
   * @param newKey new value of element, must be of higher priority
   * @throws Exception
   */
  public void increaseKey(T key, T newKey) throws Exception;
}
