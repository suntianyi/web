package com.sun.demo.zookeeper;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @auther zhsun5@iflytek.com
 * @date 2017/12/29
 */
//@Configuration
public class ZookeeperClient {
    private final CountDownLatch connectedSignal = new CountDownLatch(1);

    @Value("${spring.zookeeper.host}")
    private String host = "172.31.6.48:2181";
    @Value("${spring.zookeeper.timeout}")
    private String timeout = "5000";

    @Bean
    public ZooKeeper zkClient() {

        ZooKeeper zkClient = null;
        try {
            zkClient = new ZooKeeper(host,Integer.valueOf(timeout), we -> {

                if (we.getState() == Watcher.Event.KeeperState.SyncConnected) {
                    connectedSignal.countDown();
                }
            });
            connectedSignal.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zkClient;
    }
}
