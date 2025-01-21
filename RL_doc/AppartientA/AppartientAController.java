package ch.heigvd.dai.controllers;

import ch.heigvd.dai.models.AppartientA;
import ch.heigvd.dai.services.AppartientAService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class AppartientAController {
    private static final AppartientAService appartientAService = new AppartientAService();

    public static void registerRoutes(Javalin app) {
        app.post("/api/groupes/utilisateurs", AppartientAController::ajouterUtilisateurDansGroupe);
        app.delete("/api/groupes/utilisateurs/{groupe}/{utilisateur}", AppartientAController::supprimerUtilisateurDeGroupe);
    }

    private static void ajouterUtilisateurDansGroupe(Context ctx) {
        AppartientA appartientA = ctx.bodyAsClass(AppartientA.class);
        appartientAService.ajouterUtilisateurDansGroupe(appartientA);
        ctx.status(201).result("Utilisateur ajouté au groupe.");
    }

    private static void supprimerUtilisateurDeGroupe(Context ctx) {
        String groupe = ctx.pathParam("groupe");
        String utilisateur = ctx.pathParam("utilisateur");
        appartientAService.supprimerUtilisateurDeGroupe(utilisateur, groupe);
        ctx.status(200).result("Utilisateur supprimé du groupe.");
    }
}
