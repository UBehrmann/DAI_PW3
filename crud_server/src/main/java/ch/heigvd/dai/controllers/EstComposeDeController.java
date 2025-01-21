package ch.heigvd.dai.controllers;

import ch.heigvd.dai.models.EstComposeDe;
import ch.heigvd.dai.services.EstComposeDeService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EstComposeDeController {

    private static final Map<String, List<String>> cache = new HashMap<>();
    private static final Map<String, LocalDateTime> cacheTimestamps = new HashMap<>();

    private static final long CACHE_DURATION_MINUTES = 15;

    private static final EstComposeDeService estComposeDeService = new EstComposeDeService();

    public static void registerRoutes(Javalin app) {
        app.get("/api/groupes-capteurs/{groupeId}/appareils", EstComposeDeController::getAppareilsDansGroupe);
        app.post("/api/groupes-capteurs/{groupeId}/appareils", EstComposeDeController::ajouterAppareilAuGroupe);
        app.delete("/api/groupes-capteurs/{groupeId}/appareils/{ip}", EstComposeDeController::retirerAppareilDuGroupe);
    }

    private static void getAppareilsDansGroupe(Context ctx) {

        int groupeId = Integer.parseInt(ctx.pathParam("groupeId"));

        String cacheKey = "GROUPE:" + groupeId;

        if (isCachedAndValid(cacheKey)) {
            ctx.json(cache.get(cacheKey));
            return;
        }

        List<String> appareils = estComposeDeService.getAppareilsDansGroupe(groupeId);

        cache.put(cacheKey, appareils);
        cacheTimestamps.put(cacheKey, LocalDateTime.now());

        ctx.json(appareils);
    }

    private static void ajouterAppareilAuGroupe(Context ctx) {

        int groupeId = Integer.parseInt(ctx.pathParam("groupeId"));

        EstComposeDe estComposeDe = ctx.bodyAsClass(EstComposeDe.class);
        estComposeDe.setGroupe(groupeId);

        estComposeDeService.ajouterAppareilAuGroupe(estComposeDe);

        invalidateCache("GROUPE:" + groupeId);

        ctx.status(201).result("Appareil ajouté au groupe.");
    }

    private static void retirerAppareilDuGroupe(Context ctx) {

        int groupeId = Integer.parseInt(ctx.pathParam("groupeId"));

        String ip = ctx.pathParam("ip");

        estComposeDeService.retirerAppareilDuGroupe(ip, groupeId);

        invalidateCache("GROUPE:" + groupeId);

        ctx.status(200).result("Appareil retiré du groupe.");
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
