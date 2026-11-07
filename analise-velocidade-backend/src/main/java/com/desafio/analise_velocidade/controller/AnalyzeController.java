package com.desafio.analise_velocidade.controller;

import com.desafio.analise_velocidade.dto.AnalyzeResponse;
import com.desafio.analise_velocidade.service.AnalyzeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/analyze")
@CrossOrigin(origins = "http://localhost:4200")
public class AnalyzeController {
    private final AnalyzeService analyzeService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AnalyzeResponse> analyse(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(analyzeService.analyze(file.getInputStream()));
    }
}
