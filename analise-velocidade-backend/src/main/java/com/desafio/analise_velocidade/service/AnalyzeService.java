package com.desafio.analise_velocidade.service;

import com.desafio.analise_velocidade.dto.AnalyzeResponse;
import com.desafio.analise_velocidade.dto.FileResponse;
import com.desafio.analise_velocidade.entity.Episode;
import com.desafio.analise_velocidade.entity.Vehicle;
import com.desafio.analise_velocidade.enums.GravityType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyzeService {
    private final FileParserService fileParserService;

    public AnalyzeResponse analyze(InputStream file) throws IOException {
        FileResponse response = fileParserService.readCsvArchive(file);

        Map<String, List<Vehicle>> groupedVehicles = groupVehicles(response.vehicles());
        sortVehicles(groupedVehicles);

        List<Episode> episodes = new ArrayList<>();
        for (List<Vehicle> vehicles : groupedVehicles.values()) {
            episodes.addAll(identifyEpisode(vehicles));
        }

        return new AnalyzeResponse(episodes, response.invalidLines());
    }

    private Map<String, List<Vehicle>> groupVehicles(List<Vehicle> vehicles) {
        return vehicles.stream().collect(Collectors.groupingBy(Vehicle::plate));
    }

    private void sortVehicles(Map<String, List<Vehicle>> vehiclesByPlate) {
        vehiclesByPlate.values().forEach(list -> list.sort(Comparator.comparing(Vehicle::dateTime)));
    }

    private List<Episode> identifyEpisode(List<Vehicle> sortedVehicles) {
        List<Episode> episodes = new ArrayList<>();
        List<Vehicle> current = new ArrayList<>();

        for (Vehicle vehicle : sortedVehicles) {
            boolean excess = isExcess(vehicle);
            boolean gapExceed = !current.isEmpty() && isDurationExcessFiveMinutes(current.getLast(), vehicle);

            if (!current.isEmpty() && (!excess || gapExceed)) {
                episodes.add(buildEpisode(current));
                current.clear();
            }

            if (excess) current.add(vehicle);
        }

        if (!current.isEmpty()) episodes.add(buildEpisode(current));

        return episodes;
    }

    private Episode buildEpisode(List<Vehicle> vehicles) {
        Vehicle first = vehicles.getFirst();
        Vehicle last = vehicles.getLast();

        long durationSeconds = Duration.between(first.dateTime(), last.dateTime()).getSeconds();
        double maxVelocity = vehicles.stream().mapToDouble(Vehicle::velocityKmh).max().orElse(0);
        double maxExcess = vehicles.stream().mapToDouble(this::calculateExcess).max().orElse(0);

        return new Episode(
                first.plate(),
                first.dateTime(),
                last.dateTime(),
                durationSeconds,
                maxVelocity,
                maxExcess,
                calculateGravity(maxExcess)
        );
    }

    private boolean isExcess(Vehicle vehicle) {
        return vehicle.velocityKmh() > vehicle.limitKmh() + 5;
    }

    private boolean isDurationExcessFiveMinutes(Vehicle previous, Vehicle next) {
        Duration duration = Duration.between(previous.dateTime(), next.dateTime());
        return duration.compareTo(Duration.ofMinutes(5)) > 0;
    }

    private double calculateExcess(Vehicle vehicle) {
        double excess = (vehicle.velocityKmh() - vehicle.limitKmh()) / vehicle.limitKmh() * 100;
        return Math.round(excess * 10.0) / 10.0;
    }

    private GravityType calculateGravity(double maxExcess) {
        if (maxExcess <= 20) return GravityType.LIGHT;
        if (maxExcess <= 50) return GravityType.SERIOUS;
        return GravityType.VERY_SERIOUS;
    }
}
