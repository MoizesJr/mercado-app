# 📝 Anotações do Projeto - MERCADO-APP

## 🅰️ Angular (Frontend)

### Instalação e Criação:

**Instalar CLI ANGULAR:** `npm install -g @angular/cli`
**Criar projeto com Standalone Components:**
- `ng new frontend`: Criei o projeto chamado frontend.
- `--standalone`: Usei o novo padrão do Angular (sem AppModule), mais moderno e leve.
- `--routing`: Ativei o sistema de rotas para navegar entre páginas (Ex: lista -> cadastro).
- `--style=css`: Escolhi o CSS tradicional para estilização.
- **SSR/SSG:** Respondi "No", pois é um sistema interno e não precisa de SEO de buscadores.

### Arquitetura do Frontend:

- **AuthGuard:** Protege as rotas privadas.
- **AuthService:** Gerencia o Token de login.
- **Produto/Estoque Services:** Fazem o CRUD (Create, Read, Update, Delete) com o Spring Boot.
- **Navbar:** Componente global que fica no topo do sistema.


## 🍃 Spring Boot (Backend)

### ⚙️ Tecnologias e Dependências:

* **Spring Web:** Criação de APIs RESTful.
* **Spring Data JPA:** Persistência de dados e comunicação com o Banco.
* **PostgreSQL Driver:** Conector para o banco de dados via Docker.
* **Spring Security:** Proteção das rotas e controle de acesso.
* **OAuth2/Google Login:** Integração para autenticação social.
* **Lombok:** Redução de código boilerplate (Getters/Setters/Constructors).
* **Validation:** Validação de regras nos dados de entrada (Bean Validation).

### 🏗️ Arquitetura do Pacote (`com.mercado.api`)

| **Model** | Classes que representam as tabelas no Banco de Dados. |
| **DTO** | *Data Transfer Objects* para tráfego seguro de dados entre API e Cliente. |
| **Repository** | Interfaces que executam comandos SQL (abstração via JPA). |
| **Service** | Camada de inteligência onde residem as **Regras de Negócio**. |
| **Controller** | Pontos de entrada (Endpoints) que recebem as requisições HTTP. |
| **Infra** | Configurações globais: Segurança, Filtros, Erros e Tokens. |

### 🏷️ Glossário de Anotações Spring/Lombok

- `@Entity`: Diz ao Spring que esta classe é uma tabela do banco.
- `@Data`: Atalho do Lombok para não precisar escrever Getters/Setters.
- `@RequestBody`: Avisa que os dados estão vindo no "corpo" da requisição (JSON).
- `@Valid`: Manda o Spring verificar as regras (como `@NotBlank`) no DTO.
- `@Transactional`: (Sugestão para o futuro) Garante que se algo der errado no banco, nada seja salvo pela metade.

### 📋 Funcionalidades Implementadas: Produto

#### **1. Entidade (Model)**
- **Classe:** `Produto` mapeada para a tabela `produtos`.
- **Destaque:** Uso de `BigDecimal` para valores monetários (evita erros de precisão decimal) e `GenerationType.IDENTITY` para o banco de dados gerenciar o ID.

#### **2. DTO (Data Transfer Object)**
- **Classe:** `ProdutoRequestDTO` (Java Record).
- **Vantagem:** Imutabilidade e código limpo.
- **Validações:**
  - `@NotBlank`: Descrição obrigatória.
  - `@PositiveOrZero`: Impede preços negativos.
  - `@Valid`: Ativado no Controller para garantir que o Spring barre dados inválidos antes de chegar no Service.

#### **3. Service (Regras de Negócio)**
- Implementada a lógica de conversão do DTO para a Entidade antes de salvar.
- Utiliza **Injeção de Dependência por Construtor**, que é a prática recomendada pelo Spring para facilitar testes unitários.

#### **4. Controller (API)**
- **Endpoint:** `POST /api/produtos`
- **Status HTTP:** Retorna `201 Created` após o sucesso.
- **CORS:** Liberado para integração com o Frontend Angular.

#### **5. Configurações (`application.properties`)**
- Conexão configurada para o banco Postgres no Docker.
- `ddl-auto=update`: O Hibernate cria/atualiza as tabelas automaticamente baseado nas classes Java.
- `show-sql=true`: Permite ver no console os comandos SQL que o Spring está executando.

#### **6. Configuração Dinâmica de Ambiente:**
- O arquivo `application.properties` utiliza variáveis de ambiente com valores padrão (`${VAR:default}`). Isso permite que o projeto rode nativamente no VS Code (apontando para localhost) ou dentro do Docker (apontando para o serviço db) sem alteração manual de código.


## 🐳 Infraestrutura (Docker)

O projeto utiliza **Docker Compose** para orquestrar o banco de dados e a ferramenta de gerenciamento, garantindo que o ambiente seja o mesmo em qualquer máquina.

### 🛠️ Serviços Configurados

|     Serviço    |        Imagem      | Porta Local | Descrição |

| **mercado-api** | `Custom (Dockerfile)` | `8080` | Container da aplicação Spring Boot.. |
| **mercado-db** | `postgres:15-alpine` | `5432` | Banco de dados PostgreSQL (versão leve). |
| **mercado-pgadmin** | `dpage/pgadmin4` | `5050` | Interface web para gerenciar tabelas e dados. |

### 🔑 Credenciais de Acesso

**Banco de Dados (PostgreSQL):**
- **Usuário:** `user_mercado`
- **Senha:** `password_mercado`
- **Database:** `mercado_db`

**Interface de Gerenciamento (pgAdmin):**
- **URL:** [http://localhost:5050](http://localhost:5050)
- **Login:** `admin@admin.com`
- **Senha:** `admin`

### 🚀 Comandos Rápidos

|              Comando              |    Ação                                      |

| `docker-compose up -d`            | **Liga** todos os serviços em segundo plano. |
| `docker-compose ps`               | **Lista** os containers e verifica se estão rodando. |
| `docker-compose down`             | **Desliga** e remove os containers/processos. |
| `docker-compose logs -f backend`  | **Mostra a saída do console (o log do Spring) do seu backend. |
| `docker-compose restart backend`  | **Reinicia apenas o container do Java sem mexer no banco de dados.. |
| `docker system prune`             | **Remove imag e contai.. parados. Útil quando o computador s/espaço. |

> **Nota:** O serviço `pgadmin` possui uma dependência (`depends_on`) do serviço `db`, garantindo que o banco de dados esteja pronto antes da interface subir.

## 🌳 Estratégia de Branches (Git Flow)

No desenvolvimento do **Mercado App**, utilizo branches para separar as tarefas:

- **main**: Código estável e pronto para rodar.
- **feat/**: Novas funcionalidades (Ex: `feat/backend-produtos`).
- **fix/**: Correção de bugs.
- **docs/**: Melhorias na documentação.

### ✅ Checklist antes de dar Merge na Main:
1. O código compila sem erros?
2. O endpoint de cadastro retorna `201 Created`?
3. O `.gitignore` está ignorando a pasta `target`?