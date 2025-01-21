package ch.heigvd.dai.controllers;

import ch.heigvd.dai.models.Alerte;
import ch.heigvd.dai.models.Configuration;
import ch.heigvd.dai.services.SerieService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SerieController {
    private static final SerieService serieService = new SerieService();

    private static final Map<String, Object> cache = new HashMap<>();
    private static final Map<String, LocalDateTime> cacheTimestamps = new HashMap<>();

    private static final long CACHE_DURATION_MINUTES = 15;

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

        String cacheKey = "SERIE_CONFIG_" + id;

        if (isCachedAndValid(cacheKey)) {

            ctx.json(cache.get(cacheKey));
            return;
        }

        Configuration configuration = serieService.getConfigurationForSerie(id);

        if (configuration != null) {

            cache.put(cacheKey, configuration);
            cacheTimestamps.put(cacheKey, LocalDateTime.now());

            ctx.json(configuration);

        } else {
            ctx.status(404).result("Configuration non trouvée pour la série spécifiée.");
        }
    }

    private static void getAlertes(Context ctx) {

        int id = Integer.parseInt(ctx.pathParam("id"));

        String cacheKey = "SERIE_ALERTES_" + id;

        if (isCachedAndValid(cacheKey)) {

            ctx.json(cache.get(cacheKey));
            return;
        }

        List<Alerte> alertes = serieService.getAlertes(id);

        if (alertes != null && !alertes.isEmpty()) {

            cache.put(cacheKey, alertes);
            cacheTimestamps.put(cacheKey, LocalDateTime.now());

            ctx.json(alertes);

        } else {
            ctx.status(404).result("Aucune alerte trouvée pour cette série.");
        }
    }

    private static void ajouterConfiguration(Context ctx) {

        int id = Integer.parseInt(ctx.pathParam("id"));

        Configuration configuration = ctx.bodyAsClass(Configuration.class);

        serieService.ajouterConfiguration(id, configuration);

        invalidateCache("SERIE_CONFIG_" + id);

        ctx.status(201).result("Configuration ajoutée avec succès.");
    }

    private static void modifierConfiguration(Context ctx) {

        int configId = Integer.parseInt(ctx.pathParam("configId"));

        Configuration configuration = ctx.bodyAsClass(Configuration.class);

        serieService.modifierConfiguration(configId, configuration);

        cache.keySet().removeIf(key -> key.startsWith("SERIE_CONFIG_"));
        cacheTimestamps.keySet().removeIf(key -> key.startsWith("SERIE_CONFIG_"));

        ctx.status(200).result("Configuration modifiée avec succès.");
    }

    private static void ajouterAlerte(Context ctx) {

        int id = Integer.parseInt(ctx.pathParam("id"));

        Alerte alerte = ctx.bodyAsClass(Alerte.class);

        serieService.ajouterAlerte(id, alerte);

        invalidateCache("SERIE_ALERTES_" + id);

        ctx.status(201).result("Alerte ajoutée avec succès.");
    }

    private static void modifierAlerte(Context ctx) {

        int id = Integer.parseInt(ctx.pathParam("id"));

        Alerte alerte = ctx.bodyAsClass(Alerte.class);

        serieService.modifierAlerte(id, alerte);

        invalidateCache("SERIE_ALERTES_" + id);

        ctx.status(200).result("Alerte modifiée avec succès.");
    }

    private static void supprimerAlerte(Context ctx) {

        int id = Integer.parseInt(ctx.pathParam("id"));

        String timestamp = ctx.queryParam("timestamp");

        serieService.supprimerAlerte(id, timestamp);

        invalidateCache("SERIE_ALERTES_" + id);

        ctx.status(200).result("Alerte supprimée avec succès.");
    }

    // Check if entry is cached and within XX minutes
    private static boolean isCachedAndValid(String key) {

        if (!cache.containsKey(key) || !cacheTimestamps.containsKey(key)) {
            return false;
        }

        LocalDateTime lastFetch = cacheTimestamps.get(key);

        return lastFetch.isAfter(LocalDateTime.now().minusMinutes(CACHE_DURATION_MINUTES));
    }

    // Invalidate a cache entry
    private static void invalidateCache(String key) {

        cache.remove(key);
        cacheTimestamps.remove(key);
    }
}
