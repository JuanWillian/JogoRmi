package org.ucsal;

public class Main {
    public static void main(String[] args) {
        //a
        if (args.length == 0) {
            System.out.println("Use org.ucsal.server.ServidorMain para subir o servidor e org.ucsal.client.ClienteMain para conectar um jogador");
            return;
        }

        if ("servidor".equalsIgnoreCase(args[0])) {
            org.ucsal.server.ServidorMain.main(new String[0]);
            return;
        }

        if ("cliente".equalsIgnoreCase(args[0])) {
            org.ucsal.client.ClienteMain.main(new String[0]);
            return;
        }

        System.out.println("Argumento invalido. Use 'servidor' ou 'cliente'.");
    }
}