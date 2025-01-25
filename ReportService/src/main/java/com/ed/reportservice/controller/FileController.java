package com.ed.reportservice.controller;

import com.ed.reportservice.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RequestMapping("/api/files")
@AllArgsConstructor
@RestController
public class FileController {

    private final ReportService reportService;

    @GetMapping("/pdf")
    public ResponseEntity<InputStreamResource> getPDF() throws IOException {
        ByteArrayInputStream data = reportService.generationPDF();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=report.pdf");

        return ResponseEntity.ok()
                .headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(data));
    }

}
