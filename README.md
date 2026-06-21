# myeshop — Backend Library

![License](https://img.shields.io/badge/License-MIT-green)
![Java](https://img.shields.io/badge/Java-21-ED8B00?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.2-6DB33F?logo=springboot&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring_Data_JPA-6DB33F?logo=spring&logoColor=white)
![Oracle](https://img.shields.io/badge/Oracle_DB-F80000?logo=oracle&logoColor=white)
![Lombok](https://img.shields.io/badge/Lombok-ED1C24)
![Maven](https://img.shields.io/badge/Maven-3-C71A36?logo=apachemaven&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?logo=docker&logoColor=white)

A layered backend library for a basic e-commerce platform, packaged as a reusable JAR and designed to be consumed by a desktop client. Only the **Controller** and **DTO** layers are part of the public contract — domain entities and repositories are never exposed to the caller.

This is the second iteration of the project. The [first iteration](https://github.com/kunohami/ecommerceProject) built the JPA domain model from scratch with plain Hibernate; here the architecture is modernised with Spring Boot, Oracle, and a strict separation of concerns across four layers.

---

## Architecture

```
Desktop Application
       │  (consumes JAR via Maven dependency)
       ▼
┌──────────────────────────────────────────────┐
│           myeshop-backend  (library)          │
│                                               │
│  ┌──────────────────┐  ┌──────────────────┐  │
│  │   Controller     │  │      DTO         │  │ ◄─ Public API
│  └────────┬─────────┘  └──────────────────┘  │
│           │                                   │
│  ┌────────▼─────────┐                         │
│  │    Service       │  Business logic          │
│  └────────┬─────────┘                         │
│           │                                   │
│  ┌────────▼─────────┐                         │
│  │   Repository     │  Spring Data JPA         │
│  └────────┬─────────┘                         │
│           │                                   │
│  ┌────────▼─────────┐                         │
│  │  Model / Entity  │  (internal — not exposed)│
│  └──────────────────┘                         │
└──────────────────────────────────────────────┘
       │
       ▼
  Oracle Database
```

## Domain model

| Entity | Description |
|---|---|
| `Cliente` | Registered customer. NIF/CIF is the natural primary key. |
| `InformacionFiscal` | Billing information. 1:1 with `Cliente`, shares its PK via `@MapsId`. |
| `Compra` | Purchase order header. N:1 with `Cliente`. |
| `Articulo` | Product in the catalog. |
| `ArticuloCompra` | Junction entity for the N:M between `Compra` and `Articulo`. Carries quantity and the unit price frozen at purchase time. |

## Key design decisions

**Shared primary key (1:1 relationship)**
`InformacionFiscal` uses `@MapsId` to share `Cliente`'s PK instead of adding a redundant FK column. `CascadeType.ALL` ensures fiscal data is removed alongside its customer — fiscal records have no independent lifecycle.

**Junction entity with composite key (N:M with attributes)**
`ArticuloCompra` is modelled as a full entity with an `@EmbeddedId` (`ArticuloCompraId`), not a plain join table. The table owns two extra columns: units purchased and the **unit price frozen at the moment of the transaction**. Since catalog prices change over time, the line-item price must be captured and decoupled from `Articulo.precioActual`.

**N+1 prevention via `JOIN FETCH`**
Custom JPQL queries use `JOIN FETCH` to load associated data in a single database round-trip instead of triggering a query per row.

**Spring profiles (dev / pro)**
Database connection details are isolated in profile-specific property files. Maven filtering injects the active profile at build time (`mvn install -Ppro`), so the same artifact can be configured for different environments without recompilation.

**`open-in-view=false`**
The JPA session is closed as soon as the service/transaction finishes. This prevents unexpected lazy-loading from outside the service layer and makes transaction boundaries explicit.

## Public API

A client application only needs to import controllers and DTOs.

### Controllers

| Class | Methods |
|---|---|
| `ClienteController` | `registrarCliente(ClienteRegistroDTO)`, `listarClientes()`, `obtenerPorNif(String)` |
| `ArticuloController` | `listarArticulos()`, `obtenerArticulo(Integer id)` |
| `CompraController` | `procesarCompra(CompraSolicitudDTO)`, `obtenerHistorial(String nif)` |

### DTOs

| DTO | Direction | Description |
|---|---|---|
| `ClienteRegistroDTO` | Input | Registration data: NIF/CIF, name, email, phone, fiscal address |
| `ClienteRespuestaDTO` | Output | Customer summary (no internal fields) |
| `ClienteUpdateDTO` | Input | Partial update for customer or fiscal data |
| `ArticuloDTO` | Output | Product catalog entry |
| `CompraSolicitudDTO` | Input | Purchase request: client NIF, delivery address, list of items |
| `CompraRespuestaDTO` | Output | Confirmed purchase receipt with totals and full line breakdown |
| `LineaCompraDTO` | Output | Single line item within a purchase receipt |

## Getting started

### Prerequisites

- Java 21
- Maven 3
- Docker (recommended) or a local Oracle instance

### 1. Start Oracle in Docker

The database runs on the `gvenzl/oracle-free` image. A ready-to-use script is included in the repo (`script-creacion-docker`):

```bash
docker run -d \
  --name usuarios_oracle \
  -p 1521:1521 \
  -e ORACLE_PASSWORD=Abcd1234 \
  -e ORACLE_PDB=FREEPDB1 \
  -v usuarios_oracle_data:/opt/oracle/oradata \
  gvenzl/oracle-free:full-faststart
```

Wait for the container to finish initialising (check `docker logs -f usuarios_oracle` until you see `DATABASE IS READY TO USE`).

### 2. Create the schema

Run the provided SQL script as `sys` to create the `proyecto_eshop` user, grant privileges, and create and seed all five tables:

```bash
docker exec -i usuarios_oracle sqlplus sys/Abcd1234@//localhost:1521/FREEPDB1 as sysdba @script-creacion-bd.sql
```

The script drops and recreates the tables on each run, so it is safe to re-execute.

### 3. Configure the connection

Edit `src/main/resources/application-dev.properties` with your Oracle instance URL:

```properties
spring.datasource.url=jdbc:oracle:thin:@//localhost:1521/FREEPDB1
spring.datasource.username=proyecto_eshop
spring.datasource.password=Abcd1234
```

### 4. Install to local Maven repository

```bash
mvn clean install
```

This compiles the project, runs validation against the schema, and installs `myeshop-backend-0.0.1-SNAPSHOT.jar` to your local `.m2` repository so any other Maven project on the same machine can declare it as a dependency.

To build for production:

```bash
mvn clean install -Ppro
```

## Related projects

| Project | Description |
|---|---|
| [ecommerceSpringBootFront](https://github.com/SilviaBalmaseda/ecommerceSpringBootFront) | Console client that consumes this library |
| [ecommerceProject](https://github.com/kunohami/ecommerceProject) | First iteration — raw JPA & Hibernate without Spring |

## Authors

Silvia Balmaseda Hernández · Rafael Robles García  
DAM — Desarrollo de Aplicaciones Multiplataforma, 2026
