package ch.heigvd.dai.services;

import ch.heigvd.dai.models.GroupeUtilisateurs;
import ch.heigvd.dai.repositories.GroupeUtilisateursRepository;

import java.util.List;

public class GroupeUtilisateursService {
    private final GroupeUtilisateursRepository groupeUtilisateurRepository = new GroupeUtilisateursRepository();

    public GroupeUtilisateurs getGroupeByNom(String nom) {
        return groupeUtilisateurRepository.getGroupeByNom(nom);
    }

    public void ajouterGroupe(GroupeUtilisateurs groupe) {
        groupeUtilisateurRepository.ajouterGroupe(groupe);
    }

    public void mettreAJourGroupe(GroupeUtilisateurs groupe) {
        groupeUtilisateurRepository.mettreAJourGroupe(groupe);
    }

    public void supprimerGroupe(String nom) {
        groupeUtilisateurRepository.supprimerGroupe(nom);
    }

    public List<GroupeUtilisateurs> getGroupes() {
        return groupeUtilisateurRepository.getGroupes();
    }
}
