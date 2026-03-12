# Projeto Final - Catálogo de Filmes (Etapa 7 - ORM)

## 🎯 Objetivo
Este projeto consiste na integração do banco de dados relacional (PostgreSQL) com uma aplicação Java utilizando mapeamento objeto-relacional (ORM). O sistema realiza operações de CRUD e consultas avançadas sem a necessidade de escrever SQL manualmente, utilizando o Hibernate/Spring Data JPA.

## 🛠️ Tecnologias Utilizadas
* **Linguagem:** Java
* **Framework:** Spring Boot
* **ORM:** Spring Data JPA / Hibernate
* **Banco de Dados:** PostgreSQL
* **Ferramentas Adicionais:** Maven e Lombok

## ⚙️ Como configurar o banco de dados
O banco de dados deve estar rodando localmente. A conexão é configurada no arquivo src/main/resources/application.properties.

Para testar no ambiente, altere a senha do PostgreSQL nas linhas abaixo:
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=SUA_SENHA_AQUI
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=none

## ▶️ Como executar (Passo a Passo)
1. Abra o seu gerenciador de banco de dados (ex: DBeaver).
2. Execute o arquivo de script script_banco.sql (enviado junto a este repositório) para criar o esquema, as tabelas e popular os dados iniciais.
3. Importe o projeto Java para a sua IDE de preferência (IntelliJ IDEA, Eclipse ou VS Code).
4. Aguarde o Maven atualizar as dependências.
5. Execute a classe principal FilmesApplication.java.
6. Acompanhe a saída no terminal/console da IDE, onde todas as operações de banco de dados serão exibidas automaticamente.

## 🔍 Operações Realizadas no Teste Automático
Ao rodar a aplicação, o sistema executa sequencialmente via ORM:
1. **CREATE:** Inserção de 3 novos registros na entidade Midia.
2. **READ:** Listagem de todos os registros da tabela.
3. **UPDATE:** Atualização de um atributo de um registro existente.
4. **DELETE:** Remoção segura de um registro.
5. **Consultas com Relacionamento e Filtros (Parte 4):**
    * Busca de mídias filtrando por gênero e ordenando de forma decrescente pelo ano de lançamento.
    * Busca de todas as interações (JOIN) vinculadas a um usuário específico através do nome.
    * Busca de usuários (JOIN) que interagiram com mídias de um gênero específico.