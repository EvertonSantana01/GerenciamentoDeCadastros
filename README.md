# Sistema de Gerenciamento de Cadastros e Operações

Este é um sistema completo desenvolvido em Java utilizando o paradigma de Programação Orientada a Objetos (POO), combinado com Bootstrap para a interface web, com o objetivo de oferecer uma solução eficiente para gerenciamento de cadastros, operações de vendas e controle de produtos. O projeto aplica boas práticas de desenvolvimento, incluindo organização modular, validação de dados e lógica de programação estruturada.

## 📋 Funcionalidades Principais

### Cadastros

O sistema permite realizar operações completas de cadastro, edição e exclusão para:

- Estados
- Cidades
- Funcionários
- Clientes
- Fornecedores
- Produtos

### Edição e Exclusão

Os registros podem ser alterados ou removidos diretamente pela interface, com validação para evitar exclusões acidentais e manter a integridade dos dados.

### Listagens

Exibição detalhada de Estados, Cidades, Funcionários, Clientes, Fornecedores e Produtos, com filtros para facilitar a busca e organização.

### Operações de Negócio

- **Efetuar Vendas**: Fluxo completo de vendas, desde a seleção de produtos até a finalização e geração de um comprovante.
- **Efetuar Entradas de Produtos**: Controle de estoque atualizado automaticamente com base nas entradas de novos produtos.
- **Listar Vendas**: Histórico detalhado de vendas, incluindo data, cliente, produtos vendidos e valores.
- **Listar Entradas de Produtos**: Histórico das entradas de estoque.

## 🛠️ Tecnologias Utilizadas

### Backend

- **Java 17**: Linguagem principal do sistema.
- **Spring Boot 3.4.0**: Framework para simplificar o desenvolvimento backend.
- **JPA (Java Persistence API)**: Para mapeamento de dados relacionais.
- **PostgreSQL**: Banco de dados relacional utilizado.
- **Thymeleaf**: Motor de templates para renderização dinâmica.

### Frontend

- **Bootstrap 5**: Design responsivo e moderno.

### Outras Ferramentas

- **Maven**: Gerenciador de dependências e build.
- **JUnit 5 e Mockito**: Testes unitários e de integração.

## 📖 Como Configurar e Executar o Projeto

### Pré-Requisitos

- Java 17 ou superior instalado.
- Maven configurado na máquina.
- PostgreSQL instalado e rodando.

### Passos para Configuração

1. Clone o repositório:

    ```bash
    git clone https://github.com/SeuUsuario/SeuRepositorio.git
    ```

2. Navegue até o diretório do projeto:

    ```bash
    cd sistema-gerenciamento
    ```

3. Configure o banco de dados:

    O sistema utiliza uma configuração personalizada para o banco de dados no arquivo `ConfiguracaoBancoDeDados.java`. Dentro dessa classe, você pode definir as informações do banco de dados, como URL, usuário e senha, da seguinte forma:

    ```java
    @Configuration
    public class ConfiguracaoBancoDeDados {
        @Bean
        public DataSource dataSource() {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("org.postgresql.Driver");
            dataSource.setUrl("jdbc:postgresql://localhost:5432/nome_do_banco"); // URL do banco
            dataSource.setUsername("usuario"); // Substitua pelo seu usuário
            dataSource.setPassword("senha"); // Substitua pela sua senha
            return dataSource;
        }

        @Bean
        public JpaVendorAdapter jpaVendorAdapter() {
            HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
            adapter.setDatabase(Database.POSTGRESQL);
            adapter.setShowSql(true);
            adapter.setGenerateDdl(true);
            adapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQLDialect");
            adapter.setPrepareConnection(true);
            return adapter;
        }
    }
    ```

    Substitua `nome_do_banco`, `usuario` e `senha` pelas informações do seu banco de dados PostgreSQL.

4. Compile e execute o projeto:

    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

5. Acesse a aplicação em: [http://localhost:8080](http://localhost:8080).

## 📽️ Demonstração em Vídeo

[![](https://img.youtube.com/vi/eN6YZk3tL_s/0.jpg)](https://www.youtube.com/watch?v=eN6YZk3tL_s)
