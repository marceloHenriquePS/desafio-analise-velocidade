package com.desafio.analise_velocidade.dto;

import com.desafio.analise_velocidade.entity.Episode;
import com.desafio.analise_velocidade.entity.InvalidLines;
import com.desafio.analise_velocidade.entity.Ranking;

import java.util.List;

public record AnalyzeResponse(
        List<Episode> episodes,
        List<Ranking> rankings,
        List<InvalidLines> invalidLines,
) {
}
