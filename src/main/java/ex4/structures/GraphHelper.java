package ex4.structures;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ex3.structures.*;

class GraphHelper {

  public static <V, E extends Number> Pair<List<V>, E> dijkstra
  (
    Graph<V, E> graph, Comparator<? super Node<V, E>> comparator, E min, E max, V source, V destination
  ) throws Exception 
  {    
    PriorityQueue<Node<V, E>> queue = new MinHeap<>(comparator);
    Map<V, Node<V, E>> references = new HashMap<>();
    Map<V, E> distances = new HashMap<>(); // use null to mark infinity
    List<V> predecessors = new ArrayList<>(); // use null to mark undefined
    
    distances.put(source, min);
    
    for(V v : graph.getVertices()) {

      if(!v.equals(source)) {
        distances.put(v, max);
      }
      
      Node<V, E> tmp = new Node<>(v, distances.get(v));
      queue.insert(tmp);
      references.put(v, tmp);
    }

    while(!queue.isEmpty()) {
      Node<V, E> u = queue.remove();
      // if(u.item.equals(destination)) {
      //   break;
      // }

      for (V neigh : graph.getNeighbors(u.item)) {
        E alt = addNumbers(distances.get(u.item), graph.getEdge(u.item, neigh));
        if (isLower(alt ,distances.get(u.item))) {
          distances.put(neigh, alt);
          Node<V, E> newVal = new Node<>(neigh, alt);
          queue.increaseKey(references.get(neigh), newVal);
          references.put(neigh, newVal);
          predecessors.add(neigh);
        }
      }
    }
    return new Pair<>(predecessors, distances.get(destination));
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