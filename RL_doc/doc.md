# API pour la gestion des utilisateurs, séries, points de données, et alertes

Cette API permet de gérer les utilisateurs, séries de données, points de données associés, et les alertes configurées. 
Elle expose plusieurs endpoints RESTful et utilise le protocole HTTP avec le format JSON pour les échanges.

## Endpoints
### Gestion des accès (AAccesController)
#### Récupérer les appareils accessibles par un groupe
> GET /api/groupes/{groupe}/appareils

> Retourne la liste des appareils accessibles par le groupe spécifié.

Modèle de donnée :
```json
[
  {
    "nom": "Appareil1",
    "ip": "192.168.0.1",
    "type": "capteur",
    "status": "actif"
  }
]

```

Code de status :
- 200 OK : Appareils récupérés avec succès.
- 404 Not Found : Aucun appareil trouvé pour le groupe.

#### Ajouter un accès à un appareil pour un groupe
> POST /api/groupes/{groupe}/appareils/{ip}

> Ajoute un accès à un appareil spécifique pour un groupe donné.

Code de status :
- 201 Created : Accès ajouté avec succès.

#### Supprimer l'accès d'un appareil pour un groupe
> DELETE /api/groupes/{groupe}/appareils/{ip}

> Supprime l'accès d'un groupe à un appareil.

Code de status :
- 200 OK : Accès supprimé avec succès.

---
### Gestion des appareils (AppareilController)
#### Récupérer tous les appareils
> GET /api/appareils

> Retourne la liste de tous les appareils.
#### Récupérer les appareils pour un utilisateur
> GET /api/appareils/utilisateur/{username}

> Retourne les appareils accessibles par un utilisateur donné.
#### Récupérer les détails d'un appareil par son IP
> GET /api/appareils/{ip}

> Retourne les détails d’un appareil.
#### Ajouter un nouvel appareil
> POST /api/appareils

> Ajoute un nouvel appareil à la base.

---
### Gestion des groupes et utilisateurs (AppartientAController)
#### Ajouter un utilisateur à un groupe
> POST /api/groupes/utilisateurs

> Ajoute un utilisateur à un groupe.
### Supprimer un utilisateur d’un groupe
> DELETE /api/groupes/utilisateurs/{groupe}/{utilisateur}

> Supprime un utilisateur d’un groupe.







---
### Gestion des groupes de capteurs (GroupeCapteursController)
#### Ajouter un groupe de capteurs
> POST /api/groupe-capteurs

> Ajoute un nouveau groupe de capteurs.
#### Supprimer un groupe de capteurs
> DELETE /api/groupe-capteurs/{id}

> Supprime un groupe de capteurs donné.
#### Modifier un groupe de capteurs
> PUT /api/groupe-capteurs/{id}

> Met à jour les informations d'un groupe de capteurs.

---
### Gestion des groupes d’utilisateurs (GroupeUtilisateursController)
#### Récupérer tous les groupes
> GET /api/groupes
#### Ajouter un groupe
> POST /api/groupes
#### Supprimer un groupe
> DELETE /api/groupes/{nom}

---
### Gestion des appareils dans les groupes (EstComposeDeController)
#### Ajouter un appareil à un groupe
> POST /api/groupes-capteurs/{groupeId}/appareils
#### Retirer un appareil d’un groupe
> DELETE /api/groupes-capteurs/{groupeId}/appareils/{ip}

---
### Gestion des utilisateurs (UtilisateurController)
#### Récupérer tous les utilisateurs
> GET /api/utilisateurs

> Retourne la liste de tous les utilisateurs.
#### Récupérer un utilisateur
> GET /api/utilisateurs/{nomUtilisateur}

> Retourne les détails d’un utilisateur spécifique.
#### Ajouter un utilisateur
> POST /api/utilisateurs

> Ajoute un nouvel utilisateur.
#### Mettre à jour un utilisateur
> PUT /api/utilisateurs/{nomUtilisateur}

> Met à jour les informations d’un utilisateur existant.
#### Supprimer un utilisateur
> DELETE /api/utilisateurs/{nomUtilisateur}

> Supprime un utilisateur par son identifiant.
#### Vérifier les identifiants d’un utilisateur
> GET /api/utilisateurs/{nomUtilisateur}/{motDePasse}

> Vérifie si les identifiants fournis sont corrects.

---
### Gestion des séries (SerieController)
#### Récupérer les alertes d’une série
> GET /api/series/{id}/alertes

> Retourne les alertes associées à une série.
#### Ajouter une alerte à une série
> POST /api/series/{id}/alertes

> Ajoute une alerte à une série donnée.
#### Modifier une alerte
> PUT /api/series/{id}/alertes

> Modifie une alerte existante.
#### Supprimer une alerte
> DELETE /api/series/{id}/alertes

> Supprime une alerte associée à une série.
#### Ajouter une configuration
> POST /api/series/{id}/configurations

> Ajoute une nouvelle configuration de seuil pour une série.
#### Modifier une configuration
> PUT /api/series/{id}/configurations/{configId}

> Met à jour une configuration existante.

---
### Gestion des points de données (PointDeDonneesController)
#### Récupérer les points de données d’une série dans une plage de dates
> GET /api/series/{serieId}/points

> Retourne les points de données d’une série pour une plage de dates donnée.
#### Récupérer les derniers points de données d’une série (limite 1000)
> GET /api/series/{serieId}/points/limit

> Retourne les 1000 derniers points de données d’une série.
#### Statistiques des derniers points d’une série
> GET /api/series/{serieId}/statistics/limit

> Retourne des statistiques (max, min, moyenne, médiane) sur les 1000 derniers points.
