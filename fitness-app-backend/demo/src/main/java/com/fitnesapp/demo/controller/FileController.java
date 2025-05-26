package com.fitnesapp.demo.controller;

import com.fitnesapp.demo.services.GcsUploadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final GcsUploadService gcsUploadService;

    public FileController(GcsUploadService gcsUploadService) {
        this.gcsUploadService = gcsUploadService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> upload(@RequestPart MultipartFile file) throws IOException {
        String publicUrl = gcsUploadService.uploadFile(file);
        String fileName = file.getOriginalFilename();
        return ResponseEntity.ok(Map.of("url", publicUrl, "fileName", fileName));
    }
}