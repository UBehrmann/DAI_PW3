package ch.heigvd.dai.services;

import ch.heigvd.dai.models.Alerte;
import ch.heigvd.dai.models.Configuration;
import ch.heigvd.dai.repositories.AppareilRepository;

import java.util.List;

public class AppareilService {
    private final AppareilRepository appareilRepository = new AppareilRepository();

    public Configuration getConfiguration(int idAppareil) {
        return appareilRepository.getConfiguration(idAppareil);
    }

    public List<Alerte> getAlertes(int idAppareil) {
        return appareilRepository.getAlertes(idAppareil);
    }

    public void modifierConfiguration(int idAppareil, Configuration configuration) {
        appareilRepository.modifierConfiguration(idAppareil, configuration);
    }

    public void ajouterAlerte(int idAppareil, Alerte alerte) {
        appareilRepository.ajouterAlerte(idAppareil, alerte);
    }

    public void supprimerAlerte(int idAppareil, String timestamp) {
        appareilRepository.supprimerAlerte(idAppareil, timestamp);
    }

    public void modifierAlerte(int idAppareil, Alerte alerte) {
        appareilRepository.modifierAlerte(idAppareil, alerte);
    }
}
