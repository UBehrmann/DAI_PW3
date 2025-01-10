package ch.heigvd.dai.models;

import java.time.LocalDateTime;

public class PointDeDonnees {
    private float valeurs;
    private LocalDateTime timestamp; // Clé primaire
    private int serieId; // Clé étrangère vers Serie

    public PointDeDonnees(float valeurs, LocalDateTime timestamp, int serieId) {
        this.valeurs = valeurs;
        this.timestamp = timestamp;
        this.serieId = serieId;
    }

    // Getters et Setters
    public float getValeurs() {
        return valeurs;
    }

    public void setValeurs(float valeurs) {
        this.valeurs = valeurs;
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
