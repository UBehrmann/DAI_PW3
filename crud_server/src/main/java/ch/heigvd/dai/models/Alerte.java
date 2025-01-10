package ch.heigvd.dai.models;

import java.time.LocalDateTime;

public class Alerte {
    private String type;
    private int niveau;
    private LocalDateTime timestamp; // Clé primaire
    private int serieId; // Clé étrangère vers Serie

    public Alerte(String type, int niveau, LocalDateTime timestamp, int serieId) {
        this.type = type;
        this.niveau = niveau;
        this.timestamp = timestamp;
        this.serieId = serieId;
    }

    // Getters et Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getSerieId() {
        return serieId;
    }

    public void setSerieId(int serieId) {
        this.serieId = serieId;
    }
}
