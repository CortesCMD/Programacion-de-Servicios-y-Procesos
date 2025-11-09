package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conector {
    // URL de conexión a la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/LibreriaFantasia?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = "root"; // Usuario de BD
    private static final String PASSWORD = ""; // Contraseña de BD


    private Connection connection; // Objeto Connection para mantener la conexión

    /**
     * Metodo para conectar a la base de datos.
     * Connection si se establece correctamente, null si falla.
     */

    public Connection conectar() {
        try {
            // Conexión mediante DriverManager
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // Verificar que la conexión sea válida
            if (connection.isValid(2)) {
                System.out.println("Conexión a la base de datos establecida correctamente.");
            }
            // Mostrar la base de datos activa
            System.out.println("🔍 Base de datos activa: " + connection.getCatalog());
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            // Consider throwing a custom exception instead of returning null
        }
        return connection;
    }


    /**
     * Cierra la conexión a la base de datos si está abierta.
     */

    public void cerrar() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    System.out.println("Conexión cerrada correctamente.");
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            } finally {
                connection = null; // Evitar reutilización accidental
            }
        }
    }

    /**
     * Metodo de utilidad para comprobar si la conexión sigue activa.
     *  true si está conectada y válida, false en caso contrario.
     */

    public boolean isConectado() {
        try {
            return connection != null && !connection.isClosed() && connection.isValid(2);
        } catch (SQLException e) {
            return false;
        }
    }
}