package memorycard.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tabuleiro {
    private Carta[] cartas;

    public Tabuleiro(int qtdPares) {
        inicializarCartas(qtdPares);
    }

    private void inicializarCartas(int qtdPares) {
        List<Carta> baralhoTemp = new ArrayList<>();

        for (int i = 0; i < qtdPares; i++) {
            baralhoTemp.add(new Carta(i, "img" + i + ".png"));
            baralhoTemp.add(new Carta(i, "img" + i + ".png"));
        }

        Collections.shuffle(baralhoTemp);

        cartas = baralhoTemp.toArray(new Carta[0]);
    }

    public Carta getCarta(int indice) {
        if (indice >= 0 && indice < cartas.length) {
            return cartas[indice];
        }
        return null;
    }

    public boolean verificarPar(int i1, int i2) {
        Carta c1 = getCarta(i1);
        Carta c2 = getCarta(i2);

        if (c1 != null && c2 != null && c1.comparar(c2)) {
            c1.setEncontrada(true);
            c2.setEncontrada(true);
            return true;
        }

        return false;
    }

    public int getTamanho() {
        return cartas.length;
    }

    public Carta[] getCartas() {
        return cartas;
    }
}
