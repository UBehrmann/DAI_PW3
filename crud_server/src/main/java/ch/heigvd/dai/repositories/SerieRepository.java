package ch.heigvd.dai.repositories;

import ch.heigvd.dai.models.Alerte;
import ch.heigvd.dai.models.Configuration;
import ch.heigvd.dai.models.Serie;
import ch.heigvd.dai.config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SerieRepository {

    public Configuration getConfigurationForSerie(int id) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT c.id, c.seuilMinWarning, c.seuilMaxWarning, c.seuilMinAlarme, c.seuilMaxAlarme " +
                             "FROM projet.Configuration c " +
                             "JOIN projet.Serie s ON s.config_id = c.id " +
                             "WHERE s.id = ?")) {
            stmt.setInt(1, id);
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

    public List<Alerte> getAlertes(int id) {
        List<Alerte> alertes = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM projet.Alerte WHERE serie_id = ?")) {
            stmt.setInt(1, id);
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

    public void ajouterConfiguration(int id, Configuration configuration) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO projet.Configuration (seuilMinWarning, seuilMaxWarning, seuilMinAlarme, seuilMaxAlarme) " +
                             "VALUES (?, ?, ?, ?)")) {
            stmt.setFloat(1, configuration.getSeuilMinWarning());
            stmt.setFloat(2, configuration.getSeuilMaxWarning());
            stmt.setFloat(3, configuration.getSeuilMinAlarme());
            stmt.setFloat(4, configuration.getSeuilMaxAlarme());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modifierConfiguration(int configId, Configuration configuration) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE projet.Configuration SET seuilMinWarning = ?, seuilMaxWarning = ?, seuilMinAlarme = ?, seuilMaxAlarme = ? " +
                             "WHERE id = ?")) {
            stmt.setFloat(1, configuration.getSeuilMinWarning());
            stmt.setFloat(2, configuration.getSeuilMaxWarning());
            stmt.setFloat(3, configuration.getSeuilMinAlarme());
            stmt.setFloat(4, configuration.getSeuilMaxAlarme());
            stmt.setInt(5, configId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ajouterAlerte(int id, Alerte alerte) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO projet.Alerte (type, niveau, timestamp, serie_id) VALUES (?, ?, ?, ?)")) {
            stmt.setString(1, alerte.getType());
            stmt.setInt(2, alerte.getNiveau());
            stmt.setTimestamp(3, Timestamp.valueOf(alerte.getTimestamp()));
            stmt.setInt(4, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modifierAlerte(int id, Alerte alerte) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE projet.Alerte SET type = ?, niveau = ? WHERE timestamp = ? AND serie_id = ?")) {
            stmt.setString(1, alerte.getType());
            stmt.setInt(2, alerte.getNiveau());
            stmt.setTimestamp(3, Timestamp.valueOf(alerte.getTimestamp()));
            stmt.setInt(4, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void supprimerAlerte(int id, String timestamp) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "DELETE FROM projet.Alerte WHERE timestamp = ? AND serie_id = ?")) {
            stmt.setTimestamp(1, Timestamp.valueOf(timestamp));
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
