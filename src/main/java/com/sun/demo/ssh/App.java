package com.sun.demo.ssh;

/**
 * SSH远程登陆执行命令
 * @author zhsun5@iflytek.com
 */
public class App {
    public static void main(String[] args) {
        try {
            SSHUtils.DestHost host = new SSHUtils.DestHost("172.31.6.77", "root", "iflyrec!");
            String stdout;
            stdout = SSHUtils.execCommandByJSch(host, "cd /iflytek/hiseeNL && ./start.sh");
            System.out.println(stdout);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
