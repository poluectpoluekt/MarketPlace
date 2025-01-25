package com.ed.reportservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Configuration
public class YandexCloudConfig {

    @Value("${yandex.key.id}")
    private String KEY_ID;
    @Value("${yandex.key.secret}")
    private String SECRET_KEY;
    @Value("${yandex.key.region}")
    private String REGION;
    @Value("${yandex.key.endpoint}")
    private String S3_ENDPOINT;

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of(REGION))
                .endpointOverride(URI.create(S3_ENDPOINT))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(KEY_ID, SECRET_KEY)))
                .build();
    }

}
