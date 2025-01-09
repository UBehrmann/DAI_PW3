-- Insertion des utilisateurs
INSERT INTO Utilisateur (nom, prenom, rue, noRue, npa, lieu, dateNaissance, nomUtilisateur, motDePasse, statutCompte, derniereConnexionDate, derniereConnexionHeure)
VALUES ('Dupont', 'Jean', 'Rue de la Paix', '10', '75000', 'Paris', '1985-01-01', 'jdupont', 'password123', 'ACTIF', CURRENT_DATE, CURRENT_TIME);

-- Insertion des groupes d'utilisateurs
INSERT INTO GroupeUtilisateurs (nom, dateCreation)
VALUES ('Administrateurs', CURRENT_DATE);

-- Autres données a faire...
