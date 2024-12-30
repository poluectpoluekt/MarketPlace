package com.ed.marketplace.config;

import com.ed.marketplace.app_class.redis.BaseEntityForRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

    private final String hostName;

//    private final int port;

    @Autowired
    public RedisConfig(@Value("${redis.host_name}") String hostName) {
        this.hostName = hostName;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(hostName);
        //jedisConnFactory.setPort(port);
        return new LettuceConnectionFactory(redisConfig);
    }

    @Bean
    public RedisTemplate<String, BaseEntityForRedis> redisTemplate() {
        RedisTemplate<String, BaseEntityForRedis> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        return template;

    }
}
