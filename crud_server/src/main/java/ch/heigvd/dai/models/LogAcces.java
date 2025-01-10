package ch.heigvd.dai.models;

import java.sql.Timestamp;

public class LogAcces {
    private int id;
    private String groupe;
    private String ip;
    private String action;
    private Timestamp dateAction;

    // Constructors
    public LogAcces() {
    }

    public LogAcces(int id, String groupe, String ip, String action, Timestamp dateAction) {
        this.id = id;
        this.groupe = groupe;
        this.ip = ip;
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

    public String getGroupe() {
        return groupe;
    }

    public void setGroupe(String groupe) {
        this.groupe = groupe;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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
