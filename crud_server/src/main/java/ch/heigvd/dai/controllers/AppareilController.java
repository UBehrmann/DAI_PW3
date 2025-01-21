package ch.heigvd.dai.controllers;

import ch.heigvd.dai.models.Appareil;
import ch.heigvd.dai.services.AppareilService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppareilController {

    private static final Map<String, Object> cache = new HashMap<>();
    private static final Map<String, LocalDateTime> cacheTimestamps = new HashMap<>();

    private static final long CACHE_DURATION_MINUTES = 15;

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

        String cacheKey = "ALL_APPAREILS";

        if (isCachedAndValid(cacheKey)) {
            ctx.json(cache.get(cacheKey));
            return;
        }

        List<Appareil> appareils = appareilService.getAllAppareils();

        if (appareils != null && !appareils.isEmpty()) {

            cache.put(cacheKey, appareils);
            cacheTimestamps.put(cacheKey, LocalDateTime.now());

            ctx.json(appareils);

        } else {
            ctx.status(404).result("Aucun appareil trouvé.");
        }
    }

    private static void getAppareilsForUser(Context ctx) {

        String username = ctx.pathParam("username");

        String cacheKey = "USER:" + username;

        if (isCachedAndValid(cacheKey)) {

            ctx.json(cache.get(cacheKey));
            return;
        }

        List<Appareil> appareils = appareilService.getAppareilsForUser(username);

        if (appareils != null && !appareils.isEmpty()) {

            cache.put(cacheKey, appareils);
            cacheTimestamps.put(cacheKey, LocalDateTime.now());

            ctx.json(appareils);

        } else {
            ctx.status(404).result("Aucun appareil trouvé pour cet utilisateur.");
        }
    }

    private static void getAppareilByIp(Context ctx) {

        String ip = ctx.pathParam("ip");

        String cacheKey = "APPAREIL:" + ip;

        if (isCachedAndValid(cacheKey)) {

            ctx.json(cache.get(cacheKey));
            return;
        }

        Appareil appareil = appareilService.getAppareilByIp(ip);

        if (appareil != null) {

            cache.put(cacheKey, appareil);
            cacheTimestamps.put(cacheKey, LocalDateTime.now());

            ctx.json(appareil);

        } else {
            ctx.status(404).result("Appareil non trouvé.");
        }
    }

    private static void getSeriesForAppareil(Context ctx) {

        String ip = ctx.pathParam("ip");

        String cacheKey = "SERIES:" + ip;

        if (isCachedAndValid(cacheKey)) {

            ctx.json(cache.get(cacheKey));
            return;
        }

        var series = appareilService.getSeriesForAppareil(ip);

        cache.put(cacheKey, series);
        cacheTimestamps.put(cacheKey, LocalDateTime.now());

        ctx.json(series);
    }

    private static void createAppareil(Context ctx) {

        Appareil appareil = ctx.bodyAsClass(Appareil.class);

        appareilService.createAppareil(appareil);

        invalidateCache("ALL_APPAREILS");

        ctx.status(201).result("Appareil créé avec succès.");
    }

    private static void updateAppareil(Context ctx) {

        String ip = ctx.pathParam("ip");

        Appareil appareil = ctx.bodyAsClass(Appareil.class);

        appareilService.updateAppareil(ip, appareil);

        invalidateCache("ALL_APPAREILS");
        invalidateCache("APPAREIL:" + ip);
        invalidateCache("SERIES:" + ip);

        ctx.status(200).result("Appareil mis à jour avec succès.");
    }

    private static void deleteAppareil(Context ctx) {

        String ip = ctx.pathParam("ip");

        appareilService.deleteAppareil(ip);

        invalidateCache("ALL_APPAREILS");
        invalidateCache("APPAREIL:" + ip);
        invalidateCache("SERIES:" + ip);

        ctx.status(200).result("Appareil supprimé avec succès.");
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
