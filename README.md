# Aplicação Bancária Simples

Implementação de uma API REST para uma aplicação bancária simples, desenvolvida com Spring Boot. O projeto simula funcionalidades essenciais como criação de contas, depósitos, transferências, consulta de saldo e extrato.

Esse projeto é uma extensão do repositório https://github.com/lucasbarbosaalves/bank-app-challenge seguindo as mesmas premissas e objetivos descritos no README.md.

## Rotas Implementadas

### Gerenciamento de Usuários

*   `POST /api/users`
    *   Cria um novo usuário e sua respectiva conta bancária.

### Operações de Conta

*   `POST /api/accounts/{accountId}/deposit`
    *   Realiza um depósito de um determinado valor na conta especificada.

*   `GET /api/accounts/{accountId}/balance`
    *   Retorna o saldo atual da conta.

*   `GET /api/accounts/{accountId}/statement`
    *   Retorna o extrato completo da conta em formato JSON, com todas as transações de débito e crédito.

### Transações

*   `POST /api/transactions/transfer`
    *   Realiza uma transferência de valor entre duas contas.

*   `GET /api/transactions/export/csv/{accountId}`
    *   Gera e disponibiliza para download um arquivo `.csv` com o extrato da conta.

## Como Executar a Aplicação

### Pré-requisitos

*   Java 21 ou superior
*   Apache Maven 3.8 ou superior

### Passos para Execução

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/lucasbarbosaalves/bank-api-challenge.git
    ```

2. **Execute a aplicação com o Maven:**
    ```bash
    mvn spring-boot:run
    ```

Após a inicialização, a aplicação estará disponível em `http://localhost:8080`.

## Documentação da API (Swagger) 

A API está documentada com OpenAPI (Swagger). Após iniciar a aplicação, você pode acessar a interface interativa do Swagger para visualizar e testar todos os endpoints.

*   **URL da Documentação:** https://bank-api-challenge.onrender.com/swagger-ui/index.html

## Banco de Dados

Este projeto utiliza um banco de dados em memória **H2 Database**. Isso significa que não é necessário configurar um banco de dados externo. Os dados são armazenados na memória enquanto a aplicação está em execução e são perdidos quando ela é encerrada.

Você pode acessar o console do H2 para visualizar as tabelas e os dados em tempo real.

*   **URL do Console H2:** https://bank-api-challenge.onrender.com/h2-console

Use as seguintes credenciais para conectar:

*   **Driver Class:** `org.h2.Driver`
*   **JDBC URL:** `jdbc:h2:mem:testdb`
*   **User Name:** `sa`
*   **Password:** 