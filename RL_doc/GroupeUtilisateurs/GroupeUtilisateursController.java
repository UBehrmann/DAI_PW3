package ch.heigvd.dai.controllers;

import ch.heigvd.dai.models.GroupeUtilisateurs;
import ch.heigvd.dai.services.GroupeUtilisateursService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class GroupeUtilisateursController {
    private static final GroupeUtilisateursService groupeUtilisateurService = new GroupeUtilisateursService();

    public static void registerRoutes(Javalin app) {
        app.get("/api/groupes", GroupeUtilisateursController::getGroupes);
        app.get("/api/groupes/{nom}", GroupeUtilisateursController::getGroupeByNom);
        app.get("/api/groupes/utilisateur/{nomUtilisateur}", GroupeUtilisateursController::getGroupesByUtilisateur);
        app.post("/api/groupes", GroupeUtilisateursController::ajouterGroupe);
        app.put("/api/groupes/{nom}", GroupeUtilisateursController::mettreAJourGroupe);
        app.delete("/api/groupes/{nom}", GroupeUtilisateursController::supprimerGroupe);
    }

    private static void getGroupes(Context ctx) {
        List<GroupeUtilisateurs> groupes = groupeUtilisateurService.getGroupes();
        ctx.json(groupes);
    }

    private static void getGroupeByNom(Context ctx) {
        String nom = ctx.pathParam("nom");
        GroupeUtilisateurs groupe = groupeUtilisateurService.getGroupeByNom(nom);
        if (groupe != null) {
            ctx.json(groupe);
        } else {
            ctx.status(404).result("Groupe introuvable");
        }
    }

    private static void getGroupesByUtilisateur(Context ctx) {
        String nomUtilisateur = ctx.pathParam("nomUtilisateur");
        List<GroupeUtilisateurs> groupes = groupeUtilisateurService.getGroupesByUtilisateur(nomUtilisateur);
        ctx.json(groupes);
    }

    private static void ajouterGroupe(Context ctx) {
        GroupeUtilisateurs groupe = ctx.bodyAsClass(GroupeUtilisateurs.class);
        groupeUtilisateurService.ajouterGroupe(groupe);
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
        groupe.setNom(nom); // Assure que l'identifiant reste inchangé
        groupeUtilisateurService.mettreAJourGroupe(groupe);
        ctx.status(200).result("Groupe mis à jour");
    }

    private static void supprimerGroupe(Context ctx) {
        String nom = ctx.pathParam("nom");
        groupeUtilisateurService.supprimerGroupe(nom);
        ctx.status(200).result("Groupe supprimé");
    }
}
