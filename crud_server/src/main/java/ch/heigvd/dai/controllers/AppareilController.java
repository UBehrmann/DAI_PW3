package ch.heigvd.dai.controllers;

import ch.heigvd.dai.models.Appareil;
import ch.heigvd.dai.services.AppareilService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class AppareilController {
    private static final AppareilService appareilService = new AppareilService();

    public static void registerRoutes(Javalin app) {
        app.get("/api/appareils", AppareilController::getAllAppareils);
        app.get("/api/appareils/utilisateur/{username}", AppareilController::getAppareilsForUser);
        app.get("/api/appareils/{ip}", AppareilController::getAppareilByIp);
        app.get("/api/appareils/{ip}/series", AppareilController::getSeriesForAppareil);
        app.post("/api/appareils", AppareilController::createAppareil);
        app.put("/api/appareils/{ip}", AppareilController::updateAppareil);
        app.delete("/api/appareils/{ip}", AppareilController::deleteAppareil);
    }

    private static void getAllAppareils(Context ctx) {
        List<Appareil> appareils = appareilService.getAllAppareils();
        if (appareils != null && !appareils.isEmpty()) {
            ctx.json(appareils);
        } else {
            ctx.status(404).result("Aucun appareil trouvé.");
        }
    }

    private static void getAppareilsForUser(Context ctx) {
        String username = ctx.pathParam("username");
        List<Appareil> appareils = appareilService.getAppareilsForUser(username);
        if (appareils != null && !appareils.isEmpty()) {
            ctx.json(appareils);
        } else {
            ctx.status(404).result("Aucun appareil trouvé pour cet utilisateur.");
        }
    }

    private static void getAppareilByIp(Context ctx) {
        String ip = ctx.pathParam("ip");
        Appareil appareil = appareilService.getAppareilByIp(ip);
        if (appareil != null) {
            ctx.json(appareil);
        } else {
            ctx.status(404).result("Appareil non trouvé.");
        }
    }

    private static void getSeriesForAppareil(Context ctx) {
        String ip = ctx.pathParam("ip");
        ctx.json(appareilService.getSeriesForAppareil(ip));
    }

    private static void createAppareil(Context ctx) {
        Appareil appareil = ctx.bodyAsClass(Appareil.class);
        appareilService.createAppareil(appareil);
        ctx.status(201).result("Appareil créé avec succès.");
    }

    private static void updateAppareil(Context ctx) {
        String ip = ctx.pathParam("ip");
        Appareil appareil = ctx.bodyAsClass(Appareil.class);
        appareilService.updateAppareil(ip, appareil);
        ctx.status(200).result("Appareil mis à jour avec succès.");
    }

    private static void deleteAppareil(Context ctx) {
        String ip = ctx.pathParam("ip");
        appareilService.deleteAppareil(ip);
        ctx.status(200).result("Appareil supprimé avec succès.");
    }
}
