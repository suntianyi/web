package com.sun.demo.leetCode;

/**
 * 不用加号实现加法运算
 *
 * @author 12989
 */

public class App7 {
    public static void main(String[] args) {
        int a = 7;
        int b = 500;
        System.out.println(getSum(a, b));
        System.out.println(negate(b));
    }

    public static int getSum(int a, int b) {
        if (a == 0) return b;
        if (b == 0) return a;

        while (b != 0) {
            int carry = a & b;
            a = a ^ b;
            b = carry << 1;
        }
        return a;
    }

    // Iterative
    public static int getSubtract(int a, int b) {
        while (b != 0) {
            int borrow = (~a) & b;
            a = a ^ b;
            b = borrow << 1;
        }

        return a;
    }

    // Recursive
    public static int getSum2(int a, int b) {
        return (b == 0) ? a : getSum(a ^ b, (a & b) << 1);
    }

    // Recursive
    public static int getSubtract2(int a, int b) {
        return (b == 0) ? a : getSubtract(a ^ b, (~a & b) << 1);
    }

    // Get negative number
    public static int negate(int x) {
        return ~x + 1;
    }

}
