package me.parker.simplekafkaadminclient;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeConfigsResult;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.config.ConfigResource;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Slf4j
public class SimpleKafkaAdminClient {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties configs = new Properties();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "my-kafka:9092");

        AdminClient admin = AdminClient.create(configs);

        log.info("== Get broker information");
        for (Node node : admin.describeCluster().nodes().get()) {
            log.info("node: {}", node);

            ConfigResource cr = new ConfigResource(ConfigResource.Type.BROKER, node.idString());
            DescribeConfigsResult describeConfigs = admin.describeConfigs(Collections.singleton(cr));
            describeConfigs.all().get().forEach((broker, config) -> {
                config.entries().forEach(configEntry -> log.info(configEntry.name() + "= " + configEntry.value()));
            });
        }

        log.info("== Get topic information");
        Map<String, TopicDescription> topicInfo = admin.describeTopics(Collections.singletonList("test")).all().get();
        log.info("{}", topicInfo);

        admin.close();
    }
}
