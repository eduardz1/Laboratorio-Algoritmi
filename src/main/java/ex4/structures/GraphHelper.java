package ex4.structures;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ex3.structures.*;

class GraphHelper {

  public <V> Pair<List<V>, Number> dijkstra
  (
    Graph<V, Number> graph, Comparator<? super Node<V, Number>> comparator, Number min, Number max, V source, V destination
  ) throws Exception 
  {    
    PriorityQueue<Node<V, Number>> queue = new MinHeap<>(comparator);
    Map<V, Node<V, Number>> references = new HashMap<>();
    Map<V, Number> distances = new HashMap<>(); // use null to mark infinity
    List<V> predecessors = new ArrayList<>(); // use null to mark undefined
    
    distances.put(source, min);
    
    for(V v : graph.getVertices()) {

      if(!v.equals(source)) {
        distances.put(v, max);
      }
      
      Node<V, Number> tmp = new Node<V, Number>(v, distances.get(v));
      queue.insert(tmp);
      references.put(v, tmp);
    }

    while(!queue.isEmpty()) {
      Node<V, Number> u = queue.remove();
      // if(u.item.equals(destination)) {
      //   break;
      // }

      for (V neigh : graph.getNeighbors(u.item)) {
        Number alt = addNumbers(distances.get(u.item), graph.getEdge(u.item, neigh));
        if (isLower(alt ,distances.get(u.item))) {
          distances.put(neigh, alt);
          Node<V, Number> newVal = new Node<>(neigh, alt);
          queue.increaseKey(references.get(neigh), newVal);
          references.put(neigh, newVal);
          predecessors.add(neigh);
        }
      }
    }
    return new Pair<>(predecessors, distances.get(destination));
  }

  public static Number addNumbers(Number a, Number b) {
    if(a instanceof Double || b instanceof Double) {
        return a.doubleValue() + b.doubleValue();
    } else if(a instanceof Float || b instanceof Float) {
        return a.floatValue() + b.floatValue();
    } else if(a instanceof Long || b instanceof Long) {
        return a.longValue() + b.longValue();
    } else {
        return a.intValue() + b.intValue();
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