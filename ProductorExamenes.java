/**
 * Clase ProductorExamenes
 * ------------------------
 * Cada instancia representa un hilo productor encargado de generar un nuevo examen
 * con un código único (Ej: E1-2025) y añadirlo al buffer compartido.
 */
import java.time.LocalDateTime;

public class ProductorExamenes implements Runnable {
    private final BufferExamenes buffer;
    private static int numeroExamen = 0;
    private final Thread hilo;

    public ProductorExamenes(BufferExamenes buffer) {
        synchronized (ProductorExamenes.class) {
            numeroExamen++;
        }
        this.hilo = new Thread(this, "E" + numeroExamen);
        this.hilo.setPriority(Thread.NORM_PRIORITY + 1); // Prioridad mayor para el productor
        this.buffer = buffer;
        this.hilo.start();
    }

    @Override
    public void run() {
        int anio = LocalDateTime.now().getYear();
        String codigo = this.hilo.getName() + "-" + anio;
        buffer.fabricarNuevoExamen(codigo);
    }

    public Thread getHilo() {
        return hilo;
    }
}
