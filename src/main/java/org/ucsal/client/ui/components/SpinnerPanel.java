package org.ucsal.client.ui.components;

import javax.swing.*;
import java.awt.*;

// Painel que exibe um spinner circular animado (arco girando)
public class SpinnerPanel extends JPanel {

    private int angle = 0;
    private Timer timer;
    private boolean running = false;

    public SpinnerPanel() {
        setOpaque(false);
        setPreferredSize(new Dimension(52, 52));
    }

    public void iniciar() {
        if (running) return;
        running = true;
        timer = new Timer(25, e -> {
            angle = (angle - 8 + 360) % 360;
            repaint();
        });
        timer.start();
    }

    public void parar() {
        running = false;
        if (timer != null) timer.stop();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int pad = 6;
        int size = Math.min(getWidth(), getHeight()) - pad * 2;

        // Arco de fundo (cinza claro)
        g2.setStroke(new BasicStroke(4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setColor(new Color(229, 231, 235));
        g2.drawArc(pad, pad, size, size, 0, 360);

        // Arco principal (azul, ~270 graus)
        g2.setColor(GameColors.SPINNER);
        g2.drawArc(pad, pad, size, size, angle, 270);

        g2.dispose();
    }
}
