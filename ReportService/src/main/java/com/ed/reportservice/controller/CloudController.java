package com.ed.reportservice.controller;

import com.ed.reportservice.service.YandexCloudService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RequestMapping("/api/cloud")
@RestController
public class CloudController {

    private final YandexCloudService cloudService;

    @PostMapping("/add")
    public String addImageToCloud(@RequestParam("file") MultipartFile file) {
        return cloudService.pushDataToCloud(file);
    }
}
