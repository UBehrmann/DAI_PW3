package ch.heigvd.dai.services;

import ch.heigvd.dai.models.Utilisateur;
import ch.heigvd.dai.repositories.UtilisateurRepository;

public class UtilisateurService {
    private final UtilisateurRepository utilisateurRepository = new UtilisateurRepository();

    // Récupérer un utilisateur par son nomUtilisateur
    public Utilisateur getUtilisateurByNomUtilisateur(String nomUtilisateur) {
        return utilisateurRepository.getUtilisateurByNomUtilisateur(nomUtilisateur);
    }

    // Ajouter un nouvel utilisateur
    public void ajouterUtilisateur(Utilisateur utilisateur) {
        utilisateurRepository.ajouterUtilisateur(utilisateur);
    }

    // Mettre à jour un utilisateur existant
    public void mettreAJourUtilisateur(Utilisateur utilisateur) {
        utilisateurRepository.mettreAJourUtilisateur(utilisateur);
    }

    // Supprimer un utilisateur par son nomUtilisateur
    public void supprimerUtilisateur(String nomUtilisateur) {
        utilisateurRepository.supprimerUtilisateur(nomUtilisateur);
    }
}
