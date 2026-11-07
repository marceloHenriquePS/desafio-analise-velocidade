package com.desafio.analise_velocidade.entity;

import java.time.LocalDateTime;

public record Vehicle(
        String plate,
        LocalDateTime dateTime,
        double velocityKmh,
        double limitKmh
) {
}