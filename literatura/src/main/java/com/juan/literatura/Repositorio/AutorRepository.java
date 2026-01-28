package com.juan.literatura.Repositorio;

import com.juan.literatura.Model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    Optional<Autor> findByNombre(String nombre);

    @Query("SELECT a FROM Autor a WHERE a.fechaNacimiento IS NOT NULL AND a.fechaFallecimiento IS NOT NULL AND a.fechaNacimiento <= :anio AND a.fechaFallecimiento > :anio")
    ArrayList<Autor> findAutoresVivosEnAnio(int anio);

}
