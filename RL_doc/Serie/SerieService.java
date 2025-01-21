package ch.heigvd.dai.services;

import ch.heigvd.dai.models.Alerte;
import ch.heigvd.dai.models.Configuration;
import ch.heigvd.dai.models.Serie;
import ch.heigvd.dai.repositories.SerieRepository;

import java.util.List;

public class SerieService {
    private final SerieRepository serieRepository = new SerieRepository();

    public Configuration getConfigurationForSerie(int id) {
        return serieRepository.getConfigurationForSerie(id);
    }

    public List<Alerte> getAlertes(int id) {
        return serieRepository.getAlertes(id);
    }

    public void ajouterConfiguration(int id, Configuration configuration) {
        serieRepository.ajouterConfiguration(id, configuration);
    }

    public void modifierConfiguration(int configId, Configuration configuration) {
        serieRepository.modifierConfiguration(configId, configuration);
    }

    public void ajouterAlerte(int id, Alerte alerte) {
        serieRepository.ajouterAlerte(id, alerte);
    }

    public void modifierAlerte(int id, Alerte alerte) {
        serieRepository.modifierAlerte(id, alerte);
    }

    public void supprimerAlerte(int id, String timestamp) {
        serieRepository.supprimerAlerte(id, timestamp);
    }

}
