package me.parker.example;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

@Slf4j
@SpringBootApplication
public class SpringConsumerApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SpringConsumerApplication.class);
        application.run(args);
    }

    @KafkaListener(topics = "test", groupId = "test-group-01")
    public void commitListener(ConsumerRecords<String, String> records, Acknowledgment ack) {
        records.forEach(record -> log.info(record.toString()));
        ack.acknowledge();
    }

    @KafkaListener(topics = "test", groupId = "test-group-01")
    public void consumerCommitListener(ConsumerRecords<String, String> records, Consumer<String, String> consumer) {
        records.forEach(record -> log.info(record.toString()));
        consumer.commitAsync();
    }
}
