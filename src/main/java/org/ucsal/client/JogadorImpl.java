package org.ucsal.client;

import org.ucsal.model.Jogador;
import org.ucsal.rmi.JogadorRemoto;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class JogadorImpl extends UnicastRemoteObject implements JogadorRemoto {
    private final Jogador jogador;
    private final Scanner scanner;
    private final CountDownLatch latch;

    public JogadorImpl(String nome, Scanner scanner, CountDownLatch latch) throws RemoteException {
        super();
        this.jogador = new Jogador(nome);
        this.scanner = scanner;
        this.latch = latch;
    }

    @Override
    public String getNome() throws RemoteException {
        return jogador.getNome();
    }

    @Override
    public String escolherParOuImpar() throws RemoteException {
        while (true) {
            System.out.print(jogador.getNome() + ", escolha PAR ou IMPAR: ");
            String valor = scanner.nextLine().trim().toUpperCase();

            if ("PAR".equals(valor) || "IMPAR".equals(valor)) {
                return valor;
            }

            System.out.println("Digite PAR ou IMPAR.");
        }
    }

    //podemos mudar pra aceitar qualquer numero, porem pode deixar assim so pra usar de exemplo 
    @Override
    public int escolherNumero() throws RemoteException {
        while (true) {
            System.out.print(jogador.getNome() + ", digite um numero entre 0 e 10: ");

            try {
                int numero = Integer.parseInt(scanner.nextLine().trim());

                if (numero >= 0 && numero <= 10) {
                    return numero;
                }

                System.out.println("digite um numero de 0 a 10");
            } catch (NumberFormatException e) {
                System.out.println("numero invalido");
            }
        }
    }

    @Override
    public void receberMensagem(String mensagem) throws RemoteException {
        System.out.println("[" + jogador.getNome() + "] " + mensagem);
    }

    @Override
    public void receberResultado(String resultado) throws RemoteException {
        System.out.println("[" + jogador.getNome() + "] " + resultado);

        if (latch != null) {
            latch.countDown();
        }
    }
}