# API Intermunicipal (PE Livre Acesso)

> API para gestão de beneficiários e lookups do programa PE Livre Acesso Intermunicipais.

## Sumário
- Visão geral
- Requisitos
- Configuração (env) e execução
- Scripts úteis (Maven)
- Variáveis de ambiente
- Segurança e perfis
- Banco de dados e migrações (Flyway)
- Documentação da API (OpenAPI/Swagger)
- Testes
- Estrutura do projeto
- Licença
- Links úteis

## Visão geral
- Linguagem/Stack: Java 21, Spring Boot 3.5.6
- Build/Empacotamento: Maven (mvnw wrapper incluso)
- Banco de dados: MySQL (JDBC) com migrações via Flyway
- Documentação de API: springdoc-openapi-starter-webmvc-ui (Swagger UI)
- Segurança: Spring Security (comportamento variando por perfil de execução)
- Outras dependências: Lombok, Thymeleaf, spring-dotenv (carregamento automático do .env)

Ponto de entrada da aplicação: `src/main/java/br/gov/pe/sjdh/apiIntermunicipal/ApiIntermunicipalApplication.java`.

## Requisitos
- JDK 21 instalado e configurado (JAVA_HOME)
- MySQL 8.x disponível (local ou remoto)
- Permissões para executar o wrapper Maven (`./mvnw`)

## Configuração (env) e execução
1) Copie o arquivo de exemplo de variáveis ambiente para `.env`:
   - Atenção: o repositório contém `.env.exemplo` (e não `.env.example`).
   ```bash
   cp .env.exemplo .env
   ```

2) Ajuste os valores no `.env` conforme seu ambiente. Para desenvolvimento local sem autenticação, exporte o perfil de desenvolvimento:
   ```bash
   export APP_PROFILE=dev
   ```
   ou defina `APP_PROFILE=dev` dentro do próprio `.env`.

3) Garanta que o MySQL esteja em execução e, se necessário, crie o banco (padrão `pelivreacesso`).

4) Compilação rápida (sem testes):
   ```bash
   ./mvnw -q -DskipTests package
   ```

5) Executar a aplicação:
   ```bash
   ./mvnw spring-boot:run
   ```

- Porta padrão: `3000` (configurável por `APP_PORT`).
- Swagger UI: acesse `http://localhost:3000/swagger`.

## Scripts úteis (Maven)
- Rodar a aplicação: `./mvnw spring-boot:run`
- Build sem testes: `./mvnw -q -DskipTests package`
- Todos os testes: `./mvnw -q test`
- Teste por classe: `./mvnw -q -Dtest=NomeDaClasseTest test`
- Teste por método: `./mvnw -q -Dtest=NomeDaClasseTest#nomeDoMetodo test`

## Variáveis de ambiente
As variáveis abaixo possuem defaults definidos em `src/main/resources/application.properties` e/ou são lidas via spring-dotenv.

- APP_PORT (default: 3000)
- APP_PROFILE (default: dev) — controla regras de segurança; ver seção “Segurança e perfis”
- SECURITY_USERNAME, SECURITY_PASSWORD — credenciais padrão caso o perfil exija autenticação
- CORS_ALLOWED_ORIGINS (default: `http://localhost:3000,http://localhost:4200,http://localhost:8080`)
- DB_HOST (default: `localhost`)
- DB_PORT (default: `3306`)
- DB_NAME (default: `pelivreacesso`)
- DB_USERNAME (default: `pelivre`)
- DB_PASSWORD (sem default)
- SHOW_SQL (default: `true`)
- MAX_FILE_SIZE (default: `20MB`)
- MAX_REQUEST_SIZE (default: `100MB`)
- UPLOAD_DIR (default: `uploads`)

Observações:
- CORS lê de `app.cors.allowed-origins` que é populado a partir de `CORS_ALLOWED_ORIGINS`.
- Swagger: caminhos configurados em `application.properties` (`/v3/api-docs` e `/swagger`).

