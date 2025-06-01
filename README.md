# 📅 Event Management API

A **Event Management API** é uma aplicação RESTful desenvolvida com **Spring Boot** que permite o gerenciamento completo de eventos e usuários. Ela foi projetada para facilitar o registro, consulta e cancelamento de inscrições em eventos, além de oferecer rotas administrativas para controle de usuários e eventos.

## 🔧 Funcionalidades Principais

### 📌 Eventos
- Registrar um novo evento
- Buscar evento por ID
- Deletar evento existente

### 👤 Usuários
- Listar todos os usuários
- Buscar usuário por ID
- Atualizar informações de usuário
- Deletar usuário

### 📝 Inscrições em Eventos
- Inscrever usuário em um evento
- Cancelar inscrição
- Listar eventos nos quais um usuário está inscrito
- Listar usuários inscritos em um evento

## 🚀 Tecnologias e Dependências

A aplicação utiliza o ecossistema Spring e outras ferramentas para facilitar o desenvolvimento, testes e versionamento do banco de dados:

- `spring-boot-starter-web` – Criação de APIs REST
- `spring-boot-starter-data-jpa` – Integração com JPA/Hibernate
- `spring-boot-devtools` – Ferramentas para desenvolvimento
- `mysql-connector-j` – Driver JDBC para MySQL
- `flyway-core` e `flyway-mysql` – Migrações e versionamento de banco de dados
- `spring-boot-starter-test` – Testes com JUnit, Mockito, etc.
- `testcontainers-mysql` – Testes de integração com MySQL real
- `rest-assured` – Testes de endpoints REST

### Instruções para executar os testes do projeto:
``` bash
    mvn test
```

### Instruções para rodar o projeto:
1. Clone o repositório.
2. Navegue até o diretório do projeto.
3. Execute: 

``` bash
    docker compose up
```
para executar todo o projeto, acesse os endepoints a partir de http://localhost:8181/


## User Endpoint

### `GET /api/user/v1/` (Get all users)
query params
``` bash
    page, defaultValue = 0
    size, defaultValue = 10
    direction, defaultValue = "asc"
```

```json
response:
{
    "content": [
        {
            "id": 4,
            "name": "Ana",
            "last_name": "Costa",
            "email": "ana.costa@example.com",
            "gender": "FEMALE"
        },
        {
            "id": 10,
            "name": "Camila",
            "last_name": "Fernandes",
            "email": "camila.fernandes@example.com",
            "gender": "FEMALE"
        },
        {
            "id": 3,
            "name": "Carlos",
            "last_name": "Oliveira",
            "email": "carlos.oliveira@example.com",
            "gender": "MALE"
        },
    ],
    "pageable": {
        "pageNumber": 0,
        "pageSize": 10,
        "sort": {
            "sorted": true,
            "unsorted": false,
            "empty": false
        },
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "totalPages": 1,
    "totalElements": 10,
    "last": true,
    "numberOfElements": 10,
    "size": 10,
    "number": 0,
    "sort": {
        "sorted": true,
        "unsorted": false,
        "empty": false
    },
    "first": true,
    "empty": false
}
``` 

### `GET BY ID /api/user/v1/{id}` (Get User by id)
path variable
``` bash
    id
```

``` json
    response
    {
        "id": 1,
        "name": "Ana",
        "last_name": "Costa",
        "email": "ana.costa@example.com",
        "gender": "FEMALE"
    },
```

### `POST /api/user/v1/` (Create User)
``` json
    request:
    {
        "name": "Ana",
        "last_name": "Costa",
        "email": "ana.costa@example.com",
        "gender": "FEMALE"
    }

    response:
    {
        "id": 1,
        "name": "Ana",
        "last_name": "Costa",
        "email": "ana.costa@example.com",
        "gender": "FEMALE"
    },

```

### `PUT /api/user/v1/{id}` (Update User)
path variable
``` bash
    id
```
``` json
    request:
    {
        "name": "Ana",
        "last_name": "Souza",
        "email": "ana.costa@example.com",
        "gender": "FEMALE"
    }

    response:
    {
        "id": 1,
        "name": "Ana",
        "last_name": "Souza",
        "email": "ana.costa@example.com",
        "gender": "FEMALE"
    },

```

### `DELETE /api/user/v1/{id}` (Delete User)
path variable
``` bash
    id
```
``` json
    response: 204 no-content

```

## Event Endpoint

