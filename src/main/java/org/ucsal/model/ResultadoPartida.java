package org.ucsal.model;

public class ResultadoPartida {
    private final Jogada jogadaPrimeiroJogador;
    private final Jogada jogadaSegundoJogador;
    private final int soma;
    private final TipoParidade paridadeDaSoma;
    private final String nomeVencedor;
    private final TipoParidade escolhaPrimeiroJogador;
    private final TipoParidade escolhaSegundoJogador;

    public ResultadoPartida(Jogada jogadaPrimeiroJogador,
                            Jogada jogadaSegundoJogador,
                            int soma,
                            TipoParidade paridadeDaSoma,
                            String nomeVencedor,
                            TipoParidade escolhaPrimeiroJogador,
                            TipoParidade escolhaSegundoJogador) {
        this.jogadaPrimeiroJogador = jogadaPrimeiroJogador;
        this.jogadaSegundoJogador = jogadaSegundoJogador;
        this.soma = soma;
        this.paridadeDaSoma = paridadeDaSoma;
        this.nomeVencedor = nomeVencedor;
        this.escolhaPrimeiroJogador = escolhaPrimeiroJogador;
        this.escolhaSegundoJogador = escolhaSegundoJogador;
    }

    public String formatarResumo() {
        return "Partida finalizada. "
                + jogadaPrimeiroJogador.getNomeJogador() + " jogou " + jogadaPrimeiroJogador.getNumeroEscolhido() + ", "
                + jogadaSegundoJogador.getNomeJogador() + " jogou " + jogadaSegundoJogador.getNumeroEscolhido() + ". "
                + "Soma: " + soma + " (" + paridadeDaSoma + "). Vencedor: " + nomeVencedor + ".";
    }

    public String formatarMensagemPara(String nomeJogador) {
        String texto = formatarResumo();

        if (nomeVencedor.equalsIgnoreCase(nomeJogador)) {
            return texto + " Voce venceu.";
        }

        return texto + " Voce perdeu.";
    }

    public Jogada getJogadaPrimeiroJogador() {
        return jogadaPrimeiroJogador;
    }

    public Jogada getJogadaSegundoJogador() {
        return jogadaSegundoJogador;
    }

    public int getSoma() {
        return soma;
    }

    public TipoParidade getParidadeDaSoma() {
        return paridadeDaSoma;
    }

    public String getNomeVencedor() {
        return nomeVencedor;
    }

    public TipoParidade getEscolhaPrimeiroJogador() {
        return escolhaPrimeiroJogador;
    }

    public TipoParidade getEscolhaSegundoJogador() {
        return escolhaSegundoJogador;
    }
}