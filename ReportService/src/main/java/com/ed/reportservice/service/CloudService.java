package com.ed.reportservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface CloudService {

    String pushDataToCloud(MultipartFile file);
}
