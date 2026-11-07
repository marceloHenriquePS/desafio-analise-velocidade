# Desafio técnico: Dev Júnior Java + Angular

**Backend:** Java 21+ / Spring Boot 4.1.2\
**Frontend:** Angular 17+

## Pré-Requisitos

1. Java 21 ou superior
2. Node.js (versão LTS) e npm
3. Angular CLI: npm install -g @angular/cli

## Como Rodar

Abra dois terminais, um para cada parte.

1. **Backend - porta:8080**

`cd analise-velocidade-backend`\
`./mvnw spring-boot:run`

**Ou**: Abrir o projeto na IDE InteliJ, e rodar o aquivo `AnaliseVelocidadeApplication`

**Caso queira ver a documentação**: http://localhost:8080/swagger-ui/index.html#/

2. **Frontend - porta:4200**

`cd analise-velocidade-frontend`\
`npm install`\
`ng serve`

**Acesse:** http://localhost:4200.

**Como usar:**
1. Clique em **Escolher arquivo** e selecione um arquivo `CSV`.
2. Clique em **Analisar**.
3. Role a tela para ver as informações.
