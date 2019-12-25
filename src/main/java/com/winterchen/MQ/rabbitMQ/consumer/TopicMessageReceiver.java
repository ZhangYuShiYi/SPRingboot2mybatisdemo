package com.winterchen.MQ.rabbitMQ.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by zy on 2019/12/24.
 */
@Component
@RabbitListener(queues = "topic.message")  //从topic.message消息队列中接收消息
public class TopicMessageReceiver {

    @RabbitHandler  //处理消息
    public void process(String msg) {
        System.out.println("topicMessageReceiver  : " + msg);
    }

}
