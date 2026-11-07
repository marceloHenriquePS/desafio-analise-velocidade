package com.desafio.analise_velocidade.dto;

import com.desafio.analise_velocidade.entity.InvalidLines;
import com.desafio.analise_velocidade.entity.Vehicle;

import java.util.List;

public record FileResponse(
        List<Vehicle> vehicles,
        List<InvalidLines> invalidLines
) {
}
