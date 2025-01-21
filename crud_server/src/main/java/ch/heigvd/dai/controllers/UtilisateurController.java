// Bär, here's a 15-minute cache approach using LocalDateTime for UtilisateurController

package ch.heigvd.dai.controllers;

import io.javalin.Javalin;
import io.javalin.http.Context;
import ch.heigvd.dai.models.Utilisateur;
import ch.heigvd.dai.services.UtilisateurService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class UtilisateurController {
    private static final UtilisateurService utilisateurService = new UtilisateurService();

    private static final Map<String, Object> cache = new HashMap<>();
    private static final Map<String, LocalDateTime> cacheTimestamps = new HashMap<>();

    private static final long CACHE_DURATION_MINUTES = 15;

    public static void registerRoutes(Javalin app) {
        app.get("/api/utilisateurs/{nomUtilisateur}", UtilisateurController::getUtilisateurByNomUtilisateur);
        app.post("/api/utilisateurs", UtilisateurController::ajouterUtilisateur);
        app.put("/api/utilisateurs/{nomUtilisateur}", UtilisateurController::mettreAJourUtilisateur);
        app.delete("/api/utilisateurs/{nomUtilisateur}", UtilisateurController::supprimerUtilisateur);
        app.get("/api/utilisateurs", UtilisateurController::getUtilisateurs);
        app.get("/api/utilisateurs/{nomUtilisateur}/{motDePasse}", UtilisateurController::verifierUtilisateur);
    }

    private static void getUtilisateurByNomUtilisateur(Context ctx) {

        String nomUtilisateur = ctx.pathParam("nomUtilisateur");

        String cacheKey = "UTILISATEUR_" + nomUtilisateur;

        if (isCachedAndValid(cacheKey)) {

            ctx.json(cache.get(cacheKey));
            return;
        }

        Utilisateur utilisateur = utilisateurService.getUtilisateurByNomUtilisateur(nomUtilisateur);

        if (utilisateur != null) {

            cache.put(cacheKey, utilisateur);
            cacheTimestamps.put(cacheKey, LocalDateTime.now());

            ctx.json(utilisateur);

        } else {
            ctx.status(404).result("Utilisateur introuvable");
        }
    }

    private static void ajouterUtilisateur(Context ctx) {

        Utilisateur utilisateur = ctx.bodyAsClass(Utilisateur.class);

        utilisateurService.ajouterUtilisateur(utilisateur);

        invalidateCache("ALL_UTILISATEURS");

        ctx.status(201).result("Utilisateur ajouté");
    }

    private static void mettreAJourUtilisateur(Context ctx) {

        String nomUtilisateur = ctx.pathParam("nomUtilisateur");

        Utilisateur existingUtilisateur = utilisateurService.getUtilisateurByNomUtilisateur(nomUtilisateur);

        if (existingUtilisateur == null) {

            ctx.status(404).result("Utilisateur introuvable");
            return;
        }

        Utilisateur utilisateur = ctx.bodyAsClass(Utilisateur.class);
        utilisateur.setNomUtilisateur(nomUtilisateur);

        utilisateurService.mettreAJourUtilisateur(utilisateur);

        invalidateCache("ALL_UTILISATEURS");
        invalidateCache("UTILISATEUR_" + nomUtilisateur);

        ctx.status(200).result("Utilisateur mis à jour");
    }

    private static void supprimerUtilisateur(Context ctx) {

        String nomUtilisateur = ctx.pathParam("nomUtilisateur");

        Utilisateur existingUtilisateur = utilisateurService.getUtilisateurByNomUtilisateur(nomUtilisateur);

        if (existingUtilisateur == null) {

            ctx.status(404).result("Utilisateur introuvable");
            return;
        }

        utilisateurService.supprimerUtilisateur(nomUtilisateur);

        invalidateCache("ALL_UTILISATEURS");
        invalidateCache("UTILISATEUR_" + nomUtilisateur);

        ctx.status(200).result("Utilisateur supprimé");
    }

    private static void getUtilisateurs(Context ctx) {

        String cacheKey = "ALL_UTILISATEURS";

        if (isCachedAndValid(cacheKey)) {
            ctx.json(cache.get(cacheKey));
            return;
        }

        Utilisateur[] utilisateurs = utilisateurService.getUtilisateurs();

        if (utilisateurs != null && utilisateurs.length > 0) {

            cache.put(cacheKey, utilisateurs);
            cacheTimestamps.put(cacheKey, LocalDateTime.now());

            ctx.json(utilisateurs);

        } else {
            ctx.status(404).result("Aucun utilisateur trouvé");
        }
    }

    private static void verifierUtilisateur(Context ctx) {

        String nomUtilisateur = ctx.pathParam("nomUtilisateur");
        String motDePasse = ctx.pathParam("motDePasse");

        String cacheKey = "VERIF_UTILISATEUR_" + nomUtilisateur + "_" + motDePasse;

        if (isCachedAndValid(cacheKey)) {

            boolean cachedResult = (boolean) cache.get(cacheKey);

            if (cachedResult) {
                ctx.status(200).result("Utilisateur trouvé (cache)");
            } else {
                ctx.status(404).result("Utilisateur introuvable (cache)");
            }
            return;
        }

        boolean utilisateurExiste = utilisateurService.verifierUtilisateur(nomUtilisateur, motDePasse);

        cache.put(cacheKey, utilisateurExiste);
        cacheTimestamps.put(cacheKey, LocalDateTime.now());

        if (utilisateurExiste) {
            ctx.status(200).result("Utilisateur trouvé");
        } else {
            ctx.status(404).result("Utilisateur introuvable");
        }
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
