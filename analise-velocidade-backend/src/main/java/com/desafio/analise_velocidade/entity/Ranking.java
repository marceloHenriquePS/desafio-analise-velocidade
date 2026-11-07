package com.desafio.analise_velocidade.entity;

public record Ranking(
        String plate,
        int totalPoints,
        double maxVelocity
) {
}
