package org.ucsal.server;

import org.ucsal.model.Jogada;
import org.ucsal.model.ResultadoPartida;
import org.ucsal.model.TipoParidade;
import org.ucsal.rmi.JogadorRemoto;

import java.rmi.RemoteException;

public class Partida {
    private final JogadorRemoto primeiroJogador;
    private final JogadorRemoto segundoJogador;

    public Partida(JogadorRemoto primeiroJogador, JogadorRemoto segundoJogador) {
        this.primeiroJogador = primeiroJogador;
        this.segundoJogador = segundoJogador;
    }

    public ResultadoPartida executar() throws RemoteException {
        primeiroJogador.receberMensagem("escolha PAR ou IMPAR e o segundo jogador fica com a opcao restante");
        segundoJogador.receberMensagem("espere pela escolha do primeiro jogador");

        TipoParidade escolhaPrimeiroJogador = TipoParidade.fromTexto(primeiroJogador.escolherParOuImpar());
        TipoParidade escolhaSegundoJogador = escolhaPrimeiroJogador.oposta();

        primeiroJogador.receberMensagem("Sua opcao foi " + escolhaPrimeiroJogador + ". O adversario ficou com " + escolhaSegundoJogador + ".");
        segundoJogador.receberMensagem("Sua opcao foi " + escolhaSegundoJogador + ".");

        int numeroPrimeiroJogador = primeiroJogador.escolherNumero();
        int numeroSegundoJogador = segundoJogador.escolherNumero();

        Jogada jogadaPrimeiroJogador = new Jogada(primeiroJogador.getNome(), numeroPrimeiroJogador);
        Jogada jogadaSegundoJogador = new Jogada(segundoJogador.getNome(), numeroSegundoJogador);

        int soma = numeroPrimeiroJogador + numeroSegundoJogador;
        TipoParidade paridadeDaSoma = TipoParidade.fromNumero(soma);
        String nomeVencedor = paridadeDaSoma == escolhaPrimeiroJogador ? primeiroJogador.getNome() : segundoJogador.getNome();

        ResultadoPartida resultadoPartida = new ResultadoPartida(
                jogadaPrimeiroJogador,
                jogadaSegundoJogador,
                soma,
                paridadeDaSoma,
                nomeVencedor,
                escolhaPrimeiroJogador,
                escolhaSegundoJogador
        );

        primeiroJogador.receberResultado(resultadoPartida.formatarMensagemPara(primeiroJogador.getNome()));
        segundoJogador.receberResultado(resultadoPartida.formatarMensagemPara(segundoJogador.getNome()));

        return resultadoPartida;
    }
}