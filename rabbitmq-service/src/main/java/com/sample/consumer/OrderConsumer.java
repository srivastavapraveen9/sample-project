package com.sample.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.sample.config.RabbitConfig;

@Service
public class OrderConsumer {

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void receiveOrder(String message) {
        System.out.println("📥 Received order: " + message);
    }
}
