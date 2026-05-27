package org.ucsal.client.ui.components;

import java.awt.*;

public final class GameColors {

    // Backgrounds
    public static final Color BG_PAGE    = new Color(243, 244, 246);
    public static final Color BG_CARD    = Color.WHITE;
    public static final Color BG_LOG     = new Color(249, 250, 251);
    public static final Color BG_LOG_HDR = new Color(243, 244, 246);

    // Text
    public static final Color TEXT_TITLE    = new Color(17, 24, 39);
    public static final Color TEXT_SUBTITLE = new Color(107, 114, 128);
    public static final Color TEXT_LABEL    = new Color(55, 65, 81);
    public static final Color TEXT_MUTED    = new Color(156, 163, 175);
    public static final Color TEXT_LOG      = new Color(30, 64, 175);

    // Inputs / Borders
    public static final Color INPUT_BORDER = new Color(209, 213, 219);

    // Button
    public static final Color BTN_PRIMARY  = new Color(59, 130, 246);
    public static final Color BTN_HOVER    = new Color(37, 99, 235);
    public static final Color BTN_DISABLED = new Color(147, 197, 253);

    // Spinner
    public static final Color SPINNER     = new Color(59, 130, 246);

    // Result — win
    public static final Color WIN_TEXT   = new Color(22, 163, 74);
    public static final Color WIN_CIRCLE = new Color(220, 252, 231);

    // Result — lose
    public static final Color LOSE_TEXT   = new Color(220, 38, 38);
    public static final Color LOSE_CIRCLE = new Color(254, 226, 226);

    // Table rows
    public static final Color ROW_SEP     = new Color(229, 231, 235);
    public static final Color ROW_KEY     = new Color(75, 85, 99);
    public static final Color ROW_VALUE   = new Color(17, 24, 39);

    // Fonts
    public static final Font FONT_TITLE        = new Font("Arial", Font.BOLD, 34);
    public static final Font FONT_SUBTITLE     = new Font("Arial", Font.PLAIN, 14);
    public static final Font FONT_LABEL        = new Font("Arial", Font.BOLD, 13);
    public static final Font FONT_INPUT        = new Font("Arial", Font.PLAIN, 14);
    public static final Font FONT_BTN          = new Font("Arial", Font.BOLD, 14);
    public static final Font FONT_LOBBY_MSG    = new Font("Arial", Font.BOLD, 15);
    public static final Font FONT_LOG_HDR      = new Font("Arial", Font.BOLD, 11);
    public static final Font FONT_LOG          = new Font("Monospaced", Font.PLAIN, 13);
    public static final Font FONT_RESULT_TITLE = new Font("Arial", Font.BOLD, 24);
    public static final Font FONT_ROW_KEY      = new Font("Arial", Font.PLAIN, 14);
    public static final Font FONT_ROW_VALUE    = new Font("Arial", Font.BOLD, 14);
    public static final Font FONT_PAR_BTN      = new Font("Arial", Font.BOLD, 18);
    public static final Font FONT_NUM_BTN         = new Font("Arial", Font.BOLD, 16);
    // Aliases usados em ResultadoPanel
    public static final Font FONT_RESULTADO_TITULO = FONT_RESULT_TITLE;
    public static final Font FONT_TABLE_KEY        = FONT_ROW_KEY;
    public static final Font FONT_TABLE_VAL        = FONT_ROW_VALUE;

    private GameColors() {}
}
