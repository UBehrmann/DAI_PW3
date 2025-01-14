SET SEARCH_PATH TO projet, public;

-- Création des trigger et Création de vue





-- pour la table utilisateur
CREATE OR REPLACE FUNCTION log_modif_utilisateur()
RETURNS TRIGGER AS $$
BEGIN
    IF (TG_OP = 'INSERT') THEN
        INSERT INTO projet.LogUtilisateur (utilisateur, action, details)
        VALUES (NEW.nomUtilisateur, TG_OP, row_to_json(NEW));
    ELSIF (TG_OP = 'UPDATE') THEN
        INSERT INTO projet.LogUtilisateur (utilisateur, action, details)
        VALUES (NEW.nomUtilisateur, 'modifié', jsonb_build_object('ancien', row_to_json(OLD), 'nouveau', row_to_json(NEW)));
    ELSIF (TG_OP = 'DELETE') THEN
        INSERT INTO projet.LogUtilisateur (utilisateur, action, details)
        VALUES (OLD.nomUtilisateur, 'supprimé', row_to_json(OLD));
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER log_trigger_utilisateur
AFTER INSERT OR UPDATE OR DELETE
ON Utilisateur
FOR EACH ROW
EXECUTE FUNCTION log_modif_utilisateur();


-- pour la table groupeUtilisateur
CREATE OR REPLACE FUNCTION log_modif_groupe()
RETURNS TRIGGER AS $$
BEGIN
    IF (TG_OP = 'INSERT') THEN
        INSERT INTO projet.LogGroupe (groupe, action, details)
        VALUES (NEW.nom, 'ajouté', row_to_json(NEW));
    ELSIF (TG_OP = 'UPDATE') THEN
        INSERT INTO projet.LogGroupe (groupe, action, details)
        VALUES (NEW.nom, 'modifié', jsonb_build_object('ancien', row_to_json(OLD), 'nouveau', row_to_json(NEW)));
    ELSIF (TG_OP = 'DELETE') THEN
        INSERT INTO projet.LogGroupe (groupe, action, details)
        VALUES (OLD.nom, 'supprimé', row_to_json(OLD));
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER log_trigger_groupe
AFTER INSERT OR UPDATE OR DELETE
ON GroupeUtilisateurs
FOR EACH ROW
EXECUTE FUNCTION log_modif_groupe();


-- pour la table appartient_a
CREATE OR REPLACE FUNCTION log_modif_appartenance()
RETURNS TRIGGER AS $$
BEGIN
    IF (TG_OP = 'INSERT') THEN
        INSERT INTO projet.LogAppartenance (utilisateur, groupe, action)
        VALUES (NEW.utilisateur, NEW.groupe, 'ajouté');
    ELSIF (TG_OP = 'DELETE') THEN
        INSERT INTO projet.LogAppartenance (utilisateur, groupe, action)
        VALUES (OLD.utilisateur, OLD.groupe, 'supprimé');
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER log_trigger_appartenance
AFTER INSERT OR DELETE
ON appartient_a
FOR EACH ROW
EXECUTE FUNCTION log_modif_appartenance();

-- pour la table a_acces
CREATE OR REPLACE FUNCTION log_modif_acces()
RETURNS TRIGGER AS $$
BEGIN
    IF (TG_OP = 'INSERT') THEN
        INSERT INTO projet.LogAcces (groupe, ip, action)
        VALUES (NEW.groupe, NEW.ip, 'ajouté');
    ELSIF (TG_OP = 'DELETE') THEN
        INSERT INTO projet.LogAcces (groupe, ip, action)
        VALUES (OLD.groupe, OLD.ip, 'supprimé');
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER log_trigger_acces
AFTER INSERT OR DELETE
ON a_acces
FOR EACH ROW
EXECUTE FUNCTION log_modif_acces();


-- **********************************************************************
-- Vue pour consulter le profil utilisateur
-- **********************************************************************
CREATE OR REPLACE VIEW ProfilUtilisateur AS
SELECT
    nomUtilisateur,
    nom,
    prenom,
    rue,
    noRue,
    npa,
    lieu,
    dateNaissance,
    statutCompte,
    derniereConnexionDate,
    derniereConnexionHeure
FROM Utilisateur;


-- **********************************************************************
-- Vue pour consulter les groupes d'un utilisateur sous forme d'array json
-- **********************************************************************
CREATE OR REPLACE VIEW GroupesUtilisateur AS
SELECT
    u.nomUtilisateur,
    array_agg(
        jsonb_build_object(
            'nom', g.nom,
            'dateCreation', g.dateCreation,
            'administrateur', g.administrateur
        )
    ) AS groupes
FROM
    Utilisateur u
JOIN
    appartient_a a ON u.nomUtilisateur = a.utilisateur
JOIN
    GroupeUtilisateurs g ON a.groupe = g.nom
GROUP BY
    u.nomUtilisateur;



-- **********************************************************************
-- Vue pour consulter la liste des appareils
-- **********************************************************************
CREATE OR REPLACE VIEW ListeAppareils AS
SELECT
    nom,
    ip,
    type,
    status
FROM Appareil;


-- **********************************************************************
-- Requête préparée pour consulter les données d'un appareil
-- **********************************************************************
CREATE OR REPLACE FUNCTION DonneesAppareil_Fonction(
    date_debut TIMESTAMP,
    date_fin TIMESTAMP DEFAULT NOW()
)
RETURNS TABLE (
    appareil TEXT,
    serie TEXT,
    valeurs JSONB,
    "timestamp" TIMESTAMP
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        a.nom AS appareil,
        s.nom AS serie,
        p.valeurs,
        p.timestamp
    FROM
        Appareil a
    JOIN
        Serie s ON a.ip = s.appareil_ip
    JOIN
        PointDeDonnees p ON s.id = p.serie_id
    WHERE
        p.timestamp BETWEEN date_debut AND date_fin;
END;
$$ LANGUAGE plpgsql;



-- **********************************************************************
-- Requête préparée pour consulter les alertes par série
-- **********************************************************************
CREATE OR REPLACE FUNCTION AlertesSerie_Fonction(
    date_debut TIMESTAMP,
    date_fin TIMESTAMP DEFAULT NOW()
)
RETURNS TABLE (
    serie TEXT,
    type TEXT,
    niveau TEXT,
    "timestamp" TIMESTAMP
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        s.nom AS serie,
        a.type,
        a.niveau,
        a.timestamp
    FROM
        Serie s
    JOIN
        Alerte a ON s.id = a.serie_id
    WHERE
        a.timestamp BETWEEN date_debut AND date_fin;
END;
$$ LANGUAGE plpgsql;
