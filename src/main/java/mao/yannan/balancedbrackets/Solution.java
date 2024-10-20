package mao.yannan.balancedbrackets;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.IntStream;

public class Solution {

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int t = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, t).forEach(tItr -> {
            try {
                String s = bufferedReader.readLine();

                String result = Result.isBalanced(s);

                bufferedWriter.write(result);
                bufferedWriter.newLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }

}

class Result {

    private static final String YES = "YES";
    private static final String NO = "NO";

    private Result() { }

    /*
     * Complete the 'isBalanced' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts STRING s as parameter.
     */

    public static String isBalanced(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        for (var i = 0; i < s.length(); i++) {
            var current = s.charAt(i);
            if (current == '(' || current == '[' || current == '{') {
                stack.push(current);
            } else {
                if (!stack.isEmpty() && (
                        (stack.peek() == '(' && current == ')') || (stack.peek() == '[' && current == ']') || (stack.peek() == '{' && current == '}')
                )) {
                    stack.pop();
                } else {
                    return NO;
                }
            }
        }
        return stack.isEmpty() ? YES : NO;
    }

}
