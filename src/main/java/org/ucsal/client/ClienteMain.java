package org.ucsal.client;

import org.ucsal.rmi.JogadorRemoto;
import org.ucsal.rmi.ServidorRemoto;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class ClienteMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Digite seu nome: ");
            String nome = scanner.nextLine().trim();
            CountDownLatch latch = new CountDownLatch(1);

            JogadorRemoto jogador = new JogadorImpl(nome, scanner, latch);
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            ServidorRemoto servidor = (ServidorRemoto) registry.lookup("JogoParOuImpar");

            System.out.println(servidor.registrarJogador(jogador));
            System.out.println(servidor.consultarStatus());

            latch.await();
            System.out.println("encerrado!@");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}