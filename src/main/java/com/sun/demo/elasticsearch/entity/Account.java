package com.sun.demo.elasticsearch.entity;

/**
 * @auther zhsun5@iflytek.com
 * @date 2017/12/22
 */
public class Account {
    private int id;
    private String name;
    private float balance;

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

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}
