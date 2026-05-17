package org.ucsal.model;

public class Jogador {
    private final String nome;
    private int pontos;

    public Jogador(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public int getPontos() {
        return pontos;
    }

    public void adicionarPonto() {
        pontos++;
    }
}