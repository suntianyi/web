package com.sun.demo.ssh;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * SSH远程登陆执行命令
 * @author zhsun5@iflytek.com
 */
public class App {
    public static void main(String[] args) {
        try {
            SSHUtils.DestHost host = new SSHUtils.DestHost("172.31.6.48", "root", "iflyrec!");
            List<String> commands = Lists.newArrayList();
            commands.add("cd /data/temp\n");
            commands.add("mkdir aaa\n");
            commands.add("ls\n");
            SSHUtils.execCommandByJSch(host, commands);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
