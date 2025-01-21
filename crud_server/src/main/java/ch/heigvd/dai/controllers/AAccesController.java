package ch.heigvd.dai.controllers;

import ch.heigvd.dai.models.Appareil;
import ch.heigvd.dai.services.AAccesService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AAccesController {

    private static final Map<String, Object> cache = new HashMap<>();
    private static final Map<String, LocalDateTime> cacheTimestamps = new HashMap<>();

    private static final long CACHE_DURATION_MINUTES = 15;

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

        if (isCachedAndValid(groupe)) {
            ctx.json(cache.get(groupe));
            return;
        }

        List<Appareil> appareils = aAccesService.getAppareilsForGroupe(groupe);

        if (appareils != null && !appareils.isEmpty()) {

            cache.put(groupe, appareils);
            cacheTimestamps.put(groupe, LocalDateTime.now());

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

        invalidateCache(groupe);

        ctx.status(200).result("Accès supprimé avec succès.");
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
