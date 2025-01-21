package ch.heigvd.dai.repositories;

import ch.heigvd.dai.models.AppartientA;
import ch.heigvd.dai.config.DatabaseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AppartientARepository {
    private static final Logger logger = LoggerFactory.getLogger(AppartientARepository.class);

    public void ajouterUtilisateurDansGroupe(AppartientA appartientA) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO projet.appartient_a (utilisateur, groupe) VALUES (?, ?)")) {
            stmt.setString(1, appartientA.getUtilisateur());
            stmt.setString(2, appartientA.getGroupe());
            stmt.executeUpdate();
        } catch (Exception e) {
            logger.error("Error adding user to group", e);
        }
    }

    public void supprimerUtilisateurDeGroupe(String utilisateur, String groupe) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM projet.appartient_a WHERE utilisateur = ? AND groupe = ?")) {
            stmt.setString(1, utilisateur);
            stmt.setString(2, groupe);
            stmt.executeUpdate();
        } catch (Exception e) {
            logger.error("Error deleting user from group", e);
        }
    }
}
