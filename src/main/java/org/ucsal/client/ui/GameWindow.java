package org.ucsal.client.ui;

import org.ucsal.client.ui.panels.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// JFrame principal com CardLayout gerencia a navegação entre telas
public class GameWindow extends JFrame {

    public static final String PAINEL_CONEXAO   = "CONEXAO";
    public static final String PAINEL_LOBBY     = "LOBBY";
    public static final String PAINEL_PARIDADE  = "PARIDADE";
    public static final String PAINEL_NUMERO    = "NUMERO";
    public static final String PAINEL_RESULTADO = "RESULTADO";

    private final CardLayout    cardLayout = new CardLayout();
    private final JPanel        container  = new JPanel(cardLayout);
    private final GameUI        gameUI     = new GameUI(this);

    private final ConexaoPanel          conexaoPanel;
    private final LobbyPanel            lobbyPanel;
    private final EscolhaParidadePanel  paridadePanel;
    private final EscolhaNumeroPanel    numeroPanel;
    private final ResultadoPanel        resultadoPanel;

    public GameWindow() {
        super("Par ou Ímpar — Jogo Distribuído");

        conexaoPanel   = new ConexaoPanel(gameUI, this);
        lobbyPanel     = new LobbyPanel();
        paridadePanel  = new EscolhaParidadePanel(gameUI, this);
        numeroPanel    = new EscolhaNumeroPanel(gameUI, this);
        resultadoPanel = new ResultadoPanel();

        container.add(conexaoPanel,   PAINEL_CONEXAO);
        container.add(lobbyPanel,     PAINEL_LOBBY);
        container.add(paridadePanel,  PAINEL_PARIDADE);
        container.add(numeroPanel,    PAINEL_NUMERO);
        container.add(resultadoPanel, PAINEL_RESULTADO);

        setContentPane(container);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(660, 680);
        setMinimumSize(new Dimension(500, 550));
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                gameUI.cancelar();
                dispose();
                System.exit(0);
            }
        });

        mostrarPainel(PAINEL_CONEXAO);
    }

    // Troca o painel visível. DEVE ser chamado apenas no EDT
    public void mostrarPainel(String nome) {
        cardLayout.show(container, nome);
    }

    public ConexaoPanel         getConexaoPanel()   { return conexaoPanel; }
    public LobbyPanel           getLobbyPanel()     { return lobbyPanel; }
    public EscolhaParidadePanel getParidadePanel()  { return paridadePanel; }
    public EscolhaNumeroPanel   getNumeroPanel()    { return numeroPanel; }
    public ResultadoPanel       getResultadoPanel() { return resultadoPanel; }
    public GameUI               getGameUI()         { return gameUI; }
}
