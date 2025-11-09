package org.example;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class HiloEscuchador implements Runnable {

    private Thread hilo; // Hilo que gestionará la comunicación con un cliente
    private static int numCliente = 0; // Contador de clientes
    private Socket enchufeAlCliente; // Socket conectado al cliente
    private LibroController manager; // Controlador que gestiona la lógica de libros
    private ObjectInputStream entrada; // Stream para recibir objetos del cliente
    private ObjectOutputStream salida; // Stream para enviar objetos al cliente


    // Constructor: recibe el socket del cliente y el controlador de libros
    public HiloEscuchador(Socket cliente, LibroController manager) {
        numCliente++;
        hilo = new Thread(this, "Cliente" + numCliente);
        this.enchufeAlCliente = cliente;
        this.manager = manager;
        hilo.start(); // Inicia el hilo
    }

    @Override
    public void run() {
        System.out.println(" Estableciendo comunicación con " + hilo.getName());
        try {

            // Inicializar streams de entrada y salida para objetos
            salida = new ObjectOutputStream(enchufeAlCliente.getOutputStream());
            entrada = new ObjectInputStream(enchufeAlCliente.getInputStream());
            System.out.println(" Streams de E/S creados para " + hilo.getName());

            String comando;
            do {

                // Esperar comando del cliente
                System.out.println( hilo.getName() + " esperando comando...");
                comando = (String) entrada.readObject();

                if (comando == null) { // Ignorar comandos nulos
                    System.out.println(" Comando nulo recibido");
                    continue;
                }

                String comandoTrim = comando.trim();
                System.out.println( hilo.getName() + " recibió: '" + comandoTrim + "'");

                if (comandoTrim.equalsIgnoreCase("FIN")) {
                    // Comando para finalizar comunicación
                    salida.writeObject("Hasta pronto, gracias por establecer conexión");
                    System.out.println( hilo.getName() + " ha cerrado la comunicación");
                } else {
                    // Procesar el comando mediante el controlador
                    ArrayList<Libro> resultados = procesarComando(comandoTrim);

                    if (resultados == null) {
                        // Comando no reconocido
                        System.out.println(" Comando no reconocido: " + comandoTrim);
                        salida.writeObject("Comando no reconocido. Use: TODOS, GENERO:, AUTOR: o PALABRA:");
                    } else {
                        // Enviar resultados al cliente
                        System.out.println(" Enviando " + resultados.size() + " libros a " + hilo.getName());
                        salida.writeObject(resultados);
                        System.out.println(" Libros enviados correctamente");
                    }
                }
            } while (comando != null && !comando.trim().equalsIgnoreCase("FIN"));

        } catch (Exception e) {
            System.out.println(" Error en " + hilo.getName() + ": " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Liberar recursos: cerrar streams y socket
            try {
                if (entrada != null) entrada.close();
                if (salida != null) salida.close();
                if (enchufeAlCliente != null) enchufeAlCliente.close();
                System.out.println(" Recursos liberados para " + hilo.getName());
            } catch (Exception e) {
                System.out.println(" Error cerrando recursos: " + e.getMessage());
            }
        }
    }
    /**
     * Interpreta el comando recibido y llama a los métodos del controlador.
     *  comando String con la solicitud del cliente
     *  ArrayList<Libro> con los resultados o null si el comando no existe
     */
    private ArrayList<Libro> procesarComando(String comando) {
        try {
            System.out.println(" Procesando comando: '" + comando + "'");

            if (comando.equalsIgnoreCase("TODOS")) {
                System.out.println( hilo.getName() + " solicitó TODOS los libros");
                return manager.getTodosLibros();
            } else if (comando.toUpperCase().startsWith("GENERO:")) {
                String genero = comando.substring(7).trim();
                System.out.println( hilo.getName() + " buscó por género: '" + genero + "'");
                return manager.buscarPorGenero(genero);
            } else if (comando.toUpperCase().startsWith("AUTOR:")) {
                String autor = comando.substring(6).trim();
                System.out.println( hilo.getName() + " buscó por autor: '" + autor + "'");
                return manager.buscarPorAutor(autor);
            } else if (comando.toUpperCase().startsWith("PALABRA:")) {
                String palabra = comando.substring(8).trim();
                System.out.println( hilo.getName() + " buscó por palabra clave: '" + palabra + "'");
                return manager.buscarPorPalabraClave(palabra);
            } else {
                System.out.println(" Comando no reconocido: '" + comando + "'");
                return null;
            }
        } catch (Exception e) {
            System.out.println(" Error procesando comando '" + comando + "': " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>(); // Retorna lista vacía si hay error
        }
    }
}