### `GET /api/event/v1/` (Get all events)
query params
``` bash
    page, defaultValue = 0
    size, defaultValue = 10
    direction, defaultValue = "asc"
```
``` json
response:
{
    "content": [
        {
            "id": 3,
            "title": "AI & Machine Learning Bootcamp",
            "description": "An intensive 3-day bootcamp on applied machine learning and AI models.",
            "eventDate": "2025-08-15T13:00:00.000+00:00",
            "address": {
                "street": "456 Knowledge St",
                "neighborhood": "Tech Park",
                "city": "Campinas",
                "state": "SP",
                "cep": "13010000"
            },
            "eventUrl": "https://example.com/ai-bootcamp",
            "eventImageUrl": "https://example.com/images/ai-bootcamp.jpg",
            "capacity": 100,
            "remote": false
        },
        {
            "id": 2,
            "title": "Remote Dev Meetup",
            "description": "An online meetup for remote developers around the world to connect and share ideas.",
            "eventDate": "2025-07-05T21:00:00.000+00:00",
            "address": null,
            "eventUrl": "https://example.com/remote-dev-meetup",
            "eventImageUrl": "https://example.com/images/remote-meetup.jpg",
            "capacity": 300,
            "remote": true
        },
        {
            "id": 1,
            "title": "Tech Conference 2025",
            "description": "A major conference focused on the latest trends in software development, AI, and cloud computing.",
            "eventDate": "2025-06-20T12:00:00.000+00:00",
            "address": {
                "street": "123 Innovation Ave",
                "neighborhood": "Downtown",
                "city": "São Paulo",
                "state": "SP",
                "cep": "01001000"
            },
            "eventUrl": "https://example.com/tech-conference-2025",
            "eventImageUrl": "https://example.com/images/tech-conference.jpg",
            "capacity": 500,
            "remote": false
        }
    ],
    "pageable": {
        "pageNumber": 0,
        "pageSize": 10,
        "sort": {
            "unsorted": false,
            "sorted": true,
            "empty": false
        },
        "offset": 0,
        "unpaged": false,
        "paged": true
    },
    "totalPages": 1,
    "totalElements": 3,
    "last": true,
    "first": true,
    "numberOfElements": 3,
    "size": 10,
    "number": 0,
    "sort": {
        "unsorted": false,
        "sorted": true,
        "empty": false
    },
    "empty": false
}
```


### `GET BY ID/api/event/v1/{id}` (Get event by id)
path variable
``` bash
    id
```
```json
response:
{
    "id": 1,
    "title": "Tech Conference 2025",
    "description": "A major conference focused on the latest trends in software development, AI, and cloud computing.",
    "eventDate": "2025-06-20T12:00:00.000+00:00",
    "address": {
        "street": "123 Innovation Ave",
        "neighborhood": "Downtown",
        "city": "São Paulo",
        "state": "SP",
        "cep": "01001000"
    },
    "eventUrl": "https://example.com/tech-conference-2025",
    "eventImageUrl": "https://example.com/images/tech-conference.jpg",
    "capacity": 500,
    "remote": false
}
```


### `POST /api/event/v1/` (Create in person event)
``` json
    request:
    {
        "title": "Tech Conference 2025",
        "description": "A major conference focused on the latest trends in software development, AI, and cloud computing.",
        "eventDate": "2025-06-20T12:00:00.000+00:00",
        "address": {
            "street": "123 Innovation Ave",
            "neighborhood": "Downtown",
            "city": "São Paulo",
            "state": "SP",
            "cep": "01001000"
        },
        "eventUrl": "https://example.com/tech-conference-2025",
        "eventImageUrl": "https://example.com/images/tech-conference.jpg",
        "capacity": 500,
    }

    response:
    {
        "id": 1,
        "title": "Tech Conference 2025",
        "description": "A major conference focused on the latest trends in software development, AI, and cloud computing.",
        "eventDate": "2025-06-20T12:00:00.000+00:00",
        "address": {
            "street": "123 Innovation Ave",
            "neighborhood": "Downtown",
            "city": "São Paulo",
            "state": "SP",
            "cep": "01001000"
        },
        "eventUrl": "https://example.com/tech-conference-2025",
        "eventImageUrl": "https://example.com/images/tech-conference.jpg",
        "capacity": 500,
        "remote": false
    },

```

