package ch.heigvd.dai.models;

import java.sql.Timestamp;

public class LogGroupe {
    private int id;
    private String groupe;
    private String action;
    private String details; // JSONB is represented as a String
    private Timestamp dateAction;

    // Constructors
    public LogGroupe() {
    }

    public LogGroupe(int id, String groupe, String action, String details, Timestamp dateAction) {
        this.id = id;
        this.groupe = groupe;
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
