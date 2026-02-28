package org.wasim.orderservice.config;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaOrderTopicConfig {

    @Bean
    public NewTopic orderPlacedTopic(){
        return new NewTopic("order-placed",3,(short) 1);
    }

    @Bean
    public NewTopic orderConfirmationTopic(){
        return new NewTopic("order-confirmed",3,(short) 1);
    }
}
