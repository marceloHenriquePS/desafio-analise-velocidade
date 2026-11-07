package com.desafio.analise_velocidade.service;

import com.desafio.analise_velocidade.dto.AnalyzeResponse;
import com.desafio.analise_velocidade.dto.FileResponse;
import com.desafio.analise_velocidade.entity.Episode;
import com.desafio.analise_velocidade.entity.Ranking;
import com.desafio.analise_velocidade.entity.Summary;
import com.desafio.analise_velocidade.entity.Vehicle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyzeService {
    private final FileParserService fileParserService;
    private final EpisodeService episodeService;
    private final RankingService rankingService;

    public AnalyzeResponse analyze(InputStream file) throws IOException {
        FileResponse response = fileParserService.readCsvArchive(file);

        Map<String, List<Vehicle>> groupedVehicles = groupVehicles(response.vehicles());
        sortVehicles(groupedVehicles);

        List<Episode> episodes = episodeService.generateEpisodes(groupedVehicles);

        List<Ranking> rankingList = rankingService.generateRanking(episodes);

        Summary summary = new Summary(response.readLines(), response.validLines(), episodes.size());

        return new AnalyzeResponse(episodes, rankingList, response.invalidLines(), summary);
    }

    private Map<String, List<Vehicle>> groupVehicles(List<Vehicle> vehicles) {
        return vehicles.stream().collect(Collectors.groupingBy(Vehicle::plate));
    }

    private void sortVehicles(Map<String, List<Vehicle>> vehiclesByPlate) {
        vehiclesByPlate.values().forEach(list -> list.sort(Comparator.comparing(Vehicle::dateTime)));
    }
}
