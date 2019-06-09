package com.ayyovei.rabbitmq.config;


import com.ayyovei.rabbitmq.receive.MsgReceiver;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Receiver;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenxiaobin
 * @description
 * @create 2019/6/9
 */
@Configuration
public class RabbitmqConfig {

    public static final String topicExchangeName = "spring-boot-exchange";

    public static final String queueName = "spring-boot";

    public static final String queueNameDLX = "spring-boot-dlx";

   /* @Bean
    Queue queue() {
        return new  Queue(queueName, false);
    }*/
    @Bean
    Queue queue() {
        return QueueBuilder.durable(queueName).withArgument("x-dead-letter-exchange","")
                .withArgument("x-dead-letter-routing-key",queueNameDLX).build();
    }

    @Bean
    Queue queueDLX() {
        return new  Queue(queueNameDLX, false);
    }

    @Bean
    TopicExchange exchange1() {

        return new TopicExchange(topicExchangeName,true,false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#");
    }
    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueNameDLX);
        container.setMessageListener(listenerAdapter);
        return container;
    }


    @Bean
    MessageListenerAdapter listenerAdapter(MsgReceiver msgReceiver) {
        return new MessageListenerAdapter(msgReceiver, "receiveMessage");
    }

}
