package com.sun.demo.leetCode;

import java.util.HashSet;
import java.util.Set;

public class App10 {
    public static void main(String[] args) {
        App10 app = new App10();
        String s = "babad";
        System.out.println(app.longestPalindrome(s));
    }

    //最长不重复子字符串
    public int lengthOfLongestSubstring(String s) {
        int result = 0;
        String[] arr = s.split("");
        for (int i = 0; i < arr.length; i++){
            Set<String> set = new HashSet<>();
            for (int j = i; j>=0; j--){
                if (!set.add(arr[j])){
                    break;
                }
            }
            result = Math.max(set.size(), result);
        }
        return result;
    }

      // 更有效率
//    public int lengthOfLongestSubstring(String s) {
//        int n = s.length();
//        Set<Character> set = new HashSet<>();
//        int ans = 0, i = 0, j = 0;
//        while (i < n && j < n) {
//            // try to extend the range [i, j]
//            if (!set.contains(s.charAt(j))){
//                set.add(s.charAt(j++));
//                ans = Math.max(ans, j - i);
//            }
//            else {
//                set.remove(s.charAt(i++));
//            }
//        }
//        return ans;
//    }


    //最长回文子字符串
    public String longestPalindrome(String s) {
        String result = "";
        for (int i = 0; i < s.length(); i++){
            for (int j = i; j >= 0; j--){
                String temp = s.substring(j, i+1);
                if (isPalindrome(temp) && temp.length() > result.length()){
                    result = temp;
                }
            }
        }
        return result;
    }

    public boolean isPalindrome(String s){
        int begin = 0;
        int end = s.length() - 1;
        while(begin<end){
            if(s.charAt(begin++)!=s.charAt(end--)) return false;
        }
        return true;
    }


    //更有效率
//    public String longestPalindrome(String s) {
//        int n = s.length();
//        String res = null;
//        boolean[][] dp = new boolean[n][n];
//        for (int i = n - 1; i >= 0; i--) {
//            for (int j = i; j < n; j++) {
//                dp[i][j] = s.charAt(i) == s.charAt(j) && (j - i < 3 || dp[i + 1][j - 1]);
//
//                if (dp[i][j] && (res == null || j - i + 1 > res.length())) {
//                    res = s.substring(i, j + 1);
//                }
//            }
//        }
//        return res;
//    }
}
