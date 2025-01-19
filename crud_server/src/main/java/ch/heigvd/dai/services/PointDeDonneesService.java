package ch.heigvd.dai.services;

import ch.heigvd.dai.models.PointDeDonnees;
import ch.heigvd.dai.repositories.PointDeDonneesRepository;

import java.util.List;
import java.util.Map;

public class PointDeDonneesService {
    private final PointDeDonneesRepository pointDeDonneesRepository = new PointDeDonneesRepository();

    public List<PointDeDonnees> getPointsDeDonneesInRange(int serieId, String startDate, String endDate) {
        return pointDeDonneesRepository.getPointsDeDonneesInRange(serieId, startDate, endDate);
    }

    public List<PointDeDonnees> getLimitedPointsDeDonnees(int serieId, int limit) {
        return pointDeDonneesRepository.getLimitedPointsDeDonnees(serieId, limit);
    }

    public List<PointDeDonnees> getPointsDeDonneesForAppareil(String ip, String startDate, String endDate) {
        return pointDeDonneesRepository.getPointsDeDonneesForAppareil(ip, startDate, endDate);
    }

    public List<PointDeDonnees> getLimitedPointsDeDonneesForAppareil(String ip, int limit) {
        return pointDeDonneesRepository.getLimitedPointsDeDonneesForAppareil(ip, limit);
    }

    public Map<String, Double> getStatisticsForLimitedPoints(int serieId, int limit) {
        return pointDeDonneesRepository.getStatisticsForLimitedPoints(serieId, limit);
    }
}
