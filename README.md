# ExpresoFast — Laboratorio 6

**IF0009 - Desarrollo de Software IV**  
**II-2026 — Laboratorio 6: Seguridad JWT, RBAC, DTOs y Bitácora**  
**Estudiante:** Jimena Navarro Álvarez  
**Carnet:** C5L811

## Requisitos
- Java 21
- Maven
- Spring Boot 3.x
- Microsoft SQL Server + SSMS
- Navegador moderno
- VS Code
- Git y GitHub

## Base de datos
1. Use la base `ExpresoFastC5L811_II2026`.
2. Ejecute `database/01_schema_lab5.sql` con el esquema de su Laboratorio 5.
3. Ejecute `database/02_schema_lab6_extension.sql`.
4. Ejecute `database/03_data_seeds.sql`.
5. Configure `backend/src/main/resources/application.properties` con usuario y contraseña de SQL Server.

## Usuarios de prueba
| Usuario | Contraseña | Rol |
|---|---|---|
| admin | Password123! | ROLE_ADMIN |
| operador1 | Password123! | ROLE_OPERADOR |
| conductor1 | Password123! | ROLE_CONDUCTOR |

## Ejecución
Desde `backend`:
```bash
mvn clean package
mvn spring-boot:run
```
Luego abra `frontend/login.html` usando Live Server de VS Code (por ejemplo `http://127.0.0.1:5500`).

## Endpoints
- POST `/api/auth/login` — público.
- GET `/api/envios/optimizados` — ADMIN, OPERADOR, CONDUCTOR.
- POST `/api/envios` — ADMIN, OPERADOR.
- PATCH `/api/envios/{id}/estado` — ADMIN, CONDUCTOR.
- GET `/api/envios/{id}/bitacora` — ADMIN, OPERADOR.
- `/api/vehiculos/**` — ADMIN.

## Seguridad
JWT Stateless, BCrypt, RBAC, CORS y validaciones Jakarta Validation.

## Nota sobre el esquema del Lab 5
El archivo `01_schema_lab5.sql` es un marcador porque el esquema exacto de la primera parte no está incluido en este proyecto. Debe sustituirse por el script original del Laboratorio 5 para conservar exactamente sus tablas y columnas.
