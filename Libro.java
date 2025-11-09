package org.example;

import java.io.Serializable;
import java.util.Date;

public class Libro implements Serializable {
    private static final long serialVersionUID = 1L;

    private int libroId;
    private String titulo;
    private String descripcion;
    private String pais;
    private String editorial;
    private double precio;
    private Date fechaPublicacion;
    private String genero;
    private Autor autor;

    public Libro(int libroId, String titulo, String descripcion, String pais, String editorial,
                 double precio, Date fechaPublicacion, String genero, Autor autor) {
        this.libroId = libroId;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.pais = pais;
        this.editorial = editorial;
        this.precio = precio;
        this.fechaPublicacion = fechaPublicacion;
        this.genero = genero;
        this.autor = autor; // Relación con la clase Autor
    }

    // Getters y setters para todos los atributos
    public int getLibroId() { return libroId; }
    public void setLibroId(int libroId) { this.libroId = libroId; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }
    public String getEditorial() { return editorial; }
    public void setEditorial(String editorial) { this.editorial = editorial; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public Date getFechaPublicacion() { return fechaPublicacion; }
    public void setFechaPublicacion(Date fechaPublicacion) { this.fechaPublicacion = fechaPublicacion; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    public Autor getAutor() { return autor; }
    public void setAutor(Autor autor) { this.autor = autor; }

    @Override
    public String toString() {
        return "Libro{" +
                "titulo='" + titulo + '\'' +
                ", pais='" + pais + '\'' +
                ", editorial='" + editorial + '\'' +
                ", precio=" + precio +
                ", fechaPublicacion=" + fechaPublicacion +
                ", genero='" + genero + '\'' +
                ", autor=" + (autor != null ? autor.getNombre() : "Desconocido") +
                '}';
    }
}
