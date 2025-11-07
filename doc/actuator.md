# 1. Listar todos os endpoints do Actuator

```bash
    curl -s http://localhost:3000/actuator | jq

```

# 2. Verificar status geral da aplicação
```bash
    curl -s http://localhost:3000/actuator/health | jq

```

# 3. Verificar status do banco de dados
```bash
    curl -s http://localhost:3000/actuator/health/db | jq
```

# 4. Testar probes
```Bash
  curl -s http://localhost:3000/actuator/health/liveness | jq

```
```Bash
    curl -s http://localhost:3000/actuator/health/readiness | jq
```
# 5. Obter informações gerais
```Bash

    curl -s http://localhost:3000/actuator/info | jq
```

# 6. Listar variáveis de ambiente
```Bash

  curl -s http://localhost:3000/actuator/env | jq
```
# 7. Ver beans do contexto Spring
```Bash

    curl -s http://localhost:3000/actuator/beans | jq
```
# 8. Ver todos os endpoints mapeados (controllers, REST, etc.)
```Bash
    curl -s http://localhost:3000/actuator/mappings | jq
```
# 9. Ver métricas disponíveis
```Bash
  curl -s http://localhost:3000/actuator/metrics | jq
```
# 10. Ver detalhes de uma métrica específica
```Bash
  curl -s http://localhost:3000/actuator/metrics/jvm.memory.used | jq
```
# 11. Ver loggers e níveis de log
```Bash
  curl -s http://localhost:3000/actuator/loggers | jq
```
# 12. Ver status das migrações Flyway
```Bash
    curl -s http://localhost:3000/actuator/flyway | jq
```
# 13. Dump de threads (diagnóstico de travamentos)
```Bash 
    curl -s http://localhost:3000/actuator/threaddump | jq
```
# 14. Dump de heap (gera arquivo binário grande)
```Bash
    curl -O http://localhost:3000/actuator/heapdump
```
# 15. Encerrar aplicação (se habilitado)
```Bash
    curl -X POST http://localhost:3000/actuator/shutdown
```