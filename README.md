# API Intermunicipal – PE Livre Acesso

API oficial para gestão de beneficiários do programa PE Livre Acesso Intermunicipal (SJDHPV‑PE), incluindo cadastros, consultas, uploads de documentos e catálogos (lookups). Este README foi elaborado para orientar desde a primeira execução até o uso dos principais fluxos de negócio via Swagger, cURL e integrações front‑end.


## Sumário
- Visão geral do projeto
- Pré‑requisitos
- Configuração do ambiente (.env) e execução
- Perfis de execução, segurança e CORS
- Autenticação JWT (Bearer)
- Banco de dados e migrações (Flyway)
- Documentação da API (Swagger/OpenAPI)
- Fluxos principais da aplicação (guias de uso)
  - Beneficiários (cadastro, atualização, consulta)
  - Responsáveis
  - Usuários
  - Lookups (cidades, etnias, sexos, status, locais de retirada, tipos de deficiência, tipos de arquivo)
  - Upload de arquivos do beneficiário
- Padrões de paginação, ordenação e erros
- Variáveis de ambiente (tabela)
- Documentação adicional (Actuator e Checklist)
- Estrutura do projeto
- Dicas operacionais e troubleshooting
- Licença e créditos


## Visão geral do projeto
- Linguagem/stack: Java 21, Spring Boot 3.5.6
- Build/empacotamento: Maven (wrapper incluso `mvnw`)
- Banco de dados: MySQL 8.x (JDBC) com migrações via Flyway
- Documentação: springdoc‑openapi 2.8.13 (Swagger UI em `/swagger`)
- Segurança: Spring Security com comportamento por perfil (`APP_PROFILE`)
- Outras dependências relevantes: Lombok, Thymeleaf, spring‑dotenv (carrega `.env`), Spring Web, Spring Data JPA

Ponto de entrada: `src/main/java/br/gov/pe/sjdh/apiIntermunicipal/ApiIntermunicipalApplication.java`.


## Pré‑requisitos
- JDK 21 instalado (JAVA_HOME configurado)
- MySQL 8.x acessível (local ou remoto)
- Permissão de execução do wrapper Maven: `./mvnw`


## Configuração do ambiente (.env) e execução
1) Criar o arquivo `.env` a partir do exemplo (observação de nomenclatura):
   - O repositório traz `.env.exemplo` (e não `.env.example`).
   ```bash
   cp .env.exemplo .env
   ```

2) Ajustar variáveis no `.env` conforme seu ambiente (DB, porta, CORS, etc.). Para desenvolvimento sem autenticação, ative o perfil `dev`:
   ```bash
   export APP_PROFILE=dev
   # ou defina APP_PROFILE=dev dentro do .env
   ```

3) Garantir MySQL em execução e banco criado (padrão `pelivreacesso`).

4) Build rápido (sem testes):
   ```bash
   ./mvnw -q -DskipTests package
   ```

5) Executar a aplicação:
   ```bash
   ./mvnw spring-boot:run
   ```

- Porta padrão: `3000` (configurável por `APP_PORT`).
- Swagger UI: http://localhost:3000/swagger


## Perfis de execução, segurança e CORS
- Classe de segurança: `infra/config/SecurityConfig.java` (varia por `APP_PROFILE`).
  - `dev` ou `local`: todas as rotas liberadas (permitAll). Ideal para desenvolvimento e testes manuais.
  - Outros perfis (produção): endpoints sensíveis exigem autenticação via JWT (OAuth2 Resource Server). Swagger e algumas rotas públicas permanecem liberadas conforme a política.
- CORS: configure origens permitidas via `CORS_ALLOWED_ORIGINS` (múltiplas separadas por vírgula), populando `app.cors.allowed-origins`.

Dica: Para testar com front‑end local (React/Angular/Vue), inclua a origem do front em `CORS_ALLOWED_ORIGINS`.


## Autenticação JWT (Bearer)
A aplicação está preparada como um OAuth2 Resource Server com validação de tokens JWT.

