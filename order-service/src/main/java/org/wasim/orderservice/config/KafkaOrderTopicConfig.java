package org.wasim.orderservice.config;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaOrderTopicConfig {

    @Bean
    public NewTopic orderTopic(){
        return new NewTopic("order-topic",3,(short) 1);
    }
}
