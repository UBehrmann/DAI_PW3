package ch.heigvd.dai;

import ch.heigvd.dai.controllers.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.Javalin;
import io.javalin.http.Context;

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
        AppareilController.registerRoutes(app);
        AppartientAController.registerRoutes(app);
        GroupeCapteursController.registerRoutes(app);
        GroupeUtilisateursController.registerRoutes(app);
        PointDeDonneesController.registerRoutes(app);
        SerieController.registerRoutes(app);
        UtilisateurController.registerRoutes(app);

        // Handle preflight (OPTIONS) requests for all routes
        app.options("/api/*", Main::handlePreflight);

        // Health check endpoint
        app.get("/api/health", ctx -> ctx.result("Server is running and healthy!"));
    }

    private static void handlePreflight(Context ctx) {
        // Respond to OPTIONS requests
        ctx.status(204); // No Content
    }
}
