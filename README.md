Fórum API
Descrição
A Fórum API é uma aplicação Spring Boot que implementa uma API RESTful para um sistema de fórum, permitindo que usuários autenticados criem, listem, atualizem e excluam tópicos. A autenticação é baseada em JSON Web Tokens (JWT), com suporte a perfis de usuário e controle de acesso. A aplicação utiliza Spring Security para gerenciar autenticação e autorização, e JPA/Hibernate para persistência de dados.
Funcionalidades

Autenticação: Login de usuários com email e senha, retornando um token JWT válido por 2 horas.
Gerenciamento de Tópicos: Criação, listagem, detalhamento, atualização e exclusão de tópicos.
Controle de Acesso: Apenas usuários autenticados podem acessar endpoints protegidos (como /posts).
Validação de Dados: Validações de entrada para garantir a integridade dos dados (ex.: email, senha, título do tópico).

Tecnologias Utilizadas

Java 17+
Spring Boot 3.x
Spring Security (autenticação e autorização com JWT)
Spring Data JPA (persistência com Hibernate)
H2 Database (banco de dados em memória para desenvolvimento; pode ser substituído por outro banco)
Maven (gerenciamento de dependências)
Lombok (redução de código boilerplate)
JWT (auth0) (geração e validação de tokens)

Estrutura do Projeto
src/main/java/com/example/demo/
├── controller/              # Controladores REST
│   ├── AutenticacaoController.java  # Endpoint de login
│   └── TopicoController.java        # Endpoints para gerenciamento de tópicos
├── exceptions/security/      # Configurações de segurança
│   ├── SecurityConfigurations.java  # Configuração do Spring Security
│   ├── SecurityFilter.java          # Filtro para validação de tokens JWT
│   └── TokenService.java            # Serviço para geração e validação de tokens
├── model/                   # Entidades do domínio
│   ├── usuario/             # Modelos relacionados a usuários
│   │   ├── DadosAutenticacao.java   # DTO para dados de login
│   │   ├── DadosUsuario.java        # DTO para dados de usuário
│   │   └── Usuario.java             # Entidade do usuário
│   ├── curso/               # Entidades relacionadas a cursos
│   ├── perfil/              # Entidades relacionadas a perfis
│   └── topico/              # Entidades e DTOs para tópicos
├── repository/              # Repositórios JPA
│   ├── UsuarioRepository.java       # Repositório para usuários
│   ├── CursoRepository.java         # Repositório para cursos
│   ├── PerfilRepository.java        # Repositório para perfis
│   └── TopicoRepository.java        # Repositório para tópicos
├── service/                 # Serviços da aplicação
│   └── AutenticacaoService.java     # Serviço de autenticação
└── exceptions/              # Exceções personalizadas
└── ResourceNotFoundException.java

Pré-requisitos

Java 17+ (JDK instalado)
Maven (para gerenciamento de dependências)
Banco de Dados (H2 para desenvolvimento ou outro configurado no application.properties)
Postman ou outro cliente HTTP (para testar os endpoints)

Configuração

Clone o Repositório:
git clone <URL_DO_REPOSITORIO>
cd forum-api


Configure o Banco de Dados:

O projeto usa o banco H2 por padrão. Para usar outro banco (ex.: PostgreSQL, MySQL), edite o arquivo src/main/resources/application.properties:spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update


Para outro banco, substitua pela configuração apropriada (exemplo para PostgreSQL):spring.datasource.url=jdbc:postgresql://localhost:5432/forum
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect




Configure o Segredo JWT:

O segredo para geração de tokens JWT está definido em TokenService. Para maior segurança, configure-o no application.properties:jwt.secret=sua-chave-secreta-aqui


Evite usar o valor padrão (minha-chave-secreta-super-segura-123) em produção.


Compile e Execute a Aplicação:
mvn clean install
mvn spring-boot:run

A aplicação estará disponível em http://localhost:8080.


