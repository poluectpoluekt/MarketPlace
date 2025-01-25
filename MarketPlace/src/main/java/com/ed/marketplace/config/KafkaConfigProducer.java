package com.ed.marketplace.config;

import com.ed.marketplace.entity.kafka.KafkaMessCreateOrder;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.TopicConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfigProducer {

    private final String servers;
    private final String ACK_CONFIG;
    private final int LINGER_MS_CONFIG;
    private final String RETRIES_CONFIG;
    private final String DELIVERY_TIMEOUT;
    private final String REQUEST_TIMEOUT;
    private final boolean ENABLE_IDEMPOTENCE;

    @Autowired
    public KafkaConfigProducer(@Value("${spring.kafka.bootstrap-servers}") String servers,
                               @Value("${spring.kafka.producer.ack}") String ackConfig,
                               @Value("${spring.kafka.producer.linger-ms-config}") int lingerMsConfig,
                               @Value("${spring.kafka.producer.retries}") String retriesConfig,
                               @Value("${spring.kafka.producer.delivery-timeout}") String deliveryTimeout,
                               @Value("${spring.kafka.producer.request-timeout}") String requestTimeout,
                               @Value("${spring.kafka.producer.enable-idempotence}") boolean enableIdempotence) {
        this.servers = servers;
        ACK_CONFIG = ackConfig;
        LINGER_MS_CONFIG = lingerMsConfig;
        RETRIES_CONFIG = retriesConfig;
        DELIVERY_TIMEOUT = deliveryTimeout;
        REQUEST_TIMEOUT = requestTimeout;
        ENABLE_IDEMPOTENCE = enableIdempotence;
    }

    @Bean
    public ProducerFactory<String, KafkaMessCreateOrder> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        configProps.put(ProducerConfig.ACKS_CONFIG, ACK_CONFIG);
        configProps.put(ProducerConfig.LINGER_MS_CONFIG, LINGER_MS_CONFIG);
        configProps.put(ProducerConfig.RETRIES_CONFIG, RETRIES_CONFIG);
        configProps.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, DELIVERY_TIMEOUT);
        configProps.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, RETRIES_CONFIG);
        configProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, ENABLE_IDEMPOTENCE);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, KafkaMessCreateOrder> kafkaTemplate(ProducerFactory<String, KafkaMessCreateOrder> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name("OrdersFromMarketPlace")
                .partitions(3)
                .replicas(1)
                .config(TopicConfig.RETENTION_MS_CONFIG, String.valueOf(Duration.ofDays(30).toMillis()))
                .build();
    }


}
