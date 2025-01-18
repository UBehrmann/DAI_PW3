package ch.heigvd.dai.services;

import ch.heigvd.dai.models.Alerte;
import ch.heigvd.dai.models.Configuration;
import ch.heigvd.dai.repositories.SerieRepository;

import java.util.List;

public class SerieService {
    private final SerieRepository serieRepository = new SerieRepository();

    public List<Configuration> getConfigurations(int idSerie) {
        return serieRepository.getConfigurations(idSerie);
    }

    public void ajouterConfiguration(int idSerie, Configuration configuration) {
        serieRepository.ajouterConfiguration(idSerie, configuration);
    }

    public void modifierConfiguration(int idSerie, int configId, Configuration configuration) {
        serieRepository.modifierConfiguration(idSerie, configId, configuration);
    }

    public List<Alerte> getAlertes(int idSerie) {
        return serieRepository.getAlertes(idSerie);
    }

    public void ajouterAlerte(int idSerie, Alerte alerte) {
        serieRepository.ajouterAlerte(idSerie, alerte);
    }

    public void modifierAlerte(int idSerie, Alerte alerte) {
        serieRepository.modifierAlerte(idSerie, alerte);
    }

    public void supprimerAlerte(int idSerie, String timestamp) {
        serieRepository.supprimerAlerte(idSerie, timestamp);
    }
}
