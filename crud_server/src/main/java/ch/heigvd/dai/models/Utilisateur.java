package ch.heigvd.dai.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Utilisateur {
    private String nom;
    private String prenom;
    private String rue;
    private String noRue;
    private String npa;
    private String lieu;
    private LocalDate dateNaissance;
    private String nomUtilisateur; // Clé primaire
    private String motDePasse;
    private String statutCompte;
    private LocalDate derniereConnexionDate;
    private LocalTime derniereConnexionHeure;

    public Utilisateur(String nom, String prenom, String rue, String noRue, String npa, String lieu,
                       LocalDate dateNaissance, String nomUtilisateur, String motDePasse,
                       String statutCompte, LocalDate derniereConnexionDate, LocalTime derniereConnexionHeure) {
        this.nom = nom;
        this.prenom = prenom;
        this.rue = rue;
        this.noRue = noRue;
        this.npa = npa;
        this.lieu = lieu;
        this.dateNaissance = dateNaissance;
        this.nomUtilisateur = nomUtilisateur;
        this.motDePasse = motDePasse;
        this.statutCompte = statutCompte;
        this.derniereConnexionDate = derniereConnexionDate;
        this.derniereConnexionHeure = derniereConnexionHeure;
    }

    // Getters et Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getNoRue() {
        return noRue;
    }

    public void setNoRue(String noRue) {
        this.noRue = noRue;
    }

    public String getNpa() {
        return npa;
    }

    public void setNpa(String npa) {
        this.npa = npa;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getStatutCompte() {
        return statutCompte;
    }

    public void setStatutCompte(String statutCompte) {
        this.statutCompte = statutCompte;
    }

    public LocalDate getDerniereConnexionDate() {
        return derniereConnexionDate;
    }

    public void setDerniereConnexionDate(LocalDate derniereConnexionDate) {
        this.derniereConnexionDate = derniereConnexionDate;
    }

    public LocalTime getDerniereConnexionHeure() {
        return derniereConnexionHeure;
    }

    public void setDerniereConnexionHeure(LocalTime derniereConnexionHeure) {
        this.derniereConnexionHeure = derniereConnexionHeure;
    }
}
