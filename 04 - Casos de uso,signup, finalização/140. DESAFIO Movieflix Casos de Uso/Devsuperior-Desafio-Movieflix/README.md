# <a href="https://imgbb.com/"><img src="https://i.ibb.co/S42fsBL4/Devsuperior-logo.png" alt="Devsuperior logo" border="0" width="300"></a> Java Spring Expert - Desafio Movieflix Casos de Uso




![Java](https://img.shields.io/badge/Java-25-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.6-green)
![Spring Security](https://img.shields.io/badge/Spring_Security-6.x-brightgreen)
![OAuth2](https://img.shields.io/badge/OAuth2-Authorization-red)
![JWT](https://img.shields.io/badge/JWT-Authentication-blue)
![H2 Database](https://img.shields.io/badge/H2-Database-lightgrey)

## 👨‍💻 Desenvolvido por

**Marcos Shirafuchi**

* GitHub: [https://github.com/marcosfshirafuchi](https://github.com/marcosfshirafuchi)
* Desenvolvedor Backend Java
* Formação Java Spring Expert - DevSuperior

---

# 📚 Sobre o Projeto

Este projeto foi desenvolvido como parte da formação **Java Spring Expert**, ministrada pelo professor **Nélio Alves**, na plataforma DevSuperior.

O desafio consiste em implementar os casos de uso da aplicação Movieflix utilizando:

* Spring Boot
* Spring Data JPA
* Spring Security
* OAuth2
* JWT
* Bean Validation
* Testes automatizados com MockMvc

O objetivo principal é garantir que todos os testes de integração fornecidos pela DevSuperior sejam aprovados.

---

# 🎯 Objetivos do Desafio

* Implementar autenticação OAuth2
* Implementar autorização baseada em perfis
* Listar gêneros de filmes
* Listar filmes paginados
* Filtrar filmes por gênero
* Consultar detalhes de um filme
* Consultar avaliações de um filme
* Inserir avaliações
* Aplicar validações
* Garantir aprovação dos testes automatizados

---

# 🏗️ Modelo Conceitual

<p align="center">
    <a href="https://ibb.co/pBG8rTgv"><img src="https://i.ibb.co/9kzL30J9/Image-29.jpg" 
         alt="Modelo Conceitual Movieflix"
         width="900" border="0"></a>
</p>



## Relacionamentos

### User ↔ Role

Um usuário pode possuir um ou mais perfis.

### User ↔ Review

Um usuário pode realizar várias avaliações.

### Movie ↔ Review

Um filme pode possuir várias avaliações.

### Genre ↔ Movie

Um gênero pode possuir vários filmes.

---

# ✨ Casos de Uso

## Listar Filmes

O sistema apresenta:

* Todos os gêneros cadastrados
* Lista paginada de filmes
* Ordenação alfabética por título

O usuário visitante ou membro pode:

* Consultar todos os filmes
* Filtrar filmes por gênero

## Visualizar Detalhes do Filme

O sistema apresenta:

* Título
* Subtítulo
* Ano
* Imagem
* Sinopse
* Avaliações do filme
* Nome do usuário que realizou cada avaliação

## Avaliar Filme

O usuário com perfil MEMBER pode:

* Inserir uma nova avaliação

O sistema:

* Persiste a avaliação
* Atualiza a lista de avaliações do filme

---

# 🔐 Controle de Acesso

## ROLE_VISITOR

Pode acessar:

* GET /genres
* GET /movies
* GET /movies/{id}
* GET /movies/{id}/reviews

## ROLE_MEMBER

Possui todas as permissões do VISITOR e também:

* POST /reviews

---

# 🔑 Perfis de Usuário

| Usuário                               | Perfil       |
| ------------------------------------- | ------------ |
| [bob@gmail.com](mailto:bob@gmail.com) | ROLE_VISITOR |
| [ana@gmail.com](mailto:ana@gmail.com) | ROLE_MEMBER  |

---

# 📌 Endpoints Principais

## Autenticação

| Método | Endpoint      |
| ------ | ------------- |
| POST   | /oauth2/token |

## Gêneros

| Método | Endpoint |
| ------ | -------- |
| GET    | /genres  |

## Filmes

| Método | Endpoint             |
| ------ | -------------------- |
| GET    | /movies              |
| GET    | /movies/{id}         |
| GET    | /movies/{id}/reviews |

## Avaliações

| Método | Endpoint |
| ------ | -------- |
| POST   | /reviews |

---

# ✅ Critérios de Correção

**Mínimo para aprovação: 12 de 15 testes**

| Endpoint                 | Resultado Esperado                |
| ------------------------ | --------------------------------- |
| GET /genres              | 401 para token inválido           |
| GET /genres              | 200 para VISITOR                  |
| GET /genres              | 200 para MEMBER                   |
| GET /movies/{id}         | 401 para token inválido           |
| GET /movies/{id}         | 200 para VISITOR                  |
| GET /movies/{id}         | 200 para MEMBER                   |
| GET /movies/{id}         | 404 para id inexistente           |
| GET /movies              | 401 para token inválido           |
| GET /movies              | 200 ordenado para VISITOR         |
| GET /movies              | 200 ordenado para MEMBER          |
| GET /movies?genreId={id} | 200 filtrado por gênero           |
| POST /reviews            | 401 para token inválido           |
| POST /reviews            | 403 para VISITOR                  |
| POST /reviews            | 201 para MEMBER com dados válidos |
| POST /reviews            | 422 para dados inválidos          |

---

# 🧪 Tecnologias Utilizadas

## Backend

* Java
* Spring Boot
* Spring Web
* Spring Data JPA
* Spring Security
* OAuth2 Authorization Server
* OAuth2 Resource Server
* JWT
* Bean Validation

## Banco de Dados

* H2 Database

## Testes

* JUnit 5
* MockMvc

## Ferramentas

* Maven
* IntelliJ IDEA
* Postman

---

# 📖 Competências Avaliadas

* Desenvolvimento TDD de API Rest com Java e Spring Boot
* Realização de casos de uso
* Consultas a banco de dados relacional com Spring Data JPA
* Tratamento de erros com respostas HTTP customizadas
* Controle de acesso por perfil de usuário e rotas

---

# ▶️ Executar o Projeto

```bash
git clone https://github.com/marcosfshirafuchi/SEU-REPOSITORIO.git

cd SEU-REPOSITORIO

mvn spring-boot:run
```

---

# 🧪 Executar os Testes

```bash
mvn test
```

---

# 🎓 Curso

Java Spring Expert

Professor: Nélio Alves

Plataforma DevSuperior

[https://devsuperior.com.br](https://devsuperior.com.br)

---

# ⭐ Agradecimentos

Agradecimento ao professor Nélio Alves e à DevSuperior pelos ensinamentos sobre Spring Boot, Spring Security, OAuth2, JWT, testes automatizados e desenvolvimento de APIs REST.

