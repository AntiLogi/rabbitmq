package com.ayyovei.rabbitmq.config;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;

/**
 * @author chenxiaobin
 * @description
 * @create 2019/6/9
 */
public class TTLMessagePostProcessor implements MessagePostProcessor {

    private final Integer ttl;

    public TTLMessagePostProcessor(Integer ttl){
        this.ttl = ttl;
    }
    @Override
    public Message postProcessMessage(Message message) throws AmqpException {
        message.getMessageProperties().setExpiration(ttl.toString());
        return message;
    }
}
