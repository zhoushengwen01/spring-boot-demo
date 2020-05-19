package com.example.rabbitMQ;

import com.example.configurtion.RabbitMQConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)

public class Producer {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value("${routingKey}")
    private String routingKey;

    @Test
    public void testSendByTopics() {
        String message = "hello world";
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_TOPICS_INFORM, routingKey, message);
    }


}
