package model;

import java.util.ArrayList;
import java.util.Arrays;

import controller.ControllerTelaPrincipal;
import util.Util;

/**
 * Classe que simula o funcionamento de uma camada de enlace de dados de um transmissor,
 * em uma rede de computadores, gerenciando em quadros a mensagem transmitida e tratando
 * erros (este ultimo, ainda nao implementado).
 *
 * @author  Italo de Souza Leao (Matricula: 202410120)
 * @version 30/09/2025 (Ultima alteracao)
 * @since   17/09/2025 (Inicio)
 */
public class CamadaEnlaceDadosTransmissora {

  /**
   * Realiza as funcionalidades da camada de enlace de dados transmissora.
   * <p>
   * Este metodo eh responsavel por aplicar o enquadramento aos dados recebidos.
   * Atualmente, as funcionalidades de controle de erros e controle de fluxo
   * ainda nao foram implementadas.
   *
   * @param quadro     Array de inteiros contendo os codigos ASCII dos caracteres.
   * @param controller Controlador da interface grafica para interacoes com a UI.
   */
  protected static void camadaEnlaceDadosTransmissora(int quadro[], ControllerTelaPrincipal controller) {
    int[] quadroEnquadrado = camadaEnlaceDadosTransmissoraEnquadramento(quadro, controller);

    CamadaFisicaTransmissora.camadaFisicaTransmissora(quadroEnquadrado, controller);
  } //Fim camadaEnlaceDadosTransmissora


  /**
   * Enquadra os quadros de caracteres recebidos da camada de aplicacao, conforme
   * o enquadramento escolhido na GUI.
   *
   * @param quadro     Array de inteiros com os codigos ascii dos caracteres.
   * @param controller Controlador da interface grafica.
   * @return int[] O resultado do enquadramento.
   */
  protected static int[] camadaEnlaceDadosTransmissoraEnquadramento(int quadro[], ControllerTelaPrincipal controller) {

    int tipoEnquadramento = controller.getEnquadramento(); //Captura o enquadramento escolhido na interface grafica
    int[] quadroEnquadrado;

    String enquadramento = "";

    switch (tipoEnquadramento) {
      case 1:
        quadroEnquadrado = camadaEnlaceDadosTransmissoraEnquadramentoContagemDeCaracteres(quadro);
        enquadramento = "Contagem de Caracteres";
        break;
      case 2:
        quadroEnquadrado = camadaEnlaceDadosTransmissoraEnquadramentoInsercaoDeBytes(quadro);
        enquadramento = "Insercao de Bytes";
        break;
      case 3:
        quadroEnquadrado = camadaEnlaceDadosTransmissoraEnquadramentoInsercaoDeBits(quadro);
        enquadramento = "Insercao de Bits";
        break;
      default:
        quadroEnquadrado = camadaEnlaceDadosTransmissoraEnquadramentoViolacaoCamadaFisica(quadro);
        enquadramento = "Violacao da Camada Fisica";
        break;
    } //Fim switch

    System.out.println("\nCAMADA DE ENLACE DE DADOS TRANSMISSORA--------------" + 
    "\nO enquadramento escolhido foi: " + enquadramento + "\n");
    for(int i = 0; i < quadroEnquadrado.length; i++){
      if(!(tipoEnquadramento == 4)){
        controller.adicionarBitsEnquadradosTextArea(Util.bitsParaString(quadroEnquadrado[i]) + "\n");
        System.out.println("quadroEnquadrado[" + i + "] = " + Util.bitsParaString(quadroEnquadrado[i]));
      }
      
      try{Thread.sleep(controller.getVelocidade());} 
      catch (Exception e){e.printStackTrace();}
    } //Fim for

    return quadroEnquadrado;

  } //Fim camadaEnlaceDadosTransmissoraEnquadramento


  /**
   * Realiza o enquadramento pelo metodo de contagem de caracteres.
   * <p>
   * Cada quadro da mensagem tem por cabecalho um caracter de contagem de bytes,
   * que devem segui-lo na transmissao, e que delimita o final do quadro.
   *
   * @param quadro Array de inteiros com os codigos ascii dos caracteres.
   * @return int[] O resultado do enquadramento de contagem de caracteres.
   */
  protected static int[] camadaEnlaceDadosTransmissoraEnquadramentoContagemDeCaracteres(int quadro[]) {

    /* O tamanho do quadro de caracteres eh ajustado para 3. Isso eh justificavel,
     * haja vista que na camada fisica cada inteiro de quadro[] contera 4 bytes (1 byte de controle + 3 de carga util) */
    final int tamanhoQuadro = 3;
    int numCaracteres = quadro.length;

    //Calcula quantos blocos de carga util serao necessarios para enquadrar a mensagem em quadro[]
    int numBlocos = (int) Math.ceil(numCaracteres / (double) tamanhoQuadro);

    //O vetor enquadrado de saida tera os caracteres de carga util + 1 de controle para cada bloco
    int tamanhoSaida = numCaracteres + numBlocos;
    int[] quadroEnquadrado = new int[tamanhoSaida];

    int indiceSaida = 0; //Indice para controlar o armazenamento dos caracteres no novo quadro

    for (int i = 0; i < numCaracteres; i += tamanhoQuadro) { //Faz o enquadramento de 3 em 3
      //Calcula quantos caracteres ha no 'bloco' (quadro de carga util) de 1 a 3
      int charsQuadro = Math.min(tamanhoQuadro, numCaracteres - i);

      quadroEnquadrado[indiceSaida++] = charsQuadro + 1;

      for (int j = 0; j < charsQuadro; j++) { //Adiciona a carga util logo apos o char de contagem
        quadroEnquadrado[indiceSaida++] = quadro[i + j];
      } //Fim do for
    } //Fim do for de quadro[]

    return quadroEnquadrado;

  } //Fim camadaEnlaceDadosTransmissoraEnquadramentoContagemDeCaracteres


  /**
   * Realiza o enquadramento pelo metodo de insercao de bytes (byte stuffing).
   * <p>
   * Cada quadro da mensagem comeca e termina com bytes especiais (flags). Esse
   * metodo insere bytes de escape para diferenciar os dados das flags,
   * delimitando os quadros de forma segura.
   *
   * @param quadro Array de inteiros com os codigos ascii dos caracteres.
   * @return int[] O resultado do enquadramento por insercao de bytes.
   */
  protected static int[] camadaEnlaceDadosTransmissoraEnquadramentoInsercaoDeBytes(int quadro[]) {

    final int TAMANHO_CARGA_UTIL = 5; //Numero fixo maximo de caracteres por quadro de carga util
    final char FLAG = 'i';
    final char ESCAPE = '/';

    //Calcula o pior caso de insercao de flags e escapes e aloca o vetor
    int tamanho = 2 + (2*quadro.length) + (2*quadro.length/TAMANHO_CARGA_UTIL);
    int[] quadroEnquadrado = new int[tamanho];

    int indiceSaida = 0; //Indice para controlar a insercao de bytes
    int contCargaUtil = 0; //Contagem de caracteres de carga no quadro atual

    quadroEnquadrado[indiceSaida++] = FLAG; //FLAG inicial

    for (int i = 0; i < quadro.length; i++) {
      int caractere = quadro[i];

      //Se o caractere for FLAG ou ESCAPE, insere ESCAPE antes
      if (caractere == FLAG || caractere == ESCAPE) {
        quadroEnquadrado[indiceSaida++] = ESCAPE;
      } //Fim if

      //Insere o caractere i da mensagem original
      quadroEnquadrado[indiceSaida++] = caractere;
      contCargaUtil++;

      //Se atingiu o tamanho maximo, fecha o quadro com FLAG
      if (contCargaUtil >= TAMANHO_CARGA_UTIL) {
        quadroEnquadrado[indiceSaida++] = FLAG;
        contCargaUtil = 0;

        //Se houver mais caracteres, insere FLAG inicial para o proximo quadro
        if (i + 1 < quadro.length) {
          quadroEnquadrado[indiceSaida++] = FLAG;
        } //Fim if
      } //Fim if TAMANHO_CARGA_UTIL
    } //Fim for

    //Fecha o ultimo quadro, caso ainda nao esteja fechado
    if (contCargaUtil > 0) {
      quadroEnquadrado[indiceSaida++] = FLAG;
    }

    //Redimensiona o array, eliminando os espacos alocados nao usados
    quadroEnquadrado = Arrays.copyOf(quadroEnquadrado, indiceSaida);

    return quadroEnquadrado;

  } //Fim camadaEnlaceDadosTransmissoraEnquadramentoInsercaoDeBytes


  /**
   * Realiza o enquadramento pelo metodo de insercao de bits (bit stuffing).
   * <p>
   * Cada quadro eh delimitado pela flag (01111110). Para evitar que essa
   * sequencia de bits apareca nos dados, este metodo insere um bit 0 apos
   * toda sequencia de cinco bits 1 consecutivos nos dados.
   *
   * @param quadro Array de inteiros com os codigos ascii dos caracteres.
   * @return int[] O resultado do enquadramento por insercao de bits.
   */
  protected static int[] camadaEnlaceDadosTransmissoraEnquadramentoInsercaoDeBits(int quadro[]) {

    final int FLAG = 0b01111110; //01111110
    final int TAMANHO_CARGA_UTIL = 5; //Maximo de 5 bytes de dados por quadro

    ArrayList<Integer> quadroEnquadradoList = new ArrayList<>();
    quadroEnquadradoList.add(FLAG);

    int contBits1 = 0;
    int byteSaidaAtual = 0;
    int bitsNoByteSaida = 0;
    int contCargaUtil = 0; //Contador de bytes de carga util no quadro atual

    for (int caractereEntrada : quadro) {
      //Processa bit a bit para fazer o stuffing
      for (int i = 7; i >= 0; i--) {
        int bit = (caractereEntrada >> i) & 1;

        if (bit == 1) {
          contBits1++;
        } else {
          contBits1 = 0;
        } //Fim if-else

        //Adiciona o bit lido ao byte util sendo construido
        byteSaidaAtual = (byteSaidaAtual << 1) | bit;
        bitsNoByteSaida++;

        if (bitsNoByteSaida == 8) {
          quadroEnquadradoList.add(byteSaidaAtual);
          byteSaidaAtual = 0;
          bitsNoByteSaida = 0;
        }

        if (contBits1 == 5) {
          byteSaidaAtual = (byteSaidaAtual << 1) | 0; //Stuffing com bit 0
          bitsNoByteSaida++;
          if (bitsNoByteSaida == 8) { //Se o bit stuff completou um byte util
            quadroEnquadradoList.add(byteSaidaAtual);
            byteSaidaAtual = 0;
            bitsNoByteSaida = 0;
          }
          contBits1 = 0;
        }
      } //Fim for

      contCargaUtil++; //Incrementa o contador de bytes de carga util processados

      //Verifica se o quadro de carga util atingiu o tamanho maximo
      if (contCargaUtil >= TAMANHO_CARGA_UTIL) {
        //Se houver bits restantes no byte de saida, completa com padding e envia
        if (bitsNoByteSaida > 0) {
          byteSaidaAtual = byteSaidaAtual << (8 - bitsNoByteSaida);
          quadroEnquadradoList.add(byteSaidaAtual);
          byteSaidaAtual = 0;
          bitsNoByteSaida = 0;
        }

        quadroEnquadradoList.add(FLAG); //Fecha o quadro atual
        contCargaUtil = 0; //Zera o contador para o proximo quadro

        if (caractereEntrada != quadro[quadro.length - 1]) {
          quadroEnquadradoList.add(FLAG);
        }
      } //Fim if
    } //Fim for

    //Se o ultimo quadro nao foi fechado pela logica de tamanho
    if (contCargaUtil > 0) {
        //Se houver bits restantes no byte de saida, completa com padding e envia
        if (bitsNoByteSaida > 0) {
          byteSaidaAtual = byteSaidaAtual << (8 - bitsNoByteSaida);
          quadroEnquadradoList.add(byteSaidaAtual);
        }
        quadroEnquadradoList.add(FLAG); //Adiciona a FLAG final
    }

    int[] quadroEnquadrado = new int[quadroEnquadradoList.size()];

    for (int i = 0; i < quadroEnquadradoList.size(); i++) {
      quadroEnquadrado[i] = quadroEnquadradoList.get(i);
    }
    
    return quadroEnquadrado;

  } //Fim camadaEnlaceDadosTransmissoraEnquadramentoInsercaoDeBits


  /**
   * Realiza o enquadramento pela tecnica de violacao da camada fisica.
   * <p>
   * Simplesmente manda o mesmo fluxo de caracteres que recebeu, para
   * a camada fisica fazer o devido enquadramento.
   *
   * @param quadro Vetor de inteiros contendo os caracteres da mensagem.
   * @return int[] O proprio vetor quadro[].
   */
  protected static int[] camadaEnlaceDadosTransmissoraEnquadramentoViolacaoCamadaFisica(int quadro[]) {
    return quadro;
  } //Fim camadaEnlaceDadosTransmissoraEnquadramentoViolacaoCamadaFisica


  protected static void camadaEnlaceDadosTransmissoraControleDeErros(int quadro[]) {
  } //Fim camadaEnlaceDadosTransmissoraControleDeErros

  
  protected static void camadaEnlaceDadosTransmissoraControleDeFluxo(int quadro[]) {
  } //Fim camadaEnlaceDadosTransmissoraControleDeFluxo

} //Fim da classe CamadaEnlaceDadosTransmissora