# 📦 Catálogo de produtos

Este é uma API em Spring Boot para gerenciar um catálogo de produtos. A aplicação fornece endpoints para gerenciar produtos, categorias, usuários e autenticação. Além disso, inclui recursos como controle de acesso baseado em funções e respostas paginadas.


## 📚 Sumário
- 🛠️ [Tecnologias](#tecnologias)
- 🚀 [Instalação](#instalação)
- 🌐 [Endpoints](#endpoints)
- ↔️ [Exemplo de Requisições](#requisicoes)


## 🛠️ Tecnologias: <a id="tecnologias"></a>
- Java 17
- Spring Boot
- Hibernate
- Spring Data JPA
- Spring Security
- Jakarta Validation
- Maven
- PostgreSQL
- JUnit

## 🚀 Instalação: <a id="instalação"></a>
1. Clone o repositório:
```java
git clone https://github.com/douglasjsantos/ds-catalog
cd ds-catalog
```

## 🌐 Endpoints: <a id="endpoints"></a>
## Usuários

**Caminho Base:** `/users`

| Método | Endpoint | Descrição | Autorização |
| --- | --- | --- | --- |
| POST | /users | Registra um novo usuário. | Público |
| GET | /users | Lista todos os usuários | ROLE_ADMIN |
| GET | /users/{id} | Recupera os detalhes de um usuário pelo ID. | ROLE_ADMIN |
| PUT | /users/{id} | Atualiza um usuário existente pelo ID. | ROLE_ADMIN |
| DELETE | /users/{id} | Exclui um usuário pelo ID. | ROLE_ADMIN |
| GET | /users/me | Recupera os detalhes do usuário autenticado. | ROLE_ADMIN, ROLE_OPERATOR |


## Autenticação

**Caminho Base:** `/auth`

| Método | Endpoint | Descrição | Autorização |
| --- | --- | --- | --- |
| POST | `/oauth2/token` | Loga com usuário criado. | Público |
| POST | `/auth/recover-token` | Gera um token de recuperação de senha. | Público |
| PUT | `/auth/new-password` | Salva uma nova senha usando o token de recuperação. | Público |



## Categoria

**Caminho Base:** `/categories`     

| Método | Endpoint | Descrição | Autorização |
| --- | --- | --- | --- |
| POST | `/categories` | Registra uma nova categoria. | ROLE_ADMIN, ROLE_OPERATOR |
| GET | `/categories` | Lista todas as categorias. | Público |
| GET | `/categories/{id}` | Recupera os detalhes de uma categoria pelo ID. | Público |
| PUT | `/categories/{id}` | Atualiza uma categoria existente pelo ID. | ROLE_ADMIN, ROLE_OPERATOR |
| DELETE | `/categories/{id}` | Exclui uma categoria pelo ID *se não tiver nenhum produto vinculado a ela.* | ROLE_ADMIN |
| GET | `/users/me` | Recupera os detalhes do usuário autenticado. | ROLE_ADMIN, ROLE_OPERATOR |


## Products

**Caminho Base:** `/products`     

| Método | Endpoint | Descrição | Autorização |
| --- | --- | --- | --- |
| POST | `/products` | Registra uma novo produto. | ROLE_ADMIN, |
| GET | `/products` | Lista todas os produtos. | Público |
| GET | `/products/{id}` | Recupera os detalhes de um produto pelo ID. | Autenticado |
| PUT | `/products/{id}` | Atualiza um produto existente pelo ID. | ROLE_ADMIN |
| DELETE | `/products/{id}` | Exclui um produto pelo ID | ROLE_ADMIN, ROLE_OPERATOR |
