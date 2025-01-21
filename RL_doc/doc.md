# API pour la gestion des utilisateurs, séries, points de données, et alertes

Cette API permet de gérer les utilisateurs, séries de données, points de données associés, et les alertes configurées. 
Elle expose plusieurs endpoints RESTful et utilise le protocole HTTP avec le format JSON pour les échanges.

## Endpoints
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
