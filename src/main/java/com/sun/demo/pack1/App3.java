package com.sun.demo.pack1;

import java.util.concurrent.*;

public class App3 {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();

        CountTask task = new CountTask(1, 10000);
        Future<Integer> result = pool.submit(task);
        try {
            System.out.println(result.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}

class CountTask extends RecursiveTask<Integer> {
    private static final int THRESHOLD = 100;

    private int start;
    private int end;

    public CountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        if ((end - start) <= THRESHOLD) {
            for (int i = start; i <= end; i++)
                sum += i;
        } else {
            //如果任务大于阀值，就分裂成两个子任务计算
            int mid = (start + end) / 2;
            System.out.println("拆分任务。。。");
            CountTask leftTask = new CountTask(start, mid);
            CountTask rightTask = new CountTask(mid+1, end);

            //执行子任务
            leftTask.fork();
            rightTask.fork();

            //等待子任务执行完，并得到结果
            int leftResult = (int)leftTask.join();
            int rightResult = (int)rightTask.join();

            sum = leftResult + rightResult;
        }
        return sum;
    }
}