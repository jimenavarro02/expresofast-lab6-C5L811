# ExpresoFast — Laboratorio 8

**IF0009 - Desarrollo de Software IV**
**II-2026 — Laboratorio 8: Integración Pila Completa (Full-Stack)**
**Estudiante:** Jimena Navarro Álvarez
**Carnet:** C5L811

## Requisitos

* Java 21
* Maven 3.9.x
* Spring Boot 3.x
* Microsoft SQL Server + SSMS
* Navegador moderno (Chrome / Firefox)
* VS Code con Live Server
* Git y GitHub

## Estructura del proyecto

```text
/backend   → API REST Spring Boot (Labs 6 y 7)
/frontend  → Cliente HTML5, CSS3 y JavaScript (Lab 8)
/database  → Scripts SQL
```

## Base de datos

1. Use la base `ExpresoFastC5L811_II2026`.
2. Ejecute `database/01_schema_lab5.sql`.
3. Ejecute `database/02_schema_lab6_extension.sql`.
4. Ejecute `database/03_data_seeds.sql`.
5. Configure `backend/src/main/resources/application.properties`.

**Importante:** no publique credenciales reales en GitHub.

## Ejecución del backend

Desde la carpeta `backend`:

```bash
mvn clean package
mvn spring-boot:run
```

La API queda disponible en:

```text
http://localhost:8080
```

## Ejecución del frontend

1. Abra la carpeta `frontend` en VS Code.
2. Inicie **Live Server** sobre `index.html`.
3. El cliente se abrirá en:

```text
http://127.0.0.1:5500/index.html
```

## Credenciales de prueba

| Usuario     | Contraseña   | Rol              |
|-------------|--------------|------------------|
| admin       | Password123! | ROLE_ADMIN       |
| operador1   | Password123! | ROLE_OPERADOR    |
| conductor1  | Password123! | ROLE_CONDUCTOR   |

## Endpoints principales

* POST `/api/auth/login` — público
* GET `/api/envios` — ADMIN, OPERADOR, CONDUCTOR
* PATCH `/api/envios/{id}/estado` — ADMIN, CONDUCTOR
* GET `/api/envios/bitacora/historial` — ADMIN
* GET `/api/vehiculos` — ADMIN, OPERADOR
* POST `/api/vehiculos` — ADMIN

## Características del Lab 8

* CORS configurado en `WebConfig` para `http://localhost:5500` y `http://127.0.0.1:5500`
* Login en `index.html` con formulario accesible y mensajes ARIA
* Dashboard en `dashboard.html` con HTML5 semántico
* CSS responsivo con variables, Flexbox y CSS Grid
* Consumo de API con Fetch API, JWT en `sessionStorage` y cabecera `Authorization: Bearer`
* Interfaz adaptada por rol: ADMIN, OPERADOR y CONDUCTOR

## Pruebas automatizadas

```bash
cd backend
mvn clean test
mvn clean verify
```

Reporte JaCoCo:

```text
backend/target/site/jacoco/index.html
```
