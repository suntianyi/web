package com.sun.demo.leetCode;

import java.util.*;

/**
 * Created by 12989 on 2017/3/22.
 */
public class App1 {
    public static void main(String[] args) {
        App1 app = new App1();
        System.out.println(app.toBase7(23));
//        int[] nums = {1, 2, 3, 4, 1, 1, 1, 1};
//        app.majorityElement(nums);
//
//        String str = "ccccdd";
//        int result = app.longestPalindrome(str);
//        System.out.println(result);

        int[] nums = {9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
        app.plusOne(nums);
    }

    /**
     * Given an integer, return its base 7 string representation.
     *
     * @param num
     * @return
     */
    public String toBase7(int num) {
        boolean flag = true;
        if (num < 0) {
            num = Math.abs(num);
            flag = false;
        } else if (num == 0) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        while (num >= 1) {
            sb.append((byte) (num % 7));
            num = num / 7;
        }
        if (!flag) {
            sb.append("-");
        }
        return sb.reverse().toString();
    }

    /**
     * Given an array of size n, find the majority element. The majority element is the element that appears more than ⌊ n/2 ⌋ times.
     *
     * @param nums
     * @return
     */
    public int majorityElement(int[] nums) {
        Map<Integer, Integer> map = new HashMap();
        for (int i = 0; i < nums.length; i++) {
            Integer count = map.get(nums[i]);
            if (count == null) {
                map.put(nums[i], 1);
            } else {
                map.put(nums[i], count + 1);
            }
        }
        Iterator<Map.Entry<Integer, Integer>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Integer> entry = iterator.next();
            if (entry.getValue() >= nums.length / 2 + 1)
                return entry.getKey();
        }
        return 0;
    }

    /**
     * Given a string which consists of lowercase or uppercase letters, find the length of the longest palindromes that can be built with those letters.
     *
     * @param s
     * @return
     */
    public int longestPalindrome(String s) {
        if (s == null || s.length() == 0) return 0;
        int freq[] = new int[128];
        for (int i = 0; i < s.length(); i++) {
            freq[s.charAt(i)]++;
        }
        int length = 0;
        for (int i = 0; i < freq.length; i++) {
            if (freq[i] >= 2) {
                length += freq[i] / 2 * 2;
            }
        }
        for (int i = 0; i < freq.length; i++) {
            if (freq[i] % 2 == 1) {
                length += 1;
                break;
            }
        }
        return length;
    }

    /**
     * Given two arrays, write a function to compute their intersection.
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public int[] intersect(int[] nums1, int[] nums2) {
        List<Integer> list = new ArrayList<Integer>();
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums1.length; i++) {
            Integer count = map.get(nums1[i]);
            if (count == null) {
                map.put(nums1[i], 1);
            } else {
                map.put(nums1[i], count + 1);
            }
        }
        for (int i = 0; i < nums2.length; i++) {
            if (map.containsKey(nums2[i]) && map.get(nums2[i]) != 0) {
                list.add(nums2[i]);
                map.put(nums2[i], map.get(nums2[i]) - 1);
            }
        }
        int[] result = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    /**
     * Given an array containing n distinct numbers taken from 0, 1, 2, ..., n, find the one that is missing from the array.
     *
     * @param nums
     * @return
     */
    public int missingNumber(int[] nums) {
        int sum1 = nums.length * (nums.length + 1) / 2;
        int sum2 = 0;
        for (int i = 0; i < nums.length; i++) {
            sum2 += nums[i];
        }
        return sum1 - sum2;
    }

    /**
     * Given two non-negative integers num1 and num2 represented as string, return the sum of num1 and num2.
     *
     * @param num1
     * @param num2
     * @return
     */
    public String addStrings(String num1, String num2) {
        int i = num1.length() - 1;
        int j = num2.length() - 1;
        int carry = 0;
        char[] num1Array = num1.toCharArray();
        char[] num2Array = num2.toCharArray();
        StringBuilder sb = new StringBuilder();
        while (i >= 0 || j >= 0 || carry == 1) {
            int a = i >= 0 ? (num1Array[i--] - '0') : 0;
            int b = j >= 0 ? (num2Array[j--] - '0') : 0;
            int sum = a + b + carry;
            sb.insert(0, sum % 10);
            carry = sum / 10;
        }
        return sb.toString();
    }


