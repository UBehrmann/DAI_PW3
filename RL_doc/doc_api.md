# API pour la gestion des utilisateurs, séries, points de données, et alertes

Cette API permet de gérer les utilisateurs, séries de données, points de données associés, et les alertes configurées. 
Elle expose plusieurs endpoints RESTful et utilise le protocole HTTP avec le format JSON pour les échanges.

## Endpoints
### Gestion des accès (AAccesController)
#### Récupérer les appareils accessibles par un groupe
> GET /api/groupes/{groupe}/appareils

> Retourne la liste des appareils accessibles par le groupe spécifié.

Réponse et code de status :
- 200 OK : 
```json
[
  {
    "nom": "Appareil1",
    "ip": "192.168.0.1",
    "type": "capteur",
    "status": "actif"
  },
  {
    ...
  }
]
```
- 404 Not Found : 
```json
{
  "error": "Aucun appareil trouvé pour ce groupe."
}
```

#### Ajouter un accès à un appareil pour un groupe
> POST /api/groupes/{groupe}/appareils/{ip}

> Ajoute un accès à un appareil spécifique pour un groupe donné.

Réponse et code de status :
- 201 Created :
```json
{
  "message": "Accès ajouté avec succès."
}
```

#### Supprimer l'accès d'un appareil pour un groupe
> DELETE /api/groupes/{groupe}/appareils/{ip}

> Supprime l'accès d'un groupe à un appareil.

Réponse et code de status :
- 200 OK :
```json
{
  "message": "Accès supprimé avec succès."
}
```

---
### Gestion des appareils (AppareilController)
#### Récupérer tous les appareils
> GET /api/appareils

> Retourne la liste de tous les appareils.

Réponse et code de status :
- 200 OK :
```json
[
  {
    "nom": "Appareil1",
    "ip": "192.168.0.1",
    "type": "capteur",
    "status": "actif"
  },
  {
    ...
  }
]
```
- 404 Not Found :
```json
{
  "error": "Aucun appareil trouvé."
}
```

#### Récupérer les appareils pour un utilisateur
> GET /api/appareils/utilisateur/{username}

> Retourne les appareils accessibles par un utilisateur donné.

Réponse et code de status :
- 200 OK :
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
- 404 Not Found :
```json
{
  "error": "Aucun appareil trouvé pour cet utilisateur."
}
```

#### Récupérer les détails d'un appareil par son IP
> GET /api/appareils/{ip}

> Retourne les détails d’un appareil.

Réponse et code de status :
- 200 OK :
```json
{
  "nom": "Appareil1",
  "ip": "192.168.0.1",
  "type": "capteur",
  "status": "actif"
}
```
- 404 Not Found :
```json
{
  "error": "Appareil non trouvé."
}
```

#### Ajouter un nouvel appareil
> POST /api/appareils

> Ajoute un nouvel appareil à la base.

Réponse et code de status :
- 201 Created :
```json
{
  "nom": "Appareil1",
  "ip": "192.168.0.1",
  "type": "capteur",
  "status": "actif"
}
```

#### Supprimer un appareil
> DELETE /api/appareils/{ip}

> Supprime un appareil dans la base.

Réponse et code de status :
- 200 OK :
```json
{
  "message": "Appareil supprimé avec succès."
}
```

---
### Gestion des groupes et utilisateurs (AppartientAController)
#### Ajouter un utilisateur à un groupe
> POST /api/groupes/utilisateurs

> Ajoute un utilisateur à un groupe.

Réponse et code de status :
- 201 Created :
```json
{
  "message": "Utilisateur ajouté au groupe."
}
```
- 400 Bad Request :
```json
{
  "error": "Données invalides fournies."
}
```

#### Supprimer un utilisateur d’un groupe
> DELETE /api/groupes/utilisateurs/{groupe}/{utilisateur}

> Supprime un utilisateur d’un groupe.

Réponse et code de status :
- 200 OK : 
```json
{
  "message": "Utilisateur supprimé du groupe."
}
```
- 404 Not Found :
```json
{
  "error": "Utilisateur ou groupe introuvable."
}
```

---
### Gestion des groupes de capteurs (GroupeCapteursController)
#### Ajouter un groupe de capteurs
> POST /api/groupe-capteurs

> Ajoute un nouveau groupe de capteurs.

Réponse et code de status :
- 201 Created : 
```json
{
  "message": "Groupe de capteurs ajouté avec succès."
}
```

