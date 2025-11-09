package org.example;

import org.example.dao.LibroDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class LibroController {

    private LibroDAO dao;

    // Constructor: inicializa el DAO usando la conexión a BD
    public LibroController(Connection conn) {
        this.dao = new LibroDAO(conn);
        System.out.println(" LibroController inicializado con la nueva estructura de BD");
    }

    // Obtener todos los libros
    public ArrayList<Libro> getTodosLibros() {
        try {
            System.out.println(" Obteniendo TODOS los libros...");
            ArrayList<Libro> resultados = dao.getTodosLibros();
            System.out.println(" Se encontraron " + resultados.size() + " libros");
            return resultados;
        } catch (SQLException e) {
            System.err.println(" Error obteniendo todos los libros: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Obtener libros por género
    public ArrayList<Libro> buscarPorGenero(String genero) {
        try {
            System.out.println(" Buscando por género: " + genero);
            ArrayList<Libro> resultados = dao.getLibrosPorGenero(genero);
            System.out.println(" Se encontraron " + resultados.size() + " libros para género: " + genero);
            return resultados;
        } catch (SQLException e) {
            System.err.println(" Error buscando por género: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Obtener libros por autor
    public ArrayList<Libro> buscarPorAutor(String nombreAutor) {
        try {
            System.out.println(" Buscando por autor: " + nombreAutor);
            ArrayList<Libro> resultados = dao.getLibrosPorAutor(nombreAutor);
            System.out.println(" Se encontraron " + resultados.size() + " libros para autor: " + nombreAutor);
            return resultados;
        } catch (SQLException e) {
            System.err.println(" Error buscando por autor: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Búsqueda por palabra clave (título, género, descripción, editorial, país o autor)
    public ArrayList<Libro> buscarPorPalabraClave(String palabra) {
        try {
            System.out.println(" Buscando por palabra clave: " + palabra);
            ArrayList<Libro> todos = dao.getTodosLibros();
            ArrayList<Libro> resultados = new ArrayList<>();

            for (Libro l : todos) {
                if (l.getTitulo().toLowerCase().contains(palabra.toLowerCase()) ||
                        (l.getGenero() != null && l.getGenero().toLowerCase().contains(palabra.toLowerCase())) ||
                        (l.getDescripcion() != null && l.getDescripcion().toLowerCase().contains(palabra.toLowerCase())) ||
                        (l.getEditorial() != null && l.getEditorial().toLowerCase().contains(palabra.toLowerCase())) ||
                        (l.getPais() != null && l.getPais().toLowerCase().contains(palabra.toLowerCase())) ||
                        (l.getAutor() != null && l.getAutor().getNombre().toLowerCase().contains(palabra.toLowerCase()))) {
                    resultados.add(l);
                }
            }
            System.out.println(" Se encontraron " + resultados.size() + " libros para palabra clave: " + palabra);
            return resultados;
        } catch (Exception e) {
            System.err.println(" Error en búsqueda por palabra clave: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


}
