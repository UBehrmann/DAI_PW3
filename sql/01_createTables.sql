DROP SCHEMA IF EXISTS projet CASCADE;
CREATE SCHEMA projet;
set SEARCH_PATH to projet, public; -- public pour timescaledb

-- Table pour les utilisateurs (informations personnelles et connexion)
-- Contient les données personnelles des utilisateurs et leurs informations de connexion.
-- 'nomUtilisateur' est la clé primaire pour identifier chaque utilisateur.
-- 'statutCompte' indique l'état du compte (actif, suspendu, etc.).
-- 'derniereConnexionDate' et 'derniereConnexionHeure' permettent de suivre la dernière connexion.

CREATE TABLE Utilisateur (
    nom VARCHAR(255),
    prenom VARCHAR(255),
    rue VARCHAR(255),
    noRue VARCHAR(10),
    npa VARCHAR(10),
    lieu VARCHAR(255),
    dateNaissance DATE,
    nomUtilisateur VARCHAR(50) PRIMARY KEY,
    motDePasse VARCHAR(255),
    statutCompte VARCHAR(50),
    derniereConnexionDate DATE,
    derniereConnexionHeure TIME
);

-- Table pour les groupes d'utilisateurs
-- Regroupe des utilisateurs avec des rôles ou permissions spécifiques.
-- 'nom' est la clé primaire du groupe.
-- 'administrateur' est une référence à un utilisateur qui administre ce groupe.

CREATE TABLE GroupeUtilisateurs (
    nom VARCHAR(255) PRIMARY KEY,
    dateCreation DATE,
    administrateur VARCHAR(50) NOT NULL,
    FOREIGN KEY (administrateur) REFERENCES Utilisateur(nomUtilisateur) ON DELETE SET NULL
);

-- Table pour les appareils (informations sur les dispositifs)
-- Contient les informations sur les appareils connectés (e.g., capteurs ou contrôleurs).
-- 'ip' est la clé primaire, utilisée pour identifier chaque appareil.

CREATE TABLE Appareil (
    nom VARCHAR(255),
    ip VARCHAR(15) PRIMARY KEY,
    type VARCHAR(50),
    status VARCHAR(50)
);