#### Supprimer un groupe de capteurs
> DELETE /api/groupe-capteurs/{id}

> Supprime un groupe de capteurs donné.

Réponse et code de status :
- 200 OK : 
```json
{
  "message": "Groupe de capteurs supprimé avec succès."
}
```
- 404 Not Found :
```json
{
  "error": "Groupe de capteurs introuvable."
}
```

#### Modifier un groupe de capteurs
> PUT /api/groupe-capteurs/{id}

> Met à jour les informations d'un groupe de capteurs.

Réponse et code de status :
- 200 OK : 
```json
{
  "message": "Groupe de capteurs modifié avec succès."
}
```

---
### Gestion des groupes d’utilisateurs (GroupeUtilisateursController)
#### Récupérer tous les groupes
> GET /api/groupes

- 200 OK : 
```json
[
  {
    "nom": "Administrateurs",
    "dateCreation": "2025-01-01",
    "administrateur": "admin123"
  },
  {
    ...
  }
]
```

#### Récupérer un groupe par nom
> GET /api/groupes/{nom}

- 200 OK : 
```json
{
  "nom": "Administrateurs",
  "dateCreation": "2025-01-01",
  "administrateur": "admin123"
}
```
- 404 Not Found :
```json
{
  "error": "Groupe introuvable."
}
```

#### Ajouter un groupe
> POST /api/groupes

- 201 Created : 
```json
{
  "message": "Groupe ajouté avec succès."
}
```

#### Mettre à jour un groupe
> PUT /api/groupes/{nom}

- 200 OK : 
```json
{
  "message": "Groupe mis à jour avec succès."
}
```
- 404 Not Found :
```json
{
  "error": "Groupe introuvable."
}
```

#### Supprimer un groupe
> DELETE /api/groupes/{nom}

- 200 OK : 
```json
{
  "message": "Groupe supprimé avec succès."
}
```
- 404 Not Found :
```json
{
  "error": "Groupe introuvable."
}
```

---
### Gestion des appareils dans les groupes (EstComposeDeController)
#### Récupérer tous les appareils dans un groupe
> GET /api/groupes-capteurs/{groupeId}/appareils

- 200 OK : 
```json
[
  "Thermomètre",
  "Capteur de pression",
  "Hygromètre"
]
```
- 404 Not Found :
```json
{
  "error": "Aucun appareil trouvé pour ce groupe."
}
```

#### Ajouter un appareil à un groupe
> POST /api/groupes-capteurs/{groupeId}/appareils

- 201 Created : 
```json
{
  "message": "Appareil ajouté au groupe."
}
```
- 400 Bad Request :
```json
{
  "error": "Données invalides."
}
```

#### Retirer un appareil d’un groupe
> DELETE /api/groupes-capteurs/{groupeId}/appareils/{ip}

- 200 OK : 
```json
{
  "message": "Appareil retiré du groupe."
}
```
- 404 Not Found :
```json
{
  "error": "Appareil ou groupe introuvable."
}
```

---
### Gestion des utilisateurs (UtilisateurController)
#### Récupérer tous les utilisateurs
> GET /api/utilisateurs

> Retourne la liste de tous les utilisateurs.

- 200 OK : 
```json
[
  {
    "nomUtilisateur": "jdoe",
    "statutCompte": "Actif"
  },
  {
    ...
  }
]
```
- 404 Not Found :
```json
{
  "error": "Aucun utilisateur trouvé."
}
```

#### Récupérer un utilisateur
> GET /api/utilisateurs/{nomUtilisateur}

> Retourne les détails d’un utilisateur spécifique.

- 200 OK : 
```json
{
  "nom": "Doe",
  "prenom": "John",
  "rue": "Rue Exemple",
  "noRue": "42",
  "npa": "1000",
  "lieu": "Lausanne",
  "dateNaissance": "1990-01-01",
  "nomUtilisateur": "jdoe",
  "motDePasse": "1234",
  "statutCompte": "Actif",
  "derniereConnexionDate": "2025-01-20",
  "derniereConnexionHeure": "14:30:00"
}
```
- 404 Not Found :
```json
{
  "error": "Utilisateur introuvable."
}
```

#### Ajouter un utilisateur
> POST /api/utilisateurs

> Ajoute un nouvel utilisateur.

- 201 Created : 
```json
{
  "message": "Utilisateur ajouté avec succès."
}
```
- 404 Not Found :
```json
{
  "error": "Données invalides fournies."
}
```

