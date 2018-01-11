package com.sun.demo.elasticsearch;

import com.sun.demo.elasticsearch.entity.Account;
import com.sun.demo.elasticsearch.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther zhsun5@iflytek.com
 * @date 2017/12/22
 */
public class App {
    public static void main(String[] args) {
        DefaultElasticSearchRepo repo = new DefaultElasticSearchRepo();
        repo.init("localhost", 9200);

//        User user = createUser();
//        String json = JSON.toJSONString(user);
//        repo.save("user", "user", "2", json);

//        String json = repo.get("user", "user", "1");
//        User user = JSON.parseObject(json, User.class);
//        System.out.println(user.getName());
        repo.search("user", "user");
        repo.close();
    }

    public static User createUser(){
        Account account = new Account();
        account.setId(1);
        account.setName("招商银行");
        account.setBalance(5000);
        List<Account> accounts = new ArrayList<>(0);
        accounts.add(account);
        User user = new User();
        user.setId(2);
        user.setName("小明");
        user.setAge(20);
        user.setAccounts(accounts);
        return user;
    }
}
