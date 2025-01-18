package ch.heigvd.dai.services;

import ch.heigvd.dai.models.PointDeDonnees;
import ch.heigvd.dai.repositories.PointDeDonneesRepository;

import java.util.List;

public class PointDeDonneesService {
    private final PointDeDonneesRepository pointDeDonneesRepository = new PointDeDonneesRepository();

    public List<PointDeDonnees> getPointsDeDonneesInRange(int serieId, String startDate, String endDate) {
        return pointDeDonneesRepository.getPointsDeDonneesInRange(serieId, startDate, endDate);
    }

    public List<PointDeDonnees> getPointsDeDonneesForGroupe(int groupeId, String startDate, String endDate) {
        return pointDeDonneesRepository.getPointsDeDonneesForGroupe(groupeId, startDate, endDate);
    }
}
