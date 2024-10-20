package mao.yannan.contacts;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class Solution {

    public static void main(String[] args) throws IOException {
        var bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int queriesRows = Integer.parseInt(bufferedReader.readLine().trim());

        List<List<String>> queries = new ArrayList<>();

        IntStream.range(0, queriesRows).forEach(i -> {
            try {
                queries.add(
                        Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        List<Integer> result = Result.contacts(queries);

        bufferedWriter.write(
                result.stream()
                        .map(Object::toString)
                        .collect(joining("\n"))
                        + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }

}

class Result {

    private static final String ADD = "add";
    private static final String FIND = "find";

    private Result() { }

    /*
     * Complete the 'contacts' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts 2D_STRING_ARRAY queries as parameter.
     */

    public static List<Integer> contacts(List<List<String>> queries) {
        var trie = new Trie();
        List<Integer> result = new ArrayList<>();
        for (var query : queries) {
            var operation = query.get(0);
            var key = query.get(1);
            if (operation.equals(ADD)) {
                trie.insert(key);
            } else if (operation.equals(FIND)) {
                result.add(trie.countWord(key));
            }
        }
        return result;
    }

    private static class Trie {

        private final Node root;

        private Trie() {
            root = new Node();
        }

        private void insert(String key) {
            insertHelper(root, key);
        }

        private int countWord(String key) {
            return countWordHelper(root, key);
        }

        private void insertHelper(Node root, String key) {
            var current = root;
            for (var c : key.toCharArray()) {
                if (!current.children.containsKey(c)) {
                    var newNode = new Node();
                    current.children.put(c, newNode);
                }
                current.wordCount++;
                current = current.children.get(c);
            }
            current.wordCount++;
        }

        private int countWordHelper(Node root, String key) {
            var current = root;
            for (var c : key.toCharArray()) {
                if (!current.children.containsKey(c)) {
                    return 0;
                }
                current = current.children.get(c);
            }
            return current.wordCount;
        }

        private static class Node {

            private final Map<Character, Node> children;
            private int wordCount;

            private Node() {
                children = new HashMap<>();
            }

        }

    }

}
