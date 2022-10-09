package com.weifufa.rabbitmq.six_direct_exchange;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.weifufa.rabbitmq.utils.RabbitMqUtils;

import java.nio.charset.StandardCharsets;

/**
 * 直接交换机
 * */
public class ReceiveLogsDirect02 {
    public static final String EXCHANGE_NAME="direct_logs";
    public static void main(String[] args) throws Exception {
        Channel channel= RabbitMqUtils.getChannel();
        //声明一个直接交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        //声明一个队列
        channel.queueDeclare("disk",false,false,false,null);
        channel.queueBind("disk",EXCHANGE_NAME,"error");

        //接收消息
        DeliverCallback deliverCallback=(consumerTag, message)->{
            System.out.println("ReceiveLogsDirect02控制台打印接收到的消息："+new String(message.getBody(), StandardCharsets.UTF_8));
        };
        //消费者取消消息回调接口
        channel.basicConsume("disk",true,deliverCallback, consumerTag -> {});
    }
}