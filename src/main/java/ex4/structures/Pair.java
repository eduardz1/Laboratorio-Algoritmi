package ex4.structures;

public class Pair<V, E> {
    private V first;
    private E second;

    public Pair(V first, E second) {
        this.first = first;
        this.second = second;
    }

    public V getFirst() {
        return first;
    }

    public E getSecond() {
        return second;
    }

    public void setFirst(V first) {
        this.first = first;
    }

    public void setSecond(E second) {
        this.second = second;
    }
}