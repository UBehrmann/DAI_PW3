SET SEARCH_PATH TO projet, public;


-- Insertion des utilisateurs
-- Insertion des utilisateurs
INSERT INTO Utilisateur (nom, prenom, rue, noRue, npa, lieu, dateNaissance, nomUtilisateur, motDePasse, statutCompte, derniereConnexionDate, derniereConnexionHeure)
VALUES 
    ('Dupont', 'Jean', 'Rue de la Paix', '10', '75000', 'Paris', '1985-01-01', 'jdupont', 'password123', 'ACTIF', CURRENT_DATE, CURRENT_TIME),
    ('Durand', 'Marie', 'Avenue des Champs', '5', '75001', 'Paris', '1990-02-15', 'mdurand', 'securePass456', 'ACTIF', CURRENT_DATE, CURRENT_TIME),
    ('Smith', 'John', 'Rue des Fleurs', '8', '69000', 'Lyon', '1978-11-25', 'jsmith', 'password789', 'INACTIF', NULL, NULL);

-- Insertion des groupes d'utilisateurs
INSERT INTO GroupeUtilisateurs (nom, dateCreation, administrateur)
VALUES
    ('Administrateurs', CURRENT_DATE, 'jdupont'),
    ('Utilisateurs', CURRENT_DATE, 'mdurand');

-- Insertion des appareils
INSERT INTO Appareil (nom, ip, type, status)
VALUES
    ('Capteur Température', '192.168.1.101', 'Capteur', 'ACTIF'),
    ('Capteur Humidité', '192.168.1.102', 'Capteur', 'ACTIF'),
    ('Contrôleur Principal', '192.168.1.1', 'Contrôleur', 'ACTIF');

-- Insertion des configurations
INSERT INTO Configuration (id, seuilMinWarning, seuilMaxWarning, seuilMinAlarme, seuilMaxAlarme)
VALUES
    (1, 18.0, 25.0, 15.0, 30.0),
    (2, 30.0, 50.0, 20.0, 60.0);

-- Insertion des séries
INSERT INTO Serie (id, nom, config_id, appareil_ip)
VALUES
    (1, 'Température Salon', 1, '192.168.1.101'),
    (2, 'Humidité Bureau', 2, '192.168.1.102');

-- Insertion des groupes de capteurs
INSERT INTO GroupeCapteurs (id, nom, description)
VALUES
    (1, 'Capteurs Intérieurs', 'Capteurs situés à l’intérieur des bâtiments'),
    (2, 'Capteurs Extérieurs', 'Capteurs situés à l’extérieur des bâtiments');

-- Insertion des types de données
INSERT INTO TypeDeDonnees (nom, symboleDonnees)
VALUES
    ('Température', '°C'),
    ('Humidité', '%');

-- Relation entre utilisateurs et groupes
INSERT INTO appartient_a (utilisateur, groupe)
VALUES
    ('jdupont', 'Administrateurs'),
    ('mdurand', 'Utilisateurs'),
    ('jsmith', 'Utilisateurs');

-- Relation entre appareils et groupes de capteurs
INSERT INTO est_compose_de (ip, groupe)
VALUES
    ('192.168.1.101', 1),
    ('192.168.1.102', 1),
    ('192.168.1.1', 2);

-- Relation entre séries et types de données
INSERT INTO fournit (serie_id, type)
VALUES
    (1, 'Température'),
    (2, 'Humidité');

-- Insertion des points de données
INSERT INTO PointDeDonnees (valeurs, timestamp, serie_id)
VALUES
    (22.5, CURRENT_DATE, 1),
    (40.3, CURRENT_DATE, 2);

-- Insertion des alertes
INSERT INTO Alerte (type, niveau, timestamp, serie_id)
VALUES
    ('Température Critique', 5, NOW(), 1),
    ('Humidité Anormale', 3, NOW() + INTERVAL '1 second', 2);

