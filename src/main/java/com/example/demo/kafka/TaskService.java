package com.example.demo.kafka;

import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class TaskService {

    @Autowired
    KafkaAdmin kafkaAdmin;

    private void createNewTopic(String name) throws ExecutionException, InterruptedException {

        Map<String, String> topicConfig = new HashMap<>();
        topicConfig.put(TopicConfig.RETENTION_MS_CONFIG, String.valueOf(24 * 60 * 60 * 1000)); // 24 hours retention

        NewTopic newTopic = new NewTopic(name, 1, (short) 1)
                .configs(topicConfig);

        try (AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties())) {
            //Blocking call to make sure topic is created
            adminClient.createTopics(Collections.singletonList(newTopic)).all().get();
        }
    }

    @PostConstruct
    public void createTopic() {
        try {
            this.createNewTopic("ivson-kafka");
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
