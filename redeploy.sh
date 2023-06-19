#!/bin/bash
./mvnw compile -Pcss
# Clean the project
./mvnw clean

# Build the project, skipping tests
./mvnw package -DskipTests

# Set the necessary environment variables and run the Quarkus application
export QUARKUS_DATASOURCE_JDBC_URL="jdbc:postgresql://localhost:5432/petclinic"
export QUARKUS_DATASOURCE_USERNAME="petclinic"
export QUARKUS_DATASOURCE_PASSWORD="petclinic"
java -jar target/quarkus-app/quarkus-run.jar
