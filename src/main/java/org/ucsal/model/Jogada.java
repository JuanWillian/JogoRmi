package org.ucsal.model;

public class Jogada {
    private final String nomeJogador;
    private final int numeroEscolhido;

    public Jogada(String nomeJogador, int numeroEscolhido) {
        this.nomeJogador = nomeJogador;
        this.numeroEscolhido = numeroEscolhido;
    }

    public String getNomeJogador() {
        return nomeJogador;
    }

    public int getNumeroEscolhido() {
        return numeroEscolhido;
    }
}