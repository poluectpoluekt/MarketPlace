package com.ed.marketplace.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfigConsumer {

    private final String servers;
    private final String AUTO_OFFSET;
    private final boolean ENABLE_AUTO_COMMIT;
    private final int AUTO_COMMIT_INTERVAL_MS;
    private final int MAX_POLL_RECORDS;

    @Autowired
    public KafkaConfigConsumer(@Value("${spring.kafka.bootstrap-servers}") String servers,
                               @Value("${spring.kafka.consumer.auto-offset-reset}") String offset,
                               @Value("${spring.kafka.consumer.enable-auto-commit}") boolean autoCommit,
                               @Value("${spring.kafka.consumer.auto-commit-interval}") int commitInterval,
                               @Value("${spring.kafka.consumer.max-poll-records}") int maxPollRecords) {
        this.servers = servers;
        this.AUTO_OFFSET = offset;
        this.ENABLE_AUTO_COMMIT = autoCommit;
        this.AUTO_COMMIT_INTERVAL_MS = commitInterval;
        this.MAX_POLL_RECORDS = maxPollRecords;
    }

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, AUTO_OFFSET);
        configProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, ENABLE_AUTO_COMMIT);
        configProps.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, AUTO_COMMIT_INTERVAL_MS);
        configProps.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, MAX_POLL_RECORDS);

        return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(), new JsonDeserializer<>(Object.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

}
