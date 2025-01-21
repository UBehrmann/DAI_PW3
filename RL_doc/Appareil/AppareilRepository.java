package ch.heigvd.dai.repositories;

import ch.heigvd.dai.models.Appareil;
import ch.heigvd.dai.models.Serie;
import ch.heigvd.dai.config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppareilRepository {

    public List<Appareil> getAllAppareils() {
        List<Appareil> appareils = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM projet.Appareil")) {
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

    public List<Appareil> getAppareilsForUser(String username) {
        List<Appareil> appareils = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT a.nom, a.ip, a.type, a.status " +
                             "FROM projet.Utilisateur u " +
                             "JOIN projet.appartient_a aa ON u.nomUtilisateur = aa.utilisateur " +
                             "JOIN projet.a_acces aac ON aa.groupe = aac.groupe " +
                             "join projet.appareil a ON a.ip = aac.ip " +
                             "WHERE u.nomUtilisateur = ?")) {
            stmt.setString(1, username);
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

    public Appareil getAppareilByIp(String ip) {
        Appareil appareil = null;
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM projet.Appareil WHERE ip = ?")) {
            stmt.setString(1, ip);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                appareil = new Appareil(
                        rs.getString("nom"),
                        rs.getString("ip"),
                        rs.getString("type"),
                        rs.getString("status")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appareil;
    }

    public List<Serie> getSeriesForAppareil(String ip) {
        List<Serie> series = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT s.id, s.nom, s.config_id, s.appareil_ip " +
                             "FROM projet.Serie s WHERE s.appareil_ip = ?")) {
            stmt.setString(1, ip);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                series.add(new Serie(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getInt("config_id"),
                        rs.getString("appareil_ip")
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
                     "INSERT INTO projet.Appareil (nom, ip, type, status) VALUES (?, ?, ?, ?)")) {
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
                     "UPDATE projet.Appareil SET nom = ?, type = ?, status = ? WHERE ip = ?")) {
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
             PreparedStatement stmt = conn.prepareStatement(
                     "DELETE FROM projet.Appareil WHERE ip = ?")) {
            stmt.setString(1, ip);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
