package org.ucsal.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServidorRemoto extends Remote {
    String registrarJogador(JogadorRemoto jogador) throws RemoteException;

    String consultarStatus() throws RemoteException;
}