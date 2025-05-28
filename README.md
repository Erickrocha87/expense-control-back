# 💸 Expenditure Control API

Sistema backend em Java com Spring Boot para controle de gastos, autenticação de usuários, envio de notificações e gerenciamento de perfil. Este projeto visa oferecer uma API REST robusta e segura para aplicações de controle financeiro pessoal ou corporativo.

---

## 🚀 Tecnologias

- **Java 21**
- **Spring Boot 3.4**
- **Spring Security com JWT**
- **Spring Validation**
- **Swagger/OpenAPI (via springdoc-openapi)**
- **Spring Data JPA + H2/PostgreSQL**
- **Upload de Imagens (Multipart)**
- **Envio de E-mail (JavaMailSender)**
- **Testes com JUnit e Mockito**

---

## 📦 Módulos

| Módulo | Descrição |
|--------|-----------|
| `auth` | Registro, login, troca e redefinição de senha |
| `notification` | Envio de e-mails com token de recuperação |
| `subscription` | Cadastro, atualização, exclusão e listagem de assinaturas |
| `user` | Gerenciamento do perfil do usuário e upload de imagem |

---

## 📄 Documentação da API

A documentação da API está disponível automaticamente via Swagger:

👉 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## 📥 Instalação local

```bash
git clone https://github.com/seu-usuario/expenditure-control.git
cd expenditure-control
./mvnw spring-boot:run

## 🔐 Segurança

- Autenticação via **JWT**
- Troca de senha com **token via e-mail**
- Controle de acesso com **permissões por rota**

---

## 📸 Upload de Imagem

- Upload multipart com **validação de tipo**
- Armazenamento local de imagens *(pode ser facilmente adaptado para AWS S3 ou outra solução)*

---

## 📧 Recuperação de Senha

- Envia token de redefinição para o e-mail informado
- **Token válido por 10 minutos**
- Redefinição feita com token + nova senha

---

## 🧪 Testes

```bash
./mvnw test

📌 Requisitos

Java 21
Maven 3.8+
Docker (Opcional)

👨‍💻 Autor
Desenvolvido por Erick Rocha

🔗 https://www.linkedin.com/in/erick-rocha-saggiorato-b7861a269/
📫 ericksaggi31@gmail.com