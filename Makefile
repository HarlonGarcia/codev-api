COMPOSE=docker compose
QUARKUS_DEV=./mvnw compile quarkus:dev
LOCAL_DB_PARAMS=-DUSERNAME=postgres -DPASSWORD=postgres -DDATABASE_URL=jdbc:postgresql://localhost:5432/codev-db

prod:
	$(COMPOSE) up -d codev-api frontend

dev:
	$(COMPOSE) up -d codev-db
	$(QUARKUS_DEV) $(LOCAL_DB_PARAMS)

front-dev:
	$(COMPOSE) up -d codev-db
	$(QUARKUS_DEV) -DPORT=8000 $(LOCAL_DB_PARAMS)

stop:
	$(COMPOSE) down