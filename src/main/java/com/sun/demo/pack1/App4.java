package com.sun.demo.pack1;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

public class App4 {
    public static void main(String[] args) {
        Random random = new Random();
        long[] array = new long[16];
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextLong()%100;
        }

        System.out.println(Arrays.toString(array));

        ForkJoinTask sort = new SortTask(array);
        ForkJoinPool fjpool = new ForkJoinPool();
        fjpool.submit(sort);
        fjpool.shutdown();
        try {
            fjpool.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Arrays.toString(array));
    }
}

class SortTask extends RecursiveAction {
    private long[] array;
    private int start;
    private int end;
    private int THRESHOLD = 5;

    //构造函数
    public SortTask(long[] array) {
        this.array = array;
        this.start = 0;
        this.end = array.length - 1;
    }

    public SortTask(long[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if (end - start < THRESHOLD) {
            Arrays.sort(array, start, end + 1);
        } else {
            int pivot = partition(array, start, end);
            System.out.println("pivot = " + pivot + ", start = " + start + ", end = " + end);
            System.out.println("array" + Arrays.toString(array));
            invokeAll(new SortTask(array, start, pivot - 1), new SortTask(array, pivot + 1, end));
        }
    }

    //任务分割
    private int partition(long[] array, int lo, int hi) {
        long x = array[hi];
        int i = lo - 1;
        for (int j = lo; j < hi; j++) {
            if (array[j] <= x) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, hi);
        return i + 1;
    }

    //数值交换
    private void swap(long[] array, int i, int j) {
        if (i != j) {
            long tmp = array[i];
            array[i] = array[j];
            array[j] = tmp;
        }
    }
}
