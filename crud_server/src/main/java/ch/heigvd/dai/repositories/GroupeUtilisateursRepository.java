package ch.heigvd.dai.repositories;

import ch.heigvd.dai.models.GroupeUtilisateurs;
import ch.heigvd.dai.config.DatabaseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class GroupeUtilisateursRepository {
    private static final Logger logger = LoggerFactory.getLogger(GroupeUtilisateursRepository.class);

    public GroupeUtilisateurs getGroupeByNom(String nom) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection()) {
            // Récupérer les détails du groupe
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM projet.GroupeUtilisateurs WHERE nom = ?");
            stmt.setString(1, nom);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                GroupeUtilisateurs groupe = new GroupeUtilisateurs(
                        rs.getString("nom"),
                        rs.getDate("dateCreation").toLocalDate(),
                        rs.getString("administrateur")
                );

                // Récupérer les utilisateurs du groupe
                PreparedStatement stmtUsers = conn.prepareStatement(
                        "SELECT utilisateur FROM projet.appartient_a WHERE groupe = ?"
                );
                stmtUsers.setString(1, nom);
                ResultSet rsUsers = stmtUsers.executeQuery();

                List<String> utilisateurs = new ArrayList<>();
                while (rsUsers.next()) {
                    utilisateurs.add(rsUsers.getString("utilisateur"));
                }

                // Ajouter la liste des utilisateurs au groupe
                groupe.setUtilisateurs(utilisateurs);

                return groupe;
            }
        } catch (Exception e) {
            logger.error("Error retrieving group by name: {}", nom, e);
        }
        return null;
    }


    public void ajouterGroupe(GroupeUtilisateurs groupe) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO projet.GroupeUtilisateurs (nom, dateCreation, administrateur) VALUES (?, ?, ?)")) {
            stmt.setString(1, groupe.getNom());
            stmt.setObject(2, groupe.getDateCreation());
            stmt.setString(3, groupe.getAdministrateur());
            stmt.executeUpdate();
        } catch (Exception e) {
            logger.error("Error adding group", e);
        }
    }

    public void mettreAJourGroupe(GroupeUtilisateurs groupe) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE projet.GroupeUtilisateurs SET dateCreation = ?, administrateur = ? WHERE nom = ?")) {
            stmt.setObject(1, groupe.getDateCreation());
            stmt.setString(2, groupe.getAdministrateur());
            stmt.setString(3, groupe.getNom());
            stmt.executeUpdate();
        } catch (Exception e) {
            logger.error("Error updating group", e);
        }
    }

    public void supprimerGroupe(String nom) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM projet.GroupeUtilisateurs WHERE nom = ?")) {
            stmt.setString(1, nom);
            stmt.executeUpdate();
        } catch (Exception e) {
            logger.error("Error deleting group", e);
        }
    }

    public List<GroupeUtilisateurs> getGroupes() {
        List<GroupeUtilisateurs> groupes = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM projet.GroupeUtilisateurs");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                groupes.add(new GroupeUtilisateurs(
                        rs.getString("nom"),
                        rs.getDate("dateCreation").toLocalDate(),
                        rs.getString("administrateur")
                ));
            }
        } catch (Exception e) {
            logger.error("Error retrieving groups", e);
        }
        return groupes;
    }

    public List<GroupeUtilisateurs> getGroupesByUtilisateur(String utilisateur) {
        List<GroupeUtilisateurs> groupes = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM projet.GroupeUtilisateurs WHERE administrateur = ?")) {
            stmt.setString(1, utilisateur);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                groupes.add(new GroupeUtilisateurs(
                        rs.getString("nom"),
                        rs.getDate("dateCreation").toLocalDate(),
                        rs.getString("administrateur")
                ));
            }
        } catch (Exception e) {
            logger.error("Error retrieving groups by user", e);
        }
        return groupes;
    }
}
