package ch.heigvd.dai.controllers;

import ch.heigvd.dai.models.Alerte;
import ch.heigvd.dai.models.Configuration;
import ch.heigvd.dai.services.SerieService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class SerieController {
    private static final SerieService serieService = new SerieService();

    public static void registerRoutes(Javalin app) {
        app.get("/api/series/{id}/configurations", SerieController::getConfigurations);
        app.get("/api/series/{id}/alertes", SerieController::getAlertes);
        app.post("/api/series/{id}/configurations", SerieController::ajouterConfiguration);
        app.put("/api/series/{id}/configurations/{configId}", SerieController::modifierConfiguration);
        app.post("/api/series/{id}/alertes", SerieController::ajouterAlerte);
        app.put("/api/series/{id}/alertes", SerieController::modifierAlerte);
        app.delete("/api/series/{id}/alertes", SerieController::supprimerAlerte);
    }

    private static void getConfigurations(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        List<Configuration> configurations = serieService.getConfigurations(id);
        ctx.json(configurations);
    }

    private static void getAlertes(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        ctx.json(serieService.getAlertes(id));
    }

    private static void ajouterConfiguration(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Configuration configuration = ctx.bodyAsClass(Configuration.class);
        serieService.ajouterConfiguration(id, configuration);
        ctx.status(201).result("Configuration ajoutée avec succès.");
    }

    private static void modifierConfiguration(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        int configId = Integer.parseInt(ctx.pathParam("configId"));
        Configuration configuration = ctx.bodyAsClass(Configuration.class);
        serieService.modifierConfiguration(id, configId, configuration);
        ctx.status(200).result("Configuration modifiée avec succès.");
    }

    private static void ajouterAlerte(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Alerte alerte = ctx.bodyAsClass(Alerte.class);
        serieService.ajouterAlerte(id, alerte);
        ctx.status(201).result("Alerte ajoutée avec succès.");
    }

    private static void modifierAlerte(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Alerte alerte = ctx.bodyAsClass(Alerte.class);
        serieService.modifierAlerte(id, alerte);
        ctx.status(200).result("Alerte modifiée avec succès.");
    }

    private static void supprimerAlerte(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        String timestamp = ctx.queryParam("timestamp");
        serieService.supprimerAlerte(id, timestamp);
        ctx.status(200).result("Alerte supprimée avec succès.");
    }
}
