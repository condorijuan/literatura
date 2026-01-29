package com.juan.literatura.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro (

        @JsonAlias("title") String titulo,
        @JsonAlias("summaries") String[] resumen,
        @JsonAlias("languages") String[] idioma,
        @JsonAlias("download_count") int numeroDescargas,
        @JsonAlias("authors") DatosAutor[] autores
){
}
