package com.sun.demo.leetCode;

import java.util.Scanner;

public class App3 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        String str = "abcdaa";
        System.out.println(getMaxRoundString(str));

        String s = "FIuNrJGoGqnPTnuMuUQCpSXwCWFdWljPPcAteMPbkxoCBiPcabpssTedsrfgycuiBuWXZTakaJqKEjxJNzUTIRbhgvFVKpwJjoYREJfBlLTgO";
        System.out.println(moveUpper(s));
        int[] arr = {45,12,45,32,5,6};
        max(arr);
    }

    public static int getMaxRoundString(String str) {
        int max = 1;
        if (str.length() < 2)
            return 0;
        for (int i = 0; i < str.length(); i++) {
            for (int j = i + 1; j < str.length(); j++) {
                if (str.charAt(i) == str.charAt(j)) {
                    int temp = 2 + getMaxRoundString(str.substring(i + 1, j));
                    max = temp > max ? temp : max;
                }
            }
        }
        return max;
    }


    public static String moveUpper(String str) {
        char[] arr = str.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] >= 'a' && arr[i] <= 'z') {
                int j = i;
                while (j - 1 >= 0 && arr[j - 1] >= 'A' && arr[j - 1] <= 'Z') {
                    char temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                    j--;
                }
            }
        }
        return new String(arr);
    }

    public static int max(int[] arr){
        int min = Integer.MAX_VALUE;
        int max = 0;
        int minCount = 0, maxCount = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                int s = Math.abs(arr[j] - arr[i]);
                if (s < min){
                    min = s;
                    minCount = 1;
                }else if (s == min){
                    minCount++;
                }
                if (s > max){
                    max = s;
                    maxCount = 1;
                }else if(s == max){
                    maxCount++;
                }
            }
        }
        System.out.println(minCount + "\t" + maxCount);
        return max;
    }
}
