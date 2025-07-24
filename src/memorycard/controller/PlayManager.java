package memorycard.controller;

import memorycard.config.Config;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class PlayManager {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Scanner scanner;

    public PlayManager() {
        try {
            this.socket = new Socket(Config.SERVIDOR_IP, Config.PORTA);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.scanner = new Scanner(System.in);
        } catch (IOException e) {
            System.out.println("Erro ao conectar ao servidor: " + e.getMessage());
        }
    }

    public void iniciar() {
        Thread receptor = new Thread(this::receberMensagens);
        receptor.start();

        while (true) {
            String entrada = scanner.nextLine();
            out.println(entrada);
        }
    }

    private void receberMensagens() {
        try {
            String linha;
            boolean lendoTabuleiro = false;
            StringBuilder tabuleiro = new StringBuilder();

            while ((linha = in.readLine()) != null) {
                if (linha.equals("TABULEIRO_START")) {
                    lendoTabuleiro = true;
                    tabuleiro.setLength(0);
                    continue;
                }
                if (linha.equals("TABULEIRO_FIM")) {
                    lendoTabuleiro = false;
                    System.out.println("\n=== TABULEIRO ===");
                    System.out.println(tabuleiro.toString());
                    continue;
                }
                if (lendoTabuleiro) {
                    tabuleiro.append(linha).append("\n");
                } else {
                    System.out.println(linha);
                }
            }
        } catch (IOException e) {
            System.out.println("Conexão encerrada: " + e.getMessage());
        }
    }
}
