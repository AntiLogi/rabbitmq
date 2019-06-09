package com.ayyovei.rabbitmq.run;

import com.ayyovei.rabbitmq.config.RabbitmqConfig;
import com.ayyovei.rabbitmq.receive.MsgReceiver;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author chenxiaobin
 * @description
 * @create 2019/6/9
 */
@Component
public class Runner implements CommandLineRunner {
    private RabbitTemplate rabbitTemplate;
    private final MsgReceiver receiver;

    public Runner(MsgReceiver receiver, RabbitTemplate rabbitTemplate) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
    }
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(RabbitmqConfig.topicExchangeName, "foo.bar.baz", "Hello from RabbitMQ!");
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        System.out.println("send end,and receive the message");
    }
}
