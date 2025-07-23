package memorycard.model;

public class Cronometro extends Thread {
    private boolean rodando = true;
    private int segundos = 0;

    @Override
    public void run() {
        try {
            while (rodando) {
                Thread.sleep(1000);
                segundos++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void parar() {
        rodando = false;
    }

    public int getSegundos() {
        return segundos;
    }

    public boolean isRodando() {
        return rodando;
    }
}
