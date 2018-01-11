package com.sun.demo.pack1;

import java.util.HashMap;
import java.util.HashSet;

public class App5 {
    public static void main(String[] args) {
        int h;
        Object key = "name";
        int result = (h = key.hashCode()) ^ (h >>> 16);
        System.out.println(result);
    }
}
