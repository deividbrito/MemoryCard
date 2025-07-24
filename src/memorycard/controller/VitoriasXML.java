package memorycard;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class VitoriasXML {

    private static final String ARQUIVO = "vitorias.xml";

    public static void registrarVitoria(String jogador) {
        try {
            File arquivo = new File(ARQUIVO);

            if (!arquivo.exists()) {
                // Cria arquivo com estrutura básica
                try (PrintWriter pw = new PrintWriter(new FileWriter(arquivo))) {
                    pw.println("<vitorias>");
                    pw.println("  <jogador nome=\"" + jogador + "\">1</jogador>");
                    pw.println("</vitorias>");
                }
            } else {
                // Se arquivo existe, lê conteúdo e atualiza contagem
                StringBuilder conteudo = new StringBuilder();
                boolean encontrouJogador = false;

                try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
                    String linha;
                    while ((linha = br.readLine()) != null) {
                        if (linha.contains("<jogador nome=\"" + jogador + "\">")) {
                            // Atualiza o número da vitória
                            int inicio = linha.indexOf(">") + 1;
                            int fim = linha.indexOf("</jogador>");
                            int count = Integer.parseInt(linha.substring(inicio, fim).trim());
                            count++;
                            linha = linha.substring(0, inicio) + count + linha.substring(fim);
                            encontrouJogador = true;
                        }
                        conteudo.append(linha).append("\n");
                    }
                }

                if (!encontrouJogador) {
                    // Adiciona novo jogador antes do fechamento da tag </vitorias>
                    int idx = conteudo.lastIndexOf("</vitorias>");
                    if (idx != -1) {
                        conteudo.insert(idx, "  <jogador nome=\"" + jogador + "\">1</jogador>\n");
                    }
                }

                // Reescreve o arquivo com a atualização
                try (PrintWriter pw = new PrintWriter(new FileWriter(arquivo))) {
                    pw.print(conteudo.toString());
                }
            }
            System.out.println("Vitoria registrada para: " + jogador);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static Map<String, Integer> lerRanking() {
        Map<String, Integer> ranking = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.contains("<jogador nome=\"")) {
                    int nomeInicio = linha.indexOf("\"") + 1;
                    int nomeFim = linha.indexOf("\"", nomeInicio);
                    String nome = linha.substring(nomeInicio, nomeFim);

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
}
