package ch.heigvd.dai.controllers;

import io.javalin.Javalin;
import ch.heigvd.dai.models.Utilisateur;
import ch.heigvd.dai.services.UtilisateurService;

import java.util.Map;

public class UtilisateurController {
    private static final UtilisateurService utilisateurService = new UtilisateurService();

    public static void registerRoutes(Javalin app) {
        // Récupérer un utilisateur par son ID
        app.get("/utilisateurs/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Utilisateur utilisateur = utilisateurService.getUtilisateurById(id);
            if (utilisateur != null) {
                ctx.json(utilisateur);
            } else {
                ctx.status(404).result("Utilisateur introuvable");
            }
        });

        // Ajouter un nouvel utilisateur
        app.post("/utilisateurs", ctx -> {
            Map<String, String> body = ctx.bodyAsClass(Map.class); // Parse JSON as Map
            Utilisateur utilisateur = new Utilisateur(
                    body.get("nom"),
                    body.get("prenom"),
                    body.get("nomUtilisateur"),
                    body.get("motDePasse")
            );
            utilisateurService.ajouterUtilisateur(utilisateur);
            ctx.status(201).result("Utilisateur ajouté");
        });
    }
}
