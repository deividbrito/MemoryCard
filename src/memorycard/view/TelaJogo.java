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

    public TelaJogo(String nomeJogador) {
        setTitle("Memory Card - Jogador: " + nomeJogador);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Norte - Info do jogador
        JPanel topo = new JPanel(new GridLayout(1, 2));
        labelJogador = new JLabel("Jogador: " + nomeJogador);
        labelPontos = new JLabel("Pontos: 0");
        topo.add(labelJogador);
        topo.add(labelPontos);
        add(topo, BorderLayout.NORTH);

        // Centro - Cartas
        painelCartas = new JPanel(new GridLayout(4, 6)); // padrão 24 cartas
        add(painelCartas, BorderLayout.CENTER);

        // Sul - Área de log
        areaLog = new JTextArea(5, 40);
        areaLog.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaLog);
        add(scroll, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void setCliente(ClienteJogo cliente) {
        this.cliente = cliente;
    }

    public void atualizarTabuleiro(String[] linhas) {
        painelCartas.removeAll();
        botoesCartas.clear();

        int idx = 0;
        System.out.println("[DEBUG] Atualizando tabuleiro. É minha vez? " + minhaVez);

        for (String linha : linhas) {
            for (String s : linha.trim().split(" ")) {
                if (s.startsWith("[")) s = s.substring(1);
                if (s.endsWith("]")) s = s.substring(0, s.length() - 1);

                JButton botao = new JButton(s.equals("X") ? "" : s);
                botao.setEnabled(minhaVez && s.equals("X"));
                int pos = idx;

                if (botao.isEnabled()) {
                    System.out.println("[DEBUG] Carta " + pos + " está habilitada.");
                }

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
            System.out.println("[DEBUG] Primeira carta selecionada: " + pos);
        } else {
            String jogada = primeiraSelecao + " " + pos;
            cliente.enviarJogada(jogada);
            System.out.println("[DEBUG] Jogada enviada: " + jogada);
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
        System.out.println("[DEBUG] setMinhaVez: " + vez);
        if (vez) {
            adicionarMensagem("Sua vez de jogar!");
        } else {
            adicionarMensagem("Aguardando o outro jogador...");
        }
    }

    public void adicionarMensagem(String msg) {
        areaLog.append(msg + "\n");
    }

    public void atualizarPontos(int pontos) {
        labelPontos.setText("Pontos: " + pontos);
    }

    public void mostrarResultadoFinal(int pontos1, int pontos2) {
        String nome = labelJogador.getText().replace("Jogador: ", "");
        boolean souJogador1 = cliente != null && cliente.toString().contains("jogador 1");

        int meusPontos = souJogador1 ? pontos1 : pontos2;
        int pontosOponente = souJogador1 ? pontos2 : pontos1;
        String nomeOponente = souJogador1 ? "Jogador 2" : "Jogador 1";

        String vencedor;
        if (meusPontos > pontosOponente) {
            vencedor = "Você venceu!";
        } else if (meusPontos < pontosOponente) {
            vencedor = nomeOponente + " venceu!";
        } else {
            vencedor = "Empate!";
        }

        String msg = vencedor + "\n\nPlacar final:\n" +
                     nome + ": " + meusPontos + " ponto(s)\n" +
                     nomeOponente + ": " + pontosOponente + " ponto(s)";

        JOptionPane.showMessageDialog(this, msg, "Resultado Final", JOptionPane.INFORMATION_MESSAGE);
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
                dispose(); // fecha a janela atual
                new TelaInicial(); // volta para o menu inicial
            }
        });
    }
}
