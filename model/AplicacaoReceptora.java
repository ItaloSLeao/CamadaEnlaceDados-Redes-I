package model;

import controller.ControllerTelaPrincipal;

/**
 * Ponto final do processo de recepcao da mensagem.
 * <p>
 * Esta classe eh responsavel por receber a mensagem ja decodificada da
 * camada de aplicacao e interagir com o controlador da GUI para exibi-la
 * na interface do usuario, finalizando a simulacao.
 *
 * @author  Italo de Souza Leao (Matricula: 202410120)
 * @version 30/09/2025 (Ultima alteracao)
 * @since   26/08/2025 (Inicio)
 */
public class AplicacaoReceptora {

  /**
   * Exibe a mensagem recebida na interface grafica e finaliza a transmissao.
   * <p>
   * Este eh o metodo final da simulacao de recepcao. Ele recebe a string
   * da mensagem, comanda o controlador para exibi-la na area de texto
   * do receptor e reativa os controles da interface para uma nova transmissao.
   *
   * @param mensagem    A mensagem decodificada recebida da camada de aplicacao.
   * @param controller  O controlador da interface grafica para exibir o resultado.
   */
  public static void aplicacaoReceptora(String mensagem, ControllerTelaPrincipal controller){
    System.out.println("\nAPLICACAO RECEPTORA------------------");
    System.out.println("\nMensagem recebida: " + mensagem + "\n");
    controller.adicionarMsgRecebidaTextArea(mensagem);
    controller.reativar();
  } //Fim aplicacaoReceptora

} //Fim da classe AplicacaoReceptora