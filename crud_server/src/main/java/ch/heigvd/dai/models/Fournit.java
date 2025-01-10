package ch.heigvd.dai.models;

public class Fournit {
    private int serieId; // Clé étrangère vers Serie
    private String type; // Clé étrangère vers TypeDeDonnees

    public Fournit(int serieId, String type) {
        this.serieId = serieId;
        this.type = type;
    }

    // Getters et Setters
    public int getSerieId() {
        return serieId;
    }

    public void setSerieId(int serieId) {
        this.serieId = serieId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
