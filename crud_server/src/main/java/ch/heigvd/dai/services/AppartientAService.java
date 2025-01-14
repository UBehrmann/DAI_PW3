package ch.heigvd.dai.services;

import ch.heigvd.dai.models.AppartientA;
import ch.heigvd.dai.repositories.AppartientARepository;

import java.util.List;

public class AppartientAService {
    private final AppartientARepository appartientARepository = new AppartientARepository();

    public void ajouterUtilisateurDansGroupe(AppartientA appartientA) {
        appartientARepository.ajouterUtilisateurDansGroupe(appartientA);
    }

    public void supprimerUtilisateurDeGroupe(String utilisateur, String groupe) {
        appartientARepository.supprimerUtilisateurDeGroupe(utilisateur, groupe);
    }
}
