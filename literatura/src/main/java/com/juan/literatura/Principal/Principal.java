package com.juan.literatura.Principal;

import com.juan.literatura.Model.*;
import com.juan.literatura.Repositorio.AutorRepository;
import com.juan.literatura.Repositorio.LibroRepository;
import com.juan.literatura.Service.ConsumoAPI;
import com.juan.literatura.Service.ConvierteDatos;

import java.util.*;

public class Principal {

    final ConsumoAPI consumo = new ConsumoAPI();
    final ConvierteDatos conversor = new ConvierteDatos();
    final Scanner sc = new Scanner(System.in);
    final String URL = "https://gutendex.com/books/?search=";

    final LibroRepository repositorio;
    final AutorRepository autorRepositorio;

    public Principal(LibroRepository repository, AutorRepository autorRepository) {
        this.repositorio = repository;
        this.autorRepositorio = autorRepository;
    }

    public void Menu(){
        String option = "-1";
        while(!option.equals("0")){
            System.out.println("""
                Eliga una opcion:
                1. buscar libro por titulo
                2. listar libros registrado
                3. listar autores registrados
                4. listar autores vivos en un determinado año
                5. listar libros por idioma
                0. salir
                """);

            option = sc.nextLine();

            switch (option){
                case "1":
                    buscarLibrosPorTitulo();
                    break;
                case "2":
                    listarLibrosRegistrados();
                    break;
                case "3":
                    listarAutoresRegistrados();
                    break;
                case "4":
                    listarAutoresVivosEnUnDeterminadoAnio();
                    break;
                case "5":
                    listarLibrosPorIdioma();
                    break;
                case "0":
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opcion no valida");
            }
        }


//        var json = consumo.obtenerDatos("https://gutendex.com/books/");
//
//        Datos datosLibro = conversor.obtenerDatos(json, Datos.class);
//
//        System.out.println("Datos libros encontrados:");
//
//        for(DatosLibro dl : datosLibro.libros()){
//            System.out.println("Titulo: " + dl.titulo());
//            System.out.println("Resumen: " + String.join(", ", dl.resumen()));
//            System.out.println("Idioma: " + String.join(", ", dl.idioma()));
//            System.out.print("Autores: ");
//            for(DatosAutor da : dl.autores()){
//                System.out.print(da.nombre() + " ");
//            }
//            System.out.println("\n-----------------------");
//        }

    }

    private void buscarLibrosPorTitulo() {
        System.out.println("Ingrese el titulo del libro a buscar:");
        var titulo = sc.nextLine();
        var url = URL + titulo.replace(" ", "%20");
        try{
            var json = consumo.obtenerDatos(url);
            Datos datosLibro = conversor.obtenerDatos(json, Datos.class);

            System.out.println("Datos libros encontrados:");

            for(DatosLibro dl : datosLibro.libros()){
                System.out.println("Titulo: " + dl.titulo());
                System.out.println("Resumen: " + String.join(", ", dl.resumen()));
                System.out.println("Idioma: " + String.join(", ", dl.idioma()));
                System.out.println("Descargas: " + dl.numeroDescargas());
                System.out.print("Autores: ");
                for(DatosAutor da : dl.autores()){
                    System.out.print(da.nombre() + " ");
                }
                System.out.println("\n-----------------------");
            }

            guardarLibros(datosLibro);
        } catch (Exception e){
            System.out.println("Error al obtener datos del libro: " + e.getMessage());
        }

    }

    private void guardarLibros(Datos datosLibro) {
        if (datosLibro == null || datosLibro.libros() == null) {
            return;
        }

        for(DatosLibro dl : datosLibro.libros()){
            try{
                Libro libro = new Libro(
                        dl.titulo(),
                        String.join(", ", dl.resumen()),
                        String.join(", ", dl.idioma()),
                        dl.numeroDescargas()
                );

                Set<Autor> listaAutores = libro.getAutores();
                for(DatosAutor da : dl.autores()){
                    Optional<Autor> autorExistente = autorRepositorio.findByNombre(da.nombre());
                    if(autorExistente.isPresent()) {
                        System.out.println("Autor existente: " + da.nombre());
                        listaAutores.add(autorExistente.get());
                    } else{
                        Autor autor = new Autor(
                                da.nombre(),
                                da.fechaNacimiento(),
                                da.fechaFallecimiento()
                        );
                        listaAutores.add(autorRepositorio.save(autor));
                    }
                }
                libro.setAutores(listaAutores);
                repositorio.save(libro);
            } catch ( Exception e){
                System.out.println("Error al guardar libro: " + e.getMessage());
            }
        }
    }

    private void listarLibrosRegistrados() {

        repositorio.findAllwithAutor().stream().forEach(System.out::println);

    }

    private void listarAutoresRegistrados() {
        try{
            ArrayList<Autor> listaAutores = (ArrayList<Autor>) autorRepositorio.findAll();
            if (listaAutores == null || listaAutores.isEmpty()) {
                System.out.println("No hay autores registrados");
                return;
            }
            for(Autor autor : listaAutores){
                System.out.println("Nombre: " + autor.getNombre());
                System.out.println("Fecha de Nacimiento: " + autor.getFechaNacimiento());
                System.out.println("Fecha de Fallecimiento: " + autor.getFechaFallecimiento());
                System.out.println("\n-----------------------");
            }
        } catch (Exception e){
            System.out.println("Error al listar autores registrados: " + e.getMessage());
        }
    }

    private void listarAutoresVivosEnUnDeterminadoAnio() {
        System.out.println("Ingrese el año a consultar:");
        try {
            var anio = sc.nextInt();
            sc.nextLine();
            ArrayList<Autor> listaAutores = autorRepositorio.findAutoresVivosEnAnio(anio);

            if (listaAutores == null || listaAutores.isEmpty()) {
                System.out.println("No se encontraron autores vivos en ese año");
                return;
            }

            for(Autor autor : listaAutores){
                System.out.println("Nombre: " + autor.getNombre());
                System.out.println("Fecha de Nacimiento: " + autor.getFechaNacimiento());
                System.out.println("Fecha de Fallecimiento: " + autor.getFechaFallecimiento());
                System.out.println("\n-----------------------");
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Debe ingresar un número válido para el año.");
            sc.nextLine();
        }
        catch (Exception e){
            System.out.println("Error al listar autores vivos en un determinado año: " + e.getMessage());
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println("Ingrese el idioma a consultar (ejemplo: 'en' para ingles):");
        var idioma = sc.nextLine();
        try{
            List<Libro> listaLibros = repositorio.findByIdioma(idioma);

            if (listaLibros == null || listaLibros.isEmpty()) {
                System.out.println("No se encontraron libros en el idioma: " + idioma);
                return;
            }

            listaLibros.stream().forEach(l -> {
                        System.out.println("Titulo: " + l.getTitulo());
                        System.out.println("Resumen: " + l.getResumen());
                        System.out.println("Idioma: " + l.getIdioma());
                        System.out.println("Descargas: " + l.getNumeroDescargas());
                    }
            );
        } catch (Exception e){
            System.out.println("Error al listar libros por idioma: " + e.getMessage());
        }
    }
}
