package com.weifufa.rabbitmq.eight_dead_message;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.impl.AMQBasicProperties;
import com.weifufa.rabbitmq.utils.RabbitMqUtils;

/**
 * 死信队列 之 生产者代码
 * */
public class Producer {
    //普通交换机的名称
    public static final String NORMAL_EXCHANGE = "normal_exchange";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        //死信消息 设置TTL时间 time to live 单位是ms 10000ms=10s
       /* AMQP.BasicProperties properties=new AMQP.BasicProperties()
                .builder().expiration("10000").build();*/

        for (int i = 1; i < 11; i++) {
            String message="info"+i;
           // channel.basicPublish(NORMAL_EXCHANGE,"zhangsan",properties,message.getBytes());
            channel.basicPublish(NORMAL_EXCHANGE,"zhangsan",null,message.getBytes());
        }
    }
}