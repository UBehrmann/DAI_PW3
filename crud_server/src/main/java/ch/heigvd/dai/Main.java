package ch.heigvd.dai;

import io.javalin.Javalin;
import ch.heigvd.dai.controllers.UtilisateurController;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7000);

        // Enregistrement des routes utilisateur
        UtilisateurController.registerRoutes(app);

        // Ajout d'un endpoint de santé pour vérifier si le serveur fonctionne
        app.get("/health", ctx -> ctx.result("Server is running and healthy!"));
    }
}
