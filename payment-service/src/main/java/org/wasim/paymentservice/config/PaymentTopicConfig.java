package org.wasim.paymentservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentTopicConfig {

    @Bean
    public NewTopic createPaymentTopic(){
        return new NewTopic("payment-success",3,(short) 1);
    }
}
