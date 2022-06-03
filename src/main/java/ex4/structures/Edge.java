package ex4.structures;

/**
 * Creates an Edge with the given source, target and weight.
 *
 * @param source the source of the Edge
 * @param weight the weight of the Edge
 * @param target the target of the Edge
 */
public record Edge<V, E>(V source, E weight, V target) {

  @Override
  @SuppressWarnings("unchecked")
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof Edge)) {
      return false;
    }
    Edge<V, E> other = (Edge<V, E>) obj;
    return this.source.equals(other.source) && this.target.equals(other.target) && this.weight.equals(other.weight); // FIXME non dobbiamo checkare anche il weigth se non trattiamo multigrafi
  }

  public Edge<V, E> getReverse() {
    return new Edge<>(this.target, this.weight, this.source);
  }
}