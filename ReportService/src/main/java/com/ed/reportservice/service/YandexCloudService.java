package com.ed.reportservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.nio.file.Paths;


@RequiredArgsConstructor
@Service
public class YandexCloudService implements CloudService {

    private final S3Client s3Client;

    @Value("${yandex.key.bucket}")
    private String BUCKET;


    /**
     * Метод отправки картинки в yandex cloud
     */
    @Override
    public String pushDataToCloud(MultipartFile file) {

        try {
            String filename = Paths.get(System.currentTimeMillis() + "-" + file.getOriginalFilename()).getFileName().toString(); //можно потом переделать под String formater

            PutObjectRequest putObject = PutObjectRequest.builder()
                    .bucket(BUCKET)
                    .key(filename)
                    .contentType(file.getContentType())
                    .contentLength(file.getSize())
                    .build();

            PutObjectResponse response = s3Client
                    .putObject(putObject, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            return response.sdkHttpResponse().toString();

        } catch (IOException e) {
            return e.getMessage();
        }
    }
    
}
