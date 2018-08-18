package com.sun.demo.rabbitmq;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消息队列工具类  消费者
 *
 * @author ylyao
 * @date 2018-4-26
 */
public class MQConsumer implements Consumer {

    public Channel channel;

    private String queueName;

    private static final Log logger = LogFactory.getLog(MQConsumer.class);

    public MQConsumer(RabbitmqConnect rabbitmqConnect, String queueName) throws IOException, TimeoutException {
        this.queueName = queueName;
        //建立连接
        channel = rabbitmqConnect.getConnection().createChannel();
        channel.basicQos(1);
        channel.basicConsume(queueName, false, this);
    }

    public void close() throws IOException, TimeoutException {
        channel.close();
    }

    @Override
    public void handleConsumeOk(String consumerTag) {
        // TODO Auto-generated method stub

    }

    @Override
    public void handleCancelOk(String consumerTag) {
        // TODO Auto-generated method stub

    }

    @Override
    public void handleCancel(String consumerTag) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body, "UTF-8");
        logger.info("[" + queueName
                + "] received '" + envelope.getRoutingKey() + "':'" + message
                + "'");


        channel.basicAck(envelope.getDeliveryTag(), false);
    }

    @Override
    public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
        // TODO Auto-generated method stub

    }

    @Override
    public void handleRecoverOk(String consumerTag) {
        // TODO Auto-generated method stub

    }

}
