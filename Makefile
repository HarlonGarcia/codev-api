UP=docker compose up
MVN=./mvnw

dev:
	$(UP) -d codev-db
	$(MVN) compile quarkus:dev