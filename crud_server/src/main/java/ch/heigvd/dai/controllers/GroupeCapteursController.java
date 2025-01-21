package ch.heigvd.dai.controllers;

import ch.heigvd.dai.models.GroupeCapteurs;
import ch.heigvd.dai.services.GroupeCapteursService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class GroupeCapteursController {

    private static final Map<String, Object> cache = new HashMap<>();
    private static final Map<String, LocalDateTime> cacheTimestamps = new HashMap<>();

    private static final long CACHE_DURATION_MINUTES = 15;

    private static final GroupeCapteursService groupeCapteursService = new GroupeCapteursService();

    public static void registerRoutes(Javalin app) {
        app.post("/api/groupe-capteurs", GroupeCapteursController::ajouterGroupeCapteurs);
        app.delete("/api/groupe-capteurs/{id}", GroupeCapteursController::supprimerGroupeCapteurs);
        app.put("/api/groupe-capteurs/{id}", GroupeCapteursController::modifierGroupeCapteurs);

        app.get("/api/groupe-capteurs", GroupeCapteursController::getGroupesCapteurs);
        app.get("/api/groupe-capteurs/{id}", GroupeCapteursController::getGroupeCapteursParId);
        app.get("/api/groupe-capteurs/{id}/appareils", GroupeCapteursController::getAppareilsDansGroupe);
        app.get("/api/groupe-capteurs/{id}/appareils/{type}", GroupeCapteursController::getAppareilsDansGroupeParType);
        app.get("/api/groupe-capteurs/utilisateur/{username}", GroupeCapteursController::getGroupesCapteursParUtilisateur);
        app.get("/api/groupe-capteurs/utilisateur/{username}/appareils/{type}", GroupeCapteursController::getAppareilsParUtilisateurEtType);
    }

    private static void ajouterGroupeCapteurs(Context ctx) {

        String nom = ctx.formParam("nom");
        Date dateCreation = Date.valueOf(ctx.formParam("dateCreation"));
        String administrateur = ctx.formParam("administrateur");

        groupeCapteursService.ajouterGroupeCapteurs(nom, dateCreation, administrateur);

        // Invalidate all group cache
        invalidateCache("ALL_GROUPES");

        ctx.status(201).result("Groupe de capteurs ajouté avec succès.");
    }

    private static void supprimerGroupeCapteurs(Context ctx) {

        int id = Integer.parseInt(ctx.pathParam("id"));

        groupeCapteursService.supprimerGroupeCapteurs(id);

        // Invalidate relevant entries
        invalidateCache("ALL_GROUPES");
        invalidateCache("GROUPE_ID_" + id);
        invalidateCache("GROUPE_APPAREILS_" + id);

        ctx.status(200).result("Groupe de capteurs supprimé avec succès.");
    }

    private static void modifierGroupeCapteurs(Context ctx) {

        int id = Integer.parseInt(ctx.pathParam("id"));

        GroupeCapteurs groupeCapteurs = ctx.bodyAsClass(GroupeCapteurs.class);

        groupeCapteursService.modifierGroupeCapteurs(id, groupeCapteurs);

        // Invalidate relevant entries
        invalidateCache("ALL_GROUPES");
        invalidateCache("GROUPE_ID_" + id);
        invalidateCache("GROUPE_APPAREILS_" + id);

        ctx.status(200).result("Groupe de capteurs modifié avec succès.");
    }

    private static void getGroupesCapteurs(Context ctx) {

        String cacheKey = "ALL_GROUPES";

        if (isCachedAndValid(cacheKey)) {
            ctx.json(cache.get(cacheKey));
            return;
        }

        var groupes = groupeCapteursService.getGroupesCapteurs();

        cache.put(cacheKey, groupes);
        cacheTimestamps.put(cacheKey, LocalDateTime.now());

        ctx.status(200).json(groupes);
    }

    private static void getGroupeCapteursParId(Context ctx) {

        int id = Integer.parseInt(ctx.pathParam("id"));

        String cacheKey = "GROUPE_ID_" + id;

        if (isCachedAndValid(cacheKey)) {

            ctx.json(cache.get(cacheKey));
            return;
        }

        GroupeCapteurs groupe = groupeCapteursService.getGroupeCapteursParId(id);

        if (groupe != null) {

            cache.put(cacheKey, groupe);

            cacheTimestamps.put(cacheKey, LocalDateTime.now());

            ctx.json(groupe);

        } else {
            ctx.status(404).result("Groupe de capteurs introuvable.");
        }
    }

    private static void getAppareilsDansGroupe(Context ctx) {

        int id = Integer.parseInt(ctx.pathParam("id"));

        String cacheKey = "GROUPE_APPAREILS_" + id;

        if (isCachedAndValid(cacheKey)) {

            ctx.json(cache.get(cacheKey));
            return;
        }

        var appareils = groupeCapteursService.getAppareilsDansGroupe(id);

        cache.put(cacheKey, appareils);
        cacheTimestamps.put(cacheKey, LocalDateTime.now());

        ctx.status(200).json(appareils);
    }

    private static void getAppareilsDansGroupeParType(Context ctx) {

        int id = Integer.parseInt(ctx.pathParam("id"));
        String type = ctx.pathParam("type");

        String cacheKey = "GROUPE_APPAREILS_" + id + "_TYPE_" + type;

        if (isCachedAndValid(cacheKey)) {

            ctx.json(cache.get(cacheKey));
            return;
        }

        var appareils = groupeCapteursService.getAppareilsDansGroupeParType(id, type);

        cache.put(cacheKey, appareils);
        cacheTimestamps.put(cacheKey, LocalDateTime.now());

        ctx.status(200).json(appareils);
    }

    private static void getGroupesCapteursParUtilisateur(Context ctx) {

        String username = ctx.pathParam("username");

        String cacheKey = "GROUPES_USER_" + username;

        if (isCachedAndValid(cacheKey)) {

            ctx.json(cache.get(cacheKey));
            return;
        }

        var groupes = groupeCapteursService.getGroupesCapteursParUtilisateur(username);

        cache.put(cacheKey, groupes);
        cacheTimestamps.put(cacheKey, LocalDateTime.now());

        ctx.status(200).json(groupes);
    }

    private static void getAppareilsParUtilisateurEtType(Context ctx) {

        String username = ctx.pathParam("username");
        String type = ctx.pathParam("type");

        String cacheKey = "USER_" + username + "_TYPE_" + type;

        if (isCachedAndValid(cacheKey)) {

            ctx.json(cache.get(cacheKey));
            return;
        }

        var appareils = groupeCapteursService.getAppareilsParUtilisateurEtType(username, type);

        cache.put(cacheKey, appareils);
        cacheTimestamps.put(cacheKey, LocalDateTime.now());

        ctx.status(200).json(appareils);
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
