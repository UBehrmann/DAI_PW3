package ch.heigvd.dai.repositories;

import ch.heigvd.dai.models.EstComposeDe;
import ch.heigvd.dai.config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstComposeDeRepository {

    public List<String> getAppareilsDansGroupe(int groupeId) {
        List<String> appareils = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT a.nom " +
                             "FROM projet.Appareil a " +
                             "JOIN projet.est_compose_de ecd ON a.ip = ecd.ip " +
                             "WHERE ecd.groupe = ?")) {
            stmt.setInt(1, groupeId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                appareils.add(rs.getString("nom"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appareils;
    }

    public void ajouterAppareilAuGroupe(EstComposeDe estComposeDe) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO projet.est_compose_de (ip, groupe) VALUES (?, ?)")) {
            stmt.setString(1, estComposeDe.getIp());
            stmt.setInt(2, estComposeDe.getGroupe());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void retirerAppareilDuGroupe(String ip, int groupeId) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "DELETE FROM projet.est_compose_de WHERE ip = ? AND groupe = ?")) {
            stmt.setString(1, ip);
            stmt.setInt(2, groupeId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
