package priorityqueue;

public interface PriorityQueue<T> {

  public boolean isEmpty();

  public int size();
  
  public void insert(T element) throws Exception;

  public T extractMaximum() throws Exception;

  public void increaseKey(int i, T key) throws Exception;
  
}
