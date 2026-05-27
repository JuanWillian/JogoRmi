package org.ucsal.client.ui.components;

import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {

    private final int radius;

    public RoundedPanel(int radius) {
        this.radius = radius;
        setOpaque(false);
        setBackground(Color.WHITE);
    }

    public RoundedPanel() {
        this(16);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Sombra sutil
        g2.setColor(new Color(0, 0, 0, 12));
        g2.fillRoundRect(2, 4, getWidth() - 2, getHeight() - 2, radius, radius);
        // Card
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth() - 2, getHeight() - 4, radius, radius);
        g2.dispose();
    }
}
