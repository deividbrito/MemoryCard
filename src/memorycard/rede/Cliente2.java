package memorycard;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente2 {
    public static void main(String[] args) throws IOException {
        // Altere aqui para o IP do servidor na LAN
        Socket socket = new Socket("127.0.0.1", 12345);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        Scanner scanner = new Scanner(System.in);

        // Thread para receber mensagens
        new Thread(() -> {
            try {
                String linha;
                boolean lendoTabuleiro = false;
                StringBuilder repr = new StringBuilder();

                while ((linha = in.readLine()) != null) {
                    if (linha.equals("TABULEIRO_START")) {
                        lendoTabuleiro = true;
                        repr.setLength(0);
                        continue;
                    }
                    if (linha.equals("TABULEIRO_FIM")) {
                        lendoTabuleiro = false;
                        System.out.println("\n=== TABULEIRO ===");
                        System.out.println(repr.toString());
                        continue;
                    }
                    if (lendoTabuleiro) {
                        repr.append(linha).append("\n");
                    } else {
                        System.out.println(linha);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        // Loop para enviar jogadas
        while (true) {
            System.out.print("Digite duas posicoes (ex: 0 5): ");
            String jogada = scanner.nextLine();
            out.println(jogada);
        }
    }
}

