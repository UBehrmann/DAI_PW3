package ch.heigvd.dai.services;

import ch.heigvd.dai.models.Appareil;
import ch.heigvd.dai.repositories.AAccesRepository;

import java.util.List;

public class AAccesService {
    private final AAccesRepository aAccesRepository = new AAccesRepository();

    public List<Appareil> getAppareilsForGroupe(String groupe) {
        return aAccesRepository.getAppareilsForGroupe(groupe);
    }

    public void ajouterAcces(String groupe, String ip) {
        aAccesRepository.ajouterAcces(groupe, ip);
    }

    public void supprimerAcces(String groupe, String ip) {
        aAccesRepository.supprimerAcces(groupe, ip);
    }
}
