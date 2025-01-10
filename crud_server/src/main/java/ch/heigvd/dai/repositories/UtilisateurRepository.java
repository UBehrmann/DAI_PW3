package ch.heigvd.dai.repositories;

import ch.heigvd.dai.models.Utilisateur;
import ch.heigvd.dai.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UtilisateurRepository {

    // Méthode pour récupérer un utilisateur par son nomUtilisateur
    public Utilisateur getUtilisateurByNomUtilisateur(String nomUtilisateur) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Utilisateur WHERE nomUtilisateur = ?")) {
            stmt.setString(1, nomUtilisateur);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Utilisateur(
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("rue"),
                        rs.getString("noRue"),
                        rs.getString("npa"),
                        rs.getString("lieu"),
                        rs.getDate("dateNaissance").toLocalDate(),
                        rs.getString("nomUtilisateur"),
                        rs.getString("motDePasse"),
                        rs.getString("statutCompte"),
                        rs.getDate("derniereConnexionDate").toLocalDate(),
                        rs.getTime("derniereConnexionHeure").toLocalTime()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Méthode pour ajouter un utilisateur
    public void ajouterUtilisateur(Utilisateur utilisateur) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO Utilisateur (nom, prenom, rue, noRue, npa, lieu, dateNaissance, nomUtilisateur, motDePasse, statutCompte, derniereConnexionDate, derniereConnexionHeure) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            stmt.setString(1, utilisateur.getNom());
            stmt.setString(2, utilisateur.getPrenom());
            stmt.setString(3, utilisateur.getRue());
            stmt.setString(4, utilisateur.getNoRue());
            stmt.setString(5, utilisateur.getNpa());
            stmt.setString(6, utilisateur.getLieu());
            stmt.setObject(7, utilisateur.getDateNaissance()); // Utiliser setObject pour gérer les LocalDate
            stmt.setString(8, utilisateur.getNomUtilisateur());
            stmt.setString(9, utilisateur.getMotDePasse());
            stmt.setString(10, utilisateur.getStatutCompte());
            stmt.setObject(11, utilisateur.getDerniereConnexionDate());
            stmt.setObject(12, utilisateur.getDerniereConnexionHeure());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Méthode pour mettre à jour les informations d'un utilisateur
    public void mettreAJourUtilisateur(Utilisateur utilisateur) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE Utilisateur SET nom = ?, prenom = ?, rue = ?, noRue = ?, npa = ?, lieu = ?, dateNaissance = ?, motDePasse = ?, statutCompte = ?, derniereConnexionDate = ?, derniereConnexionHeure = ? " +
                             "WHERE nomUtilisateur = ?")) {
            stmt.setString(1, utilisateur.getNom());
            stmt.setString(2, utilisateur.getPrenom());
            stmt.setString(3, utilisateur.getRue());
            stmt.setString(4, utilisateur.getNoRue());
            stmt.setString(5, utilisateur.getNpa());
            stmt.setString(6, utilisateur.getLieu());
            stmt.setObject(7, utilisateur.getDateNaissance());
            stmt.setString(8, utilisateur.getMotDePasse());
            stmt.setString(9, utilisateur.getStatutCompte());
            stmt.setObject(10, utilisateur.getDerniereConnexionDate());
            stmt.setObject(11, utilisateur.getDerniereConnexionHeure());
            stmt.setString(12, utilisateur.getNomUtilisateur());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Méthode pour supprimer un utilisateur par son nomUtilisateur
    public void supprimerUtilisateur(String nomUtilisateur) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM Utilisateur WHERE nomUtilisateur = ?")) {
            stmt.setString(1, nomUtilisateur);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
