package ch.heigvd.dai.repositories;

import ch.heigvd.dai.models.PointDeDonnees;
import ch.heigvd.dai.config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PointDeDonneesRepository {

    public List<PointDeDonnees> getPointsDeDonneesInRange(int serieId, String startDate, String endDate) {
        List<PointDeDonnees> points = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT valeurs, timestamp, serie_id " +
                             "FROM projet.PointDeDonnees " +
                             "WHERE serie_id = ? AND timestamp BETWEEN ? AND ?")) {
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

    public List<PointDeDonnees> getLimitedPointsDeDonnees(int serieId, int limit) {
        List<PointDeDonnees> points = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT valeurs, timestamp, serie_id " +
                             "FROM projet.PointDeDonnees " +
                             "WHERE serie_id = ? " +
                             "ORDER BY timestamp DESC " +
                             "LIMIT ?")) {
            stmt.setInt(1, serieId);
            stmt.setInt(2, limit);
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

    public List<PointDeDonnees> getPointsDeDonneesForAppareil(String ip, String startDate, String endDate) {
        List<PointDeDonnees> points = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT pd.valeurs, pd.timestamp, pd.serie_id " +
                             "FROM projet.PointDeDonnees pd " +
                             "JOIN projet.Serie s ON pd.serie_id = s.id " +
                             "WHERE s.appareil_ip = ? AND pd.timestamp BETWEEN ? AND ?")) {
            stmt.setString(1, ip);
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

    public List<PointDeDonnees> getLimitedPointsDeDonneesForAppareil(String ip, int limit) {
        List<PointDeDonnees> points = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT pd.valeurs, pd.timestamp, pd.serie_id " +
                             "FROM projet.PointDeDonnees pd " +
                             "JOIN projet.Serie s ON pd.serie_id = s.id " +
                             "WHERE s.appareil_ip = ? " +
                             "ORDER BY pd.timestamp DESC " +
                             "LIMIT ?")) {
            stmt.setString(1, ip);
            stmt.setInt(2, limit);
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

    public Map<String, Double> getStatisticsForLimitedPoints(int serieId, int limit) {
        Map<String, Double> statistics = new HashMap<>();
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT MAX(valeurs) AS max_val, " +
                             "MIN(valeurs) AS min_val, " +
                             "AVG(valeurs) AS avg_val, " +
                             "PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY valeurs) AS median_val " +
                             "FROM (SELECT valeurs " +
                             "      FROM projet.PointDeDonnees " +
                             "      WHERE serie_id = ? " +
                             "      ORDER BY timestamp DESC " +
                             "      LIMIT ?) AS limited_points")) {
            stmt.setInt(1, serieId);
            stmt.setInt(2, limit);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                statistics.put("max", rs.getDouble("max_val"));
                statistics.put("min", rs.getDouble("min_val"));
                statistics.put("average", rs.getDouble("avg_val"));
                statistics.put("median", rs.getDouble("median_val"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statistics;
    }
}
