package ch.heigvd.dai.controllers;

import ch.heigvd.dai.models.PointDeDonnees;
import ch.heigvd.dai.services.PointDeDonneesService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;
import java.util.Map;

public class PointDeDonneesController {
    private static final PointDeDonneesService pointDeDonneesService = new PointDeDonneesService();

    public static void registerRoutes(Javalin app) {
        // Fetch data points for a specific series within a date range
        app.get("/api/series/{serieId}/points", PointDeDonneesController::getPointsDeDonneesInRange);

        // Fetch 1000 data points for a specific series
        app.get("/api/series/{serieId}/points/limit", PointDeDonneesController::getLimitedPointsDeDonnees);

        // Fetch all data points for all series in an appareil
        app.get("/api/series/appareils/{ip}/points", PointDeDonneesController::getPointsDeDonneesForAppareil);

        // Fetch 1000 data points for all series in an appareil
        app.get("/api/series/appareils/{ip}/points/limit", PointDeDonneesController::getLimitedPointsDeDonneesForAppareil);

        // Fetch statistics for the 1000 latest points of a series
        app.get("/api/series/{serieId}/statistics/limit", PointDeDonneesController::getStatisticsForLimitedPoints);
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

    private static void getLimitedPointsDeDonnees(Context ctx) {
        int serieId = Integer.parseInt(ctx.pathParam("serieId"));

        List<PointDeDonnees> points = pointDeDonneesService.getLimitedPointsDeDonnees(serieId, 1000);
        if (points != null && !points.isEmpty()) {
            ctx.json(points);
        } else {
            ctx.status(404).result("Aucun point de données trouvé pour cette série.");
        }
    }

    private static void getPointsDeDonneesForAppareil(Context ctx) {
        String ip = ctx.pathParam("ip");
        String startDate = ctx.queryParam("startDate");
        String endDate = ctx.queryParam("endDate");

        if (startDate == null || endDate == null) {
            ctx.status(400).result("startDate and endDate query parameters are required.");
            return;
        }

        List<PointDeDonnees> points = pointDeDonneesService.getPointsDeDonneesForAppareil(ip, startDate, endDate);
        ctx.json(points);
    }

    private static void getLimitedPointsDeDonneesForAppareil(Context ctx) {
        String ip = ctx.pathParam("ip");

        List<PointDeDonnees> points = pointDeDonneesService.getLimitedPointsDeDonneesForAppareil(ip, 1000);
        if (points != null && !points.isEmpty()) {
            ctx.json(points);
        } else {
            ctx.status(404).result("Aucun point de données trouvé pour cet appareil.");
        }
    }

    private static void getStatisticsForLimitedPoints(Context ctx) {
        int serieId = Integer.parseInt(ctx.pathParam("serieId"));

        Map<String, Double> statistics = pointDeDonneesService.getStatisticsForLimitedPoints(serieId, 1000);
        if (statistics != null && !statistics.isEmpty()) {
            ctx.json(statistics);
        } else {
            ctx.status(404).result("Impossible de calculer les statistiques pour cette série.");
        }
    }
}
