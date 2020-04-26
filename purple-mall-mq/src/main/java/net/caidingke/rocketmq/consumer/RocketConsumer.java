package net.caidingke.rocketmq.consumer;

import lombok.extern.slf4j.Slf4j;
import net.caidingke.rocketmq.params.RocketMessage;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

/**
 * @author bowen
 */
@SpringBootApplication
@Slf4j
public class RocketConsumer {

    public static void main(String[] args) {
        SpringApplication.run(RocketConsumer.class, args);
    }

    @Service
    @RocketMQMessageListener(
            topic = "message-add-topic",
            consumerGroup = "cart-consumer_cart-item-add-topic"
    )
    public class CardItemAddConsumer implements RocketMQListener<RocketMessage> {
        @Override
        public void onMessage(RocketMessage message) {
            log.info("Adding item: {}", message);
            // additional logic
        }
    }

    @Service
    @RocketMQMessageListener(
            topic = "message-removed-topic",
            consumerGroup = "cart-consumer_cart-item-removed-topic"
    )
    public class CardItemRemoveConsumer implements RocketMQListener<RocketMessage> {
        @Override
        public void onMessage(RocketMessage message) {
            log.info("Removing item: {}", message);
        }
    }

}
