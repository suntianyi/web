package com.sun.demo.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * 消息队列工具类  生产者
 *
 * @author ylyao
 * @date 2018-4-26
 */
//@Component
public class MQProducer {

    private static Logger logger = LoggerFactory.getLogger(MQProducer.class);
    @Autowired
    private RabbitmqConnect rabbitmq;

    /**
     * 向MQ发送消息
     *
     * @param queueName 队列名称
     * @param message   消息内容
     * @return boolean
     */
    public boolean sendMessage(String queueName, Map<String, Object> message) {
        Connection connection;
        Channel channel = null;
        try {
            String routingKey = queueName + ".MQ";
            connection = rabbitmq.getConnection();
            channel = connection.createChannel();
            channel.confirmSelect();
            channel.queueDeclare(queueName, true, false, false, null);
            channel.basicPublish("", routingKey,
                    MessageProperties.PERSISTENT_TEXT_PLAIN, JSONObject.toJSONString(message).getBytes());
            logger.info("send message to exchange, queueName " + queueName + ", key: "
                    + routingKey + ", content: " + message.toString());

        } catch (Exception e) {
            logger.error("channel error", e);
            return false;
        } finally {
            try {
                if (channel != null) {
                    channel.close();
                }
            } catch (IOException | TimeoutException e) {
                logger.error("发送消息出现异常", e);
            }
        }
        return true;
    }

}
