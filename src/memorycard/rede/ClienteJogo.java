package memorycard.rede;

import memorycard.view.TelaInicial;
import memorycard.view.TelaJogo;

import javax.swing.*;
import java.io.*;
import java.net.*;

public class ClienteJogo {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private TelaJogo tela;

    public ClienteJogo(String ip, int porta, TelaJogo tela) {
        try {
            this.socket = new Socket(ip, porta);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.tela = tela;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar: " + e.getMessage());
            System.exit(1);
        }
    }

    public void iniciar() {
        Thread receptor = new Thread(this::receberMensagens);
        receptor.start();
    }

    public void enviarJogada(String jogada) {
        out.println(jogada);
    }

    private void receberMensagens() {
        try {
            String linha;
            boolean lendoTabuleiro = false;
            StringBuilder tabuleiro = new StringBuilder();

            while ((linha = in.readLine()) != null) {
                System.out.println("[DEBUG] Mensagem recebida: " + linha);

                if (linha.equals("SUA_VEZ")) {
                    tela.setMinhaVez(true);
                    continue;
                } else if (linha.equals("AGUARDE")) {
                    tela.setMinhaVez(false);
                    continue;
                } else if (linha.startsWith("PONTOS ")) {
                    int pontos = Integer.parseInt(linha.split(" ")[1]);
                    tela.atualizarPontos(pontos);
                    continue;
                } else if (linha.startsWith("PLACAR_FINAL ")) {
                    String[] partes = linha.split(" ");
                    int p1 = Integer.parseInt(partes[1]);
                    int p2 = Integer.parseInt(partes[2]);
                    tela.mostrarResultadoFinal(p1, p2);
                    continue;
                } else if (linha.equalsIgnoreCase("Deseja jogar novamente? (sim/nao)")) {
                    tela.perguntarNovaPartida(resposta -> enviarJogada(resposta));
                    continue;
                } else if (linha.equalsIgnoreCase("Obrigado por jogar!")) {
                    tela.adicionarMensagem("Obrigado por jogar!");
                    SwingUtilities.invokeLater(() -> {
                        tela.dispose();
                        new TelaInicial();
                    });
                    break; // encerra o loop
                }

                if (linha.equals("TABULEIRO_START")) {
                    lendoTabuleiro = true;
                    tabuleiro.setLength(0);
                    continue;
                }

                if (linha.equals("TABULEIRO_FIM")) {
                    lendoTabuleiro = false;
                    String[] linhas = tabuleiro.toString().split("\n");
                    tela.atualizarTabuleiro(linhas);
                    continue;
                }

                if (lendoTabuleiro) {
                    tabuleiro.append(linha).append("\n");
                } else {
                    tela.adicionarMensagem(linha);
                }
            }
        } catch (IOException e) {
            tela.adicionarMensagem("Conexão encerrada.");
        }
    }
}
