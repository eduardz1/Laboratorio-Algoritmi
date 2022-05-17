package ex4.structures;

/**
 * Edge of a {@link Graph graph} composed by a source element, destination
 * element and an edge connecting the two
 * 
 * @param <V> Type of the elements
 * @param <E> Type of the edges
 */
public class Edge<V, E> {
  private V from;
  private V to;
  private E weight;

  /**
   * Creates a new {@code}Edge{@code} object
   * 
   * @param from source element
   * @param to destination element
   * @param weight edge
   */
  public Edge(V from, V to, E weight) {
    this.from = from;
    this.to = to;
    this.weight = weight;
  }

  /**
   * @return source
   */
  public V getFrom() {
    return from;
  }

  /**
   * @return destination
   */
  public V getTo() {
    return to;
  }

  /**
   * @return edge
   */
  public E getWeight() {
    return weight;
  }

}
