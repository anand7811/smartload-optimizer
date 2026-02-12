# SmartLoad Optimization API

## Run Locally

mvn clean package
java -jar target/*.jar

Service runs at:
http://localhost:8080

## Run with Docker

docker compose up --build

Service runs at:
http://localhost:8080

## Health Check

GET http://localhost:8080/actuator/health

## Optimize Endpoint

POST http://localhost:8080/api/v1/load-optimizer/optimize
Content-Type: application/json

## Example Request (One Command)

curl -X POST http://localhost:8080/api/v1/load-optimizer/optimize \
  -H "Content-Type: application/json" \
  -d @sample.json
