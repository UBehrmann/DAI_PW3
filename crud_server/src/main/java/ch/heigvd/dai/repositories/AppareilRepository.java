package ch.heigvd.dai.repositories;

import ch.heigvd.dai.models.Appareil;
import ch.heigvd.dai.models.Serie;
import ch.heigvd.dai.config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppareilRepository {

    public Appareil getAppareilByIp(String ip) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM projet.appareil WHERE ip = ?")) {
            stmt.setString(1, ip);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Appareil(
                        rs.getString("nom"),
                        rs.getString("ip"),
                        rs.getString("type"),
                        rs.getString("status")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Serie> getSeriesForAppareil(String ip) {
        List<Serie> series = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM projet.serie WHERE appareil_id = " +
                             "(SELECT id FROM projet.appareil WHERE ip = ?)")) {
            stmt.setString(1, ip);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                series.add(new Serie(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getInt("config_id"),
                        rs.getString("type")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return series;
    }

    public void createAppareil(Appareil appareil) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO projet.appareil (nom, ip, type, status) VALUES (?, ?, ?, ?)")) {
            stmt.setString(1, appareil.getNom());
            stmt.setString(2, appareil.getIp());
            stmt.setString(3, appareil.getType());
            stmt.setString(4, appareil.getStatus());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateAppareil(String ip, Appareil appareil) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE projet.appareil SET nom = ?, type = ?, status = ? WHERE ip = ?")) {
            stmt.setString(1, appareil.getNom());
            stmt.setString(2, appareil.getType());
            stmt.setString(3, appareil.getStatus());
            stmt.setString(4, ip);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteAppareil(String ip) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM projet.appareil WHERE ip = ?")) {
            stmt.setString(1, ip);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
