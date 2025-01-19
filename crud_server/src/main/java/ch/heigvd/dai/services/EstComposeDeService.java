package ch.heigvd.dai.services;

import ch.heigvd.dai.models.EstComposeDe;
import ch.heigvd.dai.repositories.EstComposeDeRepository;

import java.util.List;

public class EstComposeDeService {
    private final EstComposeDeRepository estComposeDeRepository = new EstComposeDeRepository();

    public List<String> getAppareilsDansGroupe(int groupeId) {
        return estComposeDeRepository.getAppareilsDansGroupe(groupeId);
    }

    public void ajouterAppareilAuGroupe(EstComposeDe estComposeDe) {
        estComposeDeRepository.ajouterAppareilAuGroupe(estComposeDe);
    }

    public void retirerAppareilDuGroupe(String ip, int groupeId) {
        estComposeDeRepository.retirerAppareilDuGroupe(ip, groupeId);
    }
}
