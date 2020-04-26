package net.caidingke.rabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import net.caidingke.rabbitmq.params.RabbitMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author bowen
 */
@SpringBootApplication
@Slf4j
public class RabbitConsumer {

    public static void main(String[] args) {
        SpringApplication.run(RabbitConsumer.class, args);
    }

    @RabbitListener(queues = "top-queue")
    public void process(RabbitMessage message) {
        log.info("{}", message);
    }

    @RabbitListener(queues = "fuzzy-queue")
    public void processFuzzy(RabbitMessage message) {
        log.info("{}", message);
    }
}
