package me.parker;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

@Slf4j
public class SimpleProducer {
    private final static String TOPIC_NAME = "test";
    private final static String BOOTSTRAP_SERVERS = "my-kafka:9092";

    public static void main(String[] args) {
        Properties configs = new Properties();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // 커스텀 파티셔너 추가
        configs.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CustomPartitioner.class);

        KafkaProducer<String, String> producer = new KafkaProducer<>(configs);

        // value 레코드 전송
        String messageValue = "testMessage";
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, messageValue);
        producer.send(record);
        log.info("{}", record);

        // key-value 레코드 전송
        ProducerRecord<String, String> keyRecord = new ProducerRecord<>(TOPIC_NAME, "Pangyo", "23");
        producer.send(keyRecord);
        log.info("{}", keyRecord);

        // 특정 파티션에 key-value 레코드 전송
        ProducerRecord<String, String> exactPartitionRecord = new ProducerRecord<>(TOPIC_NAME, 0, "Park", "30");
        producer.send(exactPartitionRecord);
        log.info("{}", exactPartitionRecord);


        producer.flush();
        producer.close();
    }
}
