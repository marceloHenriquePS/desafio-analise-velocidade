package com.desafio.analise_velocidade.entity;

import com.desafio.analise_velocidade.enums.GravityType;
import java.time.LocalDateTime;

public record Episode(
        String plate,
        LocalDateTime start,
        LocalDateTime end,
        double duration,
        double maxVelocity,
        double maxExcess,
        GravityType gravityType
) {
}
