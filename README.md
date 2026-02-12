# SmartLoad Optimization API

## Run Locally

mvn clean package
java -jar target/optimizer-0.0.1-SNAPSHOT.jar

Service runs at:
http://localhost:8080

## Run with Docker

docker compose up --build

## Health Check

GET http://localhost:8080/actuator/health

## Optimize Endpoint

POST /api/v1/load-optimizer/optimize
Content-Type: application/json
