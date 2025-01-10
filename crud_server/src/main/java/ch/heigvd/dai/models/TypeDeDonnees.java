package ch.heigvd.dai.models;

public class TypeDeDonnees {
    private String nom; // Clé primaire
    private String symboleDonnees;

    public TypeDeDonnees(String nom, String symboleDonnees) {
        this.nom = nom;
        this.symboleDonnees = symboleDonnees;
    }

    // Getters et Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getSymboleDonnees() {
        return symboleDonnees;
    }

    public void setSymboleDonnees(String symboleDonnees) {
        this.symboleDonnees = symboleDonnees;
    }
}
