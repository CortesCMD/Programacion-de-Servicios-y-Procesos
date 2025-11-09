package org.example.dao;

import org.example.Libro;
import org.example.Autor;

import java.sql.*;
import java.util.ArrayList;

public class LibroDAO {
    private Connection conn;

    public LibroDAO(Connection conn) {
        this.conn = conn;
        verificarConexionYTablas(); // Debug
    }

    // Método para obtener todos los libros
    public ArrayList<Libro> getTodosLibros() throws SQLException {
        System.out.println("🗃 Ejecutando consulta: OBTENER TODOS LOS LIBROS");
        ArrayList<Libro> libros = new ArrayList<>();

        String sql = "SELECT l.libro_id, l.titulo, l.descripcion, l.pais, l.editorial, l.precio, l.fecha_publicacion, l.genero, " +
                "a.autor_id, a.nombre AS autor_nombre, a.nacionalidad " +
                "FROM libros l " +
                "JOIN autores a ON l.autor_id = a.autor_id " +
                "ORDER BY l.titulo";

        System.out.println(" SQL: " + sql);

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println(" Recorriendo resultados...");
            while (rs.next()) {
                // Crear autor
                Autor autor = new Autor(
                        rs.getInt("autor_id"),
                        rs.getString("autor_nombre"),
                        rs.getString("nacionalidad")
                );

                // Crear libro
                Libro libro = new Libro(
                        rs.getInt("libro_id"),
                        rs.getString("titulo"),
                        rs.getString("descripcion"),      // NUEVO
                        rs.getString("pais"),             // NUEVO
                        rs.getString("editorial"),        // NUEVO
                        rs.getDouble("precio"),
                        rs.getDate("fecha_publicacion"),  // NUEVO
                        rs.getString("genero"),
                        autor
                );
                libros.add(libro);
                System.out.println(" Libro añadido: " + libro.getTitulo());
            }
        }
        System.out.println("✅ Consulta completada. Total libros: " + libros.size());
        return libros;
    }

    // Obtener libros por género
    public ArrayList<Libro> getLibrosPorGenero(String genero) throws SQLException {
        System.out.println("🗃 Ejecutando consulta por género: " + genero);
        ArrayList<Libro> libros = new ArrayList<>();

        String sql = "SELECT l.libro_id, l.titulo, l.descripcion, l.pais, l.editorial, l.precio, l.fecha_publicacion, l.genero, " +
                "a.autor_id, a.nombre AS autor_nombre, a.nacionalidad " +
                "FROM libros l " +
                "JOIN autores a ON l.autor_id = a.autor_id " +
                "WHERE LOWER(l.genero) LIKE ? " +  // NUEVO: insensible a mayúsculas
                "ORDER BY l.titulo";

        System.out.println(" SQL: " + sql);

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + genero.toLowerCase() + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Autor autor = new Autor(
                        rs.getInt("autor_id"),
                        rs.getString("autor_nombre"),
                        rs.getString("nacionalidad")
                );

                Libro libro = new Libro(
                        rs.getInt("libro_id"),
                        rs.getString("titulo"),
                        rs.getString("descripcion"),      // NUEVO
                        rs.getString("pais"),             // NUEVO
                        rs.getString("editorial"),        // NUEVO
                        rs.getDouble("precio"),
                        rs.getDate("fecha_publicacion"),  // NUEVO
                        rs.getString("genero"),
                        autor
                );
                libros.add(libro);
            }
        }
        System.out.println(" Consulta por género completada. Registros: " + libros.size());
        return libros;
    }

    // Obtener libros por autor
    public ArrayList<Libro> getLibrosPorAutor(String nombreAutor) throws SQLException {
        System.out.println(" Ejecutando consulta por autor: " + nombreAutor);
        ArrayList<Libro> libros = new ArrayList<>();

        String sql = "SELECT l.libro_id, l.titulo, l.descripcion, l.pais, l.editorial, l.precio, l.fecha_publicacion, l.genero, " +
                "a.autor_id, a.nombre AS autor_nombre, a.nacionalidad " +
                "FROM libros l " +
                "JOIN autores a ON l.autor_id = a.autor_id " +
                "WHERE LOWER(a.nombre) LIKE ? " +  // NUEVO: insensible a mayúsculas
                "ORDER BY l.titulo";

        System.out.println(" SQL: " + sql);

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nombreAutor.toLowerCase() + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Autor autor = new Autor(
                        rs.getInt("autor_id"),
                        rs.getString("autor_nombre"),
                        rs.getString("nacionalidad")
                );

                Libro libro = new Libro(
                        rs.getInt("libro_id"),
                        rs.getString("titulo"),
                        rs.getString("descripcion"),      // NUEVO
                        rs.getString("pais"),             // NUEVO
                        rs.getString("editorial"),        // NUEVO
                        rs.getDouble("precio"),
                        rs.getDate("fecha_publicacion"),  // NUEVO
                        rs.getString("genero"),
                        autor
                );
                libros.add(libro);
            }
        }
        System.out.println(" Consulta por autor completada. Registros: " + libros.size());
        return libros;
    }

    // Método de debug para verificar la base de datos
    private void verificarConexionYTablas() {
        try {
            System.out.println(" Verificando conexión y tablas...");

            if (conn != null && !conn.isClosed()) {
                System.out.println(" Conexión a BD activa");

                DatabaseMetaData meta = conn.getMetaData();

                ResultSet tables = meta.getTables(null, null, "libros", null);
                System.out.println(tables.next() ? " Tabla 'libros' existe" : " Tabla 'libros' NO existe");

                tables = meta.getTables(null, null, "autores", null);
                System.out.println(tables.next() ? " Tabla 'autores' existe" : " Tabla 'autores' NO existe");

                // Contar registros
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM libros")) {
                    if (rs.next())
                        System.out.println(" Total de libros: " + rs.getInt("total"));
                }

            } else {
                System.out.println(" Conexión a BD no disponible");
            }
        } catch (Exception e) {
            System.out.println(" Error verificando tablas: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

