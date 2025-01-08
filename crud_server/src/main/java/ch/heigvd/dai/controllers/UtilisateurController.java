package ch.heigvd.dai.controllers;

import io.javalin.Javalin;
import ch.heigvd.dai.models.Utilisateur;
import ch.heigvd.dai.services.UtilisateurService;

public class UtilisateurController {
    private static final UtilisateurService utilisateurService = new UtilisateurService();

    public static void registerRoutes(Javalin app) {
        // Récupérer un utilisateur par son ID
        app.get("/utilisateurs/:id", ctx -> {
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
            Utilisateur utilisateur = ctx.bodyAsClass(Utilisateur.class);
            utilisateurService.ajouterUtilisateur(utilisateur);
            ctx.status(201).result("Utilisateur ajouté");
        });

        // Autres routes...
    }
}
