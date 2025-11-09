package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;

public class Main {

    public static void main(String[] args) {
        int puerto = 5000; // Puerto del servidor

        // Inicializar conexión a la base de datos
        Conector conector = new Conector();
        Connection conn = conector.conectar();

        if (conn == null) {
            System.out.println(" No se pudo establecer la conexión a la base de datos. Saliendo...");
            return;
        }

        try {
            // Creamos el LibroController usando la conexión
            LibroController manager = new LibroController(conn);



            // Creamos el servidor
            ServerSocket servidor = new ServerSocket(puerto);
            System.out.println(" Servidor iniciado en el puerto " + puerto);
            System.out.println(" Esperando conexiones de clientes...");

            // Ciclo infinito para aceptar clientes
            while (true) {
                Socket cliente = servidor.accept();
                System.out.println(" Nuevo cliente conectado desde " + cliente.getInetAddress());

                // Crear un hilo independiente para atender al cliente
                new HiloEscuchador(cliente, manager);
            }

        } catch (IOException e) {
            System.out.println(" Error en el servidor: " + e.getMessage());
            e.printStackTrace();
        }
        // La conexión a BD permanece abierta mientras el servidor esté activo
    }
}
