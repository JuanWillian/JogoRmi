package org.ucsal.client.ui.panels;

import org.ucsal.client.ui.GameUI;
import org.ucsal.client.ui.GameWindow;
import org.ucsal.client.ui.components.GameColors;
import org.ucsal.client.ui.components.RoundedPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// Tela Escolha de número (0 a 10)
public class EscolhaNumeroPanel extends JPanel {

    private final GameUI     gameUI;
    private final GameWindow window;
    private final List<JButton> botoes = new ArrayList<>();

    public EscolhaNumeroPanel(GameUI gameUI, GameWindow window) {
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
        card.setBorder(new EmptyBorder(28, 32, 28, 32));
        card.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.setMaximumSize(new Dimension(440, 9999));

        JLabel instrucao = new JLabel("Escolha um número de 0 a 10:");
        instrucao.setFont(new Font("Arial", Font.BOLD, 16));
        instrucao.setForeground(GameColors.TEXT_LABEL);
        instrucao.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Grid de botões — primeira linha: 0-5, segunda: 6-10
        JPanel grid = new JPanel(new GridLayout(2, 6, 10, 10));
        grid.setOpaque(false);
        grid.setAlignmentX(Component.CENTER_ALIGNMENT);
        grid.setMaximumSize(new Dimension(380, 120));

        for (int i = 0; i <= 10; i++) {
            final int num = i;
            JButton btn = criarBotaoNumero(String.valueOf(i));
            btn.addActionListener(e -> confirmar(num));
            botoes.add(btn);
            grid.add(btn);
        }
        // Célula vazia para completar o grid 2×6
        grid.add(new JLabel());

        card.add(instrucao);
        card.add(Box.createVerticalStrut(20));
        card.add(grid);

        wrapper.add(titulo);
        wrapper.add(Box.createVerticalStrut(6));
        wrapper.add(sub);
        wrapper.add(Box.createVerticalStrut(24));
        wrapper.add(card);

        add(wrapper);
    }

    public void habilitarBotoes() {
        botoes.forEach(b -> b.setEnabled(true));
    }

    private void confirmar(int numero) {
        botoes.forEach(b -> b.setEnabled(false));
        SwingUtilities.invokeLater(() ->
            window.mostrarPainel(GameWindow.PAINEL_LOBBY)
        );
        gameUI.entregarEscolhaNumero(numero);
    }

    private JButton criarBotaoNumero(String texto) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                if (isEnabled()) {
                    g2.setColor(GameColors.BTN_PRIMARY);
                } else {
                    g2.setColor(new Color(219, 234, 254));
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setFont(getFont());
                g2.setColor(isEnabled() ? Color.WHITE : GameColors.TEXT_MUTED);
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(52, 52));
        return btn;
    }
}
