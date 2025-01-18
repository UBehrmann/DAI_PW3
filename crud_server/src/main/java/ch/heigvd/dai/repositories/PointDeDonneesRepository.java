package ch.heigvd.dai.repositories;

import ch.heigvd.dai.models.PointDeDonnees;
import ch.heigvd.dai.config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PointDeDonneesRepository {

    public List<PointDeDonnees> getPointsDeDonneesInRange(int serieId, String startDate, String endDate) {
        List<PointDeDonnees> points = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM projet.point_donnees WHERE serie_id = ? AND timestamp BETWEEN ? AND ?")) {
            stmt.setInt(1, serieId);
            stmt.setString(2, startDate);
            stmt.setString(3, endDate);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                points.add(new PointDeDonnees(
                        rs.getFloat("valeurs"),
                        rs.getTimestamp("timestamp").toLocalDateTime(),
                        rs.getInt("serie_id")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return points;
    }

    public List<PointDeDonnees> getPointsDeDonneesForGroupe(int groupeId, String startDate, String endDate) {
        List<PointDeDonnees> points = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT pd.* " +
                             "FROM projet.point_donnees pd " +
                             "JOIN projet.serie s ON pd.serie_id = s.id " +
                             "JOIN projet.appareil a ON s.appareil_id = a.id " +
                             "JOIN projet.groupe_appareil ga ON a.id = ga.appareil_id " +
                             "WHERE ga.groupe_id = ? AND pd.timestamp BETWEEN ? AND ?")) {
            stmt.setInt(1, groupeId);
            stmt.setString(2, startDate);
            stmt.setString(3, endDate);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                points.add(new PointDeDonnees(
                        rs.getFloat("valeurs"),
                        rs.getTimestamp("timestamp").toLocalDateTime(),
                        rs.getInt("serie_id")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return points;
    }
}
