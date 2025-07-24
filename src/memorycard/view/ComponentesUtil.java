package memorycard.view;

import javax.swing.*;
import java.awt.*;

public class ComponentesUtil {

    // cores padrao
    public static final Color COR_PRIMARIA = new Color(50, 50, 100);
    public static final Color COR_SECUNDARIA = new Color(200, 200, 255);
    public static final Color COR_TEXTO = Color.WHITE;

    // fonte padrao
    public static Font fontePadrao(int tamanho) {
        return new Font("Arial", Font.BOLD, tamanho);
    }

    // botao
    public static JButton criarBotao(String texto, int tamanhoFonte) {
        JButton botao = new JButton(texto);
        botao.setFont(fontePadrao(tamanhoFonte));
        botao.setBackground(COR_SECUNDARIA);
        botao.setForeground(COR_PRIMARIA);
        botao.setFocusPainted(false);
        return botao;
    }

    // painel
    public static JPanel criarPainel(Color corFundo) {
        JPanel painel = new JPanel();
        painel.setBackground(corFundo);
        painel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        return painel;
    }

    // label
    public static JLabel criarLabel(String texto, int tamanhoFonte) {
        JLabel label = new JLabel(texto);
        label.setFont(fontePadrao(tamanhoFonte));
        label.setForeground(COR_TEXTO);
        return label;
    }
}
