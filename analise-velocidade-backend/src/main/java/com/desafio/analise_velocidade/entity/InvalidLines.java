package com.desafio.analise_velocidade.entity;

public record InvalidLines(
    int lineNumber,
    String cause
) {
}
