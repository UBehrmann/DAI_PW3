package ch.heigvd.dai.controllers;

import io.javalin.Javalin;
import io.javalin.http.Context;
import ch.heigvd.dai.models.Utilisateur;
import ch.heigvd.dai.services.UtilisateurService;

import java.time.LocalDate;
import java.time.LocalTime;

public class UtilisateurController {
    private static final UtilisateurService utilisateurService = new UtilisateurService();

    public static void registerRoutes(Javalin app) {
        // Récupérer un utilisateur par son nomUtilisateur
        app.get("/api/utilisateurs/{nomUtilisateur}", UtilisateurController::getUtilisateurByNomUtilisateur);

        // Ajouter un nouvel utilisateur
        app.post("/api/utilisateurs", UtilisateurController::ajouterUtilisateur);

        // Mettre à jour un utilisateur
        app.put("/api/utilisateurs/{nomUtilisateur}", UtilisateurController::mettreAJourUtilisateur);

        // Supprimer un utilisateur
        app.delete("/api/utilisateurs/{nomUtilisateur}", UtilisateurController::supprimerUtilisateur);

        // Récupérer tous les utilisateurs
        app.get("/api/utilisateurs", UtilisateurController::getUtilisateurs);
    }

    private static void getUtilisateurByNomUtilisateur(Context ctx) {
        String nomUtilisateur = ctx.pathParam("nomUtilisateur");
        Utilisateur utilisateur = utilisateurService.getUtilisateurByNomUtilisateur(nomUtilisateur);
        if (utilisateur != null) {
            ctx.json(utilisateur);
        } else {
            ctx.status(404).result("Utilisateur introuvable");
        }
    }

    private static void ajouterUtilisateur(Context ctx) {
        Utilisateur utilisateur = ctx.bodyAsClass(Utilisateur.class);
        utilisateurService.ajouterUtilisateur(utilisateur);
        ctx.status(201).result("Utilisateur ajouté");
    }

    private static void mettreAJourUtilisateur(Context ctx) {
        String nomUtilisateur = ctx.pathParam("nomUtilisateur");
        Utilisateur existingUtilisateur = utilisateurService.getUtilisateurByNomUtilisateur(nomUtilisateur);
        if (existingUtilisateur == null) {
            ctx.status(404).result("Utilisateur introuvable");
            return;
        }
        Utilisateur utilisateur = ctx.bodyAsClass(Utilisateur.class);
        utilisateur.setNomUtilisateur(nomUtilisateur); // Assure que l'identifiant reste inchangé
        utilisateurService.mettreAJourUtilisateur(utilisateur);
        ctx.status(200).result("Utilisateur mis à jour");
    }

    private static void supprimerUtilisateur(Context ctx) {
        String nomUtilisateur = ctx.pathParam("nomUtilisateur");
        Utilisateur existingUtilisateur = utilisateurService.getUtilisateurByNomUtilisateur(nomUtilisateur);
        if (existingUtilisateur == null) {
            ctx.status(404).result("Utilisateur introuvable");
            return;
        }
        utilisateurService.supprimerUtilisateur(nomUtilisateur);
        ctx.status(200).result("Utilisateur supprimé");
    }

    private static void getUtilisateurs(Context ctx) {
        Utilisateur[] utilisateurs = utilisateurService.getUtilisateurs();
        if (utilisateurs != null && utilisateurs.length > 0) {
            ctx.json(utilisateurs); // Retourner les utilisateurs en JSON
        } else {
            ctx.status(404).result("Aucun utilisateur trouvé");
        }
    }

}
