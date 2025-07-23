package memorycard.model;

public class Partida {
    private Jogador jogador1;
    private Jogador jogador2;
    private Tabuleiro tabuleiro;

    private Jogador jogadorAtual;
    private int primeiraCarta = -1;
    private int segundaCarta = -1;

    public Partida(Jogador j1, Jogador j2, int qtdPares) {
        this.jogador1 = j1;
        this.jogador2 = j2;
        this.tabuleiro = new Tabuleiro(qtdPares);
        this.jogadorAtual = j1;
    }

    public boolean selecionarCarta(int indice) {
        Carta carta = tabuleiro.getCarta(indice);
        if (carta == null || carta.isRevelada() || carta.isEncontrada()) {
            return false;
        }

        carta.setRevelada(true);

        if (primeiraCarta == -1) {
            primeiraCarta = indice;
        } else if (segundaCarta == -1) {
            segundaCarta = indice;
        }

        return true;
    }

    public boolean podeVerificarPar() {
        return primeiraCarta != -1 && segundaCarta != -1;
    }

    public boolean verificarParSelecionado() {
        if (!podeVerificarPar()) return false;

        boolean par = tabuleiro.verificarPar(primeiraCarta, segundaCarta);
        if (par) {
            jogadorAtual.incrementarPontos();
        } else {
            tabuleiro.getCarta(primeiraCarta).setRevelada(false);
            tabuleiro.getCarta(segundaCarta).setRevelada(false);
            alternarTurno();
        }

        primeiraCarta = -1;
        segundaCarta = -1;

        return par;
    }

    private void alternarTurno() {
        jogadorAtual = (jogadorAtual == jogador1) ? jogador2 : jogador1;
    }

    public boolean isJogoFinalizado() {
        return tabuleiro.jogoCompleto();
    }

    public Jogador getVencedor() {
        if (!isJogoFinalizado()) return null;

        if (jogador1.getPontuacao() > jogador2.getPontuacao()) return jogador1;
        if (jogador2.getPontuacao() > jogador1.getPontuacao()) return jogador2;
        return null;
    }

    public Jogador getJogadorAtual() {
        return jogadorAtual;
    }

    public Jogador getJogador1() {
        return jogador1;
    }

    public Jogador getJogador2() {
        return jogador2;
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }
}
