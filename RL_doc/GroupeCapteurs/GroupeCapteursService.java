package ch.heigvd.dai.services;

import ch.heigvd.dai.models.GroupeCapteurs;
import ch.heigvd.dai.models.Appareil;
import ch.heigvd.dai.repositories.GroupeCapteursRepository;

import java.sql.Date;
import java.util.List;

public class GroupeCapteursService {
    private final GroupeCapteursRepository groupeCapteursRepository = new GroupeCapteursRepository();

    public void ajouterGroupeCapteurs(String nom, Date dateCreation, String administrateur) {
        groupeCapteursRepository.ajouterGroupeCapteurs(nom, dateCreation, administrateur);
    }

    public void supprimerGroupeCapteurs(int id) {
        groupeCapteursRepository.supprimerGroupeCapteurs(id);
    }

    public void modifierGroupeCapteurs(int id, GroupeCapteurs groupeCapteurs) {
        groupeCapteursRepository.modifierGroupeCapteurs(id, groupeCapteurs);
    }

    public GroupeCapteurs getGroupeCapteursParId(int id) {
        return groupeCapteursRepository.getGroupeCapteursParId(id);
    }

    public List<Appareil> getAppareilsDansGroupe(int id) {
        return groupeCapteursRepository.getAppareilsDansGroupe(id);
    }

    public List<Appareil> getAppareilsDansGroupeParType(int id, String type) {
        return groupeCapteursRepository.getAppareilsDansGroupeParType(id, type);
    }

    public List<GroupeCapteurs> getGroupesCapteursParUtilisateur(String username) {
        return groupeCapteursRepository.getGroupesCapteursParUtilisateur(username);
    }

    public List<Appareil> getAppareilsParUtilisateurEtType(String username, String type) {
        return groupeCapteursRepository.getAppareilsParUtilisateurEtType(username, type);
    }

    public List<GroupeCapteurs> getGroupesCapteurs() {
        return groupeCapteursRepository.getGroupesCapteurs();
    }
}