Endpoints
Autenticação

POST /login
Descrição: Autentica um usuário e retorna um token JWT.
Corpo da Requisição:{
"login": "joao@example.com",
"senha": "suaSenha123!"
}


Resposta de Sucesso (200 OK):{
"token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}


Resposta de Erro (401 Unauthorized):"Credenciais inválidas: ..."





Tópicos

POST /posts

Descrição: Cria um novo tópico (requer autenticação).
Cabeçalho: Authorization: Bearer <token>
Corpo da Requisição:{
"titulo": "Título do Tópico",
"mensagem": "Mensagem do Tópico",
"curso": { "id": 1 },
"autor": { "id": 1 }
}


Resposta de Sucesso: 201 Created
Resposta de Erro: 401 Unauthorized (token inválido), 404 Not Found (curso ou autor não encontrado), 409 Conflict (tópico duplicado)


GET /posts

Descrição: Lista todos os tópicos com paginação (requer autenticação).
Cabeçalho: Authorization: Bearer <token>
Parâmetros: page, size, sort (ex.: /posts?page=0&size=10&sort=dataCriacao,desc)
Resposta de Sucesso (200 OK):{
"content": [
{
"id": 1,
"titulo": "Título do Tópico",
"mensagem": "Mensagem do Tópico",
"dataCriacao": "2025-07-12T09:00:00",
"autor": { "id": 1, "nome": "João" },
"curso": { "id": 1, "nome": "Curso de Java" }
}
],
"pageable": { ... },
"totalElements": 1
}




GET /posts/{id}

Descrição: Detalha um tópico específico (requer autenticação).
Cabeçalho: Authorization: Bearer <token>
Resposta de Sucesso (200 OK): Dados do tópico.
Resposta de Erro: 404 Not Found


PUT /posts/{id}

Descrição: Atualiza um tópico existente (requer autenticação).
Cabeçalho: Authorization: Bearer <token>
Corpo da Requisição:{
"titulo": "Título Atualizado",
"mensagem": "Mensagem Atualizada",
"cursoId": 1
}


Resposta de Sucesso: 200 OK
Resposta de Erro: 404 Not Found


DELETE /posts/{id}

Descrição: Exclui um tópico (requer autenticação).
Cabeçalho: Authorization: Bearer <token>
Resposta de Sucesso: 204 No Content
Resposta de Erro: 404 Not Found



Testando a Aplicação

Autenticação:
Use o Postman para enviar uma requisição POST para http://localhost:8080/login com as credenciais de um usuário registrado.
Copie o token retornado no campo token.


Acessando Endpoints Protegidos:
Adicione o cabeçalho Authorization: Bearer <token> em todas as requisições para /posts.


Exemplo de Requisição com cURL:curl -X POST http://localhost:8080/login \
-H "Content-Type: application/json" \
-d '{"login": "joao@example.com", "senha": "suaSenha123!"}'

curl -X GET http://localhost:8080/posts \
-H "Authorization: Bearer <seu_token>"



Resolução de Problemas

Erro 401 Unauthorized: Verifique se o token JWT está sendo enviado corretamente no cabeçalho Authorization. Confirme se o token não expirou (validade de 2 horas).
Erro 403 Forbidden: Certifique-se de que o usuário autenticado tem os perfis necessários para acessar o recurso.
LazyInitializationException: Se ocorrer, verifique se o método findUsuarioComPerfis está sendo usado no SecurityFilter para carregar os perfis do usuário.
Logs de Depuração: Ative logs detalhados no application.properties:logging.level.org.springframework.security=DEBUG
logging.level.org.hibernate=DEBUG



Contribuição

Faça um fork do repositório.
Crie uma branch para sua feature: git checkout -b minha-feature.
Commit suas alterações: git commit -m 'Adiciona minha feature'.
Envie para o repositório remoto: git push origin minha-feature.
Abra um Pull Request.

Licença
Este projeto está licenciado sob a MIT License.