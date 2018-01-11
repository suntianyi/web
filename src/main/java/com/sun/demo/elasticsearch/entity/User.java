package com.sun.demo.elasticsearch.entity;


import java.util.List;

/**
 * @auther zhsun5@iflytek.com
 * @date 2017/12/22
 */
public class User {
    private int id;
    private String name;
    private int age;
    private List<Account> accounts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
