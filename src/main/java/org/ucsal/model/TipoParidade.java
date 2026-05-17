package org.ucsal.model;

public enum TipoParidade {
    PAR,
    IMPAR;

    public static TipoParidade fromTexto(String texto) {
        if (texto == null) {
            throw new IllegalArgumentException("Paridade invalida");
        }

        String valorNormalizado = texto.trim().toUpperCase();

        if ("PAR".equals(valorNormalizado)) {
            return PAR;
        }

        if ("IMPAR".equals(valorNormalizado)) {
            return IMPAR;
        }

        throw new IllegalArgumentException("Paridade invalida");
    }

    public static TipoParidade fromNumero(int numero) {
        return numero % 2 == 0 ? PAR : IMPAR;
    }

    public TipoParidade oposta() {
        return this == PAR ? IMPAR : PAR;
    }
}