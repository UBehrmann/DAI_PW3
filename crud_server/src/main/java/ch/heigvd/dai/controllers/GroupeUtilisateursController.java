package ch.heigvd.dai.controllers;

import ch.heigvd.dai.models.GroupeUtilisateurs;
import ch.heigvd.dai.services.GroupeUtilisateursService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupeUtilisateursController {
    private static final GroupeUtilisateursService groupeUtilisateurService = new GroupeUtilisateursService();

    private static final Map<String, Object> cache = new HashMap<>();
    private static final Map<String, LocalDateTime> cacheTimestamps = new HashMap<>();

    private static final long CACHE_DURATION_MINUTES = 15;

    public static void registerRoutes(Javalin app) {
        app.get("/api/groupes", GroupeUtilisateursController::getGroupes);
        app.get("/api/groupes/{nom}", GroupeUtilisateursController::getGroupeByNom);
        app.get("/api/groupes/utilisateur/{nomUtilisateur}", GroupeUtilisateursController::getGroupesByUtilisateur);
        app.post("/api/groupes", GroupeUtilisateursController::ajouterGroupe);
        app.put("/api/groupes/{nom}", GroupeUtilisateursController::mettreAJourGroupe);
        app.delete("/api/groupes/{nom}", GroupeUtilisateursController::supprimerGroupe);
    }

    private static void getGroupes(Context ctx) {

        String cacheKey = "ALL_GROUPES";

        if (isCachedAndValid(cacheKey)) {

            ctx.json(cache.get(cacheKey));
            return;
        }

        List<GroupeUtilisateurs> groupes = groupeUtilisateurService.getGroupes();

        cache.put(cacheKey, groupes);
        cacheTimestamps.put(cacheKey, LocalDateTime.now());

        ctx.json(groupes);
    }

    private static void getGroupeByNom(Context ctx) {

        String nom = ctx.pathParam("nom");

        String cacheKey = "GROUPE_NOM_" + nom;

        if (isCachedAndValid(cacheKey)) {

            ctx.json(cache.get(cacheKey));
            return;
        }

        GroupeUtilisateurs groupe = groupeUtilisateurService.getGroupeByNom(nom);

        if (groupe != null) {

            cache.put(cacheKey, groupe);
            cacheTimestamps.put(cacheKey, LocalDateTime.now());

            ctx.json(groupe);

        } else {
            ctx.status(404).result("Groupe introuvable");
        }
    }

    private static void getGroupesByUtilisateur(Context ctx) {

        String nomUtilisateur = ctx.pathParam("nomUtilisateur");

        String cacheKey = "GROUPES_UTILISATEUR_" + nomUtilisateur;

        if (isCachedAndValid(cacheKey)) {

            ctx.json(cache.get(cacheKey));
            return;
        }

        List<GroupeUtilisateurs> groupes = groupeUtilisateurService.getGroupesByUtilisateur(nomUtilisateur);

        cache.put(cacheKey, groupes);
        cacheTimestamps.put(cacheKey, LocalDateTime.now());

        ctx.json(groupes);
    }

    private static void ajouterGroupe(Context ctx) {

        GroupeUtilisateurs groupe = ctx.bodyAsClass(GroupeUtilisateurs.class);

        groupeUtilisateurService.ajouterGroupe(groupe);
        invalidateCache("ALL_GROUPES");

        ctx.status(201).result("Groupe ajouté");
    }

    private static void mettreAJourGroupe(Context ctx) {

        String nom = ctx.pathParam("nom");

        GroupeUtilisateurs existingGroupe = groupeUtilisateurService.getGroupeByNom(nom);

        if (existingGroupe == null) {

            ctx.status(404).result("Groupe introuvable");
            return;
        }

        GroupeUtilisateurs groupe = ctx.bodyAsClass(GroupeUtilisateurs.class);
        groupe.setNom(nom);

        groupeUtilisateurService.mettreAJourGroupe(groupe);

        invalidateCache("ALL_GROUPES");
        invalidateCache("GROUPE_NOM_" + nom);

        ctx.status(200).result("Groupe mis à jour");
    }

    private static void supprimerGroupe(Context ctx) {

        String nom = ctx.pathParam("nom");

        groupeUtilisateurService.supprimerGroupe(nom);

        invalidateCache("ALL_GROUPES");
        invalidateCache("GROUPE_NOM_" + nom);

        ctx.status(200).result("Groupe supprimé");
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
