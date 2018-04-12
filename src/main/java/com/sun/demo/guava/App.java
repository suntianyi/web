package com.sun.demo.guava;

import com.google.common.base.Functions;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author zhsun5@iflytek.com
 */
public class App {
    public static void main(String[] args) {
        int a = 1;
        Preconditions.checkArgument(a > 0, "Illegal Argument passed: Negative value %s.", a);

        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");
        System.out.println(Joiner.on(",").withKeyValueSeparator("=").join(map));

        List<String> list = Lists.newArrayList("moon", "dad", "refer", "son", "aaaaaa");
        Function<String, String> function = Functions.compose(input -> (input != null ? input.length() : 0) > 5 ? input.substring(0, 5) : input, String::toUpperCase);
        Collection<String> results = Collections2.transform(list, function::apply);
        System.out.println(results);

        Set<Integer> set1= Sets.newHashSet(1,2,3,4,5);
        Set<Integer> set2=Sets.newHashSet(3,4,5,6);
        Sets.SetView<Integer> inter=Sets.intersection(set1,set2); //交集
        System.out.println(inter);
        Sets.SetView<Integer> diff=Sets.difference(set1,set2); //差集,在A中不在B中
        System.out.println(diff);
        Sets.SetView<Integer> union=Sets.union(set1,set2); //并集
        System.out.println(union);


        Cache<String,String> cache= CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterAccess(1, TimeUnit.SECONDS)
                .build();
        cache.put("java","java");
        try {
            // 如果没取到，执行后面的方法
            String result=cache.get("java", () -> "hello java");
            System.out.println(result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
