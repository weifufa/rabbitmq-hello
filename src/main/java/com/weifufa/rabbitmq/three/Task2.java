package com.weifufa.rabbitmq.three;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.weifufa.rabbitmq.utils.RabbitMqUtils;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * 消息在手动应答时是不丢失、放回队列中重新消费
 * */
public class Task2 {
    //队列名称
    public static final String TASK_QUEUE_NAME="ack_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();//产生信道
        //开启发布确认
        channel.confirmSelect();
        //声明队列
        boolean durable=true;//需要让queue进行持久化
        channel.queueDeclare(TASK_QUEUE_NAME,durable,false,false,null);
        //从控制台中输入
        Scanner sc=new Scanner(System.in);
        while (sc.hasNext())
        {
            String messgae = sc.next();
            //设置生产者发送消息为持久化消息（要求保存到磁盘上）保存在内存中
            //durable为true的前提下
            channel.basicPublish("",TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,messgae.getBytes(StandardCharsets.UTF_8));
            System.out.println("生产者发出消息："+messgae);
        }
    }
}