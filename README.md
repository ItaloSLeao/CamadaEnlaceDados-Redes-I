## 🌐📡 ErrorLace de Dados - Simulação da Camada de Enlace

Repositório para agregar o projeto de Simulação do Funcionamento da Camada de Enlace de Dados em uma Rede de Computadores, desenvolvido em Java com interface gráfica em JavaFX.

O projeto tem como objetivo demonstrar, de forma prática, os processos envolvidos na transmissão de dados, abrangendo as camadas de Aplicação, Enlace de Dados e Física do modelo OSI, incluindo diferentes técnicas de codificação, enquadramento e simulação de erros.

## 🧩 Funcionalidades

- Simulação completa do fluxo de transmissão, desde a mensagem de texto no transmissor até a sua exibição no receptor.

- Controle de velocidade da transmissão para melhor visualização do processo.

- Seleção de métodos de Enquadramento da **Camada de Enlace de Dados**:

  - Contagem de Caracteres
  
  - Inserção de Bytes
  
  - Inserção de Bits
  
  - Violação da Camada Física

- Seleção de algoritmos de Codificação da **Camada Física**:

  - Codificação Binária
  
  - Codificação Manchester
  
  - Codificação Manchester Diferencial

- Simulação de Erros no **Meio de Comunicação** com probabilidade configurável.

- Interface gráfica desenvolvida em JavaFX.

## 🛠️ Tecnologias Utilizadas

- **Java**

- **JavaFX**

- **Threads**

- Padrão de arquitetura **MVC (Model-View-Controller)**

## 📂 Estrutura do Projeto

```

├── assets/         # Imagens e ícones para a interface gráfica
├── controller/     # Classe controladora para a lógica da interface
├── model/          # Classes que modelam as camadas da rede (Aplicação, Enlace, Física)
├── util/           # Classes utilitárias, como conversor de bits para string
├── view/           # Arquivos FXML para a interface e CSS para estilização
└── Principal.java  # Classe principal que executa a aplicação

```

## 🖥️ Execução

Clone este repositório:

```
git clone https://github.com/ItaloSLeao/CamadaEnlaceDados-Redes-I.git
```

Abra o projeto em um Terminal de Comandos. Compile e execute a classe Principal.java usando os comandos:

```java
javac Principal.java
java Principal
```

Aprecie a simulação!

## 🎓 Contexto Acadêmico

Este projeto foi desenvolvido como parte da disciplina de Redes de Computadores I, com o objetivo de aplicar e ilustrar de forma prática os conceitos teóricos das camadas inferiores do modelo OSI, focando no funcionamento da Camada de Enlace de Dados e da subcamada LLC.

## 📄 Licença

Este projeto é de uso acadêmico e está sob a licença MIT.
