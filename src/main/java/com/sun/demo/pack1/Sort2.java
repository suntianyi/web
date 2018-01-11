package com.sun.demo.pack1;

import java.util.Arrays;

public class Sort2 {
    public static void main(String[] args) {
        int[] arr = new int[]{3, 4, 7, 2, 9, 1, 6, 5, 8};
        int[] result = sort(arr);
        System.out.println(Arrays.toString(result));

        System.out.println("========================================");

        int[] arr2 = {13, -3, -25, 20, -3, -16, -23, 18, 20, -7, 12, -5, -22, 15, -4, 7};
        int[] nums = maxSubArr(arr2);
        System.out.println(Arrays.toString(nums));

        System.out.println("========================================");

        int a[] = {51, 46, 20, 18, 65, 97, 82, 30, 77, 50};
        heapSort(a);
        System.out.println(Arrays.toString(a));

        System.out.println("========================================");

        quickSort(a, 0, a.length - 1);
        System.out.println(Arrays.toString(a));

        System.out.println("========================================");

        int[] b = countingSort(a, 100);
        System.out.println(Arrays.toString(b));
    }

    //插入递归
    private static int[] sort(int[] nums) {
        if (nums.length == 1) {
            return nums;
        } else {
            int[] arr = new int[nums.length - 1];
            System.arraycopy(nums, 0, arr, 0, nums.length - 1);
            return merge(sort(arr), nums[nums.length - 1]);
        }
    }

    private static int[] merge(int[] nums, int n) {
        int[] result = new int[nums.length + 1];
        System.arraycopy(nums, 0, result, 0, nums.length);
        int j = result.length - 2;
        while (j >= 0 && n < result[j]) {
            result[j + 1] = result[j];
            j--;
        }
        result[j + 1] = n;
        return result;
    }

    //求最大子数组
    private static int[] maxSubArr(int[] arr) {
        int sum = arr[0], left = 0, right = 0;
        for (int i = 1; i < arr.length; i++) {
            int temp = 0;
            for (int j = i; j >= 0; j--) {
                temp += arr[j];
                if (temp > sum) {
                    sum = temp;
                    left = j;
                    right = i;
                }
            }
        }
        int[] result = new int[right - left + 1];
        System.arraycopy(arr, left, result, 0, right - left + 1);
        return result;
    }


    // 堆排序
    private static void heapSort(int[] nums) {
        for (int i = nums.length / 2; i >= 0; i--) {
            buildMaxHeap(nums, i, nums.length);
        }
        for (int i = nums.length - 1; i >= 1; i--) {
            int temp = nums[0];
            nums[0] = nums[i];
            nums[i] = temp;
            buildMaxHeap(nums, 0, i);
        }
    }

    private static void buildMaxHeap(int[] nums, int index, int heapSize) {
        int left = index * 2 + 1;
        int right = index * 2 + 2;
        int largest = index;
        if (left < heapSize && nums[left] > nums[index]) {
            largest = left;
        }

        if (right < heapSize && nums[right] > nums[largest]) {
            largest = right;
        }

        if (index != largest) {
            int temp = nums[index];
            nums[index] = nums[largest];
            nums[largest] = temp;
            buildMaxHeap(nums, largest, heapSize);
        }
    }


    // 快速排序
    private static void quickSort(int[] nums, int begin, int end) {
        if (begin < end) {
            int base = nums[end];
            int i = begin - 1;
            for (int j = begin; j < end; j++) {
                if (nums[j] <= base) {
                    i++;
                    int temp = nums[j];
                    nums[j] = nums[i];
                    nums[i] = temp;
                }
            }
            int temp = nums[end];
            nums[end] = nums[i + 1];
            nums[i + 1] = temp;

            quickSort(nums, 0, i);
            quickSort(nums, i + 2, end);
        }
    }


    // 基数排序
    private static int[] countingSort(int[] nums, int k) {
        int[] c = new int[k];
        int[] result = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            c[nums[i]] += 1;
        }
        for (int i = 1; i < k; i++) {
            c[i] += c[i - 1];
        }
        for (int i = 0; i < nums.length; i++) {
//        for (int i = nums.length - 1; i >= 0 ; i--) {
            c[nums[i]] -= 1;
            result[c[nums[i]]] = nums[i];
        }
        return result;
    }
}

