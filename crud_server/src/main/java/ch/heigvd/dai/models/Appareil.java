package ch.heigvd.dai.models;

public class Appareil {
    private String nom;
    private String ip; // Clé primaire
    private String type;
    private String status;

    public Appareil(String nom, String ip, String type, String status) {
        this.nom = nom;
        this.ip = ip;
        this.type = type;
        this.status = status;
    }

    // Getters et Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
