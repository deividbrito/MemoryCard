package memorycard.rede;

import memorycard.controller.VitoriasXML;
import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) throws IOException {
        ServerSocket servidor = new ServerSocket(12345);
        System.out.println("Servidor aguardando na porta 12345...");

        Socket jogador1 = servidor.accept();
        System.out.println("Jogador 1 conectado!");
        BufferedReader in1 = new BufferedReader(new InputStreamReader(jogador1.getInputStream()));
        PrintWriter out1 = new PrintWriter(jogador1.getOutputStream(), true);

        String nome1 = in1.readLine();
        if (nome1.startsWith("NOME:")) nome1 = nome1.substring("NOME:".length());
        out1.println("VOCE_EH_JOGADOR1");

        Socket jogador2 = servidor.accept();
        System.out.println("Jogador 2 conectado!");
        BufferedReader in2 = new BufferedReader(new InputStreamReader(jogador2.getInputStream()));
        PrintWriter out2 = new PrintWriter(jogador2.getOutputStream(), true);

        String nome2 = in2.readLine();
        if (nome2.startsWith("NOME:")) nome2 = nome2.substring("NOME:".length());

        out1.println("OPONENTE " + nome2);
        out2.println("OPONENTE " + nome1);

        ServidorJogo servidorJogo = new ServidorJogo(12);

        boolean jogarNovamente = true;
        while (jogarNovamente) {
            servidorJogo.iniciarNovaPartida();
            int vez = 1;

            while (!servidorJogo.jogoFinalizado()) {
                try {
                    if (vez == 1) {
                        out1.println("SUA_VEZ");
                        out2.println("AGUARDE");
                    } else {
                        out2.println("SUA_VEZ");
                        out1.println("AGUARDE");
                    }

                    servidorJogo.enviarTabuleiro(out1, out2);

                    String jogada = (vez == 1) ? in1.readLine() : in2.readLine();
                    if (vez == 1) {
                        servidorJogo.processarJogada(jogada, 1, out1, out2);
                        vez = 2;
                    } else {
                        servidorJogo.processarJogada(jogada, 2, out2, out1);
                        vez = 1;
                    }
                } catch (IOException e) {
                    System.out.println("Erro ao processar jogada: " + e.getMessage());
                    break;
                }
            }

            servidorJogo.pararCronometro();
            int tempo = servidorJogo.getTempoFinal();

            out1.println("Fim de jogo! Tempo total: " + tempo + " segundos.");
            out2.println("Fim de jogo! Tempo total: " + tempo + " segundos.");

            int pontos1 = servidorJogo.getPontosJogador1();
            int pontos2 = servidorJogo.getPontosJogador2();

            out1.println("RESULTADO " + pontos1 + " " + pontos2);
            out2.println("RESULTADO " + pontos1 + " " + pontos2);

            // Salvar no XML: ranking + histórico
            if (pontos1 > pontos2) {
                VitoriasXML.registrarVitoria(nome1);
                VitoriasXML.registrarPartida(nome1, pontos1, nome2, pontos2, tempo);
            } else if (pontos2 > pontos1) {
                VitoriasXML.registrarVitoria(nome2);
                VitoriasXML.registrarPartida(nome2, pontos2, nome1, pontos1, tempo);
            } else {
                VitoriasXML.registrarPartida(nome1, pontos1, nome2, pontos2, tempo);
            }

            out1.println("Deseja jogar novamente? (sim/nao)");
            out2.println("Deseja jogar novamente? (sim/nao)");

            String resp1 = in1.readLine();
            String resp2 = in2.readLine();

            jogarNovamente = resp1 != null && resp2 != null &&
                    resp1.equalsIgnoreCase("sim") && resp2.equalsIgnoreCase("sim");

            if (!jogarNovamente) {
                out1.println("Obrigado por jogar!");
                out2.println("Obrigado por jogar!");
            }
        }

        in1.close(); in2.close(); out1.close(); out2.close();
        jogador1.close(); jogador2.close(); servidor.close();
    }
}
