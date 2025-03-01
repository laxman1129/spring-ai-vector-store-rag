# Spring AI with Postgres vector DB

This project is sample for running Spring AI with Postgres vector DB.

## Pre-requisite
- Docker installed
- Ollama installed
- java 21
- maven 3.8.1 +

## SETUP

- Using docker-compose to run Postgres DB
- Using Ollama to run Spring AI
- if vector-store table is not created, create it using the script from schema.sql

## Run

Run the project from intellij or using the command below

```shell
mvn spring-boot:run
```

## Test
```shell
http://localhost:8080/api/db/ask?question="what is a good truck to pull a sportsman 212 boat?"

```


## References
- https://www.udemy.com/course/spring-ai-beginner-to-guru/learn/lecture/43620096#questions
- https://docs.spring.io/spring-ai/reference/api/vectordbs/pgvector.html#_run_postgres_pgvector_db_locally
- 