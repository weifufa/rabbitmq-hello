package com.weifufa.rabbitmq.five_fanout_exchange;

import com.rabbitmq.client.Channel;
import com.weifufa.rabbitmq.utils.RabbitMqUtils;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class EmitLog {
    //交换机的名称
    public static final String EXCHANGE_NAME="logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        //channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        Scanner sc=new Scanner(System.in);
        while(sc.hasNext())
        {
            String message=sc.next();
            channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes(StandardCharsets.UTF_8));
            System.out.println("生产者发出消息:"+message);
        }
    }
}