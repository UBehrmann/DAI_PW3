package ch.heigvd.dai.models;

public class EstComposeDe {
    private String ip; // Clé étrangère vers Appareil
    private int groupe; // Clé étrangère vers GroupeCapteurs

    public EstComposeDe(String ip, int groupe) {
        this.ip = ip;
        this.groupe = groupe;
    }

    // Getters et Setters
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getGroupe() {
        return groupe;
    }

    public void setGroupe(int groupe) {
        this.groupe = groupe;
    }
}
