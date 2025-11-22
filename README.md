# 🧪 Tech Challenge - FastFood API

Este projeto é uma API REST construída com **Spring Boot**, utilizando **PostgreSQL**, **Docker** e **Flyway**.

---

Consulte a documentação da API e o desenho técnico clicando aqui: 
[Link da solução técnica](doc/k8s-architecture-doc.md)

## 🚀 Como iniciar o projeto

### Pré-requisitos

- [Docker](https://www.docker.com/)
- [Minikube](https://minikube.sigs.k8s.io/docs/start/) (localmente em Kubernetes)
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
./.ci/scripts/deploy.sh
```

Isso irá subir os seguintes serviços:
- **PostgreSQL** na porta `5432`
- **API Spring Boot** na porta `8080`
- **Flyway** para migrações de banco de dados

### Obs
**Ao final da execução dos scripts, é necessário executar o comando abaixo (de preferência, em outro terminal) para criar o
túnel do Minikube, que irá permitir o acesso à API Spring Boot na porta `8080`.**
```bash
minikube tunnel --profile postech-cluster
```

---


## ⚙️ Subindo o banco de dados (PostgreSQL)

Apos a finalização do Kubernetes, sera disponibilizado o banco de dados:

Isso irá subir o banco na porta `5432`.

---

## 🔌 Conectando à base de dados

- **URL JDBC:** `jdbc:postgresql://localhost:5432/paymentservice`
- **Usuário:** `postgres`
- **Senha:** `postgres`

---

## 🛑 Parando a aplicação

```bash
./.ci/scripts/stop.sh
```


- **PostgreSQL** (porta `5432`)
- **API Spring Boot** (porta `8080`)

---

## 📫 Acessando a API

Com a aplicação rodando, você pode acessar os endpoints no Postman ou navegador:

```
http://localhost:8080/paymentservice/v1/products
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

- Kubernetes
- Java 17
- Spring Boot
- PostgreSQL
- Flyway

---

## 🤝 Contribuição

Sinta-se à vontade para enviar sugestões, melhorias ou abrir issues!
