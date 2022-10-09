package com.weifufa.rabbitmq.seven_topics_exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.weifufa.rabbitmq.utils.RabbitMqUtils;

import java.nio.charset.StandardCharsets;

/**
 * 声明主题交换机，及相关队列
 * 消费者C1
 * */
public class ReceiveLogsTopic02 {
    //交换机的名称
    public static final String EXCHANGE_NAME="topic_logs";
    public static void main(String[] args) throws Exception {
     //接收消息
        Channel channel = RabbitMqUtils.getChannel();
        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"topic");
        //声明队列
        String queueName="Q2";
        channel.queueDeclare(queueName,false,false,false,null);
        //绑定交换机与队列的关系
        channel.queueBind(queueName,EXCHANGE_NAME,"*.*.rabbit");
        channel.queueBind(queueName,EXCHANGE_NAME,"lazy.#");
        System.out.println("等待接收消息.....");
        DeliverCallback deliverCallback=(consumerTag, message)->{
            System.out.println(new String(message.getBody(), StandardCharsets.UTF_8));
            System.out.println("接收队列："+queueName+"绑定键:"+message.getEnvelope().getRoutingKey());
        };
        //接收消息
        channel.basicConsume(queueName,true,deliverCallback,consumerTag->{});
    }
}