package com.desafio.analise_velocidade.service;


import com.desafio.analise_velocidade.dto.FileResponse;
import com.desafio.analise_velocidade.entity.InvalidLines;
import com.desafio.analise_velocidade.entity.Vehicle;
import com.desafio.analise_velocidade.exception.InvalidArchiveException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileParserService {

    private static final String HEADER = "placa;dataHora;velocidadeKmh;limiteKmh";

    public FileResponse readCsvArchive(InputStream filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(filePath));

        String header = reader.readLine();
        validateHeader(header);

        List<Vehicle> vehicles = new ArrayList<>();
        List<InvalidLines> invalidLines = new ArrayList<>();

        String line;
        int numberLine = 1;

        while((line = reader.readLine()) != null) {
            numberLine++;

            if (line.isBlank()) continue;

            try {
                vehicles.add(parseLineToVehicle(line));
            } catch (IllegalArgumentException e) {
                invalidLines.add(new InvalidLines(numberLine, e.getMessage()));
            }
        }
        return new FileResponse(vehicles, invalidLines);
    }

    private Vehicle parseLineToVehicle(String line) {
        String[] values = line.split(";", -1);

        if (values.length != 4) throw new IllegalArgumentException("Number of values different from 4.");

        for (String value : values) {
            if (value.isBlank()) throw new IllegalArgumentException("Missing value.");
        }

        String plate = values[0].trim().toUpperCase();

        try {
            LocalDateTime localDateTime = LocalDateTime.parse(values[1].trim());
            double velocity = Double.parseDouble(values[2].trim());
            double limitVelocity = Double.parseDouble(values[3].trim());

            if (velocity < 0) throw  new IllegalArgumentException("Negative velocity.");

            if (limitVelocity <= 0) throw  new IllegalArgumentException("Limit velocity must be greater than zero.");

            return new Vehicle(plate, localDateTime, velocity, limitVelocity);

        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date: " + values[1].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid numeric value.");
        }
    }

    private void validateHeader(String header) {
        if (header == null || header.isBlank()) throw new InvalidArchiveException("Empty file.");

        if (!HEADER.equals(header.trim())) throw new InvalidArchiveException("Invalid or non-existent header");
    }
}
