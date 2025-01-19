package ch.heigvd.dai.repositories;

import ch.heigvd.dai.models.Appareil;
import ch.heigvd.dai.models.GroupeCapteurs;
import ch.heigvd.dai.config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupeCapteursRepository {

    public GroupeCapteurs getGroupeCapteursParId(int id) {
        GroupeCapteurs groupe = null;
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT id, nom, description FROM projet.GroupeCapteurs WHERE id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                groupe = new GroupeCapteurs(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return groupe;
    }

    public void ajouterGroupeCapteurs(String nom, Date dateCreation, String administrateur) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO projet.GroupeUtilisateurs (nom, dateCreation, administrateur) VALUES (?, ?, ?)")) {
            stmt.setString(1, nom);
            stmt.setDate(2, dateCreation);
            stmt.setString(3, administrateur);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void supprimerGroupeCapteurs(int id) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM projet.GroupeCapteurs WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modifierGroupeCapteurs(int id, GroupeCapteurs groupeCapteurs) {
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE projet.GroupeCapteurs SET nom = ?, description = ? WHERE id = ?")) {
            stmt.setString(1, groupeCapteurs.getNom());
            stmt.setString(2, groupeCapteurs.getDescription());
            stmt.setInt(3, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<GroupeCapteurs> getGroupesCapteursParUtilisateur(String username) {
        List<GroupeCapteurs> groupes = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT gc.id, gc.nom, gc.description " +
                             "FROM projet.GroupeCapteurs gc " +
                             "JOIN projet.appartient_a aa ON gc.nom = aa.groupe " +
                             "WHERE aa.utilisateur = ?")) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                groupes.add(new GroupeCapteurs(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return groupes;
    }


    public List<Appareil> getAppareilsParUtilisateurEtType(String username, String type) {
        List<Appareil> appareils = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT a.nom, a.ip, a.type, a.status " +
                             "FROM projet.Appareil a " +
                             "JOIN projet.est_compose_de ecd ON a.ip = ecd.ip " +
                             "JOIN projet.GroupeCapteurs gc ON ecd.groupe = gc.id " +
                             "JOIN projet.appartient_a aa ON gc.id = aa.groupe " +
                             "WHERE aa.utilisateur = ? AND a.type = ?")) {
            stmt.setString(1, username);
            stmt.setString(2, type);
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

    public List<Appareil> getAppareilsDansGroupe(int groupeId) {
        List<Appareil> appareils = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT a.nom, a.ip, a.type, a.status " +
                             "FROM projet.Appareil a " +
                             "JOIN projet.est_compose_de ecd ON a.ip = ecd.ip " +
                             "WHERE ecd.groupe = ?")) {
            stmt.setInt(1, groupeId);
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

    public List<Appareil> getAppareilsDansGroupeParType(int groupeId, String type) {
        List<Appareil> appareils = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT a.nom, a.ip, a.type, a.status " +
                             "FROM projet.Appareil a " +
                             "JOIN projet.est_compose_de ecd ON a.ip = ecd.ip " +
                             "WHERE ecd.groupe = ? AND a.type = ?")) {
            stmt.setInt(1, groupeId);
            stmt.setString(2, type);
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

    public List<GroupeCapteurs> getGroupesCapteurs() {
        List<GroupeCapteurs> groupes = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM projet.GroupeCapteurs");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                groupes.add(new GroupeCapteurs(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return groupes;
    }
}
