package ch.heigvd.dai;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.Javalin;
import ch.heigvd.dai.controllers.UtilisateurController;

public class Main {
    public static void main(String[] args) {

        // Configure ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        Javalin app = Javalin.create().start(7000);

        // Enregistrement des routes utilisateur
        UtilisateurController.registerRoutes(app);

        // Ajout d'un endpoint de santé pour vérifier si le serveur fonctionne
        app.get("/health", ctx -> ctx.result("Server is running and healthy!"));
    }
}
