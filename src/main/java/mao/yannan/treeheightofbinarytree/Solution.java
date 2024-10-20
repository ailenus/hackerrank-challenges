package mao.yannan.treeheightofbinarytree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Solution {

    public static void main(String[] args) {
        try (var reader = new BufferedReader(new InputStreamReader(System.in))) {
            var n = Integer.parseInt(reader.readLine());
            var tokens = reader.readLine().split("\\s+");
            var data = Arrays.stream(tokens).mapToInt(Integer::parseInt).toArray();
            var tree = new BinaryTree();
            for (var datum : data) {
                tree.insert(datum);
            }
            System.out.println(tree.height());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class BinaryTree {

        private Node root;

        private void insert(int datum) {
            root = insertHelper(root, datum);
        }

        private int height() {
            return heightHelper(root) - 1;
        }

        private Node insertHelper(Node root, int datum) {
            if (root == null) {
                root = new Node(datum);
                return root;
            }
            if (datum < root.datum) {
                root.left = insertHelper(root.left, datum);
            } else if (datum > root.datum) {
                root.right = insertHelper(root.right, datum);
            }
            return root;
        }

        private int heightHelper(Node root) {
            if (root == null) {
                return 0;
            }
            var leftHeight = heightHelper(root.left);
            var rightHeight = heightHelper(root.right);
            return Math.max(leftHeight, rightHeight) + 1;
        }

    }

    private static class Node {

        private final int datum;
        private Node left;
        private Node right;

        private Node(int datum) {
            this.datum = datum;
        }

    }

}
