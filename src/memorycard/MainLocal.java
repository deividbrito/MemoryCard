package memorycard;

import memorycard.model.*;

import java.util.Scanner;

public class MainLocal {
    public static void main(String[] args) {
        Jogador j1 = new Jogador("Alice");
        Jogador j2 = new Jogador("Pedro");

        //fazendo teste com apenas 3 pares
        Partida partida = new Partida(j1, j2, 3);
        Scanner sc = new Scanner(System.in);

        while (!partida.isJogoFinalizado()) {
            Jogador atual = partida.getJogadorAtual();
            System.out.println("Vez de: " + atual.getNome());

            System.out.println("Estado das cartas:");
            Carta[] cartas = partida.getTabuleiro().getCartas();
            for (int i = 0; i < cartas.length; i++) {
                String estado = cartas[i].isEncontrada() ? "[X]" :
                                cartas[i].isVirada() ? "[*]" : "[ ]";
                System.out.print(i + ":" + estado + "  ");
            }
            System.out.println("\n");

            int i1, i2;
            do {
                System.out.print("Escolha a primeira carta: ");
                i1 = sc.nextInt();
            } while (!partida.selecionarCarta(i1));

            do {
                System.out.print("Escolha a segunda carta: ");
                i2 = sc.nextInt();
            } while (!partida.selecionarCarta(i2));

            System.out.println("Cartas selecionadas: " +
                    cartas[i1].getId() + " e " + cartas[i2].getId());

            if (partida.verificarParSelecionado()) {
                System.out.println("PAR encontrado!");
            } else {
                System.out.println("Não foi par...");
            }

            System.out.println("Placar: " + j1 + " vs " + j2 + "\n");
        }

        Jogador vencedor = partida.getVencedor();
        if (vencedor != null) {
            System.out.println("Vencedor: " + vencedor.getNome());
        } else {
            System.out.println("Empate!");
        }

        sc.close();
    }
}
