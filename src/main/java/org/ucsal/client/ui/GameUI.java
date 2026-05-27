package org.ucsal.client.ui;

import org.ucsal.client.ui.panels.LobbyPanel;

import javax.swing.*;
import java.util.concurrent.SynchronousQueue;

public class GameUI {

    private final GameWindow window;

    // Capacidade zero: put bloqueia até take consumir e vice-versa
    private final SynchronousQueue<String>  filaParidade = new SynchronousQueue<>();
    private final SynchronousQueue<Integer> filaNumero   = new SynchronousQueue<>();

    public GameUI(GameWindow window) {
        this.window = window;
    }

    public void mostrarLobby(String mensagem) {
        SwingUtilities.invokeLater(() -> {
            window.getLobbyPanel().setMensagem(mensagem);
            window.mostrarPainel(GameWindow.PAINEL_LOBBY);
            window.getLobbyPanel().iniciarSpinner();
        });
    }

    public void adicionarLog(String linhaFormatada) {
        SwingUtilities.invokeLater(() ->
            window.getLobbyPanel().adicionarLog(linhaFormatada)
        );
    }

    public void ativarEscolhaParidade() {
        SwingUtilities.invokeLater(() -> {
            window.getParidadePanel().habilitarBotoes();
            window.mostrarPainel(GameWindow.PAINEL_PARIDADE);
        });
    }

    public void ativarEscolhaNumero() {
        SwingUtilities.invokeLater(() -> {
            window.getNumeroPanel().habilitarBotoes();
            window.mostrarPainel(GameWindow.PAINEL_NUMERO);
        });
    }

    public void mostrarResultado(String resultado, boolean venceu,
                                 String minhaEscolha, String meuNome) {
        SwingUtilities.invokeLater(() -> {
            window.getLobbyPanel().pararSpinner();
            window.getResultadoPanel().setResultado(resultado, venceu, minhaEscolha, meuNome);
            window.mostrarPainel(GameWindow.PAINEL_RESULTADO);
        });
    }

    public void mostrarErroConexao(String msg) {
        SwingUtilities.invokeLater(() -> {
            window.getConexaoPanel().setStatusErro(msg);
            window.mostrarPainel(GameWindow.PAINEL_CONEXAO);
        });
    }

    public String aguardarEscolhaParidade() {
        try {
            return filaParidade.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "PAR";
        }
    }

    public int aguardarEscolhaNumero() {
        try {
            return filaNumero.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return 0;
        }
    }

    // Chamado quando o usuário clica PAR ou ÍMPAR
    public void entregarEscolhaParidade(String escolha) {
        filaParidade.offer(escolha);
    }

    // Chamado quando o usuário clica em um número
    public void entregarEscolhaNumero(int numero) {
        filaNumero.offer(numero);
    }

    // Libera filas bloqueadas (usado ao fechar a janela)
    public void cancelar() {
        filaParidade.offer("CANCELADO");
        filaNumero.offer(-1);
    }
}
