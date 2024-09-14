package com.example.demo.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    /**
     * Cria um novo topico Kafka durante o startup se nao exisitr um com o mesmo nome
     */
    @Bean
    public NewTopic inventoryEventsTopic() {
        return TopicBuilder.name("inventory-events")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean NewTopic testEventTopic() {
        return TopicBuilder.name("test-event-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }

}