-- Table pour les configurations (seuils d'alerte et paramètres)
-- Définit les seuils de fonctionnement (warning/alarme) pour les séries de données.
-- La contrainte CHECK garantit que les seuils sont cohérents.

CREATE TABLE Configuration (
                               id INT PRIMARY KEY,
                               seuilMinWarning FLOAT,
                               seuilMaxWarning FLOAT,
                               seuilMaxAlarme FLOAT,
                               seuilMinAlarme FLOAT,
                               CHECK (seuilMinWarning < seuilMaxWarning AND seuilMinAlarme < seuilMaxAlarme)
);

-- Table pour les séries (regroupement logique des données d'un appareil)
-- Définit les séries de données mesurées par un appareil.
-- 'config_id' référence une configuration définissant les paramètres de la série.
-- 'appareil_ip' lie la série à un appareil.

CREATE TABLE Serie (
    id INT PRIMARY KEY,
    nom VARCHAR(255),
    config_id INT,
    appareil_ip VARCHAR(15) NOT NULL,
    FOREIGN KEY (config_id) REFERENCES Configuration(id) ON DELETE SET NULL,
    FOREIGN KEY (appareil_ip) REFERENCES Appareil(ip) ON DELETE CASCADE
);

-- Table pour les groupes de capteurs
-- Regroupe des capteurs avec des objectifs ou caractéristiques spécifiques.
-- 'id' est la clé primaire unique pour chaque groupe.

CREATE TABLE GroupeCapteurs (
    id INT PRIMARY KEY,
    nom VARCHAR(255),
    description TEXT
);



-- Table pour les alertes (informations sur les événements déclenchés)
-- Enregistre les alertes générées par les séries de données.
-- La contrainte CHECK limite 'niveau' à une valeur entre 1 et 5.

CREATE TABLE Alerte (
    type VARCHAR(50),
    niveau INT,
    timestamp TIMESTAMP NOT NULL UNIQUE ,
    serie_id INT,
   PRIMARY KEY (timestamp, serie_id),
    FOREIGN KEY (serie_id) REFERENCES Serie(id) ON DELETE CASCADE,
    CHECK (niveau BETWEEN 1 AND 5)
);

-- Table pour les types de données (description des données collectées)
-- Définit les types de données collectées par les séries.
-- 'nom' est la clé primaire.

CREATE TABLE TypeDeDonnees (
    nom VARCHAR(255) PRIMARY KEY,
    symboleDonnees VARCHAR(50)
);

-- Table pour les points de données (valeurs mesurées et leur horodatage)
-- Stocke les mesures prises par les capteurs, avec une référence temporelle.
-- La contrainte CHECK garantit que la date ne dépasse pas la date actuelle.

CREATE TABLE PointDeDonnees (
    valeurs FLOAT,
    timestamp TIMESTAMP,
    serie_id INT NOT NULL,
    PRIMARY KEY (timestamp, serie_id),
    FOREIGN KEY (serie_id) REFERENCES Serie(id) ON DELETE CASCADE,
    CHECK (timestamp <= CURRENT_DATE)
);

-- Table de relation entre les utilisateurs et les groupes d'utilisateurs
-- Indique à quel groupe appartient chaque utilisateur.
-- Les relations sont supprimées si un utilisateur ou un groupe est supprimé.

CREATE TABLE appartient_a (
    utilisateur VARCHAR(50),
    groupe VARCHAR(255),
    PRIMARY KEY (utilisateur, groupe),
    FOREIGN KEY (utilisateur) REFERENCES Utilisateur(nomUtilisateur) ON DELETE CASCADE,
    FOREIGN KEY (groupe) REFERENCES GroupeUtilisateurs(nom) ON DELETE CASCADE
);

-- Table de relation entre les capteurs et les appareils
-- Associe des capteurs à des appareils spécifiques.
-- La relation est supprimée si un appareil ou un groupe de capteurs est supprimé.

CREATE TABLE est_compose_de (
    ip VARCHAR(15),
    groupe INT,
    PRIMARY KEY (ip, groupe),
    FOREIGN KEY (ip) REFERENCES Appareil(ip) ON DELETE CASCADE,
    FOREIGN KEY (groupe) REFERENCES GroupeCapteurs(id) ON DELETE CASCADE
);

-- Table de relation entre les groupes et les appareils
-- Définit les groupes ayant accès à un appareil spécifique.
-- La relation est supprimée si un groupe ou un appareil
CREATE TABLE a_acces (
    groupe VARCHAR(255),
    ip VARCHAR(15),
    PRIMARY KEY (groupe, ip),
    FOREIGN KEY (groupe) REFERENCES GroupeUtilisateurs(nom) ON DELETE CASCADE,
    FOREIGN KEY (ip) REFERENCES Appareil(ip) ON DELETE CASCADE
);

-- Table de relation entre les séries et les types de données
CREATE TABLE fournit (
    serie_id INT,
    type VARCHAR(255),
    PRIMARY KEY (serie_id, type),
    FOREIGN KEY (serie_id) REFERENCES Serie(id) ON DELETE CASCADE,
    FOREIGN KEY (type) REFERENCES TypeDeDonnees(nom) ON DELETE CASCADE
);

-- **********************************************************************
-- Logs pour les insertions, changements et suppressions concernant les
-- utilisateurs (groupe, accès aux appareils) dans les tables utilisateur,
-- groupeUtilisateurs,
-- **********************************************************************

CREATE TABLE LogUtilisateur (
    id SERIAL PRIMARY KEY, -- ID unique pour chaque log
    utilisateur VARCHAR(50), -- Nom d'utilisateur concerné
    action VARCHAR(50), -- Action effectuée (ajouté, modifié, supprimé)
    details JSONB, -- Informations sur la ligne affectée
    dateAction TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- Date et heure de l'action
);

CREATE TABLE LogGroupe (
    id SERIAL PRIMARY KEY,
    groupe VARCHAR(255), -- Groupe affecté
    action VARCHAR(50), -- Action effectuée (ajouté, modifié, supprimé)
    details JSONB, -- Informations sur la ligne affectée
    dateAction TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE LogAppartenance (
    id SERIAL PRIMARY KEY,
    utilisateur VARCHAR(50), -- Nom d'utilisateur concerné
    groupe VARCHAR(255), -- Groupe affecté
    action VARCHAR(50), -- Action effectuée (ajouté, supprimé)
    dateAction TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE LogAcces (
    id SERIAL PRIMARY KEY,
    groupe VARCHAR(255), -- Groupe concerné
    ip VARCHAR(15), -- Adresse IP de l'appareil
    action VARCHAR(50), -- Action effectuée (ajouté, supprimé)
    dateAction TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- configuration de timescale pour de meilleures performances.
-- divise les tables selon la date
CREATE EXTENSION IF NOT EXISTS timescaledb;

SELECT create_hypertable('projet.pointdedonnees', 'timestamp', chunk_time_interval => '2 days'::interval);

SELECT create_hypertable('projet.alerte', 'timestamp', chunk_time_interval => '7 days'::interval);