### `POST /api/event/v1/remote` (Create remote event)
``` json
    request:
    {
        "title": "Remote Dev Meetup",
        "description": "An online meetup for remote developers around the world to connect and share ideas.",
        "eventDate": "2025-07-05T21:00:00.000+00:00",
        "eventUrl": "https://example.com/remote-dev-meetup",
        "eventImageUrl": "https://example.com/images/remote-meetup.jpg",
        "capacity": 300
    }

    response:
    {
        "id": 2,
        "title": "Remote Dev Meetup",
        "description": "An online meetup for remote developers around the world to connect and share ideas.",
        "eventDate": "2025-07-05T21:00:00.000+00:00",
        "address": null,
        "eventUrl": "https://example.com/remote-dev-meetup",
        "eventImageUrl": "https://example.com/images/remote-meetup.jpg",
        "capacity": 300,
        "remote": true
    },

```

### `DELETE /api/event/v1/{id}` (Delete event)
path variable
``` bash
    id
```
``` json
    response: 204 no-content

```

## EventUser Endpoint 
**Endpoint for register users for an event and retrieve a user's events or retrieve the users registered for an event**

### `GET /api/management/v1/subscriptions/{eventId}` (Get event subscriptions)
query params
``` bash
    page, defaultValue = 0
    size, defaultValue = 10
    direction, defaultValue = "asc"
```

path variable
```bash
    eventId
```
``` json
response:
{
    "content": [
        {
            "id": 2,
            "name": "Maria",
            "last_name": "Souza",
            "email": "maria.souza@example.com",
            "gender": "FEMALE"
        },
        {
            "id": 10,
            "name": "Camila",
            "last_name": "Fernandes",
            "email": "camila.fernandes@example.com",
            "gender": "FEMALE"
        }
    ],
    "pageable": {
        "pageNumber": 0,
        "pageSize": 10,
        "sort": {
            "unsorted": false,
            "sorted": true,
            "empty": false
        },
        "offset": 0,
        "unpaged": false,
        "paged": true
    },
    "totalPages": 1,
    "totalElements": 2,
    "last": true,
    "first": true,
    "numberOfElements": 2,
    "size": 10,
    "number": 0,
    "sort": {
        "unsorted": false,
        "sorted": true,
        "empty": false
    },
    "empty": false
}
```


### `GET /api/management/v1/registrations/{userId}` (Get user events registred)
query params
``` bash
    page, defaultValue = 0
    size, defaultValue = 10
    direction, defaultValue = "asc"
```

path variable
```bash
    userId
```
``` json
response:
{
    "content": [
        {
            "id": 2,
            "title": "Remote Dev Meetup",
            "description": "An online meetup for remote developers around the world to connect and share ideas.",
            "eventDate": "2025-07-05T21:00:00.000+00:00",
            "address": null,
            "eventUrl": "https://example.com/remote-dev-meetup",
            "eventImageUrl": "https://example.com/images/remote-meetup.jpg",
            "capacity": 300,
            "remote": true
        }
    ],
    "pageable": {
        "pageNumber": 0,
        "pageSize": 10,
        "sort": {
            "unsorted": false,
            "sorted": true,
            "empty": false
        },
        "offset": 0,
        "unpaged": false,
        "paged": true
    },
    "totalPages": 1,
    "totalElements": 1,
    "last": true,
    "first": true,
    "numberOfElements": 1,
    "size": 10,
    "number": 0,
    "sort": {
        "unsorted": false,
        "sorted": true,
        "empty": false
    },
    "empty": false
}
```

### `POST /api/management/v1/register/{userId}/{eventId}` (Register on event)
query params
``` bash
    page, defaultValue = 0
    size, defaultValue = 10
    direction, defaultValue = "asc"
```

path variable
```bash
    userId
    eventId
```
``` json
response:
    {
        "id": 2,
        "title": "Remote Dev Meetup",
        "description": "An online meetup for remote developers around the world to connect and share ideas.",
        "eventDate": "2025-07-05T21:00:00.000+00:00",
        "address": null,
        "eventUrl": "https://example.com/remote-dev-meetup",
        "eventImageUrl": "https://example.com/images/remote-meetup.jpg",
        "capacity": 300,
        "remote": true
    }
 
```

### `DELETE /api/management/v1/cancelSubscription/{eventId}/{userId}` (Cancel event subscription)
query params
``` bash
    page, defaultValue = 0
    size, defaultValue = 10
    direction, defaultValue = "asc"
```

path variable
```bash
    userId
    eventId
```
``` json
response: 204 no-content
 
```