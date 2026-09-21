# ExpresoFast — Laboratorio 7

**IF0009 - Desarrollo de Software IV**
**II-2026 — Laboratorio 7: Suite de Pruebas, Seguridad JWT, RBAC, DTOs y Bitácora**
**Estudiante:** Jimena Navarro Álvarez
**Carnet:** C5L811

## Requisitos

* Java 21
* Maven 3.9.x
* Spring Boot 3.x
* Microsoft SQL Server + SSMS
* Navegador moderno
* VS Code
* Git y GitHub

## Base de datos

1. Use la base `ExpresoFastC5L811_II2026`.
2. Ejecute `database/01_schema_lab5.sql` con el esquema de su Laboratorio 5.
3. Ejecute `database/02_schema_lab6_extension.sql`.
4. Ejecute `database/03_data_seeds.sql`.
5. Configure `backend/src/main/resources/application.properties` con los datos de conexión a SQL Server.

**Importante:** no publique credenciales reales de la base de datos en GitHub.

## Ejecución del backend

Desde la carpeta `backend`:

```bash
mvn clean package
```

Para ejecutar el proyecto:

```bash
mvn spring-boot:run
```

El backend se ejecuta en:

```text
http://localhost:8080
```

Luego abra `frontend/login.html` usando Live Server de VS Code, por ejemplo:

```text
http://127.0.0.1:5500
```

## Ejecución de las pruebas

Las pruebas automatizadas se encuentran en:

```text
backend/src/test/java
```

Para limpiar el proyecto y ejecutar la suite de pruebas:

```bash
mvn clean test
```

Este comando ejecuta las pruebas automatizadas del proyecto y muestra en la terminal el resultado de las pruebas.

## Verificación completa

Para ejecutar las pruebas y generar el reporte de cobertura:

```bash
mvn clean verify
```

Al finalizar correctamente, Maven debe mostrar:

```text
BUILD SUCCESS
```

## Reporte de cobertura JaCoCo

El proyecto utiliza JaCoCo para generar el reporte HTML de cobertura.

Después de ejecutar:

```bash
mvn clean verify
```

el reporte se encuentra en:

```text
backend/target/site/jacoco/index.html
```

Para visualizarlo, abra el archivo `index.html` en un navegador.

## Suite de pruebas

La suite incluye pruebas relacionadas con:

* Autenticación.
* Servicios.
* Controladores.
* Seguridad JWT.
* Roles y autorización.
* Filtros de autenticación.
* Validaciones de seguridad.

## Endpoints

* POST `/api/auth/login` — público.
* GET `/api/envios/optimizados` — ADMIN, OPERADOR, CONDUCTOR.
* POST `/api/envios` — ADMIN, OPERADOR.
* PATCH `/api/envios/{id}/estado` — ADMIN, CONDUCTOR.
* GET `/api/envios/{id}/bitacora` — ADMIN, OPERADOR.
* `/api/vehiculos/**` — ADMIN.

## Seguridad

El proyecto utiliza:

* JWT para autenticación.
* BCrypt para contraseñas.
* RBAC para control de acceso según roles.
* CORS.
* Validaciones mediante Jakarta Validation.
* Sesiones Stateless.

## Nota sobre el esquema del Lab 5

El archivo `01_schema_lab5.sql` es un marcador porque el esquema exacto de la primera parte no está incluido en este proyecto. Debe sustituirse por el script original del Laboratorio 5 para conservar exactamente sus tablas y columnas.

## Entrega

Para la entrega del Laboratorio 7 se incluye:

* Código fuente de la suite de pruebas en `backend/src/test/java`.
* Este archivo `README.md` actualizado.
* Evidencia de ejecución exitosa de `mvn clean verify`.
* Reporte HTML de cobertura generado por JaCoCo.
