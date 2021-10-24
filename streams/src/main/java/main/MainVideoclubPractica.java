package main;

import videoclub.servicios.ServiciosVideoclub;

import java.util.Random;
import java.util.Scanner;

public class MainVideoclubPractica {

    public static void main(String[] args) {

        SetUp setUp = new SetUp();
        setUp.setupSocioSocios();
        setUp.setupProductos();
        setUp.setAlquiler();

        Random r = new Random();
        ServiciosVideoclub sv = new ServiciosVideoclub();
        StreamsVideoClub streamVideoclub = new StreamsVideoClub();

        Scanner sc = new Scanner(System.in);
        int opcion;
        do {
            do {
                System.out.println("¿Qué desea hacer? \n" +
                        "1. Ejercicio 1 \n" +
                        "2. Ejercicio 2 \n" +
                        "3. Ejercicio 3 \n" +
                        "4. Ejercicio 4 \n" +
                        "5. Ejercicio 5 \n" +
                        "6. Ejercicio 6 \n" +
                        "7. Ejercicio 7 \n" +
                        "8. Ejercicio 8 \n" +
                        "9. Ejercicio 9 \n" +
                        "10. Ejercicio 10 \n" +
                        "11. Ejercicio 11 \n" +
                        "12. Salir");
                opcion = sc.nextInt();
                sc.nextLine();
                if (opcion < 1 || opcion > 12) {
                    System.out.println("Por favor, dime una de las opciones del menu." +
                            "Vuelvo a mostrartelo.");
                }
            } while (opcion < 1 || opcion > 13);
            switch (opcion) {
                case 1:
                    System.out.println("NUMERO SOCIOS SANCIONADOS");
                    for (int i = 0; i < 5; i++) {
                        sv.getTodosSocios().get(r.nextInt(sv.getTodosSocios().size())).setSancionado(true);
                    }
                    streamVideoclub.numeroSociosSancionados();
                    break;
                case 2:
                    System.out.println("MEDIA EDAD SOCIOS");
                    streamVideoclub.mediaEdadDeSociosSancionados();
                    break;
                case 3:
                    System.out.println("LISTA PRODUCTOS MAS ALQUILADOS");
                    for (int i = 0; i < 10; i++) {
                        sv.getTodosProductos().get(i).setCantidadAlquilada(sv.getTodosProductos().get(i).getCantidad());
                    }
                    streamVideoclub.listaDiezProductosMasAlquilados();
                    break;
                case 4:
                    System.out.println("PRODUCTOS ALQUILADOS POR TIPO");
                    streamVideoclub.numeroProductosAlquiladosPorTipo();
                    break;
                case 5:
                    System.out.println("ACTORES DISTINTOS");
                    streamVideoclub.todosLosActoresDistintosDeTodasLasPeliculas();
                    break;
                case 6:
                    System.out.println("PELI CON MAS ACTORES");
                    streamVideoclub.peliculaConMasActores();
                    break;
                case 7:
                    System.out.println("VALORACIONES MEDIAS DE MAYOR A MENOR");
                    streamVideoclub.productoConSuValoracionMediaOrdenadosDeMayoraMenor();
                    break;
                case 8:
                    System.out.println("PELIS MEJOR VALORADAS");
                    streamVideoclub.las10PeliculasMejorValoradas();
                    break;
                case 9:
                    System.out.println("JUEGOS MEJOR VALORADOS");
                    streamVideoclub.los10VideoJuegosMejorValoradas();
                    break;
                case 10:
                    System.out.println("Documentales y Peliculas por Formato");
                    streamVideoclub.numeroDocumentalesyPeliculasSegunSuFormato();
                    break;
                case 11:
                    System.out.println("FABRICANTES DISTINTOS DE JUEGOS");
                    streamVideoclub.todosLosFabricantesDistintosDeVideoJuegosEnUnSoloString();
                    break;
            }
        } while (opcion != 12);
    }
}

