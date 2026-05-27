package org.ucsal.client;

import org.ucsal.client.ui.GameWindow;

import javax.swing.*;

public class ClienteMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameWindow().setVisible(true));
    }
}