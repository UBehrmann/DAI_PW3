package ch.heigvd.dai.models;

import java.sql.Timestamp;

public class LogAppartenance {
    private int id;
    private String utilisateur;
    private String groupe;
    private String action;
    private Timestamp dateAction;

    // Constructors
    public LogAppartenance() {
    }

    public LogAppartenance(int id, String utilisateur, String groupe, String action, Timestamp dateAction) {
        this.id = id;
        this.utilisateur = utilisateur;
        this.groupe = groupe;
        this.action = action;
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

    public String getGroupe() {
        return groupe;
    }

    public void setGroupe(String groupe) {
        this.groupe = groupe;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Timestamp getDateAction() {
        return dateAction;
    }

    public void setDateAction(Timestamp dateAction) {
        this.dateAction = dateAction;
    }
}
