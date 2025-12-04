.PHONY: help infra all down stop-services dev-stay dev-booking dev-media dev-woofplanner logs

GREEN  := \033[0;32m
YELLOW := \033[0;33m
BLUE   := \033[0;34m
NC     := \033[0m

help: ## Affiche cette aide
	@echo "$(BLUE)WoofWoof Backend - Commandes Docker Compose$(NC)"
	@echo ""
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "  $(GREEN)%-20s$(NC) %s\n", $$1, $$2}'

infra: ## Lance l'infrastructure (Eureka, Keycloak, DBs, Gateway)
	@echo "$(YELLOW)Demarrage de l'infrastructure...$(NC)"
	docker compose up -d

all: ## Lance TOUS les services
	@echo "$(YELLOW)Demarrage de tous les services...$(NC)"
	docker compose --profile all up -d

stop-services: ## Arrete uniquement les services metier (garde l'infra)
	@echo "$(YELLOW)Arret des services metier...$(NC)"
	docker compose --profile all stop stay-service booking-service media-service woofplanner-service

down: ## Arrete et supprime tous les containers (garde les volumes)
	@echo "$(YELLOW)Arret complet...$(NC)"
	docker compose --profile all down

down-volumes: ## Arrete tout et supprime les volumes (perte de donnees)
	@echo "$(YELLOW)Suppression complete (donnees incluses)...$(NC)"
	docker compose --profile all down -v

dev-stay: ## Dev sur stay-service (lance les autres services dans Docker)
	@echo "$(YELLOW)Mode dev: stay-service$(NC)"
	docker compose --profile booking --profile media --profile woofplanner up -d
	@echo "$(GREEN)Services demarres. Lancez maintenant:$(NC)"
	@echo "   cd stayservice && mvn spring-boot:run"

dev-booking: ## Dev sur booking-service (lance les autres services dans Docker)
	@echo "$(YELLOW)Mode dev: booking-service$(NC)"
	docker compose --profile stay --profile media --profile woofplanner up -d
	@echo "$(GREEN)Services demarres. Lancez maintenant:$(NC)"
	@echo "   cd bookingservice && mvn spring-boot:run"

dev-media: ## Dev sur media-service (lance les autres services dans Docker)
	@echo "$(YELLOW)Mode dev: media-service$(NC)"
	docker compose --profile stay --profile booking --profile woofplanner up -d
	@echo "$(GREEN)Services demarres. Lancez maintenant:$(NC)"
	@echo "   cd mediaservice && mvn spring-boot:run"

dev-woofplanner: ## Dev sur woofplanner-service (lance les autres services dans Docker)
	@echo "$(YELLOW)Mode dev: woofplanner-service$(NC)"
	docker compose --profile stay --profile booking --profile media up -d
	@echo "$(GREEN)Services demarres. Lancez maintenant:$(NC)"
	@echo "   cd woofplanner && mvn spring-boot:run"

ps: ## Liste les containers en cours d'execution
	docker compose --profile all ps

clean: ## Nettoie les images Docker inutilisees
	@echo "$(YELLOW)Nettoyage des images Docker...$(NC)"
	docker system prune -f

rebuild: ## Rebuild un service specifique (usage: make rebuild SERVICE=stay-service)
	@echo "$(YELLOW)Rebuild de $(SERVICE)...$(NC)"
	docker compose build $(SERVICE)
	docker compose up -d $(SERVICE)