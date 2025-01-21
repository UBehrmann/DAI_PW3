package ch.heigvd.dai.controllers;

import ch.heigvd.dai.models.EstComposeDe;
import ch.heigvd.dai.services.EstComposeDeService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class EstComposeDeController {
    private static final EstComposeDeService estComposeDeService = new EstComposeDeService();

    public static void registerRoutes(Javalin app) {
        // Récupérer tous les appareils dans un groupe
        app.get("/api/groupes-capteurs/{groupeId}/appareils", EstComposeDeController::getAppareilsDansGroupe);

        // Ajouter un appareil à un groupe
        app.post("/api/groupes-capteurs/{groupeId}/appareils", EstComposeDeController::ajouterAppareilAuGroupe);

        // Retirer un appareil d'un groupe
        app.delete("/api/groupes-capteurs/{groupeId}/appareils/{ip}", EstComposeDeController::retirerAppareilDuGroupe);
    }

    private static void getAppareilsDansGroupe(Context ctx) {
        int groupeId = Integer.parseInt(ctx.pathParam("groupeId"));
        List<String> appareils = estComposeDeService.getAppareilsDansGroupe(groupeId);
        ctx.json(appareils);
    }

    private static void ajouterAppareilAuGroupe(Context ctx) {
        int groupeId = Integer.parseInt(ctx.pathParam("groupeId"));
        EstComposeDe estComposeDe = ctx.bodyAsClass(EstComposeDe.class);
        estComposeDe.setGroupe(groupeId);
        estComposeDeService.ajouterAppareilAuGroupe(estComposeDe);
        ctx.status(201).result("Appareil ajouté au groupe.");
    }

    private static void retirerAppareilDuGroupe(Context ctx) {
        int groupeId = Integer.parseInt(ctx.pathParam("groupeId"));
        String ip = ctx.pathParam("ip");
        estComposeDeService.retirerAppareilDuGroupe(ip, groupeId);
        ctx.status(200).result("Appareil retiré du groupe.");
    }
}
