# TicketFlow API 🎟️

Plataforma de compra e venda de ingressos desenvolvida como projeto de portfólio.

## Tecnologias

- Java 17
- Spring Boot 4
- Spring Data JPA
- PostgreSQL
- Maven

## Funcionalidades

- Cadastro de usuários
- Compra de ingressos vinculados a usuários
- Listagem e remoção de registros
- API REST com endpoints CRUD completos
- Frontend em HTML/CSS/JS consumindo a API

## Como rodar localmente

### Pré-requisitos
- Java 17+
- PostgreSQL rodando na porta 5432
- Maven (ou usar o `mvnw` incluso)

### Configuração

1. Clone o repositório
```bash
   git clone https://github.com/dgiacoboni01/ticketflow-api.git
```

2. Crie o banco de dados
```sql
   CREATE DATABASE api_db;
```

3. Configure as variáveis no `application.properties`
```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/api_db
   spring.datasource.username=postgres
   spring.datasource.password=SUA_SENHA
```

4. Rode a aplicação
```bash
   ./mvnw spring-boot:run
```

5. Acesse `http://localhost:8080`

## Endpoints

| Método | Rota | Descrição |
|--------|------|-----------|
| GET | /users | Lista todos os usuários |
| POST | /users | Cria um usuário |
| DELETE | /users/{id} | Remove um usuário |
| GET | /ingressos | Lista todos os ingressos |
| POST | /ingressos | Compra um ingresso |
| DELETE | /ingressos/{id} | Remove um ingresso |

## Próximas melhorias

- [ ] Autenticação com Spring Security e JWT
- [ ] DTOs e validações com Bean Validation
- [ ] Deploy em nuvem
- [ ] Testes automatizados
