package memorycard;

import memorycard.view.TelaInicial;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaInicial::new);
    }
}
