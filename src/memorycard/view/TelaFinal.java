package memorycard.view;

import javax.swing.*;
import java.awt.*;

public class TelaFinal extends JFrame {

    public TelaFinal(String mensagemFinal) {
        setTitle("Fim de Jogo");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painel = ComponentesUtil.criarPainel(ComponentesUtil.COR_PRIMARIA);
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));

        JLabel mensagem = ComponentesUtil.criarLabel(mensagemFinal, 20);
        mensagem.setAlignmentX(Component.CENTER_ALIGNMENT);
        mensagem.setHorizontalAlignment(SwingConstants.CENTER);

        JButton btnMenu = ComponentesUtil.criarBotao("Voltar ao Menu", 16);
        JButton btnSair = ComponentesUtil.criarBotao("Sair", 16);

        btnMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSair.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnMenu.addActionListener(e -> {
            dispose();
            new TelaInicial();
        });

        btnSair.addActionListener(e -> System.exit(0));

        painel.add(Box.createVerticalStrut(20));
        painel.add(mensagem);
        painel.add(Box.createVerticalStrut(30));
        painel.add(btnMenu);
        painel.add(Box.createVerticalStrut(10));
        painel.add(btnSair);

        add(painel);
        setVisible(true);
    }
}
