package net.caidingke.rabbitmq.consumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author bowen
 */
@Configuration
public class ConsumerRabbitConfig {

    @Bean
    public Queue topQueue() {
        return new Queue("top-queue");
    }

    @Bean
    public Queue fuzzyQueue() {
        return new Queue("fuzzy-queue");
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("top-exchange");
    }

    @Bean
    public Binding binding(TopicExchange topicExchange, Queue topQueue) {

        return BindingBuilder.bind(topQueue).to(topicExchange).with("top.router");

    }

    @Bean
    public Binding bindingFuzzy(TopicExchange topicExchange, Queue fuzzyQueue) {

        return BindingBuilder.bind(fuzzyQueue).to(topicExchange).with("top.#");

    }

    public static void main(String[] args) {
        while (true) {
            //do nothing
        }
    }
}
