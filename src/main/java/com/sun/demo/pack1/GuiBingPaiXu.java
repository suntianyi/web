package com.sun.demo.pack1;

/**
 * 归并排序
 *
 * @author 12989
 */

public class GuiBingPaiXu {
    public static void main(String[] args) {
//		int[] a = new int[]{1, 4, 5, 6, 9};
//		int[] b = new int[]{2, 3, 7, 8};
//		int[] c = merge(a, b);

        int[] nums = new int[]{8, 2, 4, 6, 9, 7, 10, 1, 5, 3};
        int[] c = mergesort(nums);
        for (int i : c) {
            System.out.print(i + " ");
        }
    }

    private static int[] mergesort(int[] nums) {
        if (nums.length > 1) {
            int m = nums.length / 2;
            int[] a = new int[m];
            int[] b = new int[nums.length - m];
            // 源数组，起始位置，目标数组，起始位置，长度
            System.arraycopy(nums, 0, a, 0, m);
            System.arraycopy(nums, m, b, 0, nums.length - m);
            return merge(mergesort(a), mergesort(b));
        } else {
            return nums;
        }

    }

    private static int[] merge(int[] a, int[] b) {
        int[] l = new int[a.length + b.length];
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < a.length && j < b.length) {
            if (a[i] < b[j]) {
                l[k] = a[i];
                i++;
            } else {
                l[k] = b[j];
                j++;
            }
            k++;
        }
        if (i == a.length) {
            for (int m = j; m < b.length; m++) {
                l[k] = b[m];
                k++;
            }
        } else {
            for (int m = i; m < a.length; m++) {
                l[k] = a[m];
                k++;
            }
        }
        return l;
    }
}
