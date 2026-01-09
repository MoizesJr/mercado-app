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


## 🐳 Infraestrutura (Docker)

O projeto utiliza **Docker Compose** para orquestrar o banco de dados e a ferramenta de gerenciamento, garantindo que o ambiente seja o mesmo em qualquer máquina.

### 🛠️ Serviços Configurados

|     Serviço    |        Imagem      | Porta Local | Descrição |

| **mercado-api** | `mercado-app-backend` | `8080` | Container da aplicação Spring Boot. |
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

|              Comando           |    Ação                                                |

| `docker-compose up -d --build` | **Roda** tudo em segundo plano (libera o terminal).    |
| `docker-compose up -d`         | **Liga** todos os serviços em segundo plano.           |
| `docker-compose ps`            | **Lista** os containers e verifica se estão rodando.   |
| `docker-compose down`          | **Desliga** e remove os containers/processos.          |
| `docker-compose stop`          | **Para** os containers em segundo plano sem apagá-los. |
| `docker-compose restart`       | **Reinicia** os containers.                            |

> **Nota:** O serviço `pgadmin` possui uma dependência (`depends_on`) do serviço `db`, garantindo que o banco de dados esteja pronto antes da interface subir.

## 🌳 Estratégia de Branches (Git Flow)

No desenvolvimento do **Mercado-App**, utilizo branches para separar as tarefas:

- **main**: Código estável e pronto para rodar.
- **feat/**: Novas funcionalidades (Ex: `feat/backend-produtos`).
- **fix/**: Correção de bugs.
- **docs/**: Melhorias na documentação.

### 💻 Comandos Essenciais do Git:

- `git checkout -b nome-da-branch`: Cria e entra em uma nova branch.
- `git checkout nome-da-branch`: Alterna entre branches existentes.
- `git status`: Verifica arquivos alterados e o estado do repositório.
- `git add .`: Prepara todas as alterações para o commit.
- `git commit -m "mensagem"`: Salva as alterações localmente com uma descrição.
- `git reset --soft HEAD~1`: Desfaz o último commit mantendo as alterações nos arquivos (volta para o status M)
- `git push origin nome-da-branch`: Envia os commits para o servidor remoto.
- `git pull origin main`: Atualiza sua branch com as últimas mudanças da principal.
- `git merge nome-da-branch`: Une as alterações de uma branch à sua branch atual.
- `git stash`: "Esconde" alterações temporariamente para permitir a troca de branch sem commit.

### ✅ Checklist antes de dar Merge na Main:

1. O código compila sem erros?
2. O endpoint de cadastro retorna `201 Created`?
3. O `.gitignore` está ignorando a pasta `target`?
4. As alterações foram testadas dentro do container Docker?

## 🧪 Testes de API (Postman)

O projeto **Mercado-App** utiliza validações rigorosas via DTO para garantir que nenhum dado incompleto chegue ao banco de dados.

### 🚀 Como realizar o cadastro de produtos:

**Configuração da Requisição:**
- Método: `POST`
- URL: `http://localhost:8080/produtos`
- Segurança: Desativada no código (exclude Security), portanto use No Auth no Postman.
- Corpo (Body): Use o formato `raw` -> `JSON`.

### 🆘 Guia de Debug (Erro 400):

- **Missing Fields:** Se esquecer o campo `quantidadeEstoque`, a API retornará `400 Bad Request` devido à anotação `@NotNull`.
- **Validação de Valores:** O campo `precoVenda` não aceita valores `negativos` (@PositiveOrZero).
- **Nomes de Campos:** O JSON diferencia maiúsculas de minúsculas. Use sempre exatamente como definido no `ProdutoDTO`.

### ✅ Checklist de Sucesso:

[ ] O Postman retornou status 201 Created.
[ ] O banco de dados atribuiu um id ao novo produto.
[ ] O console do Docker mostrou o SQL de INSERT sendo executado.