#### Mettre à jour un utilisateur
> PUT /api/utilisateurs/{nomUtilisateur}

> Met à jour les informations d’un utilisateur existant.

- 200 OK : 
```json
{
  "message": "Utilisateur mis à jour avec succès."
}
```
- 404 Not Found :
```json
{
  "error": "Utilisateur introuvable."
}
```

#### Supprimer un utilisateur
> DELETE /api/utilisateurs/{nomUtilisateur}

> Supprime un utilisateur par son identifiant.

- 200 OK : 
```json
{
  "message": "Utilisateur supprimé avec succès."
}
```
- 404 Not Found :
```json
{
  "error": "Utilisateur introuvable."
}
```

#### Vérifier les identifiants d’un utilisateur
> GET /api/utilisateurs/{nomUtilisateur}/{motDePasse}

> Vérifie si les identifiants fournis sont corrects.

- 200 OK : 
```json
{
  "message": "Utilisateur trouvé."
}
```
- 404 Not Found :
```json
{
  "error": "Utilisateur introuvable."
}
```

---
### Gestion des séries (SerieController)
#### Récupérer les configurations d’une série
> GET /api/series/{id}/configurations

> Retourne les configurations associées à une série.

- 200 OK : 
```json
{
  "id": 5,
  "seuilMinWarning": 10.0,
  "seuilMaxWarning": 30.0,
  "seuilMinAlarme": 5.0,
  "seuilMaxAlarme": 35.0
}

```
- 404 Not Found :
```json
{
  "error": "Configuration non trouvée pour la série spécifiée."
}
```

#### Récupérer les alertes d’une série
> GET /api/series/{id}/alertes

> Retourne les alertes associées à une série.

- 200 OK : 
```json
[
  {
    "type": "Warning",
    "niveau": 2,
    "timestamp": "2025-01-21T14:00:00",
    "serie_id": 1
  }
]
```
- 404 Not Found :
```json
{
  "error": "Aucune alerte trouvée pour cette série."
}
```

#### Ajouter une configuration à une série
> POST /api/series/{id}/configurations

> Ajoute une configuration à une série donnée.

- 201 Created : 
```json
{
  "message": "Configuration ajoutée avec succès."
}
```

#### Ajouter une alerte à une série
> POST /api/series/{id}/alertes

> Ajoute une alerte à une série donnée.

- 200 OK : 
```json
{
  "message": "Alerte ajoutée avec succès."
}
```

#### Modifier une configuration
> PUT /api/series/{id}/configurations/{configId}

> Modifie une configuration existante.

- 200 OK : 
```json
{
  "message": "Configuration modifiée avec succès."
}
```

#### Modifier une alerte
> PUT /api/series/{id}/alertes

> Modifie une alerte existante.

- 200 OK : 
```json
{
  "message": "Alerte modifiée avec succès."
}
```

#### Supprimer une alerte
> DELETE /api/series/{id}/alertes

> Supprime une alerte associée à une série.

- 200 OK : 
```json
{
  "message": "Alerte supprimée avec succès."
}
```

---
### Gestion des points de données (PointDeDonneesController)
#### Récupérer les points de données d’une série dans une plage de dates
> GET /api/series/{serieId}/points

> Retourne les points de données d’une série pour une plage de dates donnée.

- 200 OK : 
```json
[
  {
    "valeurs": 23.5,
    "timestamp": "2025-01-21T14:00:00",
    "serie_id": 1
  },
  {
    ...
  }
]

```
- 400 Bad Request :
```json
{
  "error": "startDate and endDate query parameters are required."
}
```

#### Récupérer les derniers points de données d’une série (limite 1000)
> GET /api/series/{serieId}/points/limit

> Retourne les 1000 derniers points de données d’une série.

- 200 OK : 
```json
[
  {
    "valeurs": 23.5,
    "timestamp": "2025-01-21T14:00:00",
    "serie_id": 1
  }
]
```
- 404 Not Found :
```json
{
  "error": "Aucun point de données trouvé pour cette série."
}
```

#### Statistiques des derniers points d’une série (limite 1000)
> GET /api/series/{serieId}/statistics/limit

> Retourne des statistiques (max, min, moyenne, médiane) sur les 1000 derniers points.

- 200 OK : 
```json
{
  "max": 45.0,
  "min": 10.5,
  "average": 22.3,
  "median": 23.0
}
```
- 404 Not Found :
```json
{
  "error": "Impossible de calculer les statistiques pour cette série."
}
```

