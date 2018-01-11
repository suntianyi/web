package com.sun.demo.leetCode;

import java.util.LinkedList;

public class App4 {
    public static void main(String[] args) {
        Thread p1 = new Thread(new Producer());
        Thread p2 = new Thread(new Producer());
        Thread p3 = new Thread(new Producer());
        Thread c1 = new Thread(new Consumer());

        p1.start();
        c1.start();
    }
}


class Producer implements Runnable{

    @Override
    public void run() {
        while (true) {
            Box box = Box.getInstance();
            try {
                box.put();
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer implements Runnable{

    @Override
    public void run() {
        while(true){
            Box box = Box.getInstance();
            try {
                box.get();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Box{
    private static Box box;
    private final LinkedList<Object> list = new LinkedList<Object>();

    private Box(){
    }

    public static Box getInstance(){
        if (box == null){
            box = new Box();
        }
        return box;
    }

    public void put() throws InterruptedException {
        synchronized (list){
            if (list.size() < 5){
                list.add(new Object());
                System.out.println("放入一个苹果");
                list.notifyAll();
            }else{
                System.out.println("仓库满了，等待消费。。。");
                list.wait();
            }
        }

    }

    public void get() throws InterruptedException {
        synchronized (list){
            if (list.size() > 0){
                list.remove();
                System.out.println("取出一个苹果");
                list.notifyAll();
            }else{
                System.out.println("仓库空了，等待生产。。。");
                list.wait();
            }
        }
    }
}