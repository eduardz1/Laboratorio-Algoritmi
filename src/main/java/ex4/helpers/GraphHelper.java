package ex4.helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ex3.structures.*;
import ex4.exceptions.ArgumentException;
import ex4.exceptions.DijkstraException;
import ex4.structures.Graph;
import ex4.structures.Node;
import ex4.structures.Pair;

public class GraphHelper {

  public static <V, E extends Number> Pair<List<V>, E> dijkstra
  (
    Graph<V, E> graph, Comparator<? super Node<V, E>> comparator, E min, E max, V source, V destination
  ) throws Exception 
  {    
    
    if (!graph.containsVertex(source) || !graph.containsVertex(destination))
      throw new ArgumentException("Source or destination are invalid");
    
    PriorityQueue<Node<V, E>> queue = new MinHeap<>(comparator);
    Map<V, Node<V, E>> references = new HashMap<>(); // Used to mark visited vertices
    Map<V, E> distances = new HashMap<>(); // use null to mark infinity
    Map<V, V> prevs = new HashMap<>(); // use null to mark undefined
    
    distances.put(source, min);
    
    for(V v : graph.getVertices()) {

      if(!v.equals(source)) {
        distances.put(v, max);
      }
      
      Node<V, E> tmp = new Node<>(v, distances.get(v));
      queue.insert(tmp);
      references.put(v, tmp);
      prevs.put(v, null);
    }

    while(!queue.isEmpty()) {
      Node<V, E> u = queue.remove();
      references.remove(u.item);

      for (V neigh : graph.getNeighbors(u.item)) {
        if (!references.containsKey(neigh)) continue;
        E alt = addNumbers(distances.get(u.item), graph.getEdge(u.item, neigh));
        if (isLower(alt ,distances.get(neigh))) {
          distances.put(neigh, alt);
          Node<V, E> newVal = new Node<>(neigh, alt);
          queue.increaseKey(references.get(neigh), newVal);
          references.put(neigh, newVal);
          prevs.put(neigh, u.item);
        }
      }
    }

    // Get shortest part to destination
    List<V> path = new ArrayList<V>();
    path.add(destination);
    V currentV = prevs.get(destination);
    if (currentV == null)
      throw new DijkstraException("Path between source and destination does not exist");

    do {
      path.add(currentV);
      currentV = prevs.get(currentV);
    } while (currentV != null);
    Collections.reverse(path);

    if (!path.get(0).equals(source))
      throw new DijkstraException("Path between source and destination does not exist");

    return new Pair<List<V>, E>(path, distances.get(destination));
  }

  @SuppressWarnings("unchecked")
  public static <E extends Number> E addNumbers(E a, E b) {
    if(a instanceof Double || b instanceof Double) {
        return (E)(Double)(a.doubleValue() + b.doubleValue());
    } else if(a instanceof Float || b instanceof Float) {
        return (E)(Float)(a.floatValue() + b.floatValue());
    } else if(a instanceof Long || b instanceof Long) {
        return (E)(Long)(a.longValue() + b.longValue());
    } else {
        return (E)(Integer)(a.intValue() + b.intValue());
    }
  }

  public static boolean isLower(Number a, Number b) {
    if(a instanceof Double || b instanceof Double) {
        return a.doubleValue() < b.doubleValue();
    } else if(a instanceof Float || b instanceof Float) {
        return a.floatValue() < b.floatValue();
    } else if(a instanceof Long || b instanceof Long) {
        return a.longValue() < b.longValue();
    } else {
        return a.intValue() < b.intValue();
    }
  }

}