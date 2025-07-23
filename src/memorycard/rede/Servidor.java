package memorycard;

import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) throws IOException {
        ServerSocket servidor = new ServerSocket(12345);
        System.out.println("Servidor aguardando na porta 12345...");

        Socket jogador1 = servidor.accept();
        System.out.println("Jogador 1 conectado!");
        Socket jogador2 = servidor.accept();
        System.out.println("Jogador 2 conectado!");

        BufferedReader in1 = new BufferedReader(new InputStreamReader(jogador1.getInputStream()));
        PrintWriter out1 = new PrintWriter(jogador1.getOutputStream(), true);

        BufferedReader in2 = new BufferedReader(new InputStreamReader(jogador2.getInputStream()));
        PrintWriter out2 = new PrintWriter(jogador2.getOutputStream(), true);

        Tabuleiro tabuleiro = new Tabuleiro();

        out1.println("Bem-vindo! Você é o jogador 1.");
        out2.println("Bem-vindo! Você é o jogador 2.");
        enviarTabuleiro(tabuleiro, out1, out2);

        int vez = 1; // 1 ou 2

        while (!tabuleiro.jogoCompleto()) {
            if (vez == 1) {
                out1.println("Vez do jogador 1: ");
                String jogada = in1.readLine();
                processarJogada(tabuleiro, jogada, out1, out2);
                vez = 2;
            } else {
                out2.println("Vez do jogador 2: ");
                String jogada = in2.readLine();
                processarJogada(tabuleiro, jogada, out2, out1);
                vez = 1;
            }
            enviarTabuleiro(tabuleiro, out1, out2);
        }

        out1.println("Fim de jogo");
        out2.println("Fim de jogo");

        jogador1.close();
        jogador2.close();
        servidor.close();
    }

    private static void processarJogada(Tabuleiro tab, String jogada, PrintWriter atual, PrintWriter outro) {
        try {
            String[] partes = jogada.trim().split(" ");
            int p1 = Integer.parseInt(partes[0]);
            int p2 = Integer.parseInt(partes[1]);

            if (!tab.posicaoValida(p1) || !tab.posicaoValida(p2) || p1 == p2) {
                atual.println("Jogada inválida!");
                return;
            }

            Carta c1 = tab.getCarta(p1);
            Carta c2 = tab.getCarta(p2);

            if (c1.isEncontrada() || c2.isEncontrada()) {
                atual.println("Carta ja encontrada!");
                return;
            }

            // Revela temporariamente
            c1.setRevelada(true);
            c2.setRevelada(true);
            enviarTabuleiro(tab, atual, outro);

            if (c1.getValor().equals(c2.getValor())) {
                c1.setEncontrada(true);
                c2.setEncontrada(true);
                atual.println("Par encontrado!");
                outro.println("Oponente encontrou um par!");
            } else {
                atual.println("Nao e par.");
                outro.println("Oponente errou.");
                try { Thread.sleep(2000); } catch (InterruptedException e) {}
                c1.setRevelada(false);
                c2.setRevelada(false);
            }
        } catch (Exception e) {
            atual.println("Erro na jogada!");
        }
    }

    private static void enviarTabuleiro(Tabuleiro tab, PrintWriter... outs) {
        String repr = tab.gerarRepresentacao();
        for (PrintWriter out : outs) {
            out.println("TABULEIRO_START");
            for (String linha : repr.split("\n")) {
                out.println(linha);
            }
            out.println("TABULEIRO_END");
        }
    }
}
