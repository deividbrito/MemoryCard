package memorycard.view;

import memorycard.rede.ClienteJogo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TelaJogo extends JFrame {

    private JPanel painelCartas;
    private JTextArea areaLog;
    private JLabel labelJogador, labelPontos;
    private List<JButton> botoesCartas = new ArrayList<>();
    private int primeiraSelecao = -1;
    private ClienteJogo cliente;
    private boolean minhaVez = false;
    private boolean perguntandoNovaPartida = false;
    private String nomeJogador;
    private String nomeOponente = "";

    public TelaJogo(String nomeJogador) {
        this.nomeJogador = nomeJogador;

        setTitle("Memory Card - Jogador: " + nomeJogador);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topo = new JPanel(new GridLayout(1, 2));
        labelJogador = new JLabel("Jogador: " + nomeJogador);
        labelPontos = new JLabel("Pontos: 0");
        topo.add(labelJogador);
        topo.add(labelPontos);
        add(topo, BorderLayout.NORTH);

        painelCartas = new JPanel(new GridLayout(4, 6));
        add(painelCartas, BorderLayout.CENTER);

        areaLog = new JTextArea(5, 40);
        areaLog.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaLog);
        add(scroll, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void setCliente(ClienteJogo cliente) {
        this.cliente = cliente;
    }

    public void setNomeJogador(String nome) {
        this.nomeJogador = nome;
        if (labelJogador != null) {
            labelJogador.setText("Jogador: " + nome);
        }
    }

    public void setNomeOponente(String nomeOponente) {
        this.nomeOponente = nomeOponente;
    }

    public void atualizarTabuleiro(String[] linhas) {
        painelCartas.removeAll();
        botoesCartas.clear();

        int idx = 0;
        for (String linha : linhas) {
            for (String s : linha.trim().split(" ")) {
                if (s.startsWith("[")) s = s.substring(1);
                if (s.endsWith("]")) s = s.substring(0, s.length() - 1);

                JButton botao = new JButton(s.equals("X") ? "" : s);
                botao.setEnabled(minhaVez && s.equals("X"));
                int pos = idx;

                botao.addActionListener(e -> selecionarCarta(pos));
                botoesCartas.add(botao);
                painelCartas.add(botao);
                idx++;
            }
        }

        painelCartas.revalidate();
        painelCartas.repaint();
    }

    private void selecionarCarta(int pos) {
        if (!minhaVez) {
            adicionarMensagem("Ainda não é sua vez!");
            return;
        }

        if (primeiraSelecao == -1) {
            primeiraSelecao = pos;
            botoesCartas.get(pos).setEnabled(false);
        } else {
            String jogada = primeiraSelecao + " " + pos;
            cliente.enviarJogada(jogada);
            primeiraSelecao = -1;
            desabilitarCartas();
        }
    }

    public void desabilitarCartas() {
        for (JButton b : botoesCartas) {
            b.setEnabled(false);
        }
    }

    public void setMinhaVez(boolean vez) {
        this.minhaVez = vez;
        adicionarMensagem(vez ? "Sua vez de jogar!" : "Aguardando o outro jogador...");
    }

    public void adicionarMensagem(String msg) {
        areaLog.append(msg + "\n");
    }

    public void atualizarPontos(int pontos) {
        labelPontos.setText("Pontos: " + pontos);
    }

    public void mostrarResultadoFinal(int pontosJogador1, int pontosJogador2) {
        int meusPontos, pontosOponente;
        String nomeVencedor, mensagem;

        boolean souJogador1 = cliente.isPrimeiroJogador();
        meusPontos = souJogador1 ? pontosJogador1 : pontosJogador2;
        pontosOponente = souJogador1 ? pontosJogador2 : pontosJogador1;

        if (meusPontos > pontosOponente) {
            mensagem = "Você venceu!";
        } else if (meusPontos < pontosOponente) {
            mensagem = nomeOponente + " venceu!";
        } else {
            mensagem = "Empate!";
        }

        String texto = mensagem + "\n\nPlacar final:\n"
                + nomeJogador + ": " + meusPontos + " ponto(s)\n"
                + nomeOponente + ": " + pontosOponente + " ponto(s)";

        JOptionPane.showMessageDialog(this, texto, "Resultado Final", JOptionPane.INFORMATION_MESSAGE);
    }


    public void perguntarNovaPartida(java.util.function.Consumer<String> callback) {
        if (perguntandoNovaPartida) return;
        perguntandoNovaPartida = true;

        SwingUtilities.invokeLater(() -> {
            int opcao = JOptionPane.showConfirmDialog(this,
                    "Deseja jogar novamente?", "Nova partida",
                    JOptionPane.YES_NO_OPTION);

            String resposta = (opcao == JOptionPane.YES_OPTION) ? "sim" : "nao";
            callback.accept(resposta);

            if (resposta.equals("nao")) {
                dispose();
                new TelaInicial();
            }
        });
    }
}
