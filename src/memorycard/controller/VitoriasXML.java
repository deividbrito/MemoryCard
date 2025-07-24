package memorycard.controller;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class VitoriasXML {

    private static final String ARQUIVO = "vitorias.xml";
    private static final int LIMITE_HISTORICO = 10;

    public static void registrarVitoria(String jogador) {
        Map<String, Integer> ranking = lerRanking();
        ranking.put(jogador, ranking.getOrDefault(jogador, 0) + 1);

        List<String> historico = lerHistoricoBruto();

        salvarArquivo(ranking, historico);
        System.out.println("Vitória registrada para: " + jogador);
    }

    public static void registrarPartida(String vencedor, int pontosV, String oponente, int pontosO, int duracaoSegundos) {
        Map<String, Integer> ranking = lerRanking();
        List<String> historico = lerHistoricoBruto();

        // Cria entrada de partida
        String data = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String hora = new SimpleDateFormat("HH:mm").format(new Date());

        String partida = String.format("    <partida>\n" +
                "      <vencedor nome=\"%s\" pontos=\"%d\"/>\n" +
                "      <oponente nome=\"%s\" pontos=\"%d\"/>\n" +
                "      <data>%s</data>\n" +
                "      <hora>%s</hora>\n" +
                "      <duracao>%d</duracao>\n" +
                "    </partida>", vencedor, pontosV, oponente, pontosO, data, hora, duracaoSegundos);

        historico.add(partida);

        // Limita ao histórico mais recente
        if (historico.size() > LIMITE_HISTORICO) {
            historico = historico.subList(historico.size() - LIMITE_HISTORICO, historico.size());
        }

        salvarArquivo(ranking, historico);
    }

    private static void salvarArquivo(Map<String, Integer> ranking, List<String> historico) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO))) {
            pw.println("<dados>");

            pw.println("  <ranking>");
            for (Map.Entry<String, Integer> entry : ranking.entrySet()) {
                pw.printf("    <jogador nome=\"%s\">%d</jogador>%n", entry.getKey(), entry.getValue());
            }
            pw.println("  </ranking>");

            pw.println("  <historico>");
            for (String partida : historico) {
                pw.println(partida);
            }
            pw.println("  </historico>");

            pw.println("</dados>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Integer> lerRanking() {
        Map<String, Integer> ranking = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            boolean dentroRanking = false;
            while ((linha = br.readLine()) != null) {
                linha = linha.trim();
                if (linha.equals("<ranking>")) dentroRanking = true;
                else if (linha.equals("</ranking>")) dentroRanking = false;
                else if (dentroRanking && linha.startsWith("<jogador")) {
                    String nome = linha.substring(linha.indexOf("\"") + 1, linha.lastIndexOf("\""));
                    int valorInicio = linha.indexOf(">") + 1;
                    int valorFim = linha.indexOf("</jogador>");
                    int vitorias = Integer.parseInt(linha.substring(valorInicio, valorFim).trim());
                    ranking.put(nome, vitorias);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ranking;
    }

    public static List<String> lerHistoricoBruto() {
        List<String> partidas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            boolean dentroPartida = false;
            StringBuilder partida = new StringBuilder();

            while ((linha = br.readLine()) != null) {
                if (linha.contains("<partida>")) {
                    dentroPartida = true;
                    partida.setLength(0);
                    partida.append(linha).append("\n");
                } else if (dentroPartida) {
                    partida.append(linha).append("\n");
                    if (linha.contains("</partida>")) {
                        partidas.add(partida.toString().trim());
                        dentroPartida = false;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return partidas;
    }
}
