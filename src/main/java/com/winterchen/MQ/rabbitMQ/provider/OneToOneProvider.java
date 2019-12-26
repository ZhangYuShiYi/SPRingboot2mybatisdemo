package com.winterchen.MQ.rabbitMQ.provider;

import com.winterchen.MQ.rabbitMQ.UserDemo;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Date;

/**
 * Created by zy on 2019/12/24.
 * 生产者
 */
@Component
public class OneToOneProvider {
    /**
     * AmqpTemplate可以说是RabbitTemplate父类，RabbitTemplate实现类RabbitOperations接口，RabbitOperations继承了AmqpTemplate接口
     */
    @Autowired
    private AmqpTemplate rabbitTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate1;

    /**
     * 用于单生产者-》单消费者测试
     */
    public void sendMessage() {
        String sendMsg = "hello1 " + new Date();
        System.out.println("Sender1 : " + sendMsg);
        this.rabbitTemplate1.convertAndSend("helloQueue", sendMsg); //向helloQueue消息队列中发送消息
    }
    /**
     * 实体类的传输（springboot完美的支持对象的发送和接收，不需要格外的配置。实体类必须序列化）
     * @param user
     */
    public void sendEntity(UserDemo user) {
        System.out.println("user send : " + user.getName()+"/"+user.getPass());
        this.rabbitTemplate.convertAndSend("userQueue", user);    //向userQueue消息队列中发送消息
    }
    //交换机为direct的消息队列
    public void sendDirect() {
        String msgString="directSender :hello i am hzb";
        System.out.println(msgString);
        this.rabbitTemplate.convertAndSend("directQueue", msgString);
    }
    //交换机为topic的消息队列
    public void sendTopic() {
        String msg1 = "I am topic.mesaage msg======";
        System.out.println("sender1 : " + msg1);
        this.rabbitTemplate.convertAndSend("exchange", "topic.message", msg1);
        String msg2 = "I am topic.mesaages msg########";
        System.out.println("sender2 : " + msg2);
        this.rabbitTemplate.convertAndSend("exchange", "topic.messages", msg2);
    }
    //交换机为fanout的消息队列
    public void sendFanout() {
        String msgString="fanoutSender :hello i am hzb";
        System.out.println(msgString);
        // 参数2被忽略
        this.rabbitTemplate.convertAndSend("fanoutExchange","", msgString);
    }

}
