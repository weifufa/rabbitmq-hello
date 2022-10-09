package com.weifufa.rabbitmq.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


/**
 * 此类为连接工厂创建信道的工具类
 * */
public class RabbitMqUtils {
    public static Channel getChannel() throws Exception{
        //创建一个连接工厂
        ConnectionFactory factory=new ConnectionFactory();
        //工厂Ip 连接RabbitMQ的队列
        factory.setHost("120.78.161.41");
        //用户名
        factory.setUsername("admin");
        //密码
        factory.setPassword("123456");
        //创建连接
        Connection connection=factory.newConnection();
        //获取信道
        Channel channel = connection.createChannel();
        return channel;
    }
}