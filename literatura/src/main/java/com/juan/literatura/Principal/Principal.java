package com.juan.literatura.Principal;

import com.juan.literatura.Model.*;
import com.juan.literatura.Repositorio.AutorRepository;
import com.juan.literatura.Repositorio.LibroRepository;
import com.juan.literatura.Service.ConsumoAPI;
import com.juan.literatura.Service.ConvierteDatos;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

public class Principal {

    private ConsumoAPI consumo = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner sc = new Scanner(System.in);

    private LibroRepository repositorio;
    private AutorRepository autorRepositorio;

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
        var url = "https://gutendex.com/books/?search=" + titulo.replace(" ", "%20");
        var json = consumo.obtenerDatos(url);
        Datos datosLibro = conversor.obtenerDatos(json, Datos.class);

        System.out.println("Datos libros encontrados:");

        for(DatosLibro dl : datosLibro.libros()){
            System.out.println("Titulo: " + dl.titulo());
            System.out.println("Resumen: " + String.join(", ", dl.resumen()));
            System.out.println("Idioma: " + String.join(", ", dl.idioma()));
            System.out.print("Autores: ");
            for(DatosAutor da : dl.autores()){
                System.out.print(da.nombre() + " ");
            }
            System.out.println("\n-----------------------");
        }

        for(DatosLibro dl : datosLibro.libros()){
            Libro libro = new Libro(
                    dl.titulo(),
                    String.join(", ", dl.resumen()),
                    String.join(", ", dl.idioma())
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
        }
    }

    private void listarLibrosRegistrados() {
        ArrayList<Libro> listaLibros = (ArrayList<Libro>) repositorio.findAllwithAutor();
        for(Libro libro : listaLibros){
            System.out.println("Titulo: " + libro.getTitulo());
            System.out.println("Resumen: " + libro.getResumen());
            System.out.println("Idioma: " + libro.getIdioma());
            System.out.print("Autores: ");
            for(Autor autor : libro.getAutores()){
                System.out.print(autor.getNombre() + " ");
            }
            System.out.println("\n-----------------------");
        }
    }

    private void listarAutoresRegistrados() {
        ArrayList<Autor> listaAutores = (ArrayList<Autor>) autorRepositorio.findAll();
        for(Autor autor : listaAutores){
            System.out.println("Nombre: " + autor.getNombre());
            System.out.println("Fecha de Nacimiento: " + autor.getFechaNacimiento());
            System.out.println("Fecha de Fallecimiento: " + autor.getFechaFallecimiento());
            System.out.println("\n-----------------------");
        }
    }

    private void listarAutoresVivosEnUnDeterminadoAnio() {
        System.out.println("Ingrese el año a consultar:");
        var anio = sc.nextInt();
        sc.nextLine();
        ArrayList<Autor> listaAutores = (ArrayList<Autor>) autorRepositorio.findAutoresVivosEnAnio(anio);
        for(Autor autor : listaAutores){
            System.out.println("Nombre: " + autor.getNombre());
            System.out.println("Fecha de Nacimiento: " + autor.getFechaNacimiento());
            System.out.println("Fecha de Fallecimiento: " + autor.getFechaFallecimiento());
            System.out.println("\n-----------------------");
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println("Ingrese el idioma a consultar (ejemplo: 'en' para ingles):");
        var idioma = sc.nextLine();
        ArrayList<Libro> listaLibros = (ArrayList<Libro>) repositorio.findByIdioma(idioma);
        for(Libro libro : listaLibros){
            System.out.println("Titulo: " + libro.getTitulo());
            System.out.println("Resumen: " + libro.getResumen());
            System.out.println("Idioma: " + libro.getIdioma());
        }
    }
}
