package ch.heigvd.dai.controllers;

import ch.heigvd.dai.services.AAccesService;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class AAccesController {
    private static final AAccesService aAccesService = new AAccesService();

    public static void registerRoutes(Javalin app) {
        // Get all devices a group has access to
        app.get("/api/groupes/{groupe}/appareils", AAccesController::getAppareilsForGroupe);

        // Add an access for a group to a device
        app.post("/api/groupes/{groupe}/appareils/{ip}", AAccesController::ajouterAcces);

        // Remove an access for a group to a device
        app.delete("/api/groupes/{groupe}/appareils/{ip}", AAccesController::supprimerAcces);
    }

    private static void getAppareilsForGroupe(Context ctx) {
        String groupe = ctx.pathParam("groupe");
        var appareils = aAccesService.getAppareilsForGroupe(groupe);
        if (appareils != null && !appareils.isEmpty()) {
            ctx.json(appareils);
        } else {
            ctx.status(404).result("Aucun appareil trouvé pour ce groupe.");
        }
    }

    private static void ajouterAcces(Context ctx) {
        String groupe = ctx.pathParam("groupe");
        String ip = ctx.pathParam("ip");
        aAccesService.ajouterAcces(groupe, ip);
        ctx.status(201).result("Accès ajouté avec succès.");
    }

    private static void supprimerAcces(Context ctx) {
        String groupe = ctx.pathParam("groupe");
        String ip = ctx.pathParam("ip");
        aAccesService.supprimerAcces(groupe, ip);
        ctx.status(200).result("Accès supprimé avec succès.");
    }
}
