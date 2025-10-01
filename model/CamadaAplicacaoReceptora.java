package model;

import controller.ControllerTelaPrincipal;

/**
 * Simula o funcionamento da camada de aplicacao de um receptor.
 * <p>
 * Esta eh a camada final do processo de recepcao. Ela recebe um array de
 * inteiros (representando caracteres ASCII) da camada de enlace, reconstroi
 * a mensagem original e a entrega para a aplicacao final, que a exibe ao
 * usuario.
 *
 * @author  Italo de Souza Leao (Matricula: 202410120)
 * @version 30/09/2025 (Ultima alteracao)
 * @since   26/08/2025 (Inicio)
 */
public class CamadaAplicacaoReceptora {

  /**
   * Processa os dados recebidos para reconstruir e exibir a mensagem final.
   * <p>
   * Este metodo itera sobre o array de inteiros, convertendo cada um para o
   * caractere ASCII correspondente para formar a string da mensagem. Ao final,
   * invoca a AplicacaoReceptora para apresentar a mensagem decodificada
   * na interface do usuario.
   *
   * @param bits        Array de inteiros (valores ASCII) recebido da camada de enlace.
   * @param controller  O controlador da interface grafica.
   */
  protected static void camadaAplicacaoReceptora(int quadro[], ControllerTelaPrincipal controller) {
    String mensagem = ""; //Armazenara a mensagem decodificada
    
    //Loop para processar cada inteiro no array quadro
    for (int i = 0; i < quadro.length; i++) {
      if (quadro[i] == 0) {break;} //Interrompe o processamento (caracteres ASCII > 0)
      
      mensagem += ((char) quadro[i]);
      
      try {
        Thread.sleep(controller.getVelocidade());
      } catch (Exception e) {
        e.printStackTrace();
      } //Fim try-catch
    } //Fim for
    
    System.out.println("\nCAMADA DE APLICACAO RECEPTORA-----------------");
    for (int i = 0; i < quadro.length; i++) {
      System.out.println(quadro[i] + " = " + (char) (quadro[i]) + ";"); //Imprime a conversao no console
      
      try { 
        Thread.sleep(controller.getVelocidade());
      } catch (Exception e) {
        e.printStackTrace();
      } //Fim try-catch
    } //Fim for

    AplicacaoReceptora.aplicacaoReceptora(mensagem, controller); //Chama a aplicacao receptora
  } //Fim camadaAplicacaoReceptora
  
} //Fim da classe CamadaAplicacaoReceptora