    char[] map = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public String toHex(int num) {
        if (num == 0) return "0";
        String result = "";
        while (num != 0) {
            result = map[(num & 15)] + result;
            num = (num >>> 4);
        }
        return result;
    }


    //将一个整数转成二进制数组
    public byte[] toBinary(int x) {
        byte[] s = new byte[32];
        for (int i = 31; x >= 1; i--) {
            if (x % 2 == 1) {
                s[i] = 1;
            }
            x = x / 2;
        }
        return s;
    }

    public String reverseVowels(String s) {
        char[] arr = s.toCharArray();
        int point = arr.length - 1;
        for (int i = 0; i < arr.length; i++) {
            if (isVowels(arr[i])) {
                for (int j = point; j > i; j--) {
                    if (isVowels(arr[j])) {
                        char c = arr[i];
                        arr[i] = arr[j];
                        arr[j] = c;
                        point = j - 1;
                        break;
                    }
                }
            }
        }
        return new String(arr);
    }


    public boolean isVowels(char c) {
        if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u'
                || c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U')
            return true;
        return false;
    }


    /**
     * 找出n个数中没有出现的数
     */
    public void findMissing() {
        int[] nums = {4, 3, 2, 7, 8, 2, 3, 1, 5};
        List<Integer> res = new ArrayList<Integer>();
        int n = nums.length;
        for (int i = 0; i < nums.length; i++) nums[(nums[i] - 1) % n] += n;
        for (int num : nums) {
            System.out.println(num);
        }
        for (int i = 0; i < nums.length; i++) if (nums[i] <= n) res.add(i + 1);
        System.out.println("==============");
        for (Integer integer : res) {
            System.out.println(integer);
        }
    }

    /**
     * 判断一个数是否满足：它的除它本身以外的所有约数加起来正好等于它
     *
     * @param num
     * @return
     */
    public boolean checkPerfectNumber(int num) {
        if (num <= 0) return false;
        int sum = 0;
        for (int i = 0; i * i < num; i++) {
            if (num % i == 0) {
                sum += i;
                if (i * i != num) {
                    sum += num / i;
                }
            }
        }
        return num == sum - num;
    }

    /**
     * 找出字符串中第一个不重复的字母的位置
     *
     * @param s
     * @return
     */
    public int firstUniqChar(String s) {
        int freq[] = new int[26];
        for (int i = 0; i < s.length(); i++)
            freq[s.charAt(i) - 'a']++;
        for (int i = 0; i < s.length(); i++)
            if (freq[s.charAt(i) - 'a'] == 1)
                return i;
        return -1;
    }

    public int[] plusOne(int[] digits) {
        if (digits[digits.length - 1] == 9) {
            digits[digits.length - 1] = 0;
            for (int i = digits.length - 2; i >= 0; i--) {
                if (digits[i] == 9) {
                    digits[i] = 0;
                } else {
                    digits[i] += 1;
                    break;
                }
            }
        } else {
            digits[digits.length - 1] += 1;
        }
        if (digits[0] == 0) {
            int[] result = new int[digits.length + 1];
            result[0] = 1;
            for (int i = 0; i < digits.length; i++) {
                result[i + 1] = digits[i];
            }
            return result;
        }
        return digits;
    }


    /**
     * 计算各个位数之和，直至只有一位
     * @param num
     * @return
     */
    public static int addDigits(int num) {
        String[] strs = String.valueOf(num).split("");
        int[] nums = new int[strs.length];
        for (int i = 0; i < strs.length; i++) {
            nums[i] = Integer.valueOf(strs[i]);
        }
        if(nums.length == 1){
            return nums[0];
        }
        int s = 0;
        for (int i = 0; i < nums.length; i++) {
            s += nums[i];
        }
        return addDigits(s);
    }
}