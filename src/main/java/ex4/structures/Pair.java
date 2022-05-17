package ex4.structures;

/**
 * Pair of two generic objects
 * 
 * @param <V> Type of the first object
 * @param <E> Type of the second object
 */
public class Pair<V, E> {

    private V first;
    private E second;

    /**
     * Creates a new {@code}Pair{@code} object
     * 
     * @param first first element
     * @param second first element
     */
    public Pair(V first, E second) {
        this.first = first;
        this.second = second;
    }

    /**
     * @return first object
     */
    public V getFirst() {
        return first;
    }

    /**
     * @return second object
     */
    public E getSecond() {
        return second;
    }

    /**
     * sets first object
     * 
     * @param first first element
     */
    public void setFirst(V first) {
        this.first = first;
    }

    /** 
     * sets second object
     * 
     * @param second second element
     */
    public void setSecond(E second) {
        this.second = second;
    }
    
}