package memorycard.model;

public class Carta {
    private int id;
    private String imagem;
    private boolean virada;
    private boolean encontrada;

    // sempre inicializa virada como false (carta virada pra baixo)
    public Carta(int id, String imagem) {
        this.id = id;
        this.imagem = imagem;
        this.virada = false;
        this.encontrada = false;
    }

    // método pra virar carta
    public void virar() {
        this.virada = !this.virada;
    }

    // compara se carta possui o mesmo ID (forma um par)
    public boolean comparar(Carta outra) {
        return this.id == outra.id;
    }

    public int getId() {
        return id;
    }

    public String getImagem() {
        return imagem;
    }

    public boolean isVirada() {
        return virada;
    }

    public boolean isEncontrada() {
        return encontrada;
    }

    public void setEncontrada(boolean encontrada) {
        this.encontrada = encontrada;
    }

    @Override
    public String toString() {
        return "Carta{" +
                "id=" + id +
                ", virada=" + virada +
                ", encontrada=" + encontrada +
                '}';
    }
}

