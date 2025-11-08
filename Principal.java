/**
 * Clase Principal
 * ----------------
 * Punto de entrada del programa.
 * Si no se proporcionan argumentos, lanza tres hilos ProductorExamenes
 * y tres Examinador (Rosa, Miguel, Carlos).
 * Si se proporcionan nombres en args, crea tantos hilos como alumnos indicados.
 */
public class Principal {
    public static void main(String[] args) throws InterruptedException {
        BufferExamenes generador = new BufferExamenes();

        // Listas para controlar los hilos y esperar su finalización
        ProductorExamenes[] productores;
        Examinador[] examinadores;

        if (args.length == 0) {
            productores = new ProductorExamenes[3];
            examinadores = new Examinador[3];
            productores[0] = new ProductorExamenes(generador);
            examinadores[0] = new Examinador("Rosa", generador);
            productores[1] = new ProductorExamenes(generador);
            examinadores[1] = new Examinador("Miguel", generador);
            productores[2] = new ProductorExamenes(generador);
            examinadores[2] = new Examinador("Carlos", generador);
        } else {
            productores = new ProductorExamenes[args.length];
            examinadores = new Examinador[args.length];
            for (int i = 0; i < args.length; i++) {
                productores[i] = new ProductorExamenes(generador);
            }
            for (int i = 0; i < args.length; i++) {
                examinadores[i] = new Examinador(args[i], generador);
            }
        }

        // Espera a que todos los hilos terminen antes de salir
        for (ProductorExamenes p : productores) {
            p.getHilo().join();
        }
        for (Examinador e : examinadores) {
            e.getHilo().join();
        }

        System.out.println("Todos los exámenes han finalizado correctamente.");
    }
}
