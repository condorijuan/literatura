package com.juan.literatura.Model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Autor")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Integer fechaNacimiento;
    private Integer fechaFallecimiento;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "autores")
    private Set<Libro> libros = new HashSet<>();

    public Autor( String nombre, String fechaNacimiento, String fechaFallecimiento) {
        this.nombre = nombre;
        this.fechaNacimiento = (fechaNacimiento != null && !fechaNacimiento.isEmpty())
                ? Integer.valueOf(fechaNacimiento)
                : null;
        this.fechaFallecimiento = (fechaFallecimiento != null && !fechaFallecimiento.isEmpty())
                ? Integer.valueOf(fechaFallecimiento)
                : null;
    }

    public Autor() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = (fechaNacimiento != null && !fechaNacimiento.isEmpty())
                ? Integer.valueOf(fechaNacimiento)
                : null;
    }

    public Integer getFechaFallecimiento() {
        return fechaFallecimiento;
    }

    public void setFechaFallecimiento(String fechaFallecimiento) {
        this.fechaFallecimiento = (fechaFallecimiento != null && !fechaFallecimiento.isEmpty())
                ? Integer.valueOf(fechaFallecimiento)
                : null;
    }

    public Set<Libro> getLibros() {
        return libros;
    }

    public void setLibros(Set<Libro> libros) {
        this.libros = libros;
    }
}
