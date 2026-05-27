package org.ucsal.client.ui.panels;

import org.ucsal.client.JogadorImpl;
import org.ucsal.client.ui.GameUI;
import org.ucsal.client.ui.GameWindow;
import org.ucsal.client.ui.components.GameColors;
import org.ucsal.client.ui.components.RoundedPanel;
import org.ucsal.rmi.ServidorRemoto;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

/**
 * Tela 1 — Formulário de conexão ao servidor.
 * Reproduz fielmente o mockup: fundo cinza, título acima do card,
 * campos "Nome" e "Código da partida", botão azul CONECTAR.
 */
public class ConexaoPanel extends JPanel {

    private final GameUI     gameUI;
    private final GameWindow window;

    private final JTextField tfNome;
    private final JTextField tfCodigo;
    private final JButton    btnConectar;
    private final JLabel     lblStatus;

    public ConexaoPanel(GameUI gameUI, GameWindow window) {
        this.gameUI = gameUI;
        this.window = window;

        setBackground(GameColors.BG_PAGE);
        setLayout(new GridBagLayout());

        // ── Wrapper vertical (título + card) ──────────────────────────────
        JPanel wrapper = new JPanel();
        wrapper.setOpaque(false);
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));

        // Título
        JLabel lblTitulo = new JLabel("PAR OU ÍMPAR");
        lblTitulo.setFont(GameColors.FONT_TITLE);
        lblTitulo.setForeground(GameColors.TEXT_TITLE);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtítulo
        JLabel lblSub = new JLabel("Jogo Distribuído");
        lblSub.setFont(GameColors.FONT_SUBTITLE);
        lblSub.setForeground(GameColors.TEXT_SUBTITLE);
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ── Card branco ────────────────────────────────────────────────────
        RoundedPanel card = new RoundedPanel(14);
        card.setBackground(GameColors.BG_CARD);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(28, 32, 28, 32));
        card.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Campo: Nome
        JLabel lblNome = criarLabel("Nome:");
        tfNome = criarCampo("Seu nome");
        tfNome.setText("Seu nome");
        tfNome.setForeground(GameColors.TEXT_MUTED);
        tfNome.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override public void focusGained(java.awt.event.FocusEvent e) {
                if (tfNome.getForeground().equals(GameColors.TEXT_MUTED)) {
                    tfNome.setText("");
                    tfNome.setForeground(GameColors.TEXT_LABEL);
                }
            }
            @Override public void focusLost(java.awt.event.FocusEvent e) {
                if (tfNome.getText().trim().isEmpty()) {
                    tfNome.setText("Seu nome");
                    tfNome.setForeground(GameColors.TEXT_MUTED);
                }
            }
        });

        // Campo: IP do servidor
        final String PLACEHOLDER_IP = "Ex: 192.168.1.10  (Deixe vazio para conectar em localhost)";
        JLabel lblCodigo = criarLabel("IP do servidor:");
        tfCodigo = criarCampo(PLACEHOLDER_IP);
        tfCodigo.setText(PLACEHOLDER_IP);
        tfCodigo.setFont(new Font("Monospaced", Font.PLAIN, 12));
        tfCodigo.setForeground(GameColors.TEXT_MUTED);
        tfCodigo.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override public void focusGained(java.awt.event.FocusEvent e) {
                if (tfCodigo.getForeground().equals(GameColors.TEXT_MUTED)) {
                    tfCodigo.setText("");
                    tfCodigo.setForeground(GameColors.TEXT_LABEL);
                    tfCodigo.setFont(GameColors.FONT_INPUT);
                }
            }
            @Override public void focusLost(java.awt.event.FocusEvent e) {
                if (tfCodigo.getText().trim().isEmpty()) {
                    tfCodigo.setText(PLACEHOLDER_IP);
                    tfCodigo.setForeground(GameColors.TEXT_MUTED);
                    tfCodigo.setFont(new Font("Monospaced", Font.PLAIN, 12));
                }
            }
        });

        // Botão Conectar
        btnConectar = criarBotaoPrimario("CONECTAR");
        btnConectar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnConectar.addActionListener(this::onConectar);

        // Label de status/erro
        lblStatus = new JLabel(" ");
        lblStatus.setFont(new Font("Arial", Font.PLAIN, 12));
        lblStatus.setForeground(GameColors.LOSE_TEXT);
        lblStatus.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Action no Enter
        Action conectarAction = new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { onConectar(e); }
        };
        tfNome.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "conectar");
        tfNome.getActionMap().put("conectar", conectarAction);
        tfCodigo.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "conectar");
        tfCodigo.getActionMap().put("conectar", conectarAction);

        // Montar card
        card.add(lblNome);
        card.add(Box.createVerticalStrut(6));
        card.add(tfNome);
        card.add(Box.createVerticalStrut(16));
        card.add(lblCodigo);
        card.add(Box.createVerticalStrut(6));
        card.add(tfCodigo);
        card.add(Box.createVerticalStrut(20));
        card.add(btnConectar);
        card.add(Box.createVerticalStrut(8));
        card.add(lblStatus);

        // Limitar largura do card
        card.setMaximumSize(new Dimension(400, 9999));
        card.setPreferredSize(new Dimension(380, 300));

        // Montar wrapper
        wrapper.add(lblTitulo);
        wrapper.add(Box.createVerticalStrut(6));
        wrapper.add(lblSub);
        wrapper.add(Box.createVerticalStrut(24));
        wrapper.add(card);

        add(wrapper);
    }

    // ── Evento: conectar ─────────────────────────────────────────────────────

    private void onConectar(ActionEvent e) {
        String nomeRaw = tfNome.getText().trim();
        String nome = nomeRaw.equalsIgnoreCase("Seu nome") ? "" : nomeRaw;
        String codigo = tfCodigo.getText().trim();

        if (nome.isEmpty()) {
            setStatusErro("Digite seu nome para continuar.");
            return;
        }

        // "Código da partida" vazio → localhost; senão, usa como host
        boolean isCodPlaceholder = codigo.isEmpty()
                || codigo.equalsIgnoreCase("Ex: 192.168.1.10  (Deixe vazio para conectar em localhost)");
        String host = isCodPlaceholder ? "localhost" : codigo;

        lblStatus.setForeground(GameColors.TEXT_SUBTITLE);
        lblStatus.setText("Conectando...");
        btnConectar.setEnabled(false);
        btnConectar.repaint();

        new ConexaoWorker(nome, host).execute();
    }

    // ── Métodos públicos chamados por GameUI ─────────────────────────────────

    public void setStatusErro(String msg) {
        lblStatus.setForeground(GameColors.LOSE_TEXT);
        lblStatus.setText(msg != null ? msg : "Erro desconhecido.");
        btnConectar.setEnabled(true);
        btnConectar.repaint();
    }

    public void resetar() {
        lblStatus.setText(" ");
        btnConectar.setEnabled(true);
        btnConectar.repaint();
    }

    // ── Helpers visuais ──────────────────────────────────────────────────────

    private JLabel criarLabel(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(GameColors.FONT_LABEL);
        l.setForeground(GameColors.TEXT_LABEL);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private JTextField criarCampo(String placeholder) {
        JTextField tf = new JTextField();
        tf.setFont(GameColors.FONT_INPUT);
        tf.setForeground(GameColors.TEXT_LABEL);
        tf.setBackground(Color.WHITE);
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GameColors.INPUT_BORDER, 1, true),
                BorderFactory.createEmptyBorder(10, 14, 10, 14)));
        tf.setAlignmentX(Component.LEFT_ALIGNMENT);
        tf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        return tf;
    }

    private JButton criarBotaoPrimario(String texto) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(isEnabled() ? GameColors.BTN_PRIMARY : GameColors.BTN_DISABLED);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setFont(getFont());
                g2.setColor(Color.WHITE);
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
        btn.setPreferredSize(new Dimension(320, 44));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        return btn;
    }

    // ── SwingWorker de conexão ────────────────────────────────────────────────

    private class ConexaoWorker extends SwingWorker<Void, Void> {

        private final String nome;
        private final String host;

        ConexaoWorker(String nome, String host) {
            this.nome = nome;
            this.host = host;
        }

        @Override
        protected Void doInBackground() throws Exception {
            // Mostra lobby imediatamente (antes do registrarJogador bloquear)
            SwingUtilities.invokeLater(() ->
                gameUI.mostrarLobby("Conectando ao servidor...")
            );

            Registry registry;
            try {
                registry = LocateRegistry.getRegistry(host, 1099);
            } catch (Exception e) {
                throw new Exception("Não foi possível localizar o servidor em " + host + ":1099", e);
            }

            ServidorRemoto servidor;
            try {
                servidor = (ServidorRemoto) registry.lookup("JogoParOuImpar");
            } catch (NotBoundException e) {
                throw new Exception("Servidor não encontrado. Verifique se está rodando.", e);
            }
            // Anuncia o IP real do cliente nos stubs de callback.
            // Usa um socket UDP (sem envio real) apontado para o servidor
            // para descobrir qual interface local será usada na comunicação.
            try (DatagramSocket ds = new DatagramSocket()) {
                ds.connect(InetAddress.getByName(host), 1099);
                String clienteIp = ds.getLocalAddress().getHostAddress();
                System.setProperty("java.rmi.server.hostname", clienteIp);
            } catch (Exception ignored) { }

            CountDownLatch latch = new CountDownLatch(1);
            JogadorImpl jogador  = new JogadorImpl(nome, gameUI, latch);

            // Bloqueia até a partida terminar (para J2) ou retorna rápido (para J1)
            servidor.registrarJogador(jogador);

            // Para J1: aguarda receberResultado() decrementar o latch
            // Para J2: latch já é 0 (receberResultado foi chamado dentro de registrarJogador)
            latch.await();
            return null;
        }

        @Override
        protected void done() {
            try {
                get(); // re-lança exceção se doInBackground() falhou
            } catch (ExecutionException ex) {
                Throwable causa = ex.getCause() != null ? ex.getCause() : ex;
                String msg = causa.getMessage();
                if (msg == null) msg = causa.getClass().getSimpleName();
                gameUI.mostrarErroConexao("Erro: " + msg);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
