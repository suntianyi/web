package com.sun.demo.leetCode;

import java.util.ArrayList;

/**
 * 编写一个在1，2，…，9（顺序不能变）数字之间插入+或-或什么都不插入，使得计算结果总是100的程序
 */

public class App8 {
    private static int SUM = 100;
    private static int[] v = {1, 2, 3, 4, 5, 6, 7, 8, 9};

    private static ArrayList<String> add(int di, String si, ArrayList<String> br) {
        for (int i = 0; i < br.size(); i++) {
            br.set(i, di + si + br.get(i));
        }
        return br;
    }

    private static ArrayList<String> f(int sum, int number, int index) {
        int di = Math.abs(number % 10);
//        System.out.println("num="+number);
        if (index >= v.length) {
            if (sum == number) {
                ArrayList<String> result = new ArrayList<>();
                result.add(Integer.toString(di));
                return result;
            } else {
                return new ArrayList<>();
            }
        }
        ArrayList br1 = f(sum - number, v[index], index + 1);
        ArrayList br2 = f(sum - number, -v[index], index + 1);
        int Number = number >= 0 ? 10 * number + v[index] : 10 * number - v[index];
        ArrayList br3 = f(sum, Number, index + 1);
        ArrayList<String> results = new ArrayList<>();
        results.addAll(add(di, "+", br1));
        results.addAll(add(di, "-", br2));
        results.addAll(add(di, "", br3));
        return results;
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (String string : f(SUM, v[0], 1)) {
            System.out.println(string);
        }
        System.out.println("耗时：" + (System.currentTimeMillis() - start));

    }
}
