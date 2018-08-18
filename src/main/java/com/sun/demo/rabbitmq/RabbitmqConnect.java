package com.sun.demo.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * RabbitMQ服务器连接及配置
 * on 2018/4/26.
 *
 * @author ylyao
 */
//@Component
public class RabbitmqConnect {

    private Connection connection = null;

    public Connection getConnection() throws IOException, TimeoutException {
        if (connection == null || !connection.isOpen()) {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setUsername("");
            factory.setPassword("");
            factory.setHost("");
            factory.setPort(0);
            connection = factory.newConnection();
        }
        return connection;
    }

    @PostConstruct
    public void initMq() throws IOException, TimeoutException {
        String mainExchangeName = "";
        String subTaskCreateQueueName = "";
        String subTaskCreateRoutingKey = "";

        final Channel channel = getConnection().createChannel();
        channel.exchangeDeclare(mainExchangeName, "topic", true, false, false, null);
        //二级任务异步创建队列初始化连接
        channel.queueDeclare(subTaskCreateQueueName, true, false, false, null);
        channel.queueBind(subTaskCreateQueueName, mainExchangeName, subTaskCreateRoutingKey);

        channel.basicQos(1);
    }

}
