# ExpresoFast - Laboratorio 10

Proyecto dividido exactamente en:

- `expresofast-backend`
- `expresofast-frontend`

## Backend

Requiere Java 21 y Maven.

```bash
cd expresofast-backend
mvn clean test
mvn spring-boot:run
```

API: http://localhost:8080/api/v1/envios

Swagger: http://localhost:8080/swagger-ui.html

## Frontend

Requiere Node.js 18+ y Angular CLI.

```bash
cd expresofast-frontend
npm install
ng serve
```

Abrir:

http://localhost:4200

## Base de datos

El backend usa la base SQL Server del Laboratorio 9:

`ExpresoFastC5L811_II2026`

La tabla `Envio` debe tener la columna `destinatario` creada por `04_lab9_schema.sql`.

## Endpoints

GET `/api/v1/envios`

GET `/api/v1/envios/rastreo/{codigo}`

POST `/api/v1/envios`

PATCH `/api/v1/envios/{id}/estado`
