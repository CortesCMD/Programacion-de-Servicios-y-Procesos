/**
 * Clase Examinador
 * -----------------
 * Cada examinador representa un alumno que consume un examen del buffer
 * y simula la realización de 10 preguntas, eligiendo respuestas aleatorias.
 */
import java.util.Random;

public class Examinador implements Runnable {
    private final Thread hilo;
    private final BufferExamenes buffer;

    public Thread getHilo() {
        return hilo;
    }

    public Examinador(String alumno, BufferExamenes generador) {
        this.hilo = new Thread(this, alumno);
        this.hilo.setPriority(Thread.NORM_PRIORITY - 1); // Menor prioridad que productores
        this.buffer = generador;
        this.hilo.start();
    }

    @Override
    public void run() {
        String codigoExamen = this.buffer.consumirExamen();
        if (codigoExamen != null) {
            Random rnd = new Random();
            char[] opciones = {'A', 'B', 'C', 'D', '-'};
            for (int i = 1; i <= 10; i++) {
                char resp = opciones[rnd.nextInt(opciones.length)];
                System.out.println(codigoExamen + ";" + this.hilo.getName() + "; Pregunta " + i + ";" + resp);
                try {
                    Thread.sleep(20 + rnd.nextInt(80)); // Simula el tiempo de respuesta
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        } else {
            System.out.println("Agotado tiempo de espera y no hay más exámenes");
        }
    }
}
