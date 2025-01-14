package ch.heigvd.dai.models;

import java.time.LocalDate;
import java.util.List;

public class GroupeUtilisateurs {
    private String nom; // Clé primaire
    private LocalDate dateCreation;
    private String administrateur; // Clé étrangère vers Utilisateur
    private List<String> utilisateurs; // Liste des utilisateurs du groupe

    public GroupeUtilisateurs(String nom, LocalDate dateCreation, String administrateur) {
        this.nom = nom;
        this.dateCreation = dateCreation;
        this.administrateur = administrateur;
    }

    public GroupeUtilisateurs() { }

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

    public List<String> getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(List<String> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }
}
