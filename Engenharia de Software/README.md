# 🎤 ROTEIRO DE APRESENTAÇÃO — SENNABANK

Projeto acadêmico de Engenharia de Software  
UNISENAI — Campus São Caetano do Sul

---

# 📌 OBJETIVO DA APRESENTAÇÃO

Este roteiro foi criado para orientar todos os integrantes durante a apresentação do projeto SennaBank.

Objetivos da apresentação:
- Explicar o sistema;
- Demonstrar domínio técnico;
- Mostrar organização do projeto;
- Apresentar arquitetura e requisitos;
- Defender decisões técnicas;
- Simular uma apresentação profissional de software.

Tempo ideal:
- 10 a 15 minutos.

---

# 🖥️ ESTRUTURA DOS SLIDES

| Slide | Tema |
|---|---|
| 1 | Capa |
| 2 | Objetivo do Projeto |
| 3 | Equipe |
| 4 | Arquitetura MVC |
| 5 | Tecnologias |
| 6 | Banco de Dados |
| 7 | Engenharia de Requisitos |
| 8 | Requisitos Funcionais |
| 9 | Requisitos Não Funcionais |
| 10 | Interface e Design |
| 11 | Prototipação |
| 12 | Git/GitHub |
| 13 | Considerações Finais |
| 14 | Perguntas |

---

# 🎬 SLIDE 1 — CAPA

## O que mostrar
- Nome do projeto;
- Nome da faculdade;
- Disciplina;
- Integrantes;
- Data.

## O que falar

> “Boa tarde pessoal.  
Hoje vamos apresentar o projeto SennaBank, desenvolvido para a disciplina de Engenharia de Software.”

> “O SennaBank é uma plataforma bancária digital voltada ao setor público, desenvolvida com foco em segurança, acessibilidade e organização financeira.”

> “Durante a apresentação vamos mostrar:
- objetivo do sistema,
- arquitetura,
- tecnologias,
- requisitos,
- modelagem do banco,
- e processo de desenvolvimento.”

---

# 🎯 SLIDE 2 — OBJETIVO DO PROJETO

## O que mostrar
Resumo do objetivo do sistema.

## O que falar

> “O principal objetivo do projeto foi desenvolver uma plataforma web bancária capaz de simular serviços financeiros digitais.”

> “O sistema foi pensado para oferecer funcionalidades como:
- cadastro de usuários,
- autenticação,
- gerenciamento financeiro,
- transações,
- investimentos,
- empréstimos.”

> “Além disso, o projeto foi estruturado aplicando conceitos reais de Engenharia de Software.”

---

# 👥 SLIDE 3 — EQUIPE

## O que mostrar
Estrutura da equipe.

## O que falar

> “A equipe foi organizada utilizando uma estrutura semelhante à utilizada em empresas reais de tecnologia.”

## Explicar rapidamente

### Product Manager
Responsável por organização geral do produto.

### Product Owner
Responsável pelos requisitos e funcionalidades.

### Tech Lead
Responsável pela arquitetura técnica e liderança de desenvolvimento.

### Front-End
Responsável pela interface e experiência visual.

### Back-End
Responsável pelas regras de negócio e integração com banco de dados.

## Finalizar falando

> “Essa divisão ajudou a organizar responsabilidades e melhorar o fluxo de desenvolvimento.”

---

# 🏗️ SLIDE 4 — ARQUITETURA MVC

## O que mostrar
Diagrama MVC.

## O que falar

> “O sistema foi desenvolvido utilizando arquitetura Cliente-Servidor seguindo o padrão MVC.”

---

## Explicação MVC

| Camada | Responsabilidade |
|---|---|
| View | Interface visual |
| Controller | Regras de negócio |
| Model | Banco de dados |

---

## Explicação técnica

### View
- HTML;
- CSS;
- JavaScript.

### Controller
- Java;
- processamento de regras.

### Model
- MySQL;
- persistência de dados.

---

## Mostrar fluxo

```text
Usuário
   ↓
Front-End
   ↓
Back-End
   ↓
Banco de Dados
```

---

## Finalizar falando

> “Essa separação melhora:
- organização,
- manutenção,
- escalabilidade,
- reutilização de código.”

---

# 🛠️ SLIDE 5 — TECNOLOGIAS UTILIZADAS

## O que mostrar

### Front-End
- HTML5
- CSS3
- JavaScript

### Back-End
- Java

### Banco
- MySQL

### Ferramentas
- Git
- GitHub
- VS Code
- IntelliJ IDEA

---

## O que falar

> “No Front-End utilizamos HTML, CSS e JavaScript.”

> “No Back-End utilizamos Java.”

> “O banco de dados foi desenvolvido em MySQL.”

> “Também utilizamos Git e GitHub para controle de versão.”

---

# 🗄️ SLIDE 6 — BANCO DE DADOS

## O que mostrar
MER do banco.

---

## O que falar

> “O banco de dados foi modelado seguindo a terceira forma normal.”

---

## Explicar objetivo da 3FN

> “O objetivo foi:
- evitar redundância,
- melhorar integridade,
- organizar relacionamentos.”

---

## Explicar tabelas principais

- usuario
- conta
- transacao
- cartao
- investimento
- emprestimo

---

## Explicar relacionamentos

> “Os relacionamentos foram implementados utilizando FOREIGN KEY.”

