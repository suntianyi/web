package com.sun.demo.pack1;

import org.junit.Test;

public class Sort {
    private static int[] arr = {3, 5, 7, 9, 4, 10, 2, 8, 23, 12, 36, 27, 15, 1};

    //冒泡排序，比较相邻元素，交换。效率 O（n²），适用于排序小列表。
    @Test
    public void sort1() {
        System.out.print("第0次：  ");
        for (int l : arr) {
            System.out.print(l + "  ");
        }
        System.out.println();
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - 1; j++) {
                if (arr[j + 1] < arr[j]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
            System.out.print("第" + (i + 1) + "次：  ");
            for (int l : arr) {
                System.out.print(l + "  ");
            }
            System.out.println();
        }

    }

    //插入排序。若列表基本有序，则插入排序比冒泡、选择更有效率。假设第一项为一个有序的列表
    @Test
    public void sort3() {
        System.out.print("第0次：  ");
        for (int l : arr) {
            System.out.print(l + "  ");
        }
        System.out.println();
        for (int i = 1; i < arr.length; i++) {
            int temp = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > temp) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = temp;
            System.out.print("第" + (i + 1) + "次：  ");
            for (int l : arr) {
                System.out.print(l + "  ");
            }
            System.out.println();
        }
    }

    //选择排序，将最小的移到前面。效率O（n²），适用于排序小的列表。
    @Test
    public void sort4() {
        System.out.print("第0次：  ");
        for (int l : arr) {
            System.out.print(l + "  ");
        }
        System.out.println();
        for (int i = 0; i < arr.length - 1; i++) {
            int min_index = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[min_index]) min_index = j;
            }
            if (min_index != i) {
                int temp = arr[i];
                arr[i] = arr[min_index];
                arr[min_index] = temp;
            }
            System.out.print("第" + (i + 1) + "次：  ");
            for (int l : arr) {
                System.out.print(l + "  ");
            }
            System.out.println();
        }
    }

    //shell排序,间隔序列3n+1,插入排序的改进
    @Test
    public void sort5() {
        int group, i, j, temp;
        for (group = (arr.length - 1) / 3; group > 0; group = (group - 1) / 3) {
            for (i = group; i < arr.length; i++) {
                for (j = i - group; j >= 0; j -= group) {
                    if (arr[j] > arr[j + group]) {
                        temp = arr[j];
                        arr[j] = arr[j + group];
                        arr[j + group] = temp;
                    }
                }
                for (int l : arr) {
                    System.out.print(l + "  ");
                }
                System.out.println();
            }
        }
        for (int l : arr) {
            System.out.print(l + "  ");
        }
        System.out.println();
    }

}
