package org.ucsal.client.ui.panels;

import org.ucsal.client.ui.components.GameColors;
import org.ucsal.client.ui.components.RoundedPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Tela de resultado da partida

public class ResultadoPanel extends JPanel {

    private static final Pattern PATTERN = Pattern.compile(
            "Partida finalizada\\. (.+?) jogou (\\d+), (.+?) jogou (\\d+)\\. " +
            "Soma: (\\d+) \\((.+?)\\)\\. Vencedor: (.+?)\\. Voce (venceu|perdeu)\\."
    );

    private final JPanel painelConteudo;

    public ResultadoPanel() {
        setBackground(GameColors.BG_PAGE);
        setLayout(new GridBagLayout());

        painelConteudo = new JPanel();
        painelConteudo.setOpaque(false);
        add(painelConteudo);
    }

    public void setResultado(String resultado, boolean venceu,
                              String minhaEscolha, String meuNome) {
        painelConteudo.removeAll();

        // Parsear string do servidor
        Matcher m = PATTERN.matcher(resultado);
        String j1 = "?", n1 = "?", j2 = "?", n2 = "?";
        String soma = "?", paridade = "PAR", vencedor = "?";

        if (m.find()) {
            j1       = m.group(1);
            n1       = m.group(2);
            j2       = m.group(3);
            n2       = m.group(4);
            soma     = m.group(5);
            paridade = m.group(6);
            vencedor = m.group(7);
        }

        paridade    = normalizar(paridade);
        minhaEscolha = normalizar(minhaEscolha);

        String[] linhas;
        boolean euSouJ1 = meuNome.equalsIgnoreCase(j1);
        if (euSouJ1) {
            linhas = new String[]{
                j1 + " jogou", n1,
                j2 + " jogou", n2
            };
        } else {
            linhas = new String[]{
                j2 + " jogou", n2,
                j1 + " jogou", n1
            };
        }

        // ── Card ──────────────────────────────────────────────────────────
        RoundedPanel card = new RoundedPanel(16);
        card.setBackground(GameColors.BG_CARD);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(32, 36, 28, 36));
        card.setPreferredSize(new Dimension(420, 480));
        card.setMaximumSize(new Dimension(440, 540));

        // ── Ícone circular
        JPanel icone = criarIconeCirculo(venceu);
        icone.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ── Título
        JLabel lblTitulo = new JLabel(venceu ? "VOCÊ VENCEU!" : "VOCÊ PERDEU");
        lblTitulo.setFont(GameColors.FONT_RESULTADO_TITULO);
        lblTitulo.setForeground(venceu ? GameColors.WIN_TEXT : GameColors.LOSE_TEXT);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ── Tabela de resultados
        JPanel tabela = new JPanel();
        tabela.setOpaque(false);
        tabela.setLayout(new BoxLayout(tabela, BoxLayout.Y_AXIS));
        tabela.setAlignmentX(Component.CENTER_ALIGNMENT);
        tabela.setMaximumSize(new Dimension(Integer.MAX_VALUE, 9999));

        // Linhas: par {chave, valor}
        String[][] dados = {
            { linhas[0], linhas[1] },
            { linhas[2], linhas[3] },
            { "Soma",        soma + " (" + paridade + ")" },
            { "Sua escolha", minhaEscolha },
            { "Vencedor",    vencedor }
        };

        for (int i = 0; i < dados.length; i++) {
            boolean ultimo = (i == dados.length - 1);
            tabela.add(criarLinha(dados[i][0], dados[i][1], !ultimo));
        }

        // ── Botão Sair 
        JButton btnSair = criarBotaoSair();
        btnSair.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSair.addActionListener(e -> System.exit(0));

        card.add(icone);
        card.add(Box.createVerticalStrut(14));
        card.add(lblTitulo);
        card.add(Box.createVerticalStrut(24));
        card.add(tabela);
        card.add(Box.createVerticalStrut(24));
        card.add(btnSair);

        painelConteudo.setLayout(new BorderLayout());
        painelConteudo.add(card, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    // ── Helpers 

    private String normalizar(String p) {
        if (p == null) return "?";
        if (p.equalsIgnoreCase("IMPAR")) return "ÍMPAR";
        return p;
    }

    private JPanel criarIconeCirculo(boolean venceu) {
        return new JPanel() {
            {
                setOpaque(false);
                setPreferredSize(new Dimension(68, 68));
                setMaximumSize(new Dimension(68, 68));
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                Color bg = venceu ? GameColors.WIN_CIRCLE : GameColors.LOSE_CIRCLE;
                g2.setColor(bg);
                g2.fillOval(4, 4, 60, 60);

                // Emoji: tenta Segoe UI Emoji (Windows), fallback para plain text
                String emoji = venceu ? "\uD83D\uDE0A" : "\uD83D\uDE14"; // 😊 ou 😔
                g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 30));
                FontMetrics fm = g2.getFontMetrics();
                int ew = fm.stringWidth(emoji);
                int ex = (68 - ew) / 2;
                int ey = (68 + fm.getAscent() - fm.getDescent()) / 2;
                g2.setColor(venceu ? GameColors.WIN_TEXT : GameColors.LOSE_TEXT);
                g2.drawString(emoji, ex, ey);
                g2.dispose();
            }
        };
    }

    private JPanel criarLinha(String chave, String valor, boolean comSeparador) {
        JPanel linha = new JPanel(new BorderLayout());
        linha.setOpaque(false);
        linha.setBorder(new EmptyBorder(12, 0, 12, 0));

        if (comSeparador) {
            linha.setBorder(BorderFactory.createCompoundBorder(
                    new MatteBorder(0, 0, 1, 0, GameColors.ROW_SEP),
                    new EmptyBorder(12, 0, 12, 0)));
        }

        JLabel lblChave = new JLabel(chave);
        lblChave.setFont(GameColors.FONT_TABLE_KEY);
        lblChave.setForeground(GameColors.TEXT_SUBTITLE);

        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(GameColors.FONT_TABLE_VAL);
        lblValor.setForeground(GameColors.TEXT_TITLE);
        lblValor.setHorizontalAlignment(SwingConstants.RIGHT);

        linha.add(lblChave, BorderLayout.WEST);
        linha.add(lblValor, BorderLayout.EAST);
        linha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        return linha;
    }

    private JButton criarBotaoSair() {
        JButton btn = new JButton("Sair") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(243, 244, 246));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setFont(getFont());
                g2.setColor(GameColors.TEXT_SUBTITLE);
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };
        btn.setFont(GameColors.FONT_BTN);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(200, 42));
        btn.setMaximumSize(new Dimension(240, 42));
        return btn;
    }
}