- Perfis `dev`/`local`: a segurança é liberada (não exige token) para acelerar o desenvolvimento.
- Demais perfis: requisições a endpoints protegidos devem enviar o cabeçalho `Authorization: Bearer <token>`.

Pontos importantes:
- Configuração base: veja `SecurityConfig#filterChain` com `.oauth2ResourceServer(oauth2 -> oauth2.jwt(...))`.
- Segredo e expiração (quando utilizado decoder simétrico/HS256):
  - `JWT_SECRET` define a chave de assinatura.
  - `JWT_EXPIRATION` define o tempo de expiração padrão (segundos).
- Alternativamente, é possível configurar um Provedor de Chaves (JWK Set URI) externo:
  - Propriedade padrão do Spring: `spring.security.oauth2.resourceserver.jwt.jwk-set-uri=https://<issuer>/.well-known/jwks.json`.
  - Ou registrar um `JwtDecoder` como `@Bean`.

Como enviar o token:
```bash
curl -H "Authorization: Bearer <SEU_JWT_AQUI>" \
     -H 'Content-Type: application/json' \
     http://localhost:3000/beneficiarios
```

No Swagger:
- Acesse `/swagger` e clique em "Authorize" (cadeado), informe `Bearer <token>`.
- Após autorizado, as chamadas do Swagger incluirão o header automaticamente.

Observação:
- Este repositório não expõe, por padrão, um endpoint de emissão de tokens ("/auth/login") — integre com seu provedor de identidade (Keycloak, Cognito, etc.) ou implemente um emissor interno conforme necessidade.

## Banco de dados e migrações (Flyway)
- URL padrão: `jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME}`
- Flyway: habilitado por padrão (`spring.flyway.enabled=true`)
- Migrações: `src/main/resources/db/migration`
  - `V1__CREATE_TABLE_beneficiarios.sql`: cria tabelas principais (`beneficiarios`, `responsaveis`, `usuarios`, `beneficiario_arquivo`, `historico_status_beneficiario`, catálogos de lookups como `tipo_arquivo`, etc.) e insere seeds iniciais de `tipo_arquivo`.

Como funciona: ao iniciar a aplicação com o banco configurado, o Flyway verifica a versão atual do schema e aplica, em ordem, os scripts pendentes da pasta `db/migration` até alinhar o banco ao estado esperado.


## Documentação da API (Swagger/OpenAPI)
- Swagger UI: `GET /swagger`
- OpenAPI JSON: `GET /v3/api-docs`
- Configuração: `infra/config/OpenApiConfig.java` (título, descrição e contato)

Recomendado: explore os endpoints diretamente no Swagger para inspecionar modelos (DTOs), exemplos e códigos de resposta.


## Fluxos principais da aplicação
A seguir, um guia prático dos fluxos mais utilizados. Todos os exemplos assumem a aplicação rodando em `http://localhost:3000`.

### 1) Usuários
- Endpoints base: `/usuarios`
- Operações:
  - `GET /usuarios` — lista paginada (`page`, `size`, `sort`)
  - `GET /usuarios/{id}` — detalhes por ID
  - `GET /usuarios/cpf/{cpf}` — buscar por CPF (somente dígitos)
  - `GET /usuarios/email/{email}` — buscar por e‑mail
  - `POST /usuarios` — cadastrar
  - `PUT /usuarios/{id}` — atualizar
  - `DELETE /usuarios/{id}` — remover

Exemplo (cadastrar):
```bash
curl -X POST http://localhost:3000/usuarios \
  -H 'Content-Type: application/json' \
  -d '{
        "nome": "Maria da Silva",
        "cpf": "12345678901",
        "senha": "segredo123",
        "email": "maria@example.com",
        "ativo": true
      }'
```
Notas:
- Senhas atualmente são persistidas em texto (padrão do projeto). Para produção, recomenda‑se hash (ex.: BCrypt) e autenticação adequada.


### 2) Responsáveis
- Endpoints base: `/responsaveis`
- Operações:
  - `GET /responsaveis` — lista paginada
  - `GET /responsaveis/{id}` — detalhes por ID
  - `GET /responsaveis/cpf/{cpf}` — buscar por CPF
  - `POST /responsaveis` — cadastrar
  - `PUT /responsaveis/{id}` — atualizar
  - `DELETE /responsaveis/{id}` — remover

