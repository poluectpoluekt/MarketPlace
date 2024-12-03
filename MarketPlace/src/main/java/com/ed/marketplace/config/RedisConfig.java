package com.ed.marketplace.config;

import com.ed.marketplace.entity.CustomerDataBasketInRedis;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.UUID;

@Configuration
public class RedisConfig {

    //@Value("${redis.host-name}")
    private final String hostName;

//    @Value("${spring.redis..jedis.port}")
//    private final int port;

    @Autowired
    public RedisConfig(@Value("${redis.host_name}") String hostName) {
        this.hostName = hostName;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConnFactory = new JedisConnectionFactory();
        jedisConnFactory.setHostName(hostName);
        //jedisConnFactory.setPort(port);
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(){
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;

    }
}
