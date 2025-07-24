package memorycard.view;

import memorycard.controller.VitoriasXML;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class TelaRanking extends JFrame {

    public TelaRanking() {
        setTitle("Ranking e Histórico de Partidas");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painelPrincipal = ComponentesUtil.criarPainel(ComponentesUtil.COR_PRIMARIA);
        painelPrincipal.setLayout(new BorderLayout(10, 10));

        JLabel titulo = ComponentesUtil.criarLabel("Ranking de Vitórias e Histórico de Partidas", 22);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        painelPrincipal.add(titulo, BorderLayout.NORTH);

        JPanel painelCentro = new JPanel(new GridLayout(1, 2, 10, 10));
        painelCentro.setBackground(ComponentesUtil.COR_PRIMARIA);

        // RANKING
        JTextArea areaRanking = new JTextArea();
        areaRanking.setEditable(false);
        areaRanking.setFont(ComponentesUtil.fontePadrao(14));
        JScrollPane scrollRanking = new JScrollPane(areaRanking);
        painelCentro.add(scrollRanking);

        // HISTÓRICO
        JTextArea areaHistorico = new JTextArea();
        areaHistorico.setEditable(false);
        areaHistorico.setFont(ComponentesUtil.fontePadrao(14));
        JScrollPane scrollHistorico = new JScrollPane(areaHistorico);
        painelCentro.add(scrollHistorico);

        painelPrincipal.add(painelCentro, BorderLayout.CENTER);

        //BOTÃO VOLTAR
        JButton voltarBtn = ComponentesUtil.criarBotao("Voltar", 16);
        voltarBtn.addActionListener(e -> {
            dispose();
            new TelaInicial();
        });
        JPanel painelSul = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelSul.setBackground(ComponentesUtil.COR_PRIMARIA);
        painelSul.add(voltarBtn);
        painelPrincipal.add(painelSul, BorderLayout.SOUTH);

        exibirRanking(areaRanking);
        exibirHistorico(areaHistorico);

        add(painelPrincipal);
        setVisible(true);
    }

    private void exibirRanking(JTextArea area) {
        Map<String, Integer> ranking = VitoriasXML.lerRanking();
        StringBuilder sb = new StringBuilder();

        sb.append("RANKING DE VITÓRIAS:\n\n");
        if (ranking.isEmpty()) {
            sb.append("Nenhuma vitória registrada ainda.");
        } else {
            ranking.entrySet().stream()
                .sorted((a, b) -> Integer.compare(b.getValue(), a.getValue()))
                .forEach(e -> sb.append(e.getKey())
                        .append(": ")
                        .append(e.getValue())
                        .append(" vitória(s)\n"));
        }

        area.setText(sb.toString());
    }

    private void exibirHistorico(JTextArea area) {
        List<String> partidas = VitoriasXML.lerHistoricoBruto();
        StringBuilder sb = new StringBuilder();
        sb.append("HISTÓRICO DE PARTIDAS (últimas " + partidas.size() + "):\n\n");

        if (partidas.isEmpty()) {
            sb.append("Nenhuma partida registrada.");
        } else {
            for (String rawXml : partidas) {
                String resumo = formatarPartida(rawXml);
                sb.append(resumo).append("\n\n");
            }
        }

        area.setText(sb.toString());
    }

    private String formatarPartida(String xml) {
        try {
            String vencedor = extrairAtributo(xml, "<vencedor", "nome");
            String pontosV = extrairAtributo(xml, "<vencedor", "pontos");
            String oponente = extrairAtributo(xml, "<oponente", "nome");
            String pontosO = extrairAtributo(xml, "<oponente", "pontos");

            String data = extrairTag(xml, "data");
            String hora = extrairTag(xml, "hora");
            String duracao = extrairTag(xml, "duracao");

            return String.format("%s (%s pts) x %s (%s pts)\n %s às %s | %s segundos",
                    vencedor, pontosV, oponente, pontosO, data, hora, duracao);
        } catch (Exception e) {
            return "Erro ao ler partida.";
        }
    }

    private String extrairAtributo(String xml, String tag, String atributo) {
        int start = xml.indexOf(tag);
        int attrIndex = xml.indexOf(atributo + "=\"", start);
        int valStart = attrIndex + atributo.length() + 2;
        int valEnd = xml.indexOf("\"", valStart);
        return xml.substring(valStart, valEnd);
    }

    private String extrairTag(String xml, String tag) {
        int start = xml.indexOf("<" + tag + ">");
        int end = xml.indexOf("</" + tag + ">");
        return xml.substring(start + tag.length() + 2, end).trim();
    }
}