## Segurança e perfis
A classe `br.gov.pe.sjdh.apiIntermunicipal.config.SecurityConfig` define o comportamento com base em `APP_PROFILE`:
- `dev` ou `local`: todas as rotas são liberadas (`permitAll()`).
- Outros perfis (produção): endpoints sensíveis exigem autenticação; Swagger e alguns públicos permanecem liberados.

CORS é configurado com origens permitidas via `CORS_ALLOWED_ORIGINS` (múltiplas origens separadas por vírgula).

## Banco de dados e migrações (Flyway)
- URL padrão: `jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME}`
- Flyway habilitado por padrão (`spring.flyway.enabled=true`). Migrações em `classpath:db/migration`.
- Migração inicial: `src/main/resources/db/migration/V1__CREATE_TABLE_beneficiarios.sql`.

Em ambientes sem MySQL ativo, a inicialização pode falhar ao tentar conectar/migrar. Para testes, veja a seção a seguir.

## Documentação da API (OpenAPI/Swagger)
- Swagger UI: `GET /swagger`
- Documentos JSON: `GET /v3/api-docs`

Configuração em `OpenApiConfig` (`br.gov.pe.sjdh.apiIntermunicipal.config.OpenApiConfig`).

## Testes
- Framework: JUnit 5 (`spring-boot-starter-test`) e `spring-security-test`.
- Executar todos os testes:
  ```bash
  ./mvnw -q test
  ```
- Observação: o teste existente `ApiIntermunicipalApplicationTests::contextLoads()` passa sem exigir MySQL.
- Para evitar dependência de infraestrutura em testes de integração, você pode desabilitar Flyway e usar H2 em memória:
  ```
  @SpringBootTest(properties = {
      "spring.flyway.enabled=false",
      "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
      "spring.datasource.driver-class-name=org.h2.Driver",
      "spring.jpa.hibernate.ddl-auto=update"
  })
  ```
  E adicionar a dependência de teste (no `pom.xml`):
  ```
  <dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>test</scope>
  </dependency>
  ```
- Alternativamente, use `@WebMvcTest` + `@MockBean` para isolar a camada web.

## Estrutura do projeto
```
api-intermunicipal/
├─ pom.xml
├─ mvnw, mvnw.cmd
├─ README.md
├─ src/
│  ├─ main/
│  │  ├─ java/br/gov/pe/sjdh/apiIntermunicipal/
│  │  │  ├─ ApiIntermunicipalApplication.java  ← ponto de entrada
│  │  │  ├─ config/ (SecurityConfig, OpenApiConfig, FileStorageProperties)
│  │  │  ├─ controller/ (BeneficiarioController, LookupController, etc.)
│  │  │  ├─ domain/ (modelos, DTOs, repositórios)
│  │  │  ├─ exception/ (GlobalExceptionHandler, ErrorResponse, etc.)
│  │  │  └─ service/ (regras de negócio)
│  │  └─ resources/
│  │     ├─ application.properties
│  │     ├─ db/migration/ (Flyway)
│  │     └─ templates/home.html
│  └─ test/
│     └─ java/br/gov/pe/sjdh/apiintermunicipal/ (testes JUnit)
├─ .env.exemplo  ← modelo para suas variáveis de ambiente
├─ uploads/      ← diretório padrão para uploads em desenvolvimento
└─ doc/
   ├─ checklist.md
   └─ rascunho.md
```

## Licença
- TODO: definir e adicionar arquivo de licença (por exemplo, `LICENSE`) a este repositório.

## Links úteis
- Checklist do progresso: `doc/checklist.md`
- Swagger UI (local): `http://localhost:3000/swagger`
- OpenAPI JSON: `http://localhost:3000/v3/api-docs`
- Dica: defina `APP_PROFILE=dev` em desenvolvimento para facilitar testes manuais sem autenticação.