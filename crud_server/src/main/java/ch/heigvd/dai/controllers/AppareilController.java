package ch.heigvd.dai.controllers;

import ch.heigvd.dai.models.Alerte;
import ch.heigvd.dai.models.Configuration;
import ch.heigvd.dai.services.AppareilService;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class AppareilController {
    private static final AppareilService appareilService = new AppareilService();

    public static void registerRoutes(Javalin app) {
        app.get("/api/appareils/{id}/configuration", AppareilController::getConfiguration);
        app.get("/api/appareils/{id}/alertes", AppareilController::getAlertes);
        app.put("/api/appareils/{id}/configuration", AppareilController::modifierConfiguration);
        app.post("/api/appareils/{id}/alertes", AppareilController::ajouterAlerte);
        app.delete("/api/appareils/{id}/alertes", AppareilController::supprimerAlerte);
        app.put("/api/appareils/{id}/alertes", AppareilController::modifierAlerte);
    }

    private static void getConfiguration(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        ctx.json(appareilService.getConfiguration(id));
    }

    private static void getAlertes(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        ctx.json(appareilService.getAlertes(id));
    }

    private static void modifierConfiguration(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Configuration configuration = ctx.bodyAsClass(Configuration.class);
        appareilService.modifierConfiguration(id, configuration);
        ctx.status(200).result("Configuration modifiée avec succès.");
    }

    private static void ajouterAlerte(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Alerte alerte = ctx.bodyAsClass(Alerte.class);
        appareilService.ajouterAlerte(id, alerte);
        ctx.status(201).result("Alerte ajoutée avec succès.");
    }

    private static void supprimerAlerte(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        String timestamp = ctx.queryParam("timestamp");
        appareilService.supprimerAlerte(id, timestamp);
        ctx.status(200).result("Alerte supprimée avec succès.");
    }

    private static void modifierAlerte(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Alerte alerte = ctx.bodyAsClass(Alerte.class);
        appareilService.modifierAlerte(id, alerte);
        ctx.status(200).result("Alerte modifiée avec succès.");
    }
}
