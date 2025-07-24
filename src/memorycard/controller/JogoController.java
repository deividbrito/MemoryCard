package memorycard.controller;

import memorycard.model.*;

public class JogoController {

    private Partida partida;
    private boolean modoOnline = false;

    public void iniciarPartida(Jogador j1, Jogador j2, int qtdPares) {
        partida = new Partida(j1, j2, qtdPares);
    }

    public boolean selecionarCarta(int indice) {
        return partida.selecionarCarta(indice);
    }

    public boolean podeVerificarPar() {
        return partida.podeVerificarPar();
    }

    public boolean verificarParSelecionado() {
        return partida.verificarParSelecionado();
    }

    public boolean isJogoFinalizado() {
        return partida.isJogoFinalizado();
    }

    public Jogador getVencedor() {
        return partida.getVencedor();
    }

    public Jogador getJogadorAtual() {
        return partida.getJogadorAtual();
    }

    public Tabuleiro getTabuleiro() {
        return partida.getTabuleiro();
    }

    public Partida getPartida() {
        return partida;
    }

    public String getTabuleiroComoTexto() {
        return partida.getTabuleiro().gerarRepresentacao();
    }

    public void reiniciarPartida() {
        Jogador j1 = partida.getJogador1();
        Jogador j2 = partida.getJogador2();
        int qtdPares = partida.getTabuleiro().getCartas().size() / 2;
        iniciarPartida(j1, j2, qtdPares);
    }

    public void setModoOnline(boolean online) {
        this.modoOnline = online;
    }

    public boolean isModoOnline() {
        return modoOnline;
    }
}
