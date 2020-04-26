package net.caidingke.rabbitmq.producer;

import net.caidingke.rabbitmq.params.RabbitMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author bowen
 */
@SpringBootApplication
public class RabbitProducer implements CommandLineRunner {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public static void main(String[] args) {
        SpringApplication.run(RabbitProducer.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        rabbitTemplate.convertAndSend("top-exchange", "top.router", new RabbitMessage(1L));
        rabbitTemplate.convertAndSend("top-exchange", "top.#", new RabbitMessage(2L));
        rabbitTemplate.convertAndSend("top-exchange", "top.aaa", new RabbitMessage(3L));
    }
}
