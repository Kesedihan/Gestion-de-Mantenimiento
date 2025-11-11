# Deploying ProyectoGestionMantCorrectivo with Docker

This document explains how to build and run the backend with Docker locally and basic guidance for deploying to DigitalOcean (App Platform or Droplet).

---

## Files added
- `Dockerfile` - multi-stage build (Maven build + runtime image)
- `docker-compose.yml` - local compose with Postgres and Adminer
- `docker-compose.prod.yml` - production-oriented compose using pre-built image and `.env.prod`
- `.env.example` - example environment variables
- `.dockerignore` - ignore local files from build context

## Required environment variables
Set the following variables (local `.env` or in your hosting environment):

- `APP_JWT_SECRET` (required): secure secret with at least 32 bytes (use `openssl rand -base64 48`)
- `POSTGRES_DB`, `POSTGRES_USER`, `POSTGRES_PASSWORD`: database credentials
- `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`: optional if you prefer explicit spring vars
- `APP_CORS_ALLOWED_ORIGINS`: comma separated list with the frontend origin for production (e.g. `https://app.midominio.com`)
- `SPRING_JPA_SHOW_SQL` (optional): `false` by default

### Generate a secure secret

Linux / macOS:
```
openssl rand -base64 48
```

Windows PowerShell (example):
```
[Convert]::ToBase64String((1..48 | ForEach-Object {Get-Random -Maximum 256}) -as [byte[]])
```

## Run locally with Docker Compose

1. Copy `.env.example` to `.env` and fill values.
2. Build and start services:

```powershell
cd Proyecto-Backend
docker compose up --build -d
```

3. Verify services:
- Backend: http://localhost:8080
- Adminer (DB UI): http://localhost:8081

To stop:
```powershell
docker compose down
```

## Run in production (simple Droplet using Docker)

Option A — DigitalOcean App Platform (recommended for simplicity):
- Create a new App and point to your GitHub repo.
- Configure a build command: `./mvnw -DskipTests package` and start command: `java -jar target/proyectoGestionMantCorrectivo-0.0.1-SNAPSHOT.war`
- Add the environment variables in the App settings (APP_JWT_SECRET, DB connection, APP_CORS_ALLOWED_ORIGINS, ...).

### Deploying to DigitalOcean App Platform with `app.yaml`

1. Install doctl and authenticate (https://docs.digitalocean.com/reference/doctl/):

```powershell
# Windows PowerShell
choco install doctl -y   # if using Chocolatey
doctl auth init
```

2. Create a `.env.prod` with production variables locally (do NOT commit it). Populate at least `APP_JWT_SECRET`, DB credentials and `APP_CORS_ALLOWED_ORIGINS`.

3. Edit `app.yaml` in the repo and set the `github.repo` value or remove the `github` block to deploy from local source.

4. Create the app on DigitalOcean using the spec:

```powershell
# From Proyecto-Backend folder
doctl app create --spec app.yaml
```

This will instruct App Platform to build the Docker image using the Dockerfile and deploy it. In the App settings on DigitalOcean you can also set environment variables via the UI (preferred for secrets).

### Important notes for App Platform
- Use DigitalOcean Managed Databases for Postgres and set `SPRING_DATASOURCE_URL` to the managed DB JDBC connection string (do not expose DB password publicly).
- For secrets, set them as environment variables in the App's Dashboard (they're stored as secrets and not exposed in builds).
- Enable automatic deploys from GitHub if desired.


Option B — Droplet (Docker + docker-compose):
- Provision a Droplet, install Docker and Docker Compose.
- Copy `.env.prod` (with production secrets) to the server (do NOT commit secrets in repo).
- Pull the repo and build the image or push to a registry and use the image in `docker-compose.prod.yml`.
- Start with `docker compose -f docker-compose.prod.yml up -d`.

Security notes for production:
- Do not store secrets in git or in files accessible to others. Use DigitalOcean App Platform env vars or Docker secrets/secret manager.
- Use HTTPS (TLS) — App Platform provides TLS automatically. For Droplets, put the app behind a reverse proxy (Nginx) with TLS or use a load balancer.
- Use a managed database or secure your Postgres with private networking and strong passwords.
- Set `spring.jpa.hibernate.ddl-auto` to `validate` and manage schema via Flyway or Liquibase.

## Advanced: using Docker secrets (Swarm/Kubernetes)

For higher security, store secrets using Docker Swarm secrets or Kubernetes Secrets. DigitalOcean App Platform also provides a secrets manager / environment variables you can set via the dashboard.

---

If you want, I can:
- Add a GitHub Actions pipeline to build and push the image to Docker Hub or GHCR.
- Add a Flyway integration and an example migration.
- Create a small Kubernetes manifest/Helm chart for DO Kubernetes.
