package mao.yannan.findrunningmedian;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class Solution {

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
        int aCount = Integer.parseInt(bufferedReader.readLine().trim());
        List<Integer> a = IntStream.range(0, aCount).mapToObj(i -> {
                    try {
                        return bufferedReader.readLine().replaceAll("\\s+$", "");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(toList());
        List<Double> result = Result.runningMedian(a);
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

    private Result() { }

    /*
     * Complete the 'runningMedian' function below.
     *
     * The function is expected to return a DOUBLE_ARRAY.
     * The function accepts INTEGER_ARRAY a as parameter.
     */

    public static List<Double> runningMedian(List<Integer> a) {
        var size = a.size() / 2 + 1;
        var maxHeap = new int[size];
        var minHeap = new int[size];
        var maxSize = 0;
        var minSize = 0;
        var median = 0.0;
        List<Double> result = new ArrayList<>();
        for (var current : a) {
            if (current < median) {
                maxHeap[maxSize] = current;
                if (current > maxHeap[0]) {
                    swap(maxHeap, maxSize, 0);
                }
                maxSize++;
            } else {
                minHeap[minSize] = current;
                if (current < minHeap[0]) {
                    swap(minHeap, minSize, 0);
                }
                minSize++;
            }
            if (Math.abs(maxSize - minSize) > 1) {
                if (maxSize > minSize) {
                    swap(maxHeap, --maxSize, 0);
                    minHeap[minSize] = maxHeap[maxSize];
                    swap(minHeap, 0, minSize++);
                    buildMaxHeap(maxHeap, maxSize);
                } else {
                    swap(minHeap, --minSize, 0);
                    maxHeap[maxSize] = minHeap[minSize];
                    swap(maxHeap, 0, maxSize++);
                    buildMinHeap(minHeap, minSize);
                }
            }
            if (maxSize == minSize) {
                median = (maxHeap[0] + minHeap[0]) / 2.0;
            } else if (maxSize > minSize) {
                median = maxHeap[0];
            } else {
                median = minHeap[0];
            }
            result.add(median);
        }
        return result;
    }

    private static void buildMaxHeap(int[] heap, int size) {
        var depth = (size - 1) / 2;
        for (var i = depth; i >= 0; i--) {
            maxHeapify(heap, i, size);
        }
    }

    private static void buildMinHeap(int[] heap, int size) {
        var depth = (size - 1) / 2;
        for (var i = depth; i >= 0; i--) {
            minHeapify(heap, i, size);
        }
    }

    private static void maxHeapify(int[] heap, int i, int size) {
        var left = 2 * i + 1;
        var right = 2 * i + 2;
        var max = i;
        if (left < size && heap[left] > heap[max]) {
            max = left;
        }
        if (right < size && heap[right] > heap[max]) {
            max = right;
        }
        if (max != i) {
            swap(heap, i, max);
            maxHeapify(heap, max, size);
        }
    }

    private static void minHeapify(int[] heap, int i, int size) {
        var left = 2 * i + 1;
        var right = 2 * i + 2;
        var min = i;
        if (left < size && heap[left] < heap[min]) {
            min = left;
        }
        if (right < size && heap[right] < heap[min]) {
            min = right;
        }
        if (min != i) {
            swap(heap, i, min);
            minHeapify(heap, min, size);
        }
    }

    private static void swap(int[] heap, int i, int j) {
        if (i != j) {
            var temp = heap[i];
            heap[i] = heap[j];
            heap[j] = temp;
        }
    }

}
