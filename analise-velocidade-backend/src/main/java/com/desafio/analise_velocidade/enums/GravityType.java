package com.desafio.analise_velocidade.enums;

import lombok.Getter;

@Getter
public enum GravityType {
    LIGHT(1),
    SERIOUS(3),
    VERY_SERIOUS(5);

    private final int points;

    GravityType(int points) {
        this.points = points;
    }
}
