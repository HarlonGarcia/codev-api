UP=docker compose up
DEV=./mvnw compile quarkus:dev

dev:
	$(UP) -d codev-db
	$(DEV) -DPORT=8000
