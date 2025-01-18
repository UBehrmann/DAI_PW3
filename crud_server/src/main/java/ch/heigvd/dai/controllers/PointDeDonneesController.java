package ch.heigvd.dai.controllers;

import ch.heigvd.dai.models.PointDeDonnees;
import ch.heigvd.dai.services.PointDeDonneesService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class PointDeDonneesController {
    private static final PointDeDonneesService pointDeDonneesService = new PointDeDonneesService();

    public static void registerRoutes(Javalin app) {
        // Fetch data points for a specific series within a date range
        app.get("/api/series/{serieId}/points", PointDeDonneesController::getPointsDeDonneesInRange);

        // Fetch all data points for all appareils in a group
        app.get("/api/groupes/{groupeId}/points", PointDeDonneesController::getPointsDeDonneesForGroupe);
    }

    private static void getPointsDeDonneesInRange(Context ctx) {
        int serieId = Integer.parseInt(ctx.pathParam("serieId"));
        String startDate = ctx.queryParam("startDate");
        String endDate = ctx.queryParam("endDate");

        if (startDate == null || endDate == null) {
            ctx.status(400).result("startDate and endDate query parameters are required.");
            return;
        }

        List<PointDeDonnees> points = pointDeDonneesService.getPointsDeDonneesInRange(serieId, startDate, endDate);
        ctx.json(points);
    }

    private static void getPointsDeDonneesForGroupe(Context ctx) {
        int groupeId = Integer.parseInt(ctx.pathParam("groupeId"));
        String startDate = ctx.queryParam("startDate");
        String endDate = ctx.queryParam("endDate");

        if (startDate == null || endDate == null) {
            ctx.status(400).result("startDate and endDate query parameters are required.");
            return;
        }

        List<PointDeDonnees> points = pointDeDonneesService.getPointsDeDonneesForGroupe(groupeId, startDate, endDate);
        ctx.json(points);
    }
}
