package org.ucsal.client;

import org.ucsal.client.ui.GameUI;
import org.ucsal.model.Jogador;
import org.ucsal.rmi.JogadorRemoto;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.CountDownLatch;

public class JogadorImpl extends UnicastRemoteObject implements JogadorRemoto {

    private final Jogador      jogador;
    private final GameUI       ui;
    private final CountDownLatch latch;

    // Escolha de PAR/ÍMPAR do jogador local (usada na tela de resultado).
    private String minhaEscolha = "?";

    public JogadorImpl(String nome, GameUI ui, CountDownLatch latch) throws RemoteException {
        super();
        this.jogador = new Jogador(nome);
        this.ui      = ui;
        this.latch   = latch;
    }

    @Override
    public String getNome() throws RemoteException {
        return jogador.getNome();
    }

    /**
     * Chamado pelo servidor para o Jogador 1 escolher PAR ou ÍMPAR.
     * Ativa a tela de escolha no EDT e bloqueia o thread RMI até o usuário clicar.
     */
    @Override
    public String escolherParOuImpar() throws RemoteException {
        ui.ativarEscolhaParidade();
        String escolha = ui.aguardarEscolhaParidade();
        this.minhaEscolha = escolha;
        return escolha;
    }

    /**
     * Chamado pelo servidor para ambos os jogadores escolherem um número.
     * Ativa a tela de número e bloqueia até o usuário clicar.
     */
    @Override
    public int escolherNumero() throws RemoteException {
        ui.ativarEscolhaNumero();
        return ui.aguardarEscolhaNumero();
    }

     // Mensagens de status do servidor (ex.: "Aguardando oponente...", "Sua opcao foi PAR").
     // Para o Jogador 2, o servidor informa a paridade via esta mensagem.
    @Override
    public void receberMensagem(String mensagem) throws RemoteException {
        // Captura a paridade apenas para o Jogador 2 (que nunca chama escolherParOuImpar).
        // J1 já tem minhaEscolha definida; não deve ser sobrescrita pela mensagem de confirmação,
        // que contém as duas paridades ("Sua opcao foi PAR. O adversario ficou com IMPAR.").
        if (minhaEscolha.equals("?") && mensagem.contains("Sua opcao foi")) {
        // Extrair apenas a paridade logo após "Sua opcao foi "
            int idx = mensagem.indexOf("Sua opcao foi ") + "Sua opcao foi ".length();
            String resto = mensagem.substring(idx).trim().toUpperCase();
            if (resto.startsWith("IMPAR")) {
                minhaEscolha = "IMPAR";
            } else if (resto.startsWith("PAR")) {
                minhaEscolha = "PAR";
            }
        }
        String linhaLog = "[" + jogador.getNome() + "] " + mensagem.trim();
        ui.mostrarLobby(linhaLog);
        ui.adicionarLog(linhaLog);
    }

    // Resultado final enviado pelo servidor. Exibe a tela de resultado.
    @Override
    public void receberResultado(String resultado) throws RemoteException {
        boolean venceu = resultado.contains("Voce venceu");
        ui.mostrarResultado(resultado, venceu, minhaEscolha, jogador.getNome());
        if (latch != null) {
            latch.countDown();
        }
    }
}