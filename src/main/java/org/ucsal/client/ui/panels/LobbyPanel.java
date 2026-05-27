package org.ucsal.client.ui.panels;

import org.ucsal.client.ui.components.GameColors;
import org.ucsal.client.ui.components.RoundedPanel;
import org.ucsal.client.ui.components.SpinnerPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// Tela  Aguardando
public class LobbyPanel extends JPanel {

    private final SpinnerPanel spinner;
    private final JLabel       lblMensagem;
    private final JTextArea    taLog;

    public LobbyPanel() {
        setBackground(GameColors.BG_PAGE);
        setLayout(new GridBagLayout());

        // ── Wrapper vertical 
        JPanel wrapper = new JPanel();
        wrapper.setOpaque(false);
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setMaximumSize(new Dimension(460, 9999));

        // ── Card superior: spinner + mensagem 
        RoundedPanel cardStatus = new RoundedPanel(14);
        cardStatus.setBackground(GameColors.BG_CARD);
        cardStatus.setLayout(new BoxLayout(cardStatus, BoxLayout.Y_AXIS));
        cardStatus.setBorder(new EmptyBorder(28, 32, 28, 32));
        cardStatus.setAlignmentX(Component.CENTER_ALIGNMENT);

        spinner = new SpinnerPanel();
        spinner.setAlignmentX(Component.CENTER_ALIGNMENT);
        spinner.setPreferredSize(new Dimension(52, 52));
        spinner.setMaximumSize(new Dimension(52, 52));

        lblMensagem = new JLabel("Aguardando...");
        lblMensagem.setFont(GameColors.FONT_LOBBY_MSG);
        lblMensagem.setForeground(GameColors.TEXT_TITLE);
        lblMensagem.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblMensagem.setHorizontalAlignment(SwingConstants.CENTER);

        cardStatus.add(spinner);
        cardStatus.add(Box.createVerticalStrut(14));
        cardStatus.add(lblMensagem);
        cardStatus.setMaximumSize(new Dimension(Integer.MAX_VALUE, 160));

        // ── Card inferior: log de mensagens 
        RoundedPanel cardLog = new RoundedPanel(14);
        cardLog.setBackground(GameColors.BG_CARD);
        cardLog.setLayout(new BorderLayout());
        cardLog.setBorder(new EmptyBorder(0, 0, 0, 0));
        cardLog.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Cabeçalho do log
        JPanel logHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logHeader.setBackground(GameColors.BG_LOG_HDR);
        logHeader.setBorder(new EmptyBorder(10, 16, 10, 16));
        JLabel lblLogTitulo = new JLabel("LOG DE MENSAGENS");
        lblLogTitulo.setFont(GameColors.FONT_LOG_HDR);
        lblLogTitulo.setForeground(GameColors.TEXT_MUTED);
        logHeader.add(lblLogTitulo);

        // Área de texto do log
        taLog = new JTextArea();
        taLog.setEditable(false);
        taLog.setFont(GameColors.FONT_LOG);
        taLog.setForeground(GameColors.TEXT_LOG);
        taLog.setBackground(Color.WHITE);
        taLog.setLineWrap(true);
        taLog.setWrapStyleWord(true);
        taLog.setBorder(new EmptyBorder(12, 16, 12, 16));

        JScrollPane scroll = new JScrollPane(taLog);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setBackground(Color.WHITE);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setPreferredSize(new Dimension(400, 150));

        cardLog.add(logHeader, BorderLayout.NORTH);
        cardLog.add(scroll, BorderLayout.CENTER);
        cardLog.setMaximumSize(new Dimension(Integer.MAX_VALUE, 230));
        cardLog.setPreferredSize(new Dimension(420, 210));

        // ── Montar wrapper 
        wrapper.add(cardStatus);
        wrapper.add(Box.createVerticalStrut(16));
        wrapper.add(cardLog);

        add(wrapper);
    }

    // ── API pública chamada por GameUI via invokeLater 

    public void setMensagem(String msg) {
        lblMensagem.setText("<html><center>" + msg.trim() + "</center></html>");
    }

    public void adicionarLog(String linha) {
        taLog.append(linha + "\n");
        // Auto-scroll para a última linha
        taLog.setCaretPosition(taLog.getDocument().getLength());
    }

    public void iniciarSpinner() {
        spinner.iniciar();
    }

    public void pararSpinner() {
        spinner.parar();
    }
}
