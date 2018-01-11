package com.sun.demo.pack1;

import java.util.*;

public class App2 {
    public static void main(String[] args) {
        App2 app = new App2();
        int[] array = {1, 2, 3, 4, 5, 6, 7};
        app.reOrderArray(array);


        int num = 325;
        int[] arrs = Arrays.stream(String.valueOf(num).split("")).mapToInt(Integer::parseInt).toArray();

        Integer[] arrs1 = (Integer[]) Arrays.stream(String.valueOf(num).split("")).toArray();
        System.out.println(Arrays.toString(arrs));


        List<Integer> list = new ArrayList<Integer>();
        list.sort((o1, o2) -> {
            String s1 = String.valueOf(o1);
            String s2 = String.valueOf(o2);
            return (s2 + s1).compareTo(s1 + s2);
        });

        Collections.sort(list, new Comparator<Integer>() {

            @Override
            public int compare(Integer o1, Integer o2) {
                String s1 = String.valueOf(o1);
                String s2 = String.valueOf(o2);
                return (s2 + s1).compareTo(s1 + s2);
            }
        });

    }

    /**
     * 将奇数放前面，偶数放后面
     * @param array
     */
    public void reOrderArray(int [] array) {
        if (array.length > 1) {
            for (int i = 0; i < array.length; i++){
                if (array[i] % 2 == 1) {
                    int n = i, num = array[i];
                    for (int j = i - 1; j >= 0; j--){
                        if (array[j] % 2 == 0){
                            array[n] = array[j];
                            array[j] = num;
                            n = j;
                        }
                    }
                }
            }
        }
    }

    public ArrayList<Integer> GetLeastNumbers_Solution(int [] input, int k) {
        ArrayList<Integer> list =  new ArrayList<>();
        Collections.reverse(list);
        if (input.length < k){
            return list;
        }
        for (int i = 0; i < k; i++){
            for (int j = input.length-1; j > i; j--){
                if (input[j] < input[j-1]){
                    int temp = input[j];
                    input[j] = input[j-1];
                    input[j-1] = temp;
                }
            }
            list.add(input[i]);
        }
        return list;
    }

    public int FindGreatestSumOfSubArray(int[] array) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < array.length; i++){
            int temp = 0;
            for (int j =i; j >=0; j--){
                temp += array[j];
                if (temp > max){
                    max = temp;
                }
            }
        }
        return max;
    }

}
