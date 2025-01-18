package ch.heigvd.dai.repositories;

import ch.heigvd.dai.models.Appareil;
import ch.heigvd.dai.models.GroupeCapteurs;
import ch.heigvd.dai.config.DatabaseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class GroupeCapteursRepository {
    private static final Logger logger = LoggerFactory.getLogger(GroupeCapteursRepository.class);

    public void ajouterGroupeCapteurs(GroupeCapteurs groupeCapteurs) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO projet.groupecapteurs (nom, description) VALUES (?, ?)")) {
            stmt.setString(1, groupeCapteurs.getNom());
            stmt.setString(2, groupeCapteurs.getDescription());
            stmt.executeUpdate();
        } catch (Exception e) {
            logger.error("Error adding groupe capteurs", e);
        }
    }

    public void supprimerGroupeCapteurs(int id) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM projet.groupecapteurs WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            logger.error("Error deleting groupe capteurs", e);
        }
    }

    public void modifierGroupeCapteurs(int id, GroupeCapteurs groupeCapteurs) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE projet.groupecapteurs SET nom = ?, description = ? WHERE id = ?")) {
            stmt.setString(1, groupeCapteurs.getNom());
            stmt.setString(2, groupeCapteurs.getDescription());
            stmt.setInt(3, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            logger.error("Error updating groupe capteurs", e);
        }
    }

    public void mettreAJourAcces(String nomGroupe) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO projet.a_acces (groupe) SELECT ? WHERE NOT EXISTS (SELECT 1 FROM projet.a_acces WHERE groupe = ?)")) {
            stmt.setString(1, nomGroupe);
            stmt.setString(2, nomGroupe);
            stmt.executeUpdate();
        } catch (Exception e) {
            logger.error("Error updating a_acces", e);
        }
    }

    public void supprimerAccesParGroupe(int idGroupe) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "DELETE FROM projet.a_acces WHERE groupe = (SELECT nom FROM projet.groupecapteurs WHERE id = ?)")) {
            stmt.setInt(1, idGroupe);
            stmt.executeUpdate();
        } catch (Exception e) {
            logger.error("Error deleting a_acces for groupe", e);
        }
    }

    public List<Appareil> getAppareilsDansGroupe(String nomGroupe) {
        List<Appareil> appareils = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT a.* FROM projet.appareil a " +
                             "JOIN projet.est_compose_de e ON a.id = e.appareil_id " +
                             "JOIN projet.groupecapteurs g ON e.groupe = g.nom " +
                             "WHERE g.nom = ?")) {
            stmt.setString(1, nomGroupe);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                appareils.add(mapResultSetToAppareil(rs));
            }
        } catch (Exception e) {
            logger.error("Error retrieving appareils in groupe", e);
        }
        return appareils;
    }

    public List<Appareil> getAppareilsDansGroupeParType(String nomGroupe, String type) {
        List<Appareil> appareils = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT a.* FROM projet.appareil a " +
                             "JOIN projet.est_compose_de e ON a.id = e.appareil_id " +
                             "JOIN projet.groupecapteurs g ON e.groupe = g.nom " +
                             "WHERE g.nom = ? AND a.type = ?")) {
            stmt.setString(1, nomGroupe);
            stmt.setString(2, type);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                appareils.add(mapResultSetToAppareil(rs));
            }
        } catch (Exception e) {
            logger.error("Error filtering appareils in groupe by type", e);
        }
        return appareils;
    }


    private Appareil mapResultSetToAppareil(ResultSet rs) throws Exception {
        Appareil appareil = new Appareil();
        appareil.setNom(rs.getString("nom"));
        appareil.setIp(rs.getString("ip"));
        appareil.setType(rs.getString("type"));
        appareil.setStatus(rs.getString("status"));
        return appareil;
    }

}
