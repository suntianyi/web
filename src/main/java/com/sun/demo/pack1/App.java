package com.sun.demo.pack1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class App {
    // 正则表达式匹配
    @Test
    public void regexMatch() {
        String str = "<root><ResultCode>0</ResultCode></root>";
        Pattern p = Pattern.compile("<ResultCode>(.*?)</ResultCode>");
        Matcher m = p.matcher(str);
        if (m.find()) {
            System.out.println(m.group(1));
            System.out.println("匹配成功");
        } else {
            System.out.println("匹配失败");
        }
    }

    //转换为32位的二进制数
    @Test
    public void toBinary() {
        int x = 129;
        System.out.println(Integer.toBinaryString(x));
        byte[] s = new byte[32];
        for (int i = 31; x >= 1; i--) {
            if (x % 2 == 1) {
                s[i] = 1;
            }
            x = x / 2;
        }
        for (byte b : s) {
            System.out.print(b);
        }
    }

    // 字符串反转
    @Test
    public void reverse() {
        String str = "hello";
        byte[] b = str.getBytes();
        byte[] a = new byte[b.length];
        for (int i = 0; i < b.length; i++) {
            a[i] = b[b.length - 1 - i];
        }
        System.out.println(new String(a));
    }

    //0 ^ N = N; N ^ N = 0; N ^ M = N+M 或N-M
    @Test
    public void singleNumber() {
        int[] nums = {2, 2, 3, 4, 5, 2, 2, 5, 4};
        int result = 0;
        for (int i : nums) {
            result ^= i;
            System.out.println(result);
        }

    }

    //位操作 按位取反为反码，反码加1为补码
    @Test
    public void operate() {
        System.out.println(16 >>> 1);
        System.out.println(~-5);
        //乘以2
        System.out.println(5 << 1);
        //除以2
        System.out.println(100 >> 1);
        // 2的n次方
        int n = 5;
        System.out.println(1 << n);
    }

    // 将0移到最后
    @Test
    public void moveZero(){
        int[] nums = {0, 1, 0, 3, 12, 0, 4, 5, 20};
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] == 0){
                int index = i;
                for (int j = index+1; j < nums.length; j++) {
                    if(nums[j] != 0){
                        int temp = nums[index];
                        nums[i] = nums[j];
                        nums[j] = temp;
                        break;
                    }
                }
            }
        }
        for (int i = 0; i < nums.length; i++) {
            System.out.print(nums[i] + "  ");
        }
    }

    @Test
    public void test1(){
        int[] nums = {1, 3, 6, 10, 2, 5, 7, 15, 30, 24, 36, 17, 40, 27, 22, 35};
        int max = 0;
        for (int i = 0; i < nums.length-1; i++) {
            if(Math.abs(nums[i] - nums[i+1]) > max){
                max = Math.abs(nums[i] - nums[i+1]);
            }
        }
        System.out.println(max);
    }

    //实现二进制的加法
    @Test
    public void test2(){
        String a = "1111";
        String b = "1111";
        StringBuffer sb = new StringBuffer();
        if(a.length() > b.length()){
            for (int i = 0; i < a.length() - b.length(); i++) {
                sb.append("0");
            }
            sb.append(b);
            b = sb.toString();
        }else if(a.length() < b.length()){
            for (int i = 0; i < b.length() - a.length(); i++) {
                sb.append("0");
            }
            sb.append(a);
            a = sb.toString();
        }
        System.out.println(a + "\t" + b);
        String[] as = a.split("");
        String[] bs = b.split("");
        int[] s = new int[a.length()+1];
        for (int i = as.length-1; i >= 0; i--) {
            int sum = Integer.valueOf(as[i]) + Integer.valueOf(bs[i]);
            if(sum + s[i+1] >= 2){
                s[i+1] = (sum + s[i+1])%2;
                s[i] = 1;
            }else{
                s[i+1] = sum;
            }
        }
        for (int i = 0; i < s.length; i++) {
            System.out.print(s[i]);
        }

    }
}
