# 🧾 Checklist Técnico Interativo — API Intermunicipal

> **Projeto:** `api-intermunicipal`  
> **Autor:** [Raul Michel de França](https://github.com/raul-franca)  
> **Linguagem:** Java 21+ / Spring Boot 3.5.6  
> **Arquitetura:** REST API + DDD + Flyway + Uploads locais  
> **Última atualização:** _21/10/2025_  
> **Total de melhorias identificadas:**

---

## 📊 Progresso geral
🟩🟨🟨🟥🟥 **35% concluído** _(recalculado com novos itens)_

> **Última atualização:** 21/10/2025
> **Total de melhorias identificadas:** 90+ itens organizados em 13 categorias

---

## 🎯 Prioridades Imediatas (Quick Wins)

### 🔴 Crítico (fazer antes do deploy)
1. **Remover `System.out.println`** - substituir por logs adequados
2. **Trocar `RuntimeException` por exceções customizadas** (`BusinessException`, `NotFoundException`)
3. **Adicionar `@Valid`** nos endpoints que recebem DTOs
4. **Remover endpoints de teste** (`/hello`, `/test-post`)
5. **Configurar senha segura** no `.env` (não usar valores default)
6. **Adicionar constraint UNIQUE para CPF** no banco de dados
7. **Implementar autenticação JWT** para produção

### 🟡 Importante (melhorias de qualidade)
1. **Substituir `@Autowired` por `@RequiredArgsConstructor`** (padrão Lombok)
2. **Adicionar MapStruct** para mapeamento automático de DTOs
3. **Implementar cache** para tabelas de lookup (cidades, etnias, etc.)
4. **Adicionar índices no banco** para CPF e data_nascimento
5. **Criar testes unitários** básicos para services
6. **Adicionar actuator** para monitoramento

### 🟢 Desejável (melhorias futuras)
1. **Implementar soft delete** ao invés de exclusão física
2. **Adicionar auditoria** (created_by, updated_by)
3. **Implementar versionamento de API** (`/v1/beneficiarios`)
4. **Criar documentação técnica** completa
5. **Configurar rate limiting**

---

<details>
<summary>🧱 <strong>Estrutura e Organização</strong> — <em>limpeza e padrão DDD</em></summary>

- [x] Estrutura base (`controller`, `service`, `domain`, `config`)
- [x] Criar pacote `exception/` com:
  - [x] `BusinessException.java`
  - [x] `NotFoundException.java`
  - [x] `GlobalExceptionHandler.java`
  - [x] `ErrorResponse.java`
- [x] Reorganizar `domain/` em subpacotes (`model`, `repository`, `dto`, `view`)
- [x] Padronizar nomes de classes (`Service`, `Repository`, `DTO`, `View`)
- [ ] Adicionar `package-info.java` nos pacotes principais

</details>

---

<details>
<summary>⚙️ <strong>Configuração e Ambientes</strong> — <em>perfis, Flyway e uploads</em></summary>

- [ ] Converter `application.properties` → `application.yml`
- [ ] Criar perfis:
  - [ ] `application-dev.yml`
  - [ ] `application-prod.yml`
  - [ ] `application-test.yml`
- [x] Criar `FileStorageProperties` com `@ConfigurationProperties`
- [ ] Centralizar uploads (`file.upload-dir: /data/uploads`)
- [x] Configurar `Flyway` com:
  - [x] `V1__CREATE_TABLE_beneficiarios.sql`
  
</details>

---

<details>
<summary>🧩 <strong>Camada de Domínio e DTOs</strong> — <em>validação e mapeamento</em></summary>

- [ ] Criar `Mapper` com **MapStruct**
  - [ ] `BeneficiarioMapper.java`
  - [ ] Mapear `DTO` ↔ `Entity`
- [x] Aplicar anotações de validação (`@NotBlank`, `@CPF`, `@Email`)
- [x] Retornar `ResponseEntity` em todos os endpoints
- [ ] Retornar status HTTP corretos (`201`, `404`, `400`)

</details>

---

<details>
<summary>📂 <strong>Upload de arquivos</strong> — <em>validação e segurança</em></summary>

- [x] Validar tipo de arquivo no upload (`PDF`, `JPG`, `PNG`)
- [x] Definir limite de tamanho (`20MB`)
- [x] Gerar nome único com `Id_Benefiario` + `_` + `qual_arquivo, ex: rg, cpf`
- [x] Salvar arquivo físico em `/uploads`
- [ ] Tratar erros com resposta padronizada (`ErrorResponse`)
- [ ] Testar com arquivos corrompidos e tipos falsos (`.exe` renomeado)
- [ ] Adicionar logs de auditoria de upload

</details>

---

<details>
<summary>🧰 <strong>Desenvolvimento e Clean Code</strong> — <em>boas práticas e clareza</em></summary>

- [x] `@Transactional` nos métodos de escrita (`save`, `update`, `delete`)
- [ ] Substituir conversões manuais por mapeamentos automáticos
- [ ] Remover `System.out.println` (encontrados em `BeneficiarioService:62,63,73`)
- [x] Adicionar `Logger` SLF4J (`private static final Logger log = LoggerFactory.getLogger(...)`)
- [x] Usar `Optional` no repositório (`findByCpf`)
- [ ] Trocar `RuntimeException` genérica por exceções customizadas (`BusinessException`, `NotFoundException`)
- [ ] Remover endpoints de teste (`/hello`, `/test-post`) antes do deploy em produção
- [ ] Substituir `@Autowired` por `@RequiredArgsConstructor` (Lombok) nos controllers
- [ ] Adicionar validação `@Valid` nos endpoints que recebem DTOs

</details>

---

<details>
<summary>📘 <strong>Documentação OpenAPI / Swagger</strong> — <em>modelo e exemplos</em></summary>

- [x] Criar schema `ErrorResponse` com exemplos (`400`, `404`, `500`)
- [x] Adicionar `ErrorResponse` ao `GlobalExceptionHandler`
- [ ] Documentar respostas no OpenAPI (`@ApiResponse`, `@Content`)
- [x] Gerar arquivo `docs/openapi.yaml` com exemplos padronizados
- [ ] Publicar documentação Swagger acessível em `/swagger`
- [ ] Adicionar referência ao `docs/endpoints.md`

</details>
---

<details>
<summary>🧪 <strong>Testes e Qualidade</strong> — <em>JUnit + cobertura</em></summary>

- [ ] Criar testes unitários (`@DataJpaTest`, `@SpringBootTest`)
- [ ] Adicionar `BeneficiarioServiceTest.java`
- [ ] Simular upload com `MockMultipartFile`
- [ ] Configurar `jacoco-maven-plugin` (mínimo 70%)
- [ ] Configurar `maven-surefire-plugin` (execução automática)

</details>

---

<details>
<summary>💾 <strong>Armazenamento e Uploads</strong> — <em>controle e segurança</em></summary>

- [x] Centralizar lógica no `FileStorageService`
- [x] Nomear arquivos como `{uuid}_{tipo}.pdf`
- [ ] Criar tabela `uploads` (metadados)
- [x] Limite de tamanho (`20MB`)
- [ ] Tratar exceções personalizadas de upload

</details>

---

<details>
<summary>🌐 <strong>Observabilidade e Deploy</strong> — <em>monitoramento e execução</em></summary>

- [ ] Adicionar dependência `spring-boot-starter-actuator`
- [ ] Expor `/actuator/health` e `/actuator/info`
- [ ] Configurar nome do JAR (`api-intermunicipal-${version}.jar`)
- [ ] Testar build completo (`mvn clean package`)
- [ ] Preparar `.env` / variáveis no Heroku ou servidor interno
- [ ] Criar profiles de logging diferenciados (dev vs prod)
- [ ] Adicionar healthcheck para conexão com banco de dados
- [ ] Configurar rate limiting para proteger APIs públicas
- [ ] Implementar versionamento de API (`/v1/beneficiarios`)

</details>

---

<details>
<summary>📄 <strong>Documentação Técnica Extra</strong> — <em>para equipe e manutenção</em></summary>

- [ ] `docs/architecture-overview.md` — visão geral e diagrama
- [ ] `docs/endpoints.md` — lista de rotas
- [ ] `docs/flyway-migrations.md` — histórico SQL
- [ ] `docs/deployment.md` — guia de deploy e configuração de ambientes
- [ ] Documentar variáveis de ambiente obrigatórias no README
- [ ] Adicionar exemplos de requisições curl ou Postman collection

</details>

---

<details>
<summary>🔒 <strong>Segurança</strong> — <em>proteção e boas práticas</em></summary>

- [ ] Revisar configuração CORS - validar origens permitidas
- [ ] Implementar autenticação JWT para produção (atualmente desabilitada)
- [ ] Adicionar rate limiting por IP
- [ ] Validar e sanitizar todos os inputs de usuário
- [ ] Implementar auditoria de ações sensíveis (criação, exclusão)
- [ ] Configurar HTTPS obrigatório em produção
- [ ] Adicionar proteção contra SQL Injection (usar sempre queries parametrizadas)
- [ ] Implementar logs de segurança (tentativas de acesso não autorizadas)
- [ ] Configurar senha segura no `.env` (não usar valores default)
- [ ] Revisar permissões de diretório de uploads

</details>

---

<details>
<summary>⚡ <strong>Performance e Otimização</strong> — <em>melhorias de desempenho</em></summary>

- [ ] Adicionar cache para lookups (cidades, etnias, etc.) com `@Cacheable`
- [ ] Implementar lazy loading estratégico nas entidades JPA
- [ ] Otimizar queries N+1 com `@EntityGraph` ou `JOIN FETCH`
- [ ] Adicionar índices no banco para campos mais consultados (CPF, data_nascimento)
- [ ] Configurar pool de conexões do HikariCP
- [ ] Implementar compressão de respostas HTTP (gzip)
- [ ] Adicionar paginação em todos endpoints de listagem
- [ ] Considerar uso de DTOs de projeção para queries específicas

</details>

---

<details>
<summary>🔄 <strong>Resiliência e Confiabilidade</strong> — <em>tratamento de falhas</em></summary>

- [ ] Implementar retry mechanism para operações críticas
- [ ] Adicionar circuit breaker se houver integrações externas
- [ ] Configurar timeout adequado para requisições HTTP
- [ ] Implementar graceful shutdown
- [ ] Adicionar validação de integridade de arquivos no upload
- [ ] Criar job de limpeza de arquivos órfãos
- [ ] Implementar backup automático do diretório de uploads
- [ ] Adicionar monitoramento de espaço em disco

</details>

---

<details>
<summary>📊 <strong>Dados e Banco</strong> — <em>migrations e integridade</em></summary>

- [x] Flyway configurado com baseline
- [x] Migration V1 criada
- [ ] Adicionar constraint UNIQUE para CPF no banco
- [ ] Criar índices para melhorar performance de buscas
- [ ] Adicionar migration V2 com campos de auditoria (created_by, updated_by)
- [ ] Implementar soft delete ao invés de exclusão física
- [ ] Criar view materializada para beneficiários completos (performance)
- [ ] Adicionar validação de integridade referencial em cascata
- [ ] Documentar modelo de dados (diagrama ER)

</details>

---

---

## 🔍 Issues Específicos Identificados no Código

### BeneficiarioService.java
- **Linha 53-54:** Usar `NotFoundException` ao invés de `RuntimeException`
- **Linha 61-66:** Usar `NotFoundException` e remover `System.out.println`
- **Linha 62-63:** Remover prints de debug
- **Linha 73:** Remover `System.out.println`
- **Linha 64:** Melhorar validação de data (usar comparação de objetos `LocalDate`)

### BeneficiarioController.java
- **Linha 27-28:** Trocar `@Autowired` por construtor com `@RequiredArgsConstructor`
- **Linha 101:** Adicionar `@Valid` no parâmetro `DadosCadastrarBeneficiarioDTO`
- **Linha 109-118:** Remover endpoints de teste antes de produção

### SecurityConfig.java
- **Linha 22-23:** Validar e restringir origens CORS permitidas em produção
- **Linha 33-35:** Remover ou condicionar modo "permitAll" apenas para dev local

### application.properties
- **Linha 6-7:** Trocar credenciais padrão por valores seguros
- **Linha 19-26:** Desabilitar logs SQL detalhados em produção
- Considerar migração para `application.yml` para melhor organização

---

## 📝 Notas Técnicas

### Dependências Sugeridas para Adicionar
```xml
<!-- Cache -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>

<!-- MapStruct -->
<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct</artifactId>
    <version>1.5.5.Final</version>
</dependency>

<!-- Actuator -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>

<!-- JWT -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.12.3</version>
</dependency>

<!-- Jacoco para cobertura -->
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.11</version>
</plugin>
```

### Configurações Recomendadas

**application-prod.yml:**
```yaml
spring:
  jpa:
    show-sql: false
    open-in-view: false

logging:
  level:
    root: WARN
    br.gov.pe.sjdh: INFO

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics
```

---

✅ **Status atual:** _Em desenvolvimento_
📅 **Última revisão:** _21/10/2025_
🎯 **Próximas ações:** Focar nos itens críticos antes do primeiro deploy

---
