package memorycard.model;

import java.util.*;

public class Tabuleiro {
    private List<Carta> cartas;
    private int linhas = 4;   // padrão, pode ser ajustado
    private int colunas = 6;

    public Tabuleiro(int qtdPares) {
        List<String> valores = new ArrayList<>();
        for (int i = 0; i < qtdPares; i++) {
            char letra = (char) ('A' + i);
            valores.add(String.valueOf(letra));
            valores.add(String.valueOf(letra));
        }
        Collections.shuffle(valores);
        cartas = new ArrayList<>();
        for (String v : valores) {
            cartas.add(new Carta(v));
        }

        // ajustar dimensões se necessário
        calcularDimensoes(qtdPares * 2);
    }

    private void calcularDimensoes(int totalCartas) {
        // tenta gerar algo próximo de um retângulo
        for (int i = 1; i <= Math.sqrt(totalCartas); i++) {
            if (totalCartas % i == 0) {
                linhas = i;
                colunas = totalCartas / i;
            }
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

    public synchronized boolean verificarPar(int i1, int i2) {
        Carta c1 = getCarta(i1);
        Carta c2 = getCarta(i2);

        if (c1 != null && c2 != null && c1.getValor().equals(c2.getValor())) {
            c1.setEncontrada(true);
            c2.setEncontrada(true);
            return true;
        }
        return false;
    }

    public String gerarRepresentacao() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cartas.size(); i++) {
            Carta c = cartas.get(i);
            if (c.isRevelada() || c.isEncontrada()) {
                sb.append("[").append(c.getValor());
                if (c.isEncontrada()) {
                    sb.append("_").append(c.getDono()); // Exemplo: [A_1] ou [B_2]
                }
                sb.append("]");
            } else {
                sb.append("[X]");
            }

            if ((i + 1) % 6 == 0) sb.append("\n");
            else sb.append(" ");
        }
        return sb.toString();
    }


    public List<Carta> getCartas() {
        return cartas;
    }
}
