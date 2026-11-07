package com.desafio.analise_velocidade.service;

import com.desafio.analise_velocidade.entity.Episode;
import com.desafio.analise_velocidade.entity.Ranking;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RankingService {

    public List<Ranking> generateRanking(List<Episode> episodes) {
        Map<String, List<Episode>> groupedEpisodes = groupEpisodeByPlate(episodes);

        List<Ranking> rankings = new ArrayList<>();
        for (List<Episode> vehicleEpisodes: groupedEpisodes.values()) {
            String plate = vehicleEpisodes.getFirst().plate();

            int totalPoints = calculateTotalPoints(vehicleEpisodes);
            double maxVelocity = getMaxVelocity(vehicleEpisodes);

            Ranking ranking = new Ranking(plate, totalPoints, maxVelocity);
            rankings.add(ranking);
        }

        sortRank(rankings);
        return rankings;
    }

    private void sortRank(List<Ranking>rankings) {
        rankings.sort(
                Comparator.comparingInt(Ranking::totalPoints).reversed()
                        .thenComparing(Comparator.comparing(Ranking::maxVelocity).reversed())
                        .thenComparing(Ranking::plate)
        );
    }

    private Map<String, List<Episode>> groupEpisodeByPlate(List<Episode> episodes) {
        return episodes.stream().collect(Collectors.groupingBy(Episode::plate));
    }

    private int calculateTotalPoints(List<Episode> episodes) {
        return episodes.stream()
                .mapToInt(episode -> episode.gravityType().getPoints())
                .sum();
    }

    private double getMaxVelocity(List<Episode> episodes) {
        return episodes.stream().mapToDouble(Episode::maxVelocity).max().orElse(0);
    }
}
