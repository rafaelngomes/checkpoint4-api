# 📦 Checkpoint 3 → Checkpoint 1/Segundo Semestre (Docker & Compose)

> API Java com Spring Boot para consolidar conceitos de **REST**, **camadas de serviço/repositório**, **validação**, **testes** e **empacotamento com Docker/Docker Compose**.

[![Java 17](https://img.shields.io/badge/Java-17+-red)]() [![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)]() [![Maven](https://img.shields.io/badge/Maven-3.9+-blue)]() [![Docker](https://img.shields.io/badge/Docker-24+-informational)]()

---

## 🔗 Tabela de Conteúdo

* [Objetivos](#-objetivos)
* [Tech stack](#-tech-stack)
* [Pré-requisitos](#-pré-requisitos)
* [Como executar (Local)](#️-como-executar-local)
* [Como executar (Docker)](#-como-executar-docker)
* [Como executar (Docker Compose)](#-como-executar-docker-compose)
* [Configuração (.env / perfis)](#-configuração-env--perfis)
* [Swagger & Consoles](#-swagger--consoles)
* [Funcionalidades](#-funcionalidades)
* [Testes](#-testes)
* [Boas práticas adotadas](#-boas-práticas-adotadas)
* [Publicação no Docker Hub](#-publicação-no-docker-hub)
* [Comandos úteis do Docker](#-comandos-úteis-do-docker)
* [Autores](#-autores)

---

## ✨ Objetivos

* Fixar conceitos de **API REST** com **Spring Boot**.
* Praticar **camadas (Controller → Service → Repository)**, **validação**, **tratamento de erros** e **testes**.
* Aprender **empacotamento com Docker** e **orquestração com Docker Compose**.

---

## 🧰 Tech stack

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
* (Opcional) H2 Console: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

> **Obs.:** No perfil `dev`, a app pode usar H2 (memória) para facilitar o desenvolvimento.

---

## 🐳 Como executar (Docker)

**Build da imagem**

```bash
docker build -t makotomano/checkpoint4-api:latest .
```

**Rodando a imagem publicada**

```bash
docker run --rm --name checkpoint_api \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=docker \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/appdb \
  -e SPRING_DATASOURCE_USERNAME=app \
  -e SPRING_DATASOURCE_PASSWORD=app123 \
  makotomano/checkpoint3-api:latest
```

> Em Linux, caso `host.docker.internal` não resolva, use o IP da sua máquina host.

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

## 📚 Swagger & Consoles

* **Swagger UI:**

  * [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
  * (alternativo do springdoc) [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
* **H2 Console (se habilitado no dev):**

  * [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

---

## ✅ Funcionalidades (exemplos)

* Cadastro de **[Entidade]**
* Listagem de **[Entidade]**
* Atualização de **[Entidade]**
* Exclusão de **[Entidade]**
* Health check: `/actuator/health`

> Substitua **[Entidade]** pelo domínio real do projeto (ex.: `Produto`, `Cliente`, etc.) e, se possível, inclua exemplos de payload.

---

## 🧪 Testes

Rodar a suíte de testes:

```bash
./mvnw test
# ou
mvn test
```

---

## 🔒 Boas práticas adotadas

* Validação com `@Valid` e Bean Validation
* Tratamento de erros com **Exception Handler**
* Camadas claras (**Controller**, **Service**, **Repository**)
* Uso de **DTOs** para entrada/saída
* Documentação com **Swagger (springdoc-openapi)**
* Health/metrics via **Spring Boot Actuator**

---

## ☁️ Publicação no Docker Hub

Recomendado versionar sua imagem além do `latest`.

```bash
docker login
docker tag rafaelngomes/checkpoint4-api:latest rafaelngomes/checkpoint4-api:1.0.0
docker push rafaelngomes/checkpoint4-api:1.0.0
# opcional: também manter latest
docker push rafaelngomes/checkpoint4-api:latest
```

**Docker Hub:** [https://hub.docker.com/repository/docker/makotomano/checkpoint3-api/general](https://hub.docker.com/repository/docker/makotomano/checkpoint3-api/general)

---

## 🛠️ Comandos úteis do Docker

```bash
# Listar containers
docker ps -a

# Parar e remover rapidamente tudo (cuidado!)
docker stop $(docker ps -aq) || true
docker rm $(docker ps -aq) || true
docker rmi $(docker images -q) || true

# Limpeza geral (sem dó)
docker system prune -af
docker volume prune -f
docker builder prune -af
```

> Dica: prefira `docker compose down -v` para remover também os volumes do compose quando quiser “zerar” o banco local.

---

## 👥 Autores

* **Rafael Nascimento Gomes** – RM550843
* **Rafael Arcoverde Melo** – RM550206

---

### Próximos passos sugeridos

* Adicionar **exemplos de requests/responses** no README (curl ou HTTPie).
* Incluir **status de CI** (GitHub Actions) e cobertura de testes.
* Detalhar **mapeamento de entidades** e **migrations** (Flyway/Liquibase), se houver.

Se quiser, já te entrego um `docker-compose.yml` pronto em um arquivo e um `.env.example`.
