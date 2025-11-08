import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Lanzador {
    public static void main(String[] args) throws IOException, InterruptedException {
        List<String> examen1 = List.of("Pepe", "Juan", "Luis");
        List<String> examen2 = List.of("Rosa", "Miguel", "Pedro");

        List<String> cmd1 = new ArrayList<>();
        cmd1.add("java"); cmd1.add("-cp"); cmd1.add(".");
        cmd1.add("Principal"); cmd1.addAll(examen1);

        List<String> cmd2 = new ArrayList<>();
        cmd2.add("java"); cmd2.add("-cp"); cmd2.add(".");
        cmd2.add("Principal"); cmd2.addAll(examen2);

        ProcessBuilder pb1 = new ProcessBuilder(cmd1);
        pb1.redirectOutput(new File("examen1.txt"));
        pb1.redirectError(new File("examen1_err.txt"));

        ProcessBuilder pb2 = new ProcessBuilder(cmd2);
        pb2.redirectOutput(new File("examen2.txt"));
        pb2.redirectError(new File("examen2_err.txt"));

        Process p1 = pb1.start();
        Process p2 = pb2.start();
        p1.waitFor(); p2.waitFor();
        System.out.println("Lanzador: ambos procesos finalizados.");
    }
}