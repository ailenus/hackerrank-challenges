package mao.yannan.swapnodesalgo;

import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class Solution {

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<List<Integer>> indexes = new ArrayList<>();

        IntStream.range(0, n).forEach(i -> {
            try {
                indexes.add(
                        Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                .map(Integer::parseInt)
                                .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        int queriesCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> queries = IntStream.range(0, queriesCount).mapToObj(i -> {
                    try {
                        return bufferedReader.readLine().replaceAll("\\s+$", "");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(toList());

        List<List<Integer>> result = Result.swapNodes(indexes, queries);

        result.stream()
                .map(
                        r -> r.stream()
                                .map(Object::toString)
                                .collect(joining(" "))
                )
                .map(r -> r + "\n")
                .collect(toList())
                .forEach(e -> {
                    try {
                        bufferedWriter.write(e);
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
     * Complete the 'swapNodes' function below.
     *
     * The function is expected to return a 2D_INTEGER_ARRAY.
     * The function accepts following parameters:
     *  1. 2D_INTEGER_ARRAY indexes
     *  2. INTEGER_ARRAY queries
     */

    public static List<List<Integer>> swapNodes(List<List<Integer>> indexes, List<Integer> queries) {
        var root = new Node(1);
        root.level = 1;
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(root);
        for (var index : indexes) {
            var current = queue.remove();
            var leftValue = index.get(0);
            if (leftValue != -1) {
                current.left = new Node(leftValue);
                queue.add(current.left);
            }
            var rightValue = index.get(1);
            if (rightValue != -1) {
                current.right = new Node(rightValue);
                queue.add(current.right);
            }
        }
        List<List<Integer>> results = new ArrayList<>();
        for (var query : queries) {
            List<Integer> result = new ArrayList<>();
            traverseInOrderSwap(root, query, result);
            results.add(result);
        }
        return results;
    }

    private static void traverseInOrderSwap(Node node, int k, List<Integer> result) {
        var isSwapLevel = node.level % k == 0;
        if (isSwapLevel) {
            var temp = node.left;
            node.left = node.right;
            node.right = temp;
        }
        if (node.left != null) {
            node.left.level = node.level + 1;
            traverseInOrderSwap(node.left, k, result);
        }
        result.add(node.value);
        if (node.right != null) {
            node.right.level = node.level + 1;
            traverseInOrderSwap(node.right, k, result);
        }
    }

    private static class Node {

        private final int value;
        private int level;
        private Node left;
        private Node right;

        private Node(int value) {
            this.value = value;
        }

    }

}
