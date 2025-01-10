package ch.heigvd.dai.models;

import java.sql.Timestamp;

public class LogUtilisateur {
    private int id;
    private String utilisateur;
    private String action;
    private String details; // JSONB is typically represented as a String in Java
    private Timestamp dateAction;

    // Constructors
    public LogUtilisateur() {
    }

    public LogUtilisateur(int id, String utilisateur, String action, String details, Timestamp dateAction) {
        this.id = id;
        this.utilisateur = utilisateur;
        this.action = action;
        this.details = details;
        this.dateAction = dateAction;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Timestamp getDateAction() {
        return dateAction;
    }

    public void setDateAction(Timestamp dateAction) {
        this.dateAction = dateAction;
    }
}
