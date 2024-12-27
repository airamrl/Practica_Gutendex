package com.aluracursos.Practica_Gutendex.principal;

import com.aluracursos.Practica_Gutendex.model.Datos;
import com.aluracursos.Practica_Gutendex.model.DatosAutor;
import com.aluracursos.Practica_Gutendex.model.DatosLibro;
import com.aluracursos.Practica_Gutendex.service.ConsumoApi;
import com.aluracursos.Practica_Gutendex.service.ConvierteDatos;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private final String URL_BASE = "https://gutendex.com/books/";
    private final String URL_YEAR_START = "?author_year_start=";
    private final String URL_YEAR_END = "&author_year_end";

    private ConsumoApi consumoApi = new ConsumoApi();
    private ConvierteDatos convierteDatos = new ConvierteDatos();

    Scanner teclado = new Scanner(System.in);

    public void mostrarMenu(){
        var json = consumoApi.obtenerDatos(URL_BASE);
//        System.out.println(json);

        var datos = convierteDatos.obtenerDatos(json, Datos.class);
//        System.out.println(datos);

        //Top 10 libros más descargados
        System.out.println("Top 10 libros más descargados");
        datos.resultados().stream()
                .sorted(Comparator.comparing(DatosLibro::numeroDescargas).reversed())
                .limit(10)
                .map(l -> l.titulo().toUpperCase())
                .forEach(System.out::println);

        //Búsqueda libros por nombre
        System.out.println("Ingresa ");
        var tituloLibro= teclado.nextLine();
        json = consumoApi.obtenerDatos(URL_BASE+"?search="+tituloLibro.replace(" ", "+"));
        var datosBusqueda = convierteDatos.obtenerDatos(json, Datos.class);
//        System.out.println(datos);

        Optional<DatosLibro> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();

        if (libroBuscado.isPresent()) {
            System.out.println("Libro encontrado ");
            System.out.println(libroBuscado.get());
        } else {
            System.out.println("Libro no encontrado");
        }



        //   Estadisticas
        DoubleSummaryStatistics est = datos.resultados().stream()
                .filter(d -> d.numeroDescargas() > 0)
                .collect(Collectors.summarizingDouble(DatosLibro::numeroDescargas));
        System.out.println("Cantidad media de descargas: " + est.getAverage());
        System.out.println("Cantidad máxima de descargas: " + est.getMax());
        System.out.println("Cantidad mínima de descargas: " + est.getMin());
        System.out.println("Cantidad de registros evaluados para calcular las estadísticas: " + est.getCount());



        //Búsqueda libros por intervalo de años

        System.out.println("********* Búsqueda libros por intervalo de año ************");
        System.out.println("Ingresa el año de inicio ");
        var yearStart = teclado.nextLine();
        System.out.println("Ingresa el año de cierre ");
        var yearEnda = teclado.nextLine();

        json = consumoApi.obtenerDatos(URL_BASE+URL_YEAR_START+yearStart+URL_YEAR_END+yearEnda);

        var datosIntervaloAnios = convierteDatos.obtenerDatos(json, Datos.class);
        System.out.println(datosIntervaloAnios);

        Optional<DatosLibro> librosIntervalos = datosIntervaloAnios.resultados().stream()
//                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();

        if (libroBuscado.isPresent()) {
            System.out.println("Libros encontrados en ese intervalo de años ");
            System.out.println(librosIntervalos.get());
        } else {
            System.out.println("Libro no encontrado  en ese intervalo de años ");
        }




    }
}
