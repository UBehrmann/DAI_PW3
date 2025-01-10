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
        app.get("/utilisateurs/{nomUtilisateur}", UtilisateurController::getUtilisateurByNomUtilisateur);

        // Ajouter un nouvel utilisateur
        app.post("/utilisateurs", UtilisateurController::ajouterUtilisateur);

        // Mettre à jour un utilisateur
        app.put("/utilisateurs/{nomUtilisateur}", UtilisateurController::mettreAJourUtilisateur);

        // Supprimer un utilisateur
        app.delete("/utilisateurs/{nomUtilisateur}", UtilisateurController::supprimerUtilisateur);
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
}
