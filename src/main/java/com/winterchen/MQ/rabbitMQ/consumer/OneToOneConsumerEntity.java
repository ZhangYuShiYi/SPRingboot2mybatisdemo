package com.winterchen.MQ.rabbitMQ.consumer;

import com.winterchen.MQ.rabbitMQ.UserDemo;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by zy on 2019/12/24.
 */
@Component
@RabbitListener(queues = "userQueue")  //从userQueue消息队列中接收消息
public class OneToOneConsumerEntity {

    @RabbitHandler  //处理消息
    public void process(UserDemo user){
        System.out.println("user receive  : " + user.getName()+"/"+user.getPass());
    }


}
