package memorycard.model;

public class Tempo extends Thread {
    private Cronometro cronometro;

    public Tempo(Cronometro cronometro) {
        this.cronometro = cronometro;
    }

    @Override
    public void run() {
        try {
            while (cronometro.isAlive() && cronometro.isRodando()) {
                System.out.print("\r️ Tempo: " + cronometro.getSegundos() + "s");
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
