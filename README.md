# WoofWoof-backend

## Setup Keycloak

Go to http://localhost:8000 on your browser

Then login with

- Username : admin
- Password : admin

The WoofWoof realm is automaticly create with basics clients, users and roles.

!! But you must link users to their roles woofer and backpacker.

!! And assign password not temporary to each users

!! Disable refresh token in the client expo-client settings

## Setup avec Docker Compose

### Commandes disponibles

Pour voir toutes les commandes disponibles, tapez :

```bash
make
```

Cela affichera :

```
WoofWoof Backend - Commandes Docker Compose

  all                  Lance TOUS les services
  clean                Nettoie les images Docker inutilisees
  dev-booking          Dev sur booking-service (lance les autres services dans Docker)
  dev-media            Dev sur media-service (lance les autres services dans Docker)
  dev-stay             Dev sur stay-service (lance les autres services dans Docker)
  dev-woofplanner      Dev sur woofplanner-service (lance les autres services dans Docker)
  down-volumes         Arrete tout et supprime les volumes (perte de donnees)
  down                 Arrete et supprime tous les containers (garde les volumes)
  help                 Affiche cette aide
  infra                Lance l'infrastructure (Eureka, Keycloak, DBs, Gateway)
  logs                 Affiche les logs de tous les services
  logs-infra           Affiche les logs de l'infrastructure uniquement
  ps                   Liste les containers en cours d'execution
  rebuild              Rebuild un service specifique (usage: make rebuild SERVICE=stay-service)
  stop-services        Arrete uniquement les services metier (garde l'infra)
```

### Exemples d'utilisation

**Demarrage rapide :**

```bash
# Lancer l'infrastructure uniquement
make infra

# Lancer tous les services
make all
```

**Developpement sur un service :**

```bash
# Exemple : travailler sur woofplanner en local
make dev-woofplanner
cd woofplanner && mvn spring-boot:run
```

## Configuration des profils Spring

Les services utilisent deux configurations :

- **Par defaut (local)** : Se connecte a `localhost`
- **Profil docker** : Se connecte aux containers Docker

Le profil est automatiquement active selon l'environnement d'execution.

## Notes

- N'oubliez pas de remplir le fichier `.env` avec les credentials
- Pour ajouter un nouveau microservice, modifiez `docker-compose.yml` et ajoutez un Dockerfile
