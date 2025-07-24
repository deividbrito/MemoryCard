package memorycard.view;

import memorycard.VitoriasXML;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class TelaRanking extends JFrame {

    public TelaRanking() {
        setTitle("Ranking de Vitórias");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painel = ComponentesUtil.criarPainel(ComponentesUtil.COR_PRIMARIA);
        painel.setLayout(new BorderLayout());

        JLabel titulo = ComponentesUtil.criarLabel("Ranking de Jogadores", 22);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        painel.add(titulo, BorderLayout.NORTH);

        JTextArea areaRanking = new JTextArea();
        areaRanking.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaRanking);
        painel.add(scroll, BorderLayout.CENTER);

        JButton voltarBtn = ComponentesUtil.criarBotao("Voltar", 16);
        voltarBtn.addActionListener(e -> {
            dispose();
            new TelaInicial();
        });
        painel.add(voltarBtn, BorderLayout.SOUTH);

        exibirRanking(areaRanking);

        add(painel);
        setVisible(true);
    }

    private void exibirRanking(JTextArea area) {
        Map<String, Integer> ranking = VitoriasXML.lerRanking();
        if (ranking.isEmpty()) {
            area.setText("Nenhuma vitória registrada ainda.");
        } else {
            StringBuilder sb = new StringBuilder();
            ranking.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
                .forEach(e -> sb.append(e.getKey()).append(": ").append(e.getValue()).append(" vitória(s)\n"));
            area.setText(sb.toString());
        }
    }
}
