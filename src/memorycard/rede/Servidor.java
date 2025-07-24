package memorycard.rede;

import memorycard.VitoriasXML;
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

        out1.println("Bem-vindo! Você é o jogador 1.");
        out2.println("Bem-vindo! Você é o jogador 2.");

        ServidorJogo servidorJogo = new ServidorJogo(12);

        boolean jogarNovamente = true;
        while (jogarNovamente) {
            servidorJogo.iniciarNovaPartida();

            int vez = 1;

            while (!servidorJogo.jogoFinalizado()) {
                try {
                    // Envia a vez antes do tabuleiro para garantir que o cliente saiba se pode clicar
                    if (vez == 1) {
                        out1.println("SUA_VEZ");
                        out2.println("AGUARDE");
                    } else {
                        out2.println("SUA_VEZ");
                        out1.println("AGUARDE");
                    }

                    servidorJogo.enviarTabuleiro(out1, out2);

                    if (vez == 1) {
                        String jogada = in1.readLine();
                        servidorJogo.processarJogada(jogada, 1, out1, out2);
                        vez = 2;
                    } else {
                        String jogada = in2.readLine();
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

            if (pontos1 > pontos2) {
                out1.println("Você venceu!");
                out2.println("Jogador 1 venceu!");
                VitoriasXML.registrarVitoria("Jogador1");
            } else if (pontos2 > pontos1) {
                out1.println("Jogador 2 venceu!");
                out2.println("Você venceu!");
                VitoriasXML.registrarVitoria("Jogador2");
            } else {
                out1.println("Empate!");
                out2.println("Empate!");
            }

            out1.println("Deseja jogar novamente? (sim/nao)");
            out2.println("Deseja jogar novamente? (sim/nao)");

            String resp1 = in1.readLine();
            String resp2 = in2.readLine();

            jogarNovamente = resp1 != null && resp2 != null
                    && resp1.equalsIgnoreCase("sim") && resp2.equalsIgnoreCase("sim");

            if (!jogarNovamente) {
                out1.println("Obrigado por jogar!");
                out2.println("Obrigado por jogar!");
            }
        }

        try {
            in1.close();
            in2.close();
            out1.close();
            out2.close();
            jogador1.close();
            jogador2.close();
            servidor.close();
        } catch (IOException e) {
            System.out.println("Erro ao fechar conexões: " + e.getMessage());
        }
    }
}
