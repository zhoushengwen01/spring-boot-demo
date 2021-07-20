//package com.example.controller;
//
//import com.example.configurtion.RabbitMQConfig;
//import com.rabbitmq.client.Channel;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//@Component
//public class MQConsumre {
//
//    //监听email队列
//    @RabbitListener(queues = {RabbitMQConfig.QUEUE_INFORM_EMAIL})
//    public void receive_email(String msg, Message message, Channel channel) {
//        System.out.println("#############EMAIL################");
//        System.out.println(msg);
//        System.out.println("#############EMAIL################");
//    }
//
//    //监听sms队列
//    @RabbitListener(queues = {RabbitMQConfig.QUEUE_INFORM_SMS})
//    public void receive_sms(String msg, Message message, Channel channel) {
//        System.out.println("#############SMS################");
//        System.out.println(msg);
//        System.out.println("#############SMS################");
//    }
//}
