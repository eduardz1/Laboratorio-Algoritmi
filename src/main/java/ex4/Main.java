package ex4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import ex4.comparable.NodeComparator;
import ex4.structures.*;
import ex4.helpers.GraphHelper;

public class Main {
  public static void main(String[] args) throws Exception {
    try {
      // File file = new File("/home/eduard/Github/laboratorio-algoritmi-2021-2022/es4_dataset/italian_dist_graph.csv");
      File file = new File("/home/emme/laboratorio-algoritmi-2021-2022/es4_dataset/italian_dist_graph.csv");
      Scanner scanner = new Scanner(file);
      Graph<String, Float> graph = new Graph<>(false);

      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        // if(line == "") {
        // continue;
        // }
        String[] tokens = line.split(",");
        Edge<String, Float> edge = new Edge<>(tokens[0], tokens[1], Float.parseFloat(tokens[2]));
        graph.addEdge(edge);

        // if (!graph.containsVertex(tokens[0]))
        //   graph.addVertex(tokens[0]);

        // if (!graph.containsVertex(tokens[1]))
        //   graph.addVertex(tokens[2]);

        // graph.addEdge(tokens[0], tokens[1], Float.parseFloat(tokens[2]));
  
      }
      scanner.close();

      Comparator<Node<String, Float>> comp = new NodeComparator<>(Comparator.comparing((Float x) -> x));
      Pair<List<String>, Float> res = GraphHelper.<String, Float>dijkstra(graph, 
          comp, 
          Float.MIN_VALUE, 
          Float.MAX_VALUE,
          "torino", 
          "catania");

      for (String string : res.getFirst()) {
        System.out.println(string);
      }

      System.out.println(res.getSecond());
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
      e.printStackTrace();
    }
  }
}
