package org.ucsal.server;

import org.ucsal.model.ResultadoPartida;
import org.ucsal.rmi.JogadorRemoto;
import org.ucsal.rmi.ServidorRemoto;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServidorImpl extends UnicastRemoteObject implements ServidorRemoto {
    private final List<JogadorRemoto> jogadores = new ArrayList<>();
    private boolean partidaEmAndamento;

    public ServidorImpl() throws RemoteException {
        super();
    }

    @Override
    public synchronized String registrarJogador(JogadorRemoto jogador) throws RemoteException {
        if (partidaEmAndamento) {
            return "partida iniciada";
        }

        if (jogadores.size() >= 2) {
            return "sala cheia ";
        }

        for (JogadorRemoto jogadorRegistrado : jogadores) {
            if (jogadorRegistrado.getNome().equalsIgnoreCase(jogador.getNome())) {
                return "jogador ja registrado.";
            }
        }

        jogadores.add(jogador);
        jogador.receberMensagem(" Aguardando oponente...");

        if (jogadores.size() == 2) {
            executarPartida();
        }

        return "jogador registrado: " + jogador.getNome();
    }

    @Override
    public synchronized String consultarStatus() throws RemoteException {
        if (partidaEmAndamento) {
            return "partida em andamento com " + jogadores.size() + "jogadores";
        }

        return "total de jg " + jogadores.size() + ".";
    }

    private void executarPartida() {
        if (jogadores.size() < 2 || partidaEmAndamento) {
            return;
        }

        partidaEmAndamento = true;

        try {
            Partida partida = new Partida(jogadores.get(0), jogadores.get(1));
            ResultadoPartida resultadoPartida = partida.executar();
            System.out.println(resultadoPartida.formatarResumo());
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        } finally {
            jogadores.clear();
            partidaEmAndamento = false;
        }
    }
}