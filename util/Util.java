package util;

/**
 * Classe com metodos estaticos utilitarios para a simulacao.
 * <p>
 * Fornece funcionalidades de apoio, como a formatacao de dados para exibicao
 * na interface grafica, utilizadas pelas camadas de enlace e pelo meio de comunicacao.
 *
 * @author  Italo de Souza Leao (Matricula: 202410120)
 * @version 27/09/2025 (Ultima alteracao)
 * @since   26/08/2025 (Inicio)
 */
public class Util {
  
  /**
   * Converte um inteiro de 32 bits para sua representacao em string binaria.
   * <p>
   * O metodo gera uma string contendo '0's e '1's que representam o inteiro.
   * Para facilitar a leitura, um espaco eh inserido a cada 8 bits, separando
   * visualmente os bytes.
   *
   * @param bits O numero inteiro de 32 bits a ser formatado.
   * @return String A representacao binaria do inteiro em formato de texto.
   */
  public static String bitsParaString(int bits) {
    String retorno = "";
    int mascara = 1;

    for (int i = 0; i < 32; i++) {
      if (i % 8 == 0){ //A cada 8 bits da um espaco de separacao visual
        retorno = ' ' + retorno;
      } //Fim if
      
      mascara <<= 1; //Move a mascara 1 bit a esquerda
      
      if ((mascara & bits) != 0) //Se o primeiro bit do inteiro nao for 0
        retorno = "1" + retorno;
      else
        retorno = "0" + retorno;
    } //Fim for

    return retorno;
  } //Fim bitsParaString

} //Fim da classe Util