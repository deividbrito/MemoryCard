package memorycard.model;

public class Carta {
    private String valor;
    private boolean encontrada = false;
    private boolean revelada = false;
    private int dono = 0;

    public Carta(String valor) {
        this.valor = valor;
    }

    public String getValor() { return valor; }
    public boolean isEncontrada() { return encontrada; }
    public void setEncontrada(boolean encontrada) { this.encontrada = encontrada; }
    public boolean isRevelada() { return revelada; }
    public void setRevelada(boolean revelada) { this.revelada = revelada; }
    public void setDono(int dono) {
    this.dono = dono;
}

public int getDono() {
    return dono;
}

    @Override
    public String toString() {
        if (encontrada || revelada) return valor;
        return "X";
    }
}
