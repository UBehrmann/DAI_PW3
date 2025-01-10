package ch.heigvd.dai.models;

public class AppartientA {
    private String utilisateur; // Clé étrangère vers Utilisateur
    private String groupe; // Clé étrangère vers GroupeUtilisateurs

    public AppartientA(String utilisateur, String groupe) {
        this.utilisateur = utilisateur;
        this.groupe = groupe;
    }

    // Getters et Setters
    public String getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getGroupe() {
        return groupe;
    }

    public void setGroupe(String groupe) {
        this.groupe = groupe;
    }
}
