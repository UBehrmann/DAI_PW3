package ch.heigvd.dai.repositories;

import ch.heigvd.dai.models.Appareil;
import ch.heigvd.dai.config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AAccesRepository {

    public List<Appareil> getAppareilsForGroupe(String groupe) {
        List<Appareil> appareils = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT a.nom, a.ip, a.type, a.status " +
                             "FROM projet.Appareil a " +
                             "JOIN projet.a_acces aa ON a.ip = aa.ip " +
                             "WHERE aa.groupe = ?")) {
            stmt.setString(1, groupe);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                appareils.add(new Appareil(
                        rs.getString("nom"),
                        rs.getString("ip"),
                        rs.getString("type"),
                        rs.getString("status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appareils;
    }

    public void ajouterAcces(String groupe, String ip) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO projet.a_acces (groupe, ip) VALUES (?, ?)")) {
            stmt.setString(1, groupe);
            stmt.setString(2, ip);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void supprimerAcces(String groupe, String ip) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "DELETE FROM projet.a_acces WHERE groupe = ? AND ip = ?")) {
            stmt.setString(1, groupe);
            stmt.setString(2, ip);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
