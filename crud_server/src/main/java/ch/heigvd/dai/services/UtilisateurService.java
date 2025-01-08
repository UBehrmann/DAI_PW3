package ch.heigvd.dai.services;

import ch.heigvd.dai.models.Utilisateur;
import ch.heigvd.dai.repositories.UtilisateurRepository;

public class UtilisateurService {
    private final UtilisateurRepository utilisateurRepository = new UtilisateurRepository();

    public Utilisateur getUtilisateurById(int id) {
        return utilisateurRepository.getUtilisateurById(id);
    }

    public void ajouterUtilisateur(Utilisateur utilisateur) {
        utilisateurRepository.ajouterUtilisateur(utilisateur);
    }

    // Autres méthodes...
}
