package memorycard;

public class Carta {
    private String valor;
    private boolean encontrada = false;
    private boolean revelada = false;

    public Carta(String valor) {
        this.valor = valor;
    }

    public String getValor() { return valor; }
    public boolean isEncontrada() { return encontrada; }
    public void setEncontrada(boolean encontrada) { this.encontrada = encontrada; }
    public boolean isRevelada() { return revelada; }
    public void setRevelada(boolean revelada) { this.revelada = revelada; }

    @Override
    public String toString() {
        if (encontrada || revelada) return valor;
        return "X";
    }
}
