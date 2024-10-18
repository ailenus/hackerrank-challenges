package mao.yannan.bfsshortestreachingraph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Solution {

    private static int[] distances;

    public static void main(String[] args) {
        try (var reader = new BufferedReader(new InputStreamReader(System.in))) {
            var q = Integer.parseInt(reader.readLine());
            for (var i = 0; i < q; i++) {
                var graphTokens = reader.readLine().split("\\s+");
                var n = Integer.parseInt(graphTokens[0]);
                var m = Integer.parseInt(graphTokens[1]);
                distances = new int[n];
                List<List<Integer>> adjacent = new ArrayList<>(n);
                for (var j = 0; j < n; j++) {
                    distances[j] = -1;
                    adjacent.add(new ArrayList<>());
                }
                for (var j = 0; j < m; j++) {
                    var edgeTokens = reader.readLine().split("\\s+");
                    var u = Integer.parseInt(edgeTokens[0]) - 1;
                    var v = Integer.parseInt(edgeTokens[1]) - 1;
                    addEdge(adjacent, u, v);
                }
                var s = Integer.parseInt(reader.readLine()) - 1;
                searchBreadthFirst(adjacent, s);
                for (var j = 0; j < n; j++) {
                    if (j != s) {
                        System.out.print(distances[j] + " ");
                    }
                }
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void searchBreadthFirst(List<List<Integer>> adjacent, int s) {
        Queue<Integer> queue = new ArrayDeque<>();
        var visited = new boolean[adjacent.size()];
        visited[s] = true;
        distances[s] = 0;
        queue.add(s);
        while (!queue.isEmpty()) {
            var current = queue.poll();
            for (var i : adjacent.get(current)) {
                if (!visited[i]) {
                    visited[i] = true;
                    distances[i] = distances[current] + 6;
                    queue.add(i);
                }
            }
        }
    }

    private static void addEdge(List<List<Integer>> adjacent, int u, int v) {
        adjacent.get(u).add(v);
        adjacent.get(v).add(u);
    }

}
