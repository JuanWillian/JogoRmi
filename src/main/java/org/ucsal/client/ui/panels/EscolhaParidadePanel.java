package org.ucsal.client.ui.panels;

import org.ucsal.client.ui.GameUI;
import org.ucsal.client.ui.GameWindow;
import org.ucsal.client.ui.components.GameColors;
import org.ucsal.client.ui.components.RoundedPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// Tela Escolha de PAR ou ÍMPAR (apenas para o Jogador 1)
public class EscolhaParidadePanel extends JPanel {

    private final GameUI     gameUI;
    private final GameWindow window;
    private final JButton    btnPar;
    private final JButton    btnImpar;

    public EscolhaParidadePanel(GameUI gameUI, GameWindow window) {
        this.gameUI = gameUI;
        this.window = window;

        setBackground(GameColors.BG_PAGE);
        setLayout(new GridBagLayout());

        JPanel wrapper = new JPanel();
        wrapper.setOpaque(false);
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));

        // Título
        JLabel titulo = new JLabel("PAR OU ÍMPAR");
        titulo.setFont(GameColors.FONT_TITLE);
        titulo.setForeground(GameColors.TEXT_TITLE);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sub = new JLabel("Jogo Distribuído");
        sub.setFont(GameColors.FONT_SUBTITLE);
        sub.setForeground(GameColors.TEXT_SUBTITLE);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Card
        RoundedPanel card = new RoundedPanel(14);
        card.setBackground(GameColors.BG_CARD);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(32, 32, 32, 32));
        card.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.setMaximumSize(new Dimension(420, 9999));

        JLabel instrucao = new JLabel("Escolha sua aposta:");
        instrucao.setFont(new Font("Arial", Font.BOLD, 16));
        instrucao.setForeground(GameColors.TEXT_LABEL);
        instrucao.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel hint = new JLabel("O adversário ficará com a opção oposta");
        hint.setFont(new Font("Arial", Font.PLAIN, 13));
        hint.setForeground(GameColors.TEXT_MUTED);
        hint.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Painel de botões lado a lado
        JPanel painelBotoes = new JPanel(new GridLayout(1, 2, 16, 0));
        painelBotoes.setOpaque(false);
        painelBotoes.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelBotoes.setMaximumSize(new Dimension(340, 90));

        btnPar   = criarBotaoEscolha("PAR",   new Color(59, 130, 246), new Color(37, 99, 235));
        btnImpar = criarBotaoEscolha("ÍMPAR", new Color(139, 92, 246), new Color(109, 40, 217));

        btnPar.addActionListener(e -> confirmar("PAR"));
        btnImpar.addActionListener(e -> confirmar("IMPAR"));

        painelBotoes.add(btnPar);
        painelBotoes.add(btnImpar);

        card.add(instrucao);
        card.add(Box.createVerticalStrut(6));
        card.add(hint);
        card.add(Box.createVerticalStrut(24));
        card.add(painelBotoes);

        wrapper.add(titulo);
        wrapper.add(Box.createVerticalStrut(6));
        wrapper.add(sub);
        wrapper.add(Box.createVerticalStrut(24));
        wrapper.add(card);

        add(wrapper);
    }

    public void habilitarBotoes() {
        btnPar.setEnabled(true);
        btnImpar.setEnabled(true);
    }

    private void confirmar(String escolha) {
        btnPar.setEnabled(false);
        btnImpar.setEnabled(false);
        // Volta ao lobby imediatamente (visualmente)
        SwingUtilities.invokeLater(() ->
            window.mostrarPainel(GameWindow.PAINEL_LOBBY)
        );
        gameUI.entregarEscolhaParidade(escolha);
    }

    private JButton criarBotaoEscolha(String texto, Color cor, Color corHover) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(isEnabled() ? cor : cor.brighter());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.setFont(getFont());
                g2.setColor(Color.WHITE);
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };
        btn.setFont(GameColors.FONT_PAR_BTN);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(150, 72));
        return btn;
    }
}
