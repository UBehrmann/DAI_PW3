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

        // Add CORS headers globally for all requests
        app.before(ctx -> {
            ctx.header("Access-Control-Allow-Origin", "*");
            ctx.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            ctx.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
        });

        // Register routes
        UtilisateurController.registerRoutes(app);

        // Health check endpoint
        app.get("/health", ctx -> ctx.result("Server is running and healthy!"));
    }
}
