package memorycard.controller;

import java.io.*;
import java.util.*;

public class XMLManager {

    private static final String ARQUIVO = "vitorias.xml";

    public static Map<String, Integer> carregarRanking() {
        Map<String, Integer> ranking = new HashMap<>();

        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) return ranking;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().startsWith("<jogador")) {
                    String nome = linha.substring(linha.indexOf("\"") + 1, linha.lastIndexOf("\""));
                    int inicio = linha.indexOf(">") + 1;
                    int fim = linha.indexOf("</jogador>");
                    int pontos = Integer.parseInt(linha.substring(inicio, fim).trim());
                    ranking.put(nome, pontos);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ranking;
    }

    public static void exibirRanking() {
        Map<String, Integer> ranking = carregarRanking();
        System.out.println("=== RANKING DE VITÓRIAS ===");
        ranking.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue() + " vitória(s)"));
    }
}
