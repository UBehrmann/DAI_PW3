package ch.heigvd.dai.services;

import ch.heigvd.dai.models.Appareil;
import ch.heigvd.dai.models.GroupeCapteurs;
import ch.heigvd.dai.repositories.GroupeCapteursRepository;

import java.util.List;

public class GroupeCapteursService {
    private final GroupeCapteursRepository groupeCapteursRepository = new GroupeCapteursRepository();

    public void ajouterGroupeCapteurs(GroupeCapteurs groupeCapteurs) {
        groupeCapteursRepository.ajouterGroupeCapteurs(groupeCapteurs);
        groupeCapteursRepository.mettreAJourAcces(groupeCapteurs.getNom()); // Mise à jour de a_acces
    }

    public void supprimerGroupeCapteurs(int id) {
        groupeCapteursRepository.supprimerAccesParGroupe(id); // Mise à jour de a_acces
        groupeCapteursRepository.supprimerGroupeCapteurs(id);
    }

    public void modifierGroupeCapteurs(int id, GroupeCapteurs groupeCapteurs) {
        groupeCapteursRepository.modifierGroupeCapteurs(id, groupeCapteurs);
    }

    public List<Appareil> getAppareilsDansGroupe(String nomGroupe) {
        return groupeCapteursRepository.getAppareilsDansGroupe(nomGroupe);
    }

    public List<Appareil> getAppareilsDansGroupeParType(String nomGroupe, String type) {
        return groupeCapteursRepository.getAppareilsDansGroupeParType(nomGroupe, type);
    }

}
