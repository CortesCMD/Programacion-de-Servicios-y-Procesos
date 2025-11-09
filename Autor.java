package org.example;

import java.io.Serializable;

public class Autor implements Serializable {
    private static final long serialVersionUID = 1L; // Para permitir la serialización en red

    private int autorId;
    private String nombre;
    private String nacionalidad;

    public Autor(int autorId, String nombre, String nacionalidad) {
        this.autorId = autorId;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
    }

    // Getters y setters  permiten acceder y modificar los atributos
    public int getAutorId() { return autorId; }
    public void setAutorId(int autorId) { this.autorId = autorId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getNacionalidad() { return nacionalidad; }
    public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }

    @Override
    public String toString() {
        return nombre + " (" + nacionalidad + ")";
    }
}

