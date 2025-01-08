package com.votreprojet;

import io.javalin.Javalin;
import com.votreprojet.controllers.UtilisateurController;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7000);

        // Routes utilisateur
        UtilisateurController.registerRoutes(app);
    }
}
