package ch.heigvd.dai.repositories;

import ch.heigvd.dai.models.Utilisateur;
import ch.heigvd.dai.config.DatabaseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;


public class UtilisateurRepository {
    private static final Logger logger = LoggerFactory.getLogger(UtilisateurRepository.class);

    public Utilisateur getUtilisateurByNomUtilisateur(String nomUtilisateur) {
        logger.info("Retrieving user by nomUtilisateur: {}", nomUtilisateur);
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM projet.Utilisateur WHERE nomUtilisateur = ?")) {
            stmt.setString(1, nomUtilisateur);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                logger.info("User found: {}", nomUtilisateur);
                return new Utilisateur(
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("rue"),
                        rs.getString("noRue"),
                        rs.getString("npa"),
                        rs.getString("lieu"),
                        rs.getDate("dateNaissance") != null ? rs.getDate("dateNaissance").toLocalDate() : null,
                        rs.getString("nomUtilisateur"),
                        rs.getString("motDePasse"),
                        rs.getString("statutCompte"),
                        rs.getDate("derniereConnexionDate") != null ? rs.getDate("derniereConnexionDate").toLocalDate() : null,
                        rs.getTime("derniereConnexionHeure") != null ? rs.getTime("derniereConnexionHeure").toLocalTime() : null
                );
            }
            logger.info("User not found: {}", nomUtilisateur);
        } catch (Exception e) {
            logger.error("Error retrieving user by nomUtilisateur: {}", nomUtilisateur, e);
        }
        return null;
    }

    public void ajouterUtilisateur(Utilisateur utilisateur) {
        if (utilisateur == null) {
            throw new IllegalArgumentException("Utilisateur cannot be null");
        }

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO projet.Utilisateur (nom, prenom, rue, noRue, npa, lieu, dateNaissance, nomUtilisateur, motDePasse, statutCompte, derniereConnexionDate, derniereConnexionHeure) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            stmt.setString(1, utilisateur.getNom());
            stmt.setString(2, utilisateur.getPrenom());
            stmt.setString(3, utilisateur.getRue());
            stmt.setString(4, utilisateur.getNoRue());
            stmt.setString(5, utilisateur.getNpa());
            stmt.setString(6, utilisateur.getLieu());
            stmt.setObject(7, utilisateur.getDateNaissance());
            stmt.setString(8, utilisateur.getNomUtilisateur());
            stmt.setString(9, utilisateur.getMotDePasse());
            stmt.setString(10, utilisateur.getStatutCompte());
            stmt.setObject(11, utilisateur.getDerniereConnexionDate() != null ? utilisateur.getDerniereConnexionDate() : null);
            stmt.setObject(12, utilisateur.getDerniereConnexionHeure() != null ? utilisateur.getDerniereConnexionHeure() : null);
            stmt.executeUpdate();
        } catch (Exception e) {
            logger.error("Error in ajouterUtilisateur", e);
        }
    }

    public void mettreAJourUtilisateur(Utilisateur utilisateur) {
        if (utilisateur == null || utilisateur.getNomUtilisateur() == null || utilisateur.getNomUtilisateur().isEmpty()) {
            throw new IllegalArgumentException("Utilisateur or nomUtilisateur cannot be null or empty");
        }

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE projet.Utilisateur SET nom = ?, prenom = ?, rue = ?, noRue = ?, npa = ?, lieu = ?, dateNaissance = ?, motDePasse = ?, statutCompte = ?, derniereConnexionDate = ?, derniereConnexionHeure = ? " +
                             "WHERE nomUtilisateur = ?")) {
            stmt.setString(1, utilisateur.getNom());
            stmt.setString(2, utilisateur.getPrenom());
            stmt.setString(3, utilisateur.getRue());
            stmt.setString(4, utilisateur.getNoRue());
            stmt.setString(5, utilisateur.getNpa());
            stmt.setString(6, utilisateur.getLieu());
            stmt.setObject(7, utilisateur.getDateNaissance());
            stmt.setString(8, utilisateur.getMotDePasse());
            stmt.setString(9, utilisateur.getStatutCompte());
            stmt.setObject(10, utilisateur.getDerniereConnexionDate() != null ? utilisateur.getDerniereConnexionDate() : null);
            stmt.setObject(11, utilisateur.getDerniereConnexionHeure() != null ? utilisateur.getDerniereConnexionHeure() : null);
            stmt.setString(12, utilisateur.getNomUtilisateur());
            stmt.executeUpdate();
        } catch (Exception e) {
            logger.error("Error in mettreAJourUtilisateur", e);
        }
    }

    public void supprimerUtilisateur(String nomUtilisateur) {
        if (nomUtilisateur == null || nomUtilisateur.isEmpty()) {
            throw new IllegalArgumentException("nomUtilisateur cannot be null or empty");
        }

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM projet.Utilisateur WHERE nomUtilisateur = ?")) {
            stmt.setString(1, nomUtilisateur);
            stmt.executeUpdate();
        } catch (Exception e) {
            logger.error("Error in supprimerUtilisateur", e);
        }
    }

    public Utilisateur[] getUtilisateurs() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM projet.Utilisateur");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setNom(rs.getString("nom"));
                utilisateur.setPrenom(rs.getString("prenom"));
                utilisateur.setRue(rs.getString("rue"));
                utilisateur.setNoRue(rs.getString("noRue"));
                utilisateur.setNpa(rs.getString("npa"));
                utilisateur.setLieu(rs.getString("lieu"));

                // Vérification avant conversion de dateNaissance
                Date dateNaissanceSql = rs.getDate("dateNaissance");
                if (dateNaissanceSql != null) {
                    utilisateur.setDateNaissance(dateNaissanceSql.toLocalDate());
                }

                utilisateur.setNomUtilisateur(rs.getString("nomUtilisateur"));
                utilisateur.setMotDePasse(rs.getString("motDePasse"));
                utilisateur.setStatutCompte(rs.getString("statutCompte"));

                // Vérification avant conversion de derniereConnexionDate
                Date derniereConnexionDateSql = rs.getDate("derniereConnexionDate");
                if (derniereConnexionDateSql != null) {
                    utilisateur.setDerniereConnexionDate(derniereConnexionDateSql.toLocalDate());
                }

                // Vérification avant conversion de derniereConnexionHeure
                Time derniereConnexionHeureSql = rs.getTime("derniereConnexionHeure");
                if (derniereConnexionHeureSql != null) {
                    utilisateur.setDerniereConnexionHeure(derniereConnexionHeureSql.toLocalTime());
                }

                utilisateurs.add(utilisateur);
            }


            return utilisateurs.toArray(new Utilisateur[0]);

        } catch (Exception e) {
            logger.error("Error in getUtilisateurs", e);
        }
        return null;
    }

}
