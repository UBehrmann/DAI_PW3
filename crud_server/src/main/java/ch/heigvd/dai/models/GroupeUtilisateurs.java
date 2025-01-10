package ch.heigvd.dai.models;

import java.time.LocalDate;

public class GroupeUtilisateurs {
    private String nom; // Clé primaire
    private LocalDate dateCreation;
    private String administrateur; // Clé étrangère vers Utilisateur

    public GroupeUtilisateurs(String nom, LocalDate dateCreation, String administrateur) {
        this.nom = nom;
        this.dateCreation = dateCreation;
        this.administrateur = administrateur;
    }

    // Getters et Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getAdministrateur() {
        return administrateur;
    }

    public void setAdministrateur(String administrateur) {
        this.administrateur = administrateur;
    }
}
