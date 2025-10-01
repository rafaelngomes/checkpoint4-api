# 📦 Checkpoint 4 

> API Java com Spring Boot para consolidar conceitos de **REST**, **camadas de serviço/repositório**, **validação**, **testes** e **empacotamento com Docker/Docker Compose**.


---

* **Linguagem:** Java 17+
* **Framework:** Spring Boot (Web, Validation, JPA, Actuator)
* **Documentação:** springdoc-openapi (Swagger)
* **Persistência:** Spring Data JPA
* **Banco:** H2 (dev) e PostgreSQL (docker)
* **Build:** Maven
* **Container:** Docker / Docker Compose

---

## ✅ Pré-requisitos

* **JDK 17+**
* **Maven 3.9+** (ou `mvnw`)
* **Docker 24+** e **Docker Compose**

---

## ▶️ Como executar (Local)

1. **Clone o repositório**

```bash
git clone https://github.com/rafaelngomes/checkpoint4-api.git
cd checkpoint4-api
```

2. **Rodando com Maven (perfil dev)**

```bash
./mvnw spring-boot:run
# ou
mvn spring-boot:run
```

3. **Acessos**

* Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)


---

## 🐳 Como executar (Docker)

**Build da imagem**

```bash
docker build -t rafaelngomes/checkpoint4-api:latest .
```

**Rodando a imagem publicada**

```bash
docker run --rm --name checkpoint_api \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=docker \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/appdb \
  -e SPRING_DATASOURCE_USERNAME=app \
  -e SPRING_DATASOURCE_PASSWORD=app123 \
  rafaelngomes/checkpoint4-api:latest
```


---

## 🧩 Como executar (Docker Compose)

**docker-compose.yml (exemplo)**

```yaml
services:
  db:
    image: postgres:16
    container_name: checkpoint_db
    environment:
      POSTGRES_DB: appdb
      POSTGRES_USER: app
      POSTGRES_PASSWORD: app123
    ports:
      - "5432:5432"
    volumes:
      - pgdata:/var/lib/postgresql/data

  api:
    image: rafaelngomes/checkpoint4-api:latest
    container_name: checkpoint_api
    depends_on:
      - db
    environment:
      SPRING_PROFILES_ACTIVE: docker
      SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/appdb
      SPRING_DATASOURCE_USERNAME: app
      SPRING_DATASOURCE_PASSWORD: app123
    ports:
      - "8080:8080"

volumes:
  pgdata:
```

**Subir os serviços**

```bash
docker compose up -d --build
# logs em tempo real:
docker compose logs -f api
# derrubar
docker compose down
```

---

## ⚙️ Configuração (.env / perfis)

**Exemplo `.env` (opcional)**

```env
# Perfil ativo
SPRING_PROFILES_ACTIVE=dev

# Se usar Postgres local
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/checkpoint
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=postgres
```

**Perfis sugeridos**

* `dev`: foco em desenvolvimento (pode usar H2)
* `docker`: conexão com Postgres via Compose

---




## 🧪 Testes

Rodar a suíte de testes:

```bash
./mvnw test
# ou
mvn test
```

---
## ☁️ Publicação no Docker Hub

Recomendado versionar sua imagem além do `latest`.

```bash
docker login
docker tag rafaelngomes/checkpoint4-api:latest rafaelngomes/checkpoint4-api:1.0.0
docker push rafaelngomes/checkpoint4-api:1.0.0
docker push rafaelngomes/checkpoint4-api:latest
```

**Docker Hub:** [https://hub.docker.com/repository/docker/rafaelngomes/checkpoint4-api/general](https://hub.docker.com/repository/docker/rafaelngomes/checkpoint4-api/general)

---


## 👥 Autores

* **Rafael Nascimento Gomes** – RM550843
* **Rafael Arcoverde Melo** – RM550206

---