package com.weifufa.rabbitmq.two;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.weifufa.rabbitmq.utils.RabbitMqUtils;

/**
 * 一个工作线程（相当于之前消费者）
 */
public class Worker01 {
    //队列的名称
    public static final String QUEUE_NAME = "hello";

    //接收消息
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        //消息的接收
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("接收到的消息：" + new String(message.getBody()));
        };
        //消息被接收取消时，执行下面的内容
        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.println(consumerTag + "消息者取消消费接口回调逻辑");
        };
        System.out.println("C1等待接收消息....");
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
    }
}