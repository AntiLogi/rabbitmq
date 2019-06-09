package com.ayyovei.rabbitmq.receive;

import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * @author chenxiaobin
 * @description
 * @create 2019/6/9
 */
@Component
public class MsgReceiver {
    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
        try {
            Thread.sleep(5000);
            System.out.println("5 seconds later");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        latch.countDown();

    }

    public CountDownLatch getLatch() {
        return latch;
    }

}
