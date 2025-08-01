# 🧪 Tech Challenge - FastFood API

Este projeto é uma API REST construída com **Spring Boot**, utilizando **PostgreSQL**, **Docker** e **Flyway**.

---

## 🚀 Como iniciar o projeto

### Pré-requisitos

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)
- [Java 17](https://adoptium.net/)
- [Maven](https://maven.apache.org/)
- [Postman](https://www.postman.com/) (opcional, para testes da API)

---

## 📁 Importar collections e environments do Postman

Para facilitar os testes, você pode importar as seguintes collections e environments do Postman:

Baixe os arquivos da pasta ```src/main/resources/static/collections``` e importe no Postman:
- PosTech - Local.postman_environment.json
- TechChallenge - Product API.postman_collection.json


---

## 🐳 Subindo todos os serviços com Docker Compose

Para iniciar **todos os serviços** (PostgreSQL e a API Spring Boot) via Docker Compose:

```bash
./scripts/start.sh
```

Isso irá subir os seguintes serviços:
- **PostgreSQL** na porta `5432`
- **API Spring Boot** na porta `8080`
- **Flyway** para migrações de banco de dados

---


## ⚙️ Subindo o banco de dados (PostgreSQL)

Para iniciar **apenas o banco de dados** via Docker Compose:

```bash
./scripts/start-db.sh
```

Isso irá subir o banco na porta `5432`.

---

## 🔌 Conectando à base de dados

- **URL JDBC:** `jdbc:postgresql://localhost:5432/techchallenge`
- **Usuário:** `postgres`
- **Senha:** `postgres`

---

## 🛑 Parando o banco de dados

```bash
./scripts/stop-db.sh
```

---

## 🧹 Limpando a base com Flyway

> **Atenção:** isso **remove todos os dados e estrutura** da base de dados!

```bash
./scripts/clean.sh
```

---

## 🧼 Limpando a aplicação e Docker (containers + imagens + volumes)

Para parar todos os serviços, remover containers, imagens e **opcionalmente** os volumes do banco de dados:

```bash
./scripts/drop-app.sh
```

---

## 🖥️ Rodando a aplicação

### 1. Gere o `.jar`

Na raiz do projeto:

```bash
mvn clean package
```

### 2. Suba todos os serviços com Docker Compose:

```bash
docker compose up --build
```

Isso irá subir:

- **PostgreSQL** (porta `5432`)
- **API Spring Boot** (porta `8080`)

---

## 📫 Acessando a API

Com a aplicação rodando, você pode acessar os endpoints no Postman ou navegador:

```
http://localhost:8080/products
```

---

## 📁 Estrutura das Migrations (Flyway)

Os scripts estão localizados em:

```
src/main/resources/db/migration
```

Eles seguem o padrão `V<numero>__descricao.sql`, por exemplo:

```
V1__init.sql
V2__insert_initial_product.sql
V3__insert_initial_customers.sql
V4__insert_into_order_items.sql
```

---

## 🧪 Testando

Execute os testes com:

```bash
mvn test
```

---

## 🛠️ Tecnologias

- Java 17
- Spring Boot
- PostgreSQL
- Flyway
- Docker & Docker Compose

---

## 🤝 Contribuição

Sinta-se à vontade para enviar sugestões, melhorias ou abrir issues!
