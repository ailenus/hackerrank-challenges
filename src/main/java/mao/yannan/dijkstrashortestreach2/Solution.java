package mao.yannan.dijkstrashortestreach2;

import java.io.*;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class Solution {

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int t = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, t).forEach(tItr -> {
            try {
                String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

                int n = Integer.parseInt(firstMultipleInput[0]);

                int m = Integer.parseInt(firstMultipleInput[1]);

                List<List<Integer>> edges = new ArrayList<>();

                IntStream.range(0, m).forEach(i -> {
                    try {
                        edges.add(
                                Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                        .map(Integer::parseInt)
                                        .collect(toList())
                        );
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                int s = Integer.parseInt(bufferedReader.readLine().trim());

                List<Integer> result = Result.shortestReach(n, edges, s);

                bufferedWriter.write(
                        result.stream()
                                .map(Object::toString)
                                .collect(joining(" "))
                                + "\n"
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }

}

class Result {

    private Result() { }

    /*
     * Complete the 'shortestReach' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. 2D_INTEGER_ARRAY edges
     *  3. INTEGER s
     */

    public static List<Integer> shortestReach(int n, List<List<Integer>> edges, int s) {
        List<List<Node>> adjacency = new ArrayList<>();
        for (var i = 0; i < n; i++) {
            adjacency.add(new ArrayList<>());
        }
        for (var edge : edges) {
            var u = edge.get(0) - 1;
            var v = edge.get(1) - 1;
            var weight = edge.get(2);
            adjacency.get(u).add(new Node(v, weight));
            adjacency.get(v).add(new Node(u, weight));
        }
        var distances = new int[n];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[--s] = 0;
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(u -> u.weight));
        priorityQueue.add(new Node(s, 0));
        while (!priorityQueue.isEmpty()) {
            var current = priorityQueue.poll();
            var node = current.node;
            for (var i : adjacency.get(node)) {
                var distance = distances[node] + i.weight;
                if (distance < distances[i.node]) {
                    distances[i.node] = distance;
                    priorityQueue.add(new Node(i.node, distance));
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        for (var i = 0; i < n; i++) {
            if (i != s) {
                var distance = distances[i];
                if (distance != Integer.MAX_VALUE) {
                    result.add(distance);
                } else {
                    result.add(-1);
                }
            }
        }
        return result;
    }

    private record Node(int node, int weight) { }

}
