# Guincho Save Lives — API REST (Backend)

API REST desenvolvida em **Java com Spring Boot** para o gerenciamento de guincheiros, clientes, solicitações de socorro veicular, consulta de endereços (ViaCEP) e autenticação de funcionários, integrada ao banco de dados **Oracle**.

---

## Integrantes do Grupo — Turma: 1TDSPY

* **Victor Hugo Velasco Guzman** — RM: `573173`
* **Enzo Macedo** — RM: `569427`
* **Bryan Germano** — RM: `569716`
* **Raphael Ferreira Tavares** — RM: `569767`
* **Vinicios Alves dos Santos** — RM: `573002`

---

## Links da Entrega

* **URL da API em Produção (Render):** `https://guincho-save-lives-backend.onrender.com`
* **Vídeo de Demonstração:** `https://www.youtube.com/watch?v=7w1gaE-_GOI&feature=youtu.be`

---

## Credenciais de Acesso (Banco Oracle & API)

Para testes locais e avaliação da conexão com o banco de dados **Oracle FIAP**:

* **JDBC URL (`DB_URL`):** `jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL`
* **Usuário Oracle (`DB_USER`):** `rm573173`
* **Senha Oracle (`DB_PASSWORD`):** `[250499]`
* **PIN de Funcionário (`EMPLOYEE_PIN`):** `12345678`

---

## Tecnologias e Arquitetura (`br.com.fiap.guincho`)

* **Linguagem & Framework:** Java 17+ / Spring Boot (`spring-boot-starter-web`)
* **Gerenciador de Dependências e Build:** Maven (`pom.xml`)
* **Banco de Dados:** Oracle Database (JDBC nativo via `ConnectionFactory` e padrão DAO)
* **Containerização & Deploy:** Docker (`Dockerfile` multi-stage hospedado no Render)
* **Modelagem UML & Dados:** Astah UML (`Class Diagram0.asta`) e Oracle SQL Developer Data Modeler (`guincho_ddl.sql`)

### Estrutura de Pacotes
* **`config`**: Configuração global de CORS (`CorsConfig.java`) para integração com o Front-end.
* **`controller`**: Controladores REST responsáveis por expor os endpoints e retornar os códigos HTTP adequados via `ResponseEntity` (`ClienteController`, `FuncionarioController`, `GuincheiroController`, `SolicitacaoController`).
* **`service`**: Camada de regras de negócio, validações de domínio e controle de sessão com tokens (`ClienteService`, `FuncionarioService`, `GuincheiroService`, `SolicitacaoService`).
* **`dominio`**: Entidades de negócio, modelos de chamado, interfaces de repositório e serviços de domínio (`Cliente`, `Guincheiro`, `Solicitacao`, `Endereco`, `ChamadoVeiculo`, `ChamadoPesado`, `CalculadoraDeTarifas`, `Correio`, `Mensageiro`, `GeradorDeRelatorio`, `RepositorioClientes`, `RepositorioGuincheiros`, `RepositorioSolicitacoes`).
* **`infra`**: Implementações de persistência JDBC/DAO (`ConnectionFactory`, `ClienteDAO`, `GuincheiroDAO`, `SolicitacaoDAO`), integração com ViaCEP (`CorreioImpl`), mensageria (`MensageiroImpl`) e geração de relatórios em arquivo (`RelacaoDeChamadosEmArquivo`).
* **`exception`**: Exceções customizadas para controle de fluxo (`RecursoNaoEncontradoException`, `RegraDeNegocioException`).

---

## Modelagem do Banco de Dados (Oracle)

O esquema relacional foi modelado no **Oracle SQL Developer Data Modeler** (`guincho_ddl.sql`, `MER_Logico.png` e `Modelo_Relacional.png`) e contempla 6 tabelas com chaves primárias, chaves estrangeiras, restrições de unicidade/checagem e auto-incremento via *Sequences* e *Triggers*:

* **`TB_CLIENTE`**: Cadastro de clientes (`id_cliente` PK, `nome`, `cpf` UNIQUE, `email`, `telefone`).
* **`TB_GUINCHEIRO`**: Cadastro de prestadores de guincho (`id_guincheiro` PK, `nome`, `regiao`, `cidade`, `tipo_veiculo`, `taxa_base`).
* **`TB_VEICULO`**: Veículos vinculados aos clientes (`id_veiculo` PK, `cliente_id` FK, `placa`, `modelo`, `ano`).
* **`TB_SOLICITACAO`**: Chamados de socorro veicular (`id_solicitacao` PK, `cliente_id` FK opcional, `nome_cliente`, `origem`, `destino`, `veiculo`, `problema`, `cep`).
* **`TB_PAGAMENTO`**: Pagamentos vinculados às solicitações (`id_pagamento` PK, `solicitacao_id` FK, `valor`, `metodo`, `data_pagamento`).
* **`TB_AVALIACAO`**: Avaliações de atendimento com nota de 1 a 5 (`id_avaliacao` PK, `solicitacao_id` FK, `nota`, `comentario`).

---

## Documentação dos Endpoints

**Base URL (Produção):** `https://guincho-save-lives-backend.onrender.com`

### 1. Guincheiros (`/guincheiros`)
| Método | Endpoint Completo | Descrição | Retorno Sucesso |
| :--- | :--- | :--- | :--- |
| `POST` | `https://guincho-save-lives-backend.onrender.com/guincheiros` | Cadastra um novo guincheiro | `201 Created` |
| `GET` | `https://guincho-save-lives-backend.onrender.com/guincheiros` | Lista todos os guincheiros (aceita filtro `?regiao=`) | `200 OK` |
| `GET` | `https://guincho-save-lives-backend.onrender.com/guincheiros/{id}` | Busca um guincheiro específico pelo ID | `200 OK` |
| `PUT` | `https://guincho-save-lives-backend.onrender.com/guincheiros/{id}` | Atualiza os dados de um guincheiro existente | `200 OK` |
| `DELETE` | `https://guincho-save-lives-backend.onrender.com/guincheiros/{id}` | Exclui um guincheiro pelo ID | `204 No Content` |

**Exemplo de Body (`POST` / `PUT /guincheiros`):**
```json
{
  "nome": "João da Silva",
  "telefone": "11988887777",
  "regiao": "Zona Sul",
  "cidade": "São Paulo",
  "preco": 150.00
}