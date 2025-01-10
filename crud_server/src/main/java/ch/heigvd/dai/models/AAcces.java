package ch.heigvd.dai.models;

public class AAcces {
    private String groupe; // Clé étrangère vers GroupeUtilisateurs
    private String ip; // Clé étrangère vers Appareil

    public AAcces(String groupe, String ip) {
        this.groupe = groupe;
        this.ip = ip;
    }

    // Getters et Setters
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
}
