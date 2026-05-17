package org.ucsal.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServidorMain {
    public static void main(String[] args) {
        try {
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}