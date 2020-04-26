package net.caidingke.rabbitmq.producer.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author bowen
 */
@Configuration
public class ProducerRabbitConfig {

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("top-exchange");
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("direct-exchange");
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanout-exchange");
    }


}
