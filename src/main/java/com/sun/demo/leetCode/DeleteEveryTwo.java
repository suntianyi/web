package com.sun.demo.leetCode;

public class DeleteEveryTwo {

    public static int getLastDeletedIndex(int len) {
        if (len < 0) {
            return -1;
        }
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = i;
        }

        final int DELFLAG = len + 1; // 删除标志位
        int currentSize = len; // 记录数组当前有效长度（即未被置为删除标志的元素个数），最后变为 0
        final int STEP = 2; // 步长
        int count = 0; // 步长计数
        int lastDelIndex = 0; // 记录最后被删除的元素的下标
        int i = 0; // 循环下标

        while (currentSize != 0) {
            if (arr[i] != DELFLAG) { // 判读当前元素是否等于删除标志
                if (count++ == STEP) { // 当步长计数满足步长则
                    arr[i] = DELFLAG; // 将元素置为删除标志位
                    lastDelIndex = i; // 记录该处下标
                    currentSize--; // 有效数组长度减 1
                    count = 0; // 步长计数归零
                    System.out.println("Deleted index is " + i % len);
                }
            }
            i = (i + 1) % len; // 下标取余实现循环下标
        }
        return lastDelIndex;
    }
}
