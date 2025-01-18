package ch.heigvd.dai.controllers;

import ch.heigvd.dai.models.GroupeCapteurs;
import ch.heigvd.dai.services.GroupeCapteursService;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class GroupeCapteursController {
    private static final GroupeCapteursService groupeCapteursService = new GroupeCapteursService();

    public static void registerRoutes(Javalin app) {
        app.post("/api/groupe-capteurs", GroupeCapteursController::ajouterGroupeCapteurs);
        app.delete("/api/groupe-capteurs/{nom}", GroupeCapteursController::supprimerGroupeCapteurs);
        app.put("/api/groupe-capteurs/{nom}", GroupeCapteursController::modifierGroupeCapteurs);
        app.get("/api/groupe-capteurs/{nom}/appareils", GroupeCapteursController::getAppareilsDansGroupe);
        app.get("/api/groupe-capteurs/{nom}/appareils/{type}", GroupeCapteursController::getAppareilsDansGroupeParType);
    }

    private static void ajouterGroupeCapteurs(Context ctx) {
        GroupeCapteurs groupeCapteurs = ctx.bodyAsClass(GroupeCapteurs.class);
        groupeCapteursService.ajouterGroupeCapteurs(groupeCapteurs);
        ctx.status(201).result("Groupe de capteurs ajouté avec succès.");
    }

    private static void supprimerGroupeCapteurs(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        groupeCapteursService.supprimerGroupeCapteurs(id);
        ctx.status(200).result("Groupe de capteurs supprimé avec succès.");
    }

    private static void modifierGroupeCapteurs(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        GroupeCapteurs groupeCapteurs = ctx.bodyAsClass(GroupeCapteurs.class);
        groupeCapteursService.modifierGroupeCapteurs(id, groupeCapteurs);
        ctx.status(200).result("Groupe de capteurs modifié avec succès.");
    }

    private static void getAppareilsDansGroupe(Context ctx) {
        String nom = ctx.pathParam("nom");
        ctx.status(200).json(groupeCapteursService.getAppareilsDansGroupe(nom));
    }

    private static void getAppareilsDansGroupeParType(Context ctx) {
        String nom = ctx.pathParam("nom");
        String type = ctx.pathParam("type");
        ctx.status(200).json(groupeCapteursService.getAppareilsDansGroupeParType(nom, type));
    }
}
