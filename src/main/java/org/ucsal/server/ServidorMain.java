package org.ucsal.server;

import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServidorMain {
    public static void main(String[] args) {
        try {
            // Determina o IP que o servidor anunciará nos stubs RMI.
            // args[0] permite forçar um IP específico (ex.: IP da interface de rede local).
            // Sem argumento, tenta detectar automaticamente o IP não-loopback.
            String hostname;
            if (args.length > 0 && !args[0].isBlank()) {
                hostname = args[0].trim();
            } else {
                hostname = InetAddress.getLocalHost().getHostAddress();
            }
            System.setProperty("java.rmi.server.hostname", hostname);
            System.out.println("IP do servidor (RMI hostname): " + hostname);

            Registry registry;

            try {
                registry = LocateRegistry.createRegistry(1099);
            } catch (Exception e) {
                registry = LocateRegistry.getRegistry(1099);
            }

            ServidorImpl servidor = new ServidorImpl();
            registry.rebind("JogoParOuImpar", servidor);
            System.out.println("servidor RMI iniciado na porta 1099.");
            System.out.println("nome de bind: JogoParOuImpar");
            System.out.println("Servidor aguardando conexões... (Ctrl+C para encerrar)");

            Object lock = new Object();
            synchronized (lock) {
                lock.wait();
            }
        } catch (InterruptedException e) {
            System.out.println("Servidor encerrado.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}