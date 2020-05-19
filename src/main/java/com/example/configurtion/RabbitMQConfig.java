package com.example.configurtion;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
topic 主题模式,可以根据routing_key自由的绑定不同的队列
 */
@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    public static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    public static final String EXCHANGE_TOPICS_INFORM = "exchange_topics_inform";

    //交换机
    @Bean(EXCHANGE_TOPICS_INFORM)
    public Exchange EXCHANGE_TOPICS_INFORM() {
        return ExchangeBuilder.topicExchange(EXCHANGE_TOPICS_INFORM).durable(true).build();
    }

    //邮件队列
    @Bean(QUEUE_INFORM_EMAIL)
    public Queue QUEUE_INFORM_EMAIL() {
        return new Queue(QUEUE_INFORM_EMAIL);
    }

    //短信队列
    @Bean(QUEUE_INFORM_SMS)
    public Queue QUEUE_INFORM_SMS() {
        return new Queue(QUEUE_INFORM_SMS);
    }

    //队列绑定交换机
    @Bean
    public Binding BINDING_QUEUQ_INFORM_EMAIL(@Qualifier(QUEUE_INFORM_EMAIL) Queue queue, @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("inform.#.email.#").noargs();
    }

    @Bean
    public Binding BINDING_QUEUQ_INFORM_SMS(@Qualifier(QUEUE_INFORM_SMS) Queue queue, @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("inform.#.sms.#").noargs();
    }


}