Exemplo (listar com ordenação):
```bash
curl 'http://localhost:3000/responsaveis?page=0&size=20&sort=updatedAt,desc'
```


### 3) Beneficiários
- Endpoints base: `/beneficiarios`
- Operações principais:
  - `GET /beneficiarios` — lista paginada (retorna DTO completo atualmente)
  - `GET /beneficiarios/{id}` — detalhes por UUID
  - `GET /beneficiarios/cpf/{cpf}/{dataNascimento}` — busca por CPF + data de nascimento (formato `yyyy-MM-dd` ou conforme definido no service)
  - `POST /beneficiarios` — cadastrar (DTO de entrada com dados pessoais, contato e endereço)
  - `PUT /beneficiarios/{id}` — atualizar (o ID do path prevalece)

Exemplo (buscar por CPF e data de nascimento):
```bash
curl http://localhost:3000/beneficiarios/cpf/12345678901/2000-05-10
```

Exemplo (cadastrar):
```bash
curl -X POST http://localhost:3000/beneficiarios \
  -H 'Content-Type: application/json' \
  -d '{
        "nome": "João Pereira",
        "nomeMae": "Ana Pereira",
        "cpf": "98765432100",
        "rg": "5566778",
        "dataNascimento": "2001-03-20",
        "telefone": "81999990000",
        "email": "",
        "endereco": {
          "logradouro": "Rua A",
          "numero": "123",
          "complemento": "Casa",
          "bairro": "Centro",
          "cidadeId": 15,
          "uf": "PE",
          "cep": "50000-000"
        },
        "sexoId": 1,
        "etniaId": 2,
        "tipoDeficienciaId": 3,
        "statusBeneficioId": 1,
        "localRetiradaId": 4,
        "vemLivreAcessoRmr": false
      }'
```

Observações:
- A API valida unicidades e integridades básicas (ex.: CPF único).
- Exclusão de beneficiário não está exposta por padrão (avaliar soft delete via `ativo` se for necessário).


### 4) Lookups (catálogos)
- Endpoints base: `/lookup`
- Disponíveis:
  - `GET /lookup/etnias`
  - `GET /lookup/cidades`
  - `GET /lookup/tipos-deficiencia`
  - `GET /lookup/sexos`
  - `GET /lookup/status-beneficio`
  - `GET /lookup/locais-retirada`
  - `GET /lookup/tipos-arquivo`

Exemplo:
```bash
curl http://localhost:3000/lookup/cidades
```

Nota: Existe o DTO genérico `DadosLookup` (id, nome, ativo) caso queira padronizar respostas de dropdowns mais leves; atualmente os endpoints retornam as entidades completas.


### 5) Upload de arquivos do beneficiário
- O projeto traz `FileUploadController` e `FileStorageService` para gerenciar uploads no diretório base definido por `UPLOAD_DIR` (padrão: `uploads/`).
- Tabelas correlatas: `beneficiario_arquivo` (metadados e vínculo) e `tipo_arquivo` (catálogo). 
- Fluxo recomendado:
  1. Cadastrar/recuperar `beneficiario_id` (UUID)
  2. Enviar arquivo indicando o `tipo_arquivo_id`
  3. Persistir o vínculo em `beneficiario_arquivo` (no controller/service responsável)
  4. Listar e remover arquivos conforme necessidade

Exemplo de upload (se o endpoint aceitar multipart em `/upload`):
```bash
curl -X POST http://localhost:3000/upload \
  -F 'file=@/caminho/para/documento.pdf' \
  -F 'tipoArquivoId=1' \
  -F 'beneficiarioId=UUID_AQUI'
```
Observação: adapte ao endpoint real exposto no projeto (consulte `/swagger`).


## Padrões de paginação, ordenação e erros
- Paginação e ordenação seguem convenções do Spring Data:
  - `page` (0‑based), `size`, `sort=campo,asc|desc` (ex.: `sort=updatedAt,desc`).
