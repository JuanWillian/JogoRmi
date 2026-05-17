package org.ucsal.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface JogadorRemoto extends Remote {
    String getNome() throws RemoteException;

    String escolherParOuImpar() throws RemoteException;

    int escolherNumero() throws RemoteException;

    void receberMensagem(String mensagem) throws RemoteException;

    void receberResultado(String resultado) throws RemoteException;
}