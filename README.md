# CamadaEnlaceDados-Redes-I

Repositório para agregar o projeto de Simulação do Funcionamento da Camada de Enlace de Dados em uma Rede de Computadores. Esse projeto foi desenvolvido como parte da disciplina de Redes de Computadores I.

## Estrutura do Repositório

### CamadaEnlaceDados-Redes-I
- **assets**: Imagens para a aplicação
- **controller**: Classes que controlam a aplicação
- **model**: Classes de modelo do projeto
- **util**: Classe utilitária
- **view**: Arquivos de marcação e estilização
- **Principal.java**: Código principal da aplicação

## Funcionalidades

- **Camada Física**: A camada física é responsável por receber os caracteres de dados enquadrados da Camada de Enlace de Dados e, então, empacotar os caracteres em um fluxo de bits codificado e passá-lo para o Meio de Comunicação.
- **Meio de Comunicação**: O meio de comunicação é responsável por receber um fluxo de bits de um ponto A, transmití-lo para o ponto B, e gerar erros que corrompem a integridade de um bit do fluxo.
- **Camada de Enlace de Dados**: A camada de enlace de dados é responsável por receber os caracteres e enquadrá-los ou desenquadrá-los, conforme o método escolhido.
- **Camada de Aplicação**: A camada de aplicação é responsável por receber a mensagem da aplicação e fragmentá-la em caracteres, para que as camadas subsequentes possam continuar a transmissão.
