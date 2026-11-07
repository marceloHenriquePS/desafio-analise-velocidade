package com.desafio.analise_velocidade.service;

import com.desafio.analise_velocidade.dto.AnalyzeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class AnalyzeService {
    private final FileParserService fileParserService;

    public AnalyzeResponse analyze(InputStream file) throws IOException {
        return fileParserService.readCsvArchive(file);
    }
}
