package com.sun.demo.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

/**
 * @auther zhsun5@iflytek.com
 * @date 2017/12/29
 */
public class App {
    public static void main(String[] args) {
        ZooKeeper zkClient = new ZookeeperClient().zkClient();
        try {
//            String result = zkClient.create("/zktest", "admin".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//            zkClient.setData("/zktest", "aaa".getBytes(), -1);
//            zkClient.delete("/zktest", -1);
            String result = new String(zkClient.getData("/zktest", false, null));

            System.out.println(result);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
