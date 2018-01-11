package com.sun.demo.leetCode;

public class App5 {
    private volatile int a = 0;
    private volatile boolean b = false;
    private void read(){a=1; b=true;}
    private void write(){
        if (b){
            System.out.print(a*a);
            a=2;
            b=false;
        }
    }

    public static void main(String[] args) {
        final App5 app = new App5();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    app.read();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    app.write();
                }
            }
        }).start();
    }
}
