package com.weifufa.rabbitmq.eight_dead_message;

import com.rabbitmq.client.*;
import com.weifufa.rabbitmq.utils.RabbitMqUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 死信队列 实战
 * 消费者1
 */
public class Consume01 {

    //普通交换机的名称
    public static final String NORMAL_EXCHANGE = "normal_exchange";
    //死信交换机
    public static final String DEAD_EXCHANGE = "dead_exchange";
    //普通队列的名称
    public static final String NORMAL_QUEUE = "normal_queue";
    //死信队列的名称
    public static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        //声明死信和普通交换机 类型为direct
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.DIRECT);
        //声明普通队列
        Map<String, Object> arguments =new HashMap<>();
        //过期时间  10s=10000ms  由生产方设置
        //arguments.put("x-message-ttl",10000);
        //正常队列设置死信交换机
        arguments.put("x-dead-letter-exchange",DEAD_EXCHANGE);
        //设置死信RoutingKey
        arguments.put("x-dead-letter-routing-key","lisi");
        //设置正常队列的长度限制
        //arguments.put("x-max-length",6);
        channel.queueDeclare(NORMAL_QUEUE,false,false,false,arguments);
        /////////////////////////////////////////////////////////////////////////////////////////
        //声明死信队列
        channel.queueDeclare(DEAD_QUEUE,false,false,false,null);

        //绑定普通的交换机与普通的队列
        channel.queueBind(NORMAL_QUEUE,NORMAL_EXCHANGE,"zhangsan");
        //绑定死信的交换机与死信的队列
        channel.queueBind(DEAD_QUEUE,DEAD_EXCHANGE,"lisi");
        System.out.println("等待接收消息.....");
        //接收消息
        DeliverCallback deliverCallback=(consumerTag,message)->{
            String msg = new String(message.getBody(), StandardCharsets.UTF_8);
            if("info5".equals(msg))
            {
                System.out.println("Consume01接收的消息是："+msg+"：此消息是被c1拒绝的");
                channel.basicReject(message.getEnvelope().getDeliveryTag(),false);
            }else{
                System.out.println("Consume01接收的消息是："+msg);
                //手动应答
                channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
            }
        };
        //开启手动应答
        channel.basicConsume(NORMAL_QUEUE,false,deliverCallback,consumerTag -> {});
    }
}