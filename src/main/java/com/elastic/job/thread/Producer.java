package com.elastic.job.thread;


import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;

import java.time.LocalDateTime;

public class Producer {

    public static void main(String[] args) throws MQClientException, InterruptedException {
        LocalDateTime localDateTime = LocalDateTime.now();
        DefaultMQProducer producer = new DefaultMQProducer("producer1");
        producer.setNamesrvAddr("132.232.31.24:9876");
        producer.setSendMsgTimeout(1000000);

        producer.start();

        for (int i = 0; i < 10; i++) {
            try {

                Message msg = new Message("TopicTest5" /* Topic */,
                        "TagA" /* Tag */,
                        ("Hello RocketMQ " + i).getBytes("UTF-8") /* Message body */
                );
                SendResult sendResult = producer.send(msg);

                System.out.printf(localDateTime + " %s%n",sendResult);
            } catch (Exception e) {
                e.printStackTrace();
                Thread.sleep(1000);
            }
        }

        producer.shutdown();
    }
}
