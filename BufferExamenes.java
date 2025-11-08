/**
 * Clase BufferExamenes
 * ---------------------
 * Recurso compartido por los hilos productores (ProductorExamenes)
 * y consumidores (Examinador). Este implementa una cola de exámenes que es
 * sincronizada mediante los métodos synchronized, wait() y notifyAll() que garantiza la coordinación entre hilos.
 */
import java.util.LinkedList;
import java.util.Queue;

public class BufferExamenes {
    private final Queue<String> colaExamenes;

    public BufferExamenes() {
        colaExamenes = new LinkedList<>();
    }

    /**
     * Añade un nuevo examen al buffer y notifica a los consumidores
     * de que hay examenes disponibles para ser procesados.
     */
    public synchronized void fabricarNuevoExamen(String codigo) {
        colaExamenes.add(codigo);
        System.out.println("Producido examen " + codigo);
        notifyAll();
    }

    /**
     * Extrae un examen del buffer. Si no hay exámenes disponibles,
     * el hilo consumidor espera hasta que se genere uno.
     */
    public synchronized String consumirExamen() {
        while (colaExamenes.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        return colaExamenes.poll();
    }
}
