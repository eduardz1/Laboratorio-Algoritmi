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
      String inputs[] = new String[3];
      Scanner input = new Scanner(System.in);

      System.out.println("Please enter the path of the dataset");
      inputs[0] = input.nextLine();

      File file = new File(inputs[0]);
      Graph<String, Float> graph = new Graph<>(false);

      Scanner scanner = new Scanner(file);
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] tokens = line.split(",");

        Edge<String, Float> edge = new Edge<>(tokens[0], tokens[1], Float.parseFloat(tokens[2]));
        graph.addEdge(edge);
      }
      scanner.close();

      System.out.println("Please enter the source city");
      inputs[1] = input.nextLine().toLowerCase();
      System.out.println("Please enter the destination city");
      inputs[2] = input.nextLine().toLowerCase();
      input.close();

      Comparator<Node<String, Float>> comp = new NodeComparator<>(Comparator.comparing((Float x) -> x));
      Pair<List<String>, Float> res = GraphHelper.<String, Float>dijkstra(graph,
          comp,
          Float.MIN_VALUE,
          Float.MAX_VALUE,
          inputs[1],
          inputs[2]);

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
