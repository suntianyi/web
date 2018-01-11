package com.sun.demo.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @auther zhsun5@iflytek.com
 * @date 2017/12/29
 */
//@Component
public class ZkBaseDao {

    @Value("spring.zookeeper.prefixPath")
    private String prefixPath;

    @Autowired
    private ZooKeeper zkClient;

    public String create(String node, String data){
        String path = prefixPath + node;
        String result = "";
        try {
            zkClient.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void update(String node, String data){
        String path = prefixPath + node;
        try {
            zkClient.setData(path, data.getBytes(), -1);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getData(String node){
        String path = prefixPath + node;
        try {
            return new String(zkClient.getData(path, false, null));
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(String node){
        String path = prefixPath + node;
        try {
            zkClient.delete(path, -1);
        } catch (InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
    }
}
