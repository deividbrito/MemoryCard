package memorycard.model;

public class Jogador {
    private String nome;
    private int pontuacao;

    // pontuacao sempre inicializa em 0
    public Jogador(String nome) {
        this.nome = nome;
        this.pontuacao = 0;
    }

    // incrementador de pontos
    public void incrementarPontos() {
        pontuacao++;
    }

    // getters e setters
    public String getNome() {
        return nome;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome + " (" + pontuacao + " pontos)";
    }
}
