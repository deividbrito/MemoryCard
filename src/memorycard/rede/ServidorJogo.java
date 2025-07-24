package memorycard.rede;

import memorycard.model.*;

import java.io.PrintWriter;

public class ServidorJogo {

    private Tabuleiro tabuleiro;
    private Cronometro cronometro;
    private int pontosJogador1 = 0;
    private int pontosJogador2 = 0;
    private int qtdPares = 12; // padrão (24 cartas)

    public ServidorJogo() {
        // construtor padrão com 12 pares
    }

    public ServidorJogo(int qtdPares) {
        this.qtdPares = qtdPares;
    }

    public void iniciarNovaPartida() {
        this.tabuleiro = new Tabuleiro(qtdPares); // corrigido aqui
        this.cronometro = new Cronometro();
        this.pontosJogador1 = 0;
        this.pontosJogador2 = 0;
        cronometro.start();
    }

    public boolean jogoFinalizado() {
        return tabuleiro.jogoCompleto();
    }

    public int getTempoFinal() {
        return cronometro.getSegundos();
    }

    public void pararCronometro() {
        if (cronometro != null) {
            cronometro.parar();
        }
    }

    public int getPontosJogador1() {
        return pontosJogador1;
    }

    public int getPontosJogador2() {
        return pontosJogador2;
    }

   public boolean processarJogada(String jogada, int jogador, PrintWriter atual, PrintWriter outro) {
    try {
        String[] partes = jogada.trim().split(" ");
        int p1 = Integer.parseInt(partes[0]);
        int p2 = Integer.parseInt(partes[1]);

        if (!tabuleiro.posicaoValida(p1) || !tabuleiro.posicaoValida(p2) || p1 == p2) {
            atual.println("Jogada inválida!");
            return false;
        }

        Carta c1 = tabuleiro.getCarta(p1);
        Carta c2 = tabuleiro.getCarta(p2);

        if (c1.isEncontrada() || c2.isEncontrada()) {
            atual.println("Carta já encontrada!");
            return false;
        }

        c1.setRevelada(true);
        c2.setRevelada(true);
        enviarTabuleiro(atual, outro);

        if (c1.getValor().equals(c2.getValor())) {
            c1.setEncontrada(true);
            c2.setEncontrada(true);

            if (jogador == 1) {
                pontosJogador1++;
                atual.println("Par encontrado!");
                atual.println("PONTOS " + pontosJogador1);
            } else {
                pontosJogador2++;
                atual.println("Par encontrado!");
                atual.println("PONTOS " + pontosJogador2);
            }

            outro.println("Oponente encontrou um par!");
            return true;
        } else {
            atual.println("Não é par.");
            outro.println("Oponente errou.");
            Thread.sleep(2000);
            c1.setRevelada(false);
            c2.setRevelada(false);
            return false;
        }
    } catch (Exception e) {
        atual.println("Erro na jogada!");
        return false;
    }
}

    public void enviarTabuleiro(PrintWriter... outs) {
        String repr = tabuleiro.gerarRepresentacao();
        for (PrintWriter out : outs) {
            out.println("TABULEIRO_START");
            for (String linha : repr.split("\n")) {
                out.println(linha);
            }
            out.println("TABULEIRO_FIM");
        }
    }
}
