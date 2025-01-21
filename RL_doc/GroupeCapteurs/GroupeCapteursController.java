package ch.heigvd.dai.controllers;

import ch.heigvd.dai.models.GroupeCapteurs;
import ch.heigvd.dai.services.GroupeCapteursService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.sql.Date;

public class GroupeCapteursController {
    private static final GroupeCapteursService groupeCapteursService = new GroupeCapteursService();

    public static void registerRoutes(Javalin app) {
        app.post("/api/groupe-capteurs", GroupeCapteursController::ajouterGroupeCapteurs);
        app.delete("/api/groupe-capteurs/{id}", GroupeCapteursController::supprimerGroupeCapteurs);
        app.put("/api/groupe-capteurs/{id}", GroupeCapteursController::modifierGroupeCapteurs);
        app.get("api/groupe-capteurs", GroupeCapteursController::getGroupesCapteurs);
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

    private static void getGroupeCapteursParId(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        GroupeCapteurs groupe = groupeCapteursService.getGroupeCapteursParId(id);
        if (groupe != null) {
            ctx.json(groupe);
        } else {
            ctx.status(404).result("Groupe de capteurs introuvable.");
        }
    }

    private static void getAppareilsDansGroupe(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        ctx.status(200).json(groupeCapteursService.getAppareilsDansGroupe(id));
    }

    private static void getAppareilsDansGroupeParType(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        String type = ctx.pathParam("type");
        ctx.status(200).json(groupeCapteursService.getAppareilsDansGroupeParType(id, type));
    }

    private static void getGroupesCapteursParUtilisateur(Context ctx) {
        String username = ctx.pathParam("username");
        ctx.status(200).json(groupeCapteursService.getGroupesCapteursParUtilisateur(username));
    }

    private static void getAppareilsParUtilisateurEtType(Context ctx) {
        String username = ctx.pathParam("username");
        String type = ctx.pathParam("type");
        ctx.status(200).json(groupeCapteursService.getAppareilsParUtilisateurEtType(username, type));
    }

    private static void getGroupesCapteurs(Context ctx) {
        ctx.status(200).json(groupeCapteursService.getGroupesCapteurs());
    }
}