---

## Finalizar falando

> “Ao todo foram criadas 13 tabelas relacionais.”

---

# 📋 SLIDE 7 — ENGENHARIA DE REQUISITOS

## O que mostrar

- Benchmarking;
- User Stories;
- Reuniões;
- Prototipação;
- Diagramas.

---

## O que falar

> “Os requisitos foram levantados utilizando técnicas de Engenharia de Software.”

---

## Explicar métodos usados

### Reuniões
Definição de funcionalidades.

### Benchmarking
Análise de:
- XP Investimentos;
- C6 Bank.

### User Stories
Descrição das funcionalidades pela visão do usuário.

### Prototipação
Validação visual antes da implementação.

---

## Finalizar falando

> “Isso ajudou a definir funcionalidades mais organizadas e próximas do mercado real.”

---

# ⚙️ SLIDE 8 — REQUISITOS FUNCIONAIS

## O que mostrar
Lista resumida dos RFs.

---

## O que falar

> “Entre os principais requisitos funcionais estão:”

- Cadastro;
- Login;
- Recuperação de senha;
- Transações;
- Investimentos;
- Cartões;
- Empréstimos.

---

## Explicar os mais importantes

### Cadastro
> “O sistema valida CPF e idade mínima.”

### Login
> “As mensagens de erro não revelam qual campo está incorreto.”

### Recuperação de senha
> “Fluxo dividido em três etapas para maior segurança.”

---

## Finalizar falando

> “Esses requisitos representam as principais funcionalidades do sistema.”

---

# 🔒 SLIDE 9 — REQUISITOS NÃO FUNCIONAIS

## O que mostrar

- Responsividade;
- Segurança;
- Performance;
- Compatibilidade;
- Acessibilidade.

---

## O que falar

> “Também definimos requisitos não funcionais importantes.”

---

## Explicar

### Responsividade
Sistema adaptável para:
- desktop;
- tablet;
- mobile.

### Segurança
- validações;
- proteção de autenticação;
- organização de acesso.

### Performance
- interface leve;
- carregamento otimizado.

---

# 🎨 SLIDE 10 — INTERFACE E DESIGN

## O que mostrar
Prints do sistema.

---

## O que falar

> “A identidade visual foi inspirada no Ayrton Senna e na bandeira do Brasil.”

---

## Explicar design

### Cores
- preto;
- verde;
- cinza escuro;
- branco.

### Interface
- moderna;
- responsiva;
- minimalista.

### Recursos visuais
- hover effects;
- animações suaves;
- header sticky;
- backdrop blur.

---

## Finalizar falando

> “O objetivo foi criar uma experiência moderna semelhante a bancos digitais reais.”

---

# 🧪 SLIDE 11 — PROTOTIPAÇÃO

## O que mostrar
Páginas desenvolvidas.

---

## O que falar

> “Ao invés de criar apenas telas estáticas, optamos por uma prototipação funcional.”

---

## Explicar

### Páginas criadas
- index;
- produtos;
- tarifas;
- imprensa;
- privacidade;
- termos;
- recuperação de senha.

---

## Recursos

### Header compartilhado
Carregado dinamicamente via JavaScript.

### Drawers
Login e cadastro reutilizáveis.

---

## Finalizar falando

> “Isso permitiu validar comportamento real da aplicação.”

---

# 🔄 SLIDE 12 — GIT E GITHUB

## O que mostrar
Print do repositório.

---

## O que falar

> “Todo o projeto utilizou Git e GitHub para versionamento.”

---

## Explicar benefícios

- histórico de alterações;
- trabalho simultâneo;
- segurança do código;
- organização;
- rastreamento de mudanças.

---

# 🚀 SLIDE 13 — CONSIDERAÇÕES FINAIS

## O que mostrar
Resumo do projeto.

---

## O que falar

> “O SennaBank foi desenvolvido como projeto acadêmico simulando um ambiente real de desenvolvimento de software.”

---

## Explicar

> “Apesar de não possuir integração bancária oficial, o sistema foi estruturado utilizando conceitos reais de:
- arquitetura,
- banco de dados,
- requisitos,
- versionamento,
- engenharia de software.”

---

## Finalizar

> “O projeto também serviu como experiência prática de desenvolvimento em equipe.”

---

# ❓ SLIDE 14 — PERGUNTAS

## O que falar

> “Obrigado pela atenção.”

> “Agora estamos disponíveis para perguntas.”

---

# ⚠️ DICAS IMPORTANTES

## O que aumenta muito a qualidade da apresentação

- Não ler os slides;
- Explicar tecnicamente;
- Demonstrar domínio;
- Explicar arquitetura;
- Explicar banco de dados;
- Mostrar lógica do sistema.

---

# 🚫 ERROS QUE DEVEM SER EVITADOS

- Ler documento inteiro;
- Explicar superficialmente;
- Não explicar MVC;
- Não explicar banco de dados;
- Não justificar decisões técnicas;
- Falar apenas “fizemos um site”.

---

# ✅ OBJETIVO FINAL DA APRESENTAÇÃO

Transmitir:
- organização;
- domínio técnico;
- aplicação de Engenharia de Software;
- visão profissional de desenvolvimento.

---

# 👨‍💻 SENNABANK

Projeto acadêmico — Engenharia de Software  
UNISENAI — Campus São Caetano do Sul
