package ch.heigvd.dai.repositories;

import ch.heigvd.dai.models.Utilisateur;
import ch.heigvd.dai.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UtilisateurRepository {
    public Utilisateur getUtilisateurById(int id) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Utilisateur WHERE id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Utilisateur(
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("nomUtilisateur"),
                        rs.getString("motDePasse")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void ajouterUtilisateur(Utilisateur utilisateur) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO Utilisateur (nom, prenom, nomUtilisateur, motDePasse) VALUES (?, ?, ?, ?)")) {
            stmt.setString(1, utilisateur.getNom());
            stmt.setString(2, utilisateur.getPrenom());
            stmt.setString(3, utilisateur.getNomUtilisateur());
            stmt.setString(4, utilisateur.getMotDePasse());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Autres méthodes...
}
