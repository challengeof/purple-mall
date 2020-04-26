package net.caidingke.rocketmq.producer;

import net.caidingke.rocketmq.params.RocketMessage;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author bowen
 */
@SpringBootApplication
public class RocketProducer implements CommandLineRunner {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public static void main(String[] args) {
        SpringApplication.run(RocketProducer.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        rocketMQTemplate.convertAndSend("message-add-topic", new RocketMessage(1L));
        rocketMQTemplate.convertAndSend("message-add-topic", new RocketMessage(2L));
        rocketMQTemplate.convertAndSend("message-removed-topic", new RocketMessage(3L));
        SendResult result = rocketMQTemplate.syncSend("message-add-topic", new RocketMessage(1L));
        System.out.println(result);
    }
}
