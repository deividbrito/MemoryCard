package memorycard;

import java.util.*;

public class Tabuleiro {
    private List<Carta> cartas;
    private int linhas = 4;
    private int colunas = 6;

    public Tabuleiro() {
        List<String> valores = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            char letra = (char) ('A' + i);
            valores.add(String.valueOf(letra));
            valores.add(String.valueOf(letra));
        }
        Collections.shuffle(valores);
        cartas = new ArrayList<>();
        for (String v : valores) {
            cartas.add(new Carta(v));
        }
    }

    public synchronized boolean posicaoValida(int pos) {
        return pos >= 0 && pos < cartas.size();
    }

    public synchronized Carta getCarta(int pos) {
        return cartas.get(pos);
    }

    public synchronized boolean jogoCompleto() {
        for (Carta c : cartas) {
            if (!c.isEncontrada()) return false;
        }
        return true;
    }

    public synchronized String gerarRepresentacao() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                int idx = i * colunas + j;
                Carta c = cartas.get(idx);
                sb.append("[").append(c.toString()).append("] ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
