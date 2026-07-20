# <a href="https://devsuperior.com.br"><img src="https://i.ibb.co/S42fsBL4/Devsuperior-logo.png" alt="DevSuperior logo" width="300"></a> Java Spring Expert - DESAFIO DSMovie Jacoco

![Java](https://img.shields.io/badge/Java-25-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-API_REST-green)
![JUnit 5](https://img.shields.io/badge/JUnit_5-Tests-success)
![Mockito](https://img.shields.io/badge/Mockito-Mocks-blue)
![Jacoco](https://img.shields.io/badge/Jacoco-Coverage-red)
![H2 Database](https://img.shields.io/badge/H2-Database-lightgrey)

## 👨‍💻 Desenvolvido por

**Marcos Shirafuchi**

- GitHub: https://github.com/marcosfshirafuchi
- Desenvolvedor Backend Java

---

## 📚 Sobre o Projeto

Este projeto faz parte do curso **Java Spring Expert**, ministrado pelo professor **Nélio Alves**, na plataforma **DevSuperior**.

O desafio consiste em implementar **testes unitários da camada de Service** para o projeto **DSMovie**, utilizando **JUnit 5**, **Mockito** e análise de cobertura com **Jacoco**.

O sistema representa uma aplicação de filmes e avaliações, onde:

- A consulta de filmes é pública;
- Inserção, atualização e exclusão de filmes são permitidas apenas para usuários **ADMIN**;
- Avaliações podem ser registradas por usuários logados com perfil **CLIENT** ou **ADMIN**;
- Cada avaliação possui uma nota de **0 a 5**;
- Ao salvar uma avaliação, o sistema recalcula a média das notas do filme e atualiza a quantidade de votos.

---

## 🎯 Objetivo do Desafio

Implementar todos os testes unitários de Service do projeto **DSMovie**.

Com todos os testes implementados, o **Jacoco** deve reportar **100% de cobertura**, porém o mínimo para aprovação no desafio são **12 de 15 testes**.

---

## 🏗️ Modelo Conceitual

O modelo do projeto é composto pelas entidades **Movie**, **User** e **Score**.

<p align="center">
  <img src="https://i.ibb.co/yFNNcG5X/Chat-GPT-Image-5-de-jul-de-2026-20-55-10.png" alt="Modelo Conceitual DSMovie Jacoco" width="900">
</p>

### Entidade Movie

Representa um filme cadastrado no sistema.

Atributos principais:

- `id : Long`
- `title : String`
- `score : Double`
- `count : Integer`
- `image : String`

### Entidade User

Representa o usuário que pode avaliar filmes.

Atributos principais:

- `id : Long`
- `email : String`

### Entidade Score

Representa a avaliação feita por um usuário para um filme.

Atributo principal:

- `value : Double`

### Relacionamento

A relação entre **Movie** e **User** é muitos-para-muitos, sendo intermediada pela entidade **Score**.

Cada usuário pode avaliar vários filmes, e cada filme pode receber avaliações de vários usuários.

---

## ✅ Critérios de Correção

Mínimo para aprovação: **12 de 15 testes**.

### MovieServiceTests

- `findAllShouldReturnPagedMovieDTO`
- `findByIdShouldReturnMovieDTOWhenIdExists`
- `findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist`
- `insertShouldReturnMovieDTO`
- `updateShouldReturnMovieDTOWhenIdExists`
- `updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist`
- `deleteShouldDoNothingWhenIdExists`
- `deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist`
- `deleteShouldThrowDatabaseExceptionWhenDependentId`

### ScoreServiceTests

- `saveScoreShouldReturnMovieDTO`
- `saveScoreShouldThrowResourceNotFoundExceptionWhenNonExistingMovieId`

### UserServiceTests

- `authenticatedShouldReturnUserEntityWhenUserExists`
- `authenticatedShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExists`
- `loadUserByUsernameShouldReturnUserDetailsWhenUserExists`
- `loadUserByUsernameShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExists`

---

## 🧪 Competências Avaliadas

- Testes unitários em projeto Spring Boot com Java;
- Implementação de testes com **JUnit 5**;
- Utilização de mocks com **Mockito**;
- Testes de cenários de sucesso;
- Testes de cenários de exceção;
- Cobertura de código com **Jacoco**;
- Testes da camada de Service sem subir todo o contexto da aplicação.

---

## ✨ Funcionalidades do Projeto DSMovie

- Listagem paginada de filmes;
- Busca de filme por ID;
- Cadastro de filme;
- Atualização de filme;
- Exclusão de filme;
- Registro de avaliação de filme;
- Recalculo automático da média de avaliações;
- Contagem automática da quantidade de votos;
- Autenticação de usuário;
- Carregamento de usuário por username/e-mail.

---

## 🚀 Tecnologias Utilizadas

### Backend

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security

### Banco de Dados

- H2 Database

### Testes

- JUnit 5
- Mockito
- Jacoco

### Ferramentas

- Maven
- Postman
- IntelliJ IDEA
- Git e GitHub

---

## 🔗 Serviços Testados

### MovieService

Responsável pelas regras de negócio relacionadas aos filmes.

Principais métodos testados:

- `findAll`
- `findById`
- `insert`
- `update`
- `delete`

### ScoreService

Responsável por registrar avaliações de filmes.

Principais métodos testados:

- `saveScore`

### UserService

Responsável pela autenticação e carregamento de usuários.

Principais métodos testados:

- `authenticated`
- `loadUserByUsername`

---

## ⚠️ Exceções Testadas

Durante os testes unitários, também são validados cenários de erro:

- `ResourceNotFoundException`
- `DatabaseException`
- `UsernameNotFoundException`

---

## 📂 Estrutura Geral do Projeto

```text
src
├── main
│   ├── java
│   │   └── com.devsuperior.dsmovie
│   │       ├── controllers
│   │       ├── dto
│   │       ├── entities
│   │       ├── repositories
│   │       ├── services
│   │       └── tests
│   └── resources
└── test
    └── java
        └── com.devsuperior.dsmovie
            ├── services
            └── tests
```

---

## ▶️ Como Executar o Projeto

```bash
mvn spring-boot:run
```

---

## 🧪 Como Executar os Testes

```bash
mvn test
```

---

## 📊 Como Gerar o Relatório Jacoco

```bash
mvn test
```

Após executar os testes, o relatório do Jacoco será gerado em:

```text
target/site/jacoco/index.html
```

Abra esse arquivo no navegador para verificar a cobertura dos testes.

---

## 📬 Coleções Postman

O desafio também pode conter arquivos de apoio para testes manuais da API com Postman:

- Collection do desafio DSMovie Jacoco;
- Environment do projeto DSMovie.

---

## 📖 Aprendizados

Durante este desafio foram praticados conceitos importantes para testes automatizados em aplicações Java com Spring Boot:

- Criação de testes unitários;
- Isolamento da camada de Service;
- Uso de mocks para simular dependências;
- Validação de retornos esperados;
- Validação de exceções;
- Cobertura de código com Jacoco;
- Organização de classes de teste;
- Boas práticas com JUnit 5 e Mockito.

---

## 🎓 Curso

**Java Spring Expert**

Professor: **Nélio Alves**

Plataforma: **DevSuperior**

https://devsuperior.com.br

---

## ⭐ Agradecimento

Agradecimento ao professor **Nélio Alves** e à **DevSuperior** pela formação prática e aprofundada em desenvolvimento backend com Java, Spring Boot e testes automatizados.
