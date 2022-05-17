package ex4.structures;

import ex4.exceptions.ElementNotFoundException;

/**
 * Private class that extends the AbstractGraph class to make it behave as an
 * undirected Graph
 */
public class UndirectedGraph<V, E> extends DirectedGraph<V, E> {

    /**
     * {@inheritDoc}
     * Divides the result into two because a double edge counts as one
     */
    @Override
    public int getEdgeCount() {
        return super.getEdgeCount() / 2;
    }

    /**
     * {@inheritDoc}
     * Calls {@code}addEdge(from, to, weight){@code} twice to add the edge in both
     * directions.
     */
    @Override
    public void addEdge(V from, V to, E weight) {
        super.addEdge(from, to, weight);
        super.addEdge(to, from, weight);
    }

    /**
     * {@inheritDoc}
     * Calls {@code}removeEdge(from, to){@code} twice to remove the edge in both
     * directions.
     */
    @Override // FIXME: siamo costretti a throware un'eccezione se la mappa è privata
    public void removeEdge(V from, V to) throws ElementNotFoundException {
        super.removeEdge(from, to);
        super.removeEdge(to, from);
    }

}