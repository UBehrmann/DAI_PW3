package ch.heigvd.dai.repositories;

import ch.heigvd.dai.models.Alerte;
import ch.heigvd.dai.models.Configuration;
import ch.heigvd.dai.config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppareilRepository {

    public Configuration getConfiguration(int idAppareil) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT c.* FROM projet.configuration c " +
                             "JOIN projet.serie s ON c.id = s.config_id " +
                             "JOIN projet.appareil a ON s.appareil_id = a.id " +
                             "WHERE a.id = ?")) {
            stmt.setInt(1, idAppareil);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Configuration(
                        rs.getInt("id"),
                        rs.getFloat("seuilMinWarning"),
                        rs.getFloat("seuilMaxWarning"),
                        rs.getFloat("seuilMaxAlarme"),
                        rs.getFloat("seuilMinAlarme")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Alerte> getAlertes(int idAppareil) {
        List<Alerte> alertes = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM projet.alerte WHERE serie_id IN " +
                             "(SELECT s.id FROM projet.serie s WHERE s.appareil_id = ?)")) {
            stmt.setInt(1, idAppareil);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                alertes.add(new Alerte(
                        rs.getString("type"),
                        rs.getInt("niveau"),
                        rs.getTimestamp("timestamp").toLocalDateTime(),
                        rs.getInt("serie_id")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return alertes;
    }

    public void modifierConfiguration(int idAppareil, Configuration configuration) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE projet.configuration " +
                             "SET seuilMinWarning = ?, seuilMaxWarning = ?, seuilMinAlarme = ?, seuilMaxAlarme = ? " +
                             "WHERE id = (SELECT config_id FROM projet.serie WHERE appareil_id = ?)")) {
            stmt.setFloat(1, configuration.getSeuilMinWarning());
            stmt.setFloat(2, configuration.getSeuilMaxWarning());
            stmt.setFloat(3, configuration.getSeuilMinAlarme());
            stmt.setFloat(4, configuration.getSeuilMaxAlarme());
            stmt.setInt(5, idAppareil);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ajouterAlerte(int idAppareil, Alerte alerte) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO projet.alerte (type, niveau, timestamp, serie_id) " +
                             "VALUES (?, ?, ?, (SELECT id FROM projet.serie WHERE appareil_id = ? LIMIT 1))")) {
            stmt.setString(1, alerte.getType());
            stmt.setInt(2, alerte.getNiveau());
            stmt.setTimestamp(3, Timestamp.valueOf(alerte.getTimestamp()));
            stmt.setInt(4, idAppareil);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void supprimerAlerte(int idAppareil, String timestamp) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "DELETE FROM projet.alerte WHERE timestamp = ? AND serie_id IN " +
                             "(SELECT s.id FROM projet.serie s WHERE s.appareil_id = ?)")) {
            stmt.setTimestamp(1, Timestamp.valueOf(timestamp));
            stmt.setInt(2, idAppareil);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modifierAlerte(int idAppareil, Alerte alerte) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE projet.alerte SET type = ?, niveau = ? " +
                             "WHERE timestamp = ? AND serie_id IN " +
                             "(SELECT s.id FROM projet.serie s WHERE s.appareil_id = ?)")) {
            stmt.setString(1, alerte.getType());
            stmt.setInt(2, alerte.getNiveau());
            stmt.setTimestamp(3, Timestamp.valueOf(alerte.getTimestamp()));
            stmt.setInt(4, idAppareil);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
