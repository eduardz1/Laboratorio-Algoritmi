package main.java.ex4.structures;

import main.java.ex3.structures.MinHeap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Comparator;

import main.java.ex4.exceptions.GraphException;

public class Graph<T>{
   private ArrayList<ArrayList<Integer>> adjacencyMatrix;
   private Comparator<? super T> comparator = null;
   private HashMap<T, Integer> rows = null;
   private HashMap<T, Integer> columns = null;

   public Graph(Comparator<? super T> comparator) throws GraphException {
      if(comparator == null)
         throw new GraphException("Graph:" + " parameter comparator cannot be null");
      this.comparator = comparator;
      this.adjacencyMatrix = new ArrayList<>();
      this.rows = new HashMap<>();
      this.columns = new HashMap<>();
   }

   public void makeEdge(T to, T from, int weight) throws GraphException {
      if(weight < 0)
         throw new GraphException("makeEdge:" + " weight must not be negative");
      int indexTo = this.rows.get(to);
      int indexFrom = this.columns.get(from);

      // FIXME: Doppio get
      // this.adjacencyMatrix.get(indexTo).get(indexFrom).add(weight);
   }
}