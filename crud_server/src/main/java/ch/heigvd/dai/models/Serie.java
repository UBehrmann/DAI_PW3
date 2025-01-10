package ch.heigvd.dai.models;

public class Serie {
    private int id; // Clé primaire
    private String nom;
    private Integer configId; // Clé étrangère vers Configuration
    private String appareilIp; // Clé étrangère vers Appareil

    public Serie(int id, String nom, Integer configId, String appareilIp) {
        this.id = id;
        this.nom = nom;
        this.configId = configId;
        this.appareilIp = appareilIp;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    public String getAppareilIp() {
        return appareilIp;
    }

    public void setAppareilIp(String appareilIp) {
        this.appareilIp = appareilIp;
    }
}
