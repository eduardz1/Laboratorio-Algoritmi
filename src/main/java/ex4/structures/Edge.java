package ex4.structures;

public class Edge<V, E> {
  private V from;
  private V to;
  private E weight;

  public Edge(V from, V to, E weight) {
    this.from = from;
    this.to = to;
    this.weight = weight;
  }

  public V getFrom() {
    return from;
  }

  public V getTo() {
    return to;
  }

  public E getWeight() {
    return weight;
  }

}
