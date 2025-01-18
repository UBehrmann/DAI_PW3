package ch.heigvd.dai.repositories;

import ch.heigvd.dai.models.Alerte;
import ch.heigvd.dai.models.Configuration;
import ch.heigvd.dai.config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SerieRepository {

    public List<Configuration> getConfigurations(int idSerie) {
        List<Configuration> configurations = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM projet.configuration WHERE serie_id = ?")) {
            stmt.setInt(1, idSerie);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                configurations.add(new Configuration(
                        rs.getInt("id"),
                        rs.getFloat("seuilMinWarning"),
                        rs.getFloat("seuilMaxWarning"),
                        rs.getFloat("seuilMaxAlarme"),
                        rs.getFloat("seuilMinAlarme")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return configurations;
    }

    public void ajouterConfiguration(int idSerie, Configuration configuration) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO projet.configuration (seuilMinWarning, seuilMaxWarning, seuilMinAlarme, seuilMaxAlarme, serie_id) " +
                             "VALUES (?, ?, ?, ?, ?)")) {
            stmt.setFloat(1, configuration.getSeuilMinWarning());
            stmt.setFloat(2, configuration.getSeuilMaxWarning());
            stmt.setFloat(3, configuration.getSeuilMinAlarme());
            stmt.setFloat(4, configuration.getSeuilMaxAlarme());
            stmt.setInt(5, idSerie);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modifierConfiguration(int idSerie, int configId, Configuration configuration) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE projet.configuration SET seuilMinWarning = ?, seuilMaxWarning = ?, " +
                             "seuilMinAlarme = ?, seuilMaxAlarme = ? WHERE id = ? AND serie_id = ?")) {
            stmt.setFloat(1, configuration.getSeuilMinWarning());
            stmt.setFloat(2, configuration.getSeuilMaxWarning());
            stmt.setFloat(3, configuration.getSeuilMinAlarme());
            stmt.setFloat(4, configuration.getSeuilMaxAlarme());
            stmt.setInt(5, configId);
            stmt.setInt(6, idSerie);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Alerte> getAlertes(int idSerie) {
        List<Alerte> alertes = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM projet.alerte WHERE serie_id = ?")) {
            stmt.setInt(1, idSerie);
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

    public void ajouterAlerte(int idSerie, Alerte alerte) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO projet.alerte (type, niveau, timestamp, serie_id) " +
                             "VALUES (?, ?, ?, ?)")) {
            stmt.setString(1, alerte.getType());
            stmt.setInt(2, alerte.getNiveau());
            stmt.setTimestamp(3, Timestamp.valueOf(alerte.getTimestamp()));
            stmt.setInt(4, idSerie);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modifierAlerte(int idSerie, Alerte alerte) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE projet.alerte SET type = ?, niveau = ? WHERE timestamp = ? AND serie_id = ?")) {
            stmt.setString(1, alerte.getType());
            stmt.setInt(2, alerte.getNiveau());
            stmt.setTimestamp(3, Timestamp.valueOf(alerte.getTimestamp()));
            stmt.setInt(4, idSerie);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void supprimerAlerte(int idSerie, String timestamp) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "DELETE FROM projet.alerte WHERE timestamp = ? AND serie_id = ?")) {
            stmt.setTimestamp(1, Timestamp.valueOf(timestamp));
            stmt.setInt(2, idSerie);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
