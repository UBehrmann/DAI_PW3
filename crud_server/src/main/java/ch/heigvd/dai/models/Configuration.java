package ch.heigvd.dai.models;

public class Configuration {
    private int id; // Clé primaire
    private float seuilMinWarning;
    private float seuilMaxWarning;
    private float seuilMaxAlarme;
    private float seuilMinAlarme;

    public Configuration(int id, float seuilMinWarning, float seuilMaxWarning,
                         float seuilMaxAlarme, float seuilMinAlarme) {
        this.id = id;
        this.seuilMinWarning = seuilMinWarning;
        this.seuilMaxWarning = seuilMaxWarning;
        this.seuilMaxAlarme = seuilMaxAlarme;
        this.seuilMinAlarme = seuilMinAlarme;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getSeuilMinWarning() {
        return seuilMinWarning;
    }

    public void setSeuilMinWarning(float seuilMinWarning) {
        this.seuilMinWarning = seuilMinWarning;
    }

    public float getSeuilMaxWarning() {
        return seuilMaxWarning;
    }

    public void setSeuilMaxWarning(float seuilMaxWarning) {
        this.seuilMaxWarning = seuilMaxWarning;
    }

    public float getSeuilMaxAlarme() {
        return seuilMaxAlarme;
    }

    public void setSeuilMaxAlarme(float seuilMaxAlarme) {
        this.seuilMaxAlarme = seuilMaxAlarme;
    }

    public float getSeuilMinAlarme() {
        return seuilMinAlarme;
    }

    public void setSeuilMinAlarme(float seuilMinAlarme) {
        this.seuilMinAlarme = seuilMinAlarme;
    }
}