- Erros são padronizados por `infra/exception/GlobalExceptionHandler`, retornando payload `ErrorResponse` com mensagem e detalhes quando aplicável.


## Variáveis de ambiente (tabela)
As variáveis têm fallback em `src/main/resources/application.properties` e podem ser definidas no `.env` (carregado pelo spring‑dotenv).

| Variável | Descrição | Default |
|---------|-----------|---------|
| `APP_PORT` | Porta HTTP da aplicação | `3000` |
| `APP_PROFILE` | Perfil de execução (`dev`, `local`, prod, etc.) | `dev` |
| `SECURITY_USERNAME` | Usuário padrão para segurança básica (se aplicável) | — |
| `SECURITY_PASSWORD` | Senha padrão (se aplicável) | — |
| `CORS_ALLOWED_ORIGINS` | Origens permitidas (CSV) | `http://localhost:3000,http://localhost:4200,http://localhost:8080` |
| `DB_HOST` | Host do MySQL | `localhost` |
| `DB_PORT` | Porta do MySQL | `3306` |
| `DB_NAME` | Nome do banco | `pelivreacesso` |
| `DB_USERNAME` | Usuário do banco | `pelivre` |
| `DB_PASSWORD` | Senha do banco | — |
| `MAX_FILE_SIZE` | Tamanho máximo de arquivo | `20MB` |
| `MAX_REQUEST_SIZE` | Tamanho máximo da requisição | `100MB` |
| `UPLOAD_DIR` | Diretório base para uploads | `uploads` |
| `JWT_SECRET` | Segredo para assinatura/validação de JWT (HS256, ambientes sem JWK) | `change-me-please-32-characters-minimum-change-me` |
| `JWT_EXPIRATION` | Expiração padrão do token em segundos | `3600` |


## Documentação adicional (Actuator e Checklist)
- Guia do Actuator: `doc/actuator.md`
- Checklist de verificação: `doc/checklist.md`


## Estrutura do projeto
```
api-intermunicipal/
├─ pom.xml
├─ mvnw, mvnw.cmd
├─ README.md
├─ src/
│  ├─ main/
│  │  ├─ java/br/gov/pe/sjdh/apiIntermunicipal/
│  │  │  ├─ ApiIntermunicipalApplication.java
│  │  │  ├─ infra/config/ (SecurityConfig, OpenApiConfig, FileStorageProperties)
│  │  │  ├─ controller/ (BeneficiarioController, LookupController, ResponsavelController, UsuarioController)
│  │  │  ├─ domain/ (beneficiario, responsavel, usuario, lookup, etc.)
│  │  │  ├─ infra/exception/ (GlobalExceptionHandler, ErrorResponse, etc.)
│  │  │  └─ service/ (BeneficiarioService, LookupService, etc.)
│  │  └─ resources/
│  │     ├─ application.properties
│  │     ├─ db/migration/
│  │     └─ templates/home.html
│  └─ test/
│     └─ java/br/gov/pe/sjdh/apiintermunicipal/
├─ .env.exemplo
├─ uploads/
└─ doc/
   ├─ checklist.md
   └─ actuator.md
```


## Dicas operacionais e troubleshooting
- Se o Swagger não abrir, confirme a porta (`APP_PORT`) e o contexto padrão. Acesse `/swagger` e não `/swagger-ui.html`.
- Se a aplicação não inicia por erro de DB: confirme `DB_*` no `.env`, permissões do usuário e se o MySQL está escutando na porta correta.
- Logs SQL podem ser verbosos em dev. Ajuste níveis no `application.properties` conforme necessidade (ex.: `logging.level.org.hibernate.SQL=INFO`).
- Em dev, limpe periodicamente o diretório `uploads/` para evitar crescimento desnecessário.


## Licença e créditos
- Secretaria de Justiça, Direitos Humanos e Prevenção à Violência do Estado de Pernambuco (SJDHPV‑PE).
- Definir a licença oficial do projeto (adicionar arquivo `LICENSE`).