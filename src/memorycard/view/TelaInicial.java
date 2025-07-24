package memorycard.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import memorycard.rede.ClienteJogo;

public class TelaInicial extends JFrame {
    public TelaInicial() {
        setTitle("MemoryCard - Menu Inicial");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // centralizando

        JPanel painel = ComponentesUtil.criarPainel(ComponentesUtil.COR_PRIMARIA);
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));

        JLabel titulo = ComponentesUtil.criarLabel("MEMORY CARD", 26);
        titulo.setAlignmentX(ComponentesUtil.criarLabel("", 0).getAlignmentX());
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        JButton btnIniciar = ComponentesUtil.criarBotao("Iniciar Jogo", 18);
        JButton btnRanking = ComponentesUtil.criarBotao("Ranking", 18);
        JButton btnSair = ComponentesUtil.criarBotao("Sair", 18);

        // listeners
        btnIniciar.addActionListener(e -> iniciarJogo());
        btnRanking.addActionListener(e -> abrirRanking());
        btnSair.addActionListener(e -> System.exit(0));

        // espaçamento e adicao
        painel.add(Box.createVerticalStrut(20));
        painel.add(titulo);
        painel.add(Box.createVerticalStrut(30));
        painel.add(btnIniciar);
        painel.add(Box.createVerticalStrut(10));
        painel.add(btnRanking);
        painel.add(Box.createVerticalStrut(10));
        painel.add(btnSair);

        add(painel);
        setVisible(true);
    }

    private void iniciarJogo() {
        String nome = JOptionPane.showInputDialog(this, "Digite seu nome:");
        if (nome == null || nome.trim().isEmpty()) return;

        String ip = JOptionPane.showInputDialog(this, "IP do servidor:", "127.0.0.1");
        if (ip == null || ip.trim().isEmpty()) ip = "127.0.0.1";

        dispose();

        TelaJogo telaJogo = new TelaJogo(nome);
        ClienteJogo cliente = new ClienteJogo(ip, 12345, telaJogo);
        telaJogo.setCliente(cliente);
        cliente.iniciar();
    }


    private void abrirRanking() {
        dispose();
        new TelaRanking(); // abre a tela de ranking
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaInicial::new);
    }
}
