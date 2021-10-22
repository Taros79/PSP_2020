package videoclub.gui;

import com.github.javafaker.Faker;
import videoclub.config.Configuration;
import videoclub.dao.modelo.*;
import videoclub.servicios.ServiciosVideoclub;
import videoclub.utils.Constantes;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
    Faker f = new Faker();
    Scanner sc = new Scanner(System.in);
    ServiciosVideoclub sv = new ServiciosVideoclub();
    //menu
    int opcion;
    boolean seguir = true;
    do {
      do {
        System.out.println("¿Qué desea hacer? \n" +
            "1. Registrar un socio \n" +
            "2. Borrar un socio \n" +
            "3. Añadir un producto nuevo \n" +
            "4. Actualizar el Stock de un producto existente \n" +
            "5. Realizar un nuevo alquiler \n" +
            "6. Devolver un producto alquilado \n" +
            "7. Pagar una multa\n" +
            "8. Salir");
        opcion = sc.nextInt();
        sc.nextLine();
        if (opcion < 1 || opcion > 8) {
          System.out.println("Por favor, dime una de las opciones del menu." +
              "Vuelvo a mostrartelo.");
        }
      } while (opcion < 1 || opcion > 8);
      switch (opcion) {
        case 1:
          //1. addSocio
          String dni = f.phoneNumber().extension();
          System.out.println("Dni: " + dni);
          String nombre = f.gameOfThrones().character();
          String direccion = f.gameOfThrones().city();
          String tel = f.phoneNumber().toString();
          int edad = f.number().numberBetween(1, 99);
          Socio socio = new Socio(dni, nombre, direccion, tel, edad);
          if (sv.addSocio(socio)) {
            System.out.println("Socio registrado");
          } else {
            System.out.println("Este socio ya se encuentra en nuestra base de datos");
          }
          break;
        case 2:
          //2. borrarSocio
          System.out.println("Para eliminar un socio digame el DNI de ese socio, por favor");
          dni = sc.nextLine();
          if (sv.borrarSocio(dni)) {
            System.out.println("Se ha encontrado el usuario y se ha eliminado de nuestro registro");
          } else {
            System.out.println("Este usuario no se encuentra en nuestro sistema");
          }
          break;
        case 3:
          //3. addProducto
          opcion = menuProducto(sc);
          switch (opcion) {
            case 1:
              Producto vj = new Videojuego(f.harryPotter().book(), f.number().numberBetween(1, 10), f.color().name(), f.animal().name());
              registrarProducto(vj, sv);
              break;
            case 2:
              Producto docu = new Documental(f.harryPotter().book(), f.number().numberBetween(1, 10), f.color().name(), FormatoPelicula.DVD, f.gameOfThrones().character(), "120min");
              registrarProducto(docu, sv);
              break;
            case 3:
              Producto peli = new Pelicula(f.harryPotter().book(), f.number().numberBetween(1, 10), f.color().name(), FormatoPelicula.DVD, f.gameOfThrones().character(), "120min");
              registrarProducto(peli, sv);
              break;
          }
          break;
        case 4:
          //4. AddStockProducto
          opcion = menuProducto(sc);
          int indiceProducto;
          int cantidadACambiar;
          switch (opcion) {
            case 1:
              if (sv.getTodosVideoJuegos().isEmpty()) {
                indiceProducto = elegirVideojuego(sv, sc);
                System.out.println("Dime la cantidad a modificar. En caso de ser una retirada de Stock, por favor indicalo en negativo");
                cantidadACambiar = sc.nextInt();
                sc.nextLine();
                Producto producto = sv.getTodosVideoJuegos().get(indiceProducto);
                actualizarStock(cantidadACambiar, producto, sv);
              } else {
                System.out.println(Constantes.NO_EXISTEN_PRODUCTOS_DE_ESTE_TIPO_TOOOORPE);
              }
              break;
            case 2:
              if (sv.getTodosDocumentales().size() > 0) {
                indiceProducto = elegirDocumental(sv, sc);
                System.out.println("Dime la cantidad a modificar. En caso de ser una retirada de Stock, por favor indicalo en negativo");
                cantidadACambiar = sc.nextInt();
                sc.nextLine();
                Producto producto = sv.getTodosDocumentales().get(indiceProducto);
                actualizarStock(cantidadACambiar, producto, sv);
              } else {
                System.out.println("No existen productos de este tipo, toooorpe");
              }
              break;
            case 3:
              if (sv.getTodasPeliculas().size() > 0) {
                indiceProducto = elegirPelicula(sv, sc);
                System.out.println("Dime la cantidad a modificar. En caso de ser una retirada de Stock, por favor indicalo en negativo");
                cantidadACambiar = sc.nextInt();
                sc.nextLine();
                Producto producto = sv.getTodasPeliculas().get(indiceProducto);
                actualizarStock(cantidadACambiar, producto, sv);
              } else {
                System.out.println("No existen productos de este tipo, toooorpe");
              }
              break;
          }
          break;
        case 5:
          //5. Alquilar
          //encontrar producto,
          opcion = menuProducto(sc);
          Producto productoAAlquilar = null;
          switch (opcion) {
            case 1:
              if (sv.getTodosVideoJuegos().size() > 0) {
                indiceProducto = elegirVideojuego(sv, sc);
                productoAAlquilar = sv.getTodosVideoJuegos().get(indiceProducto);
              } else {
                System.out.println(Constantes.DISCULPE_LAS_MOLESTIAS_POR_FAVOR);
              }
              break;
            case 2:
              if (sv.getTodosDocumentales().size() > 0) {
                indiceProducto = elegirDocumental(sv, sc);
                productoAAlquilar = sv.getTodosDocumentales().get(indiceProducto);
              } else {
                System.out.println(Constantes.DISCULPE_LAS_MOLESTIAS_POR_FAVOR);
              }
              break;
            case 3:
              if (sv.getTodasPeliculas().size() > 0) {
                indiceProducto = elegirPelicula(sv, sc);
                productoAAlquilar = sv.getTodasPeliculas().get(indiceProducto);
              } else {
                System.out.println("No disponemos de articulos de este tipo. \n" +
                    "Disculpe las molestias.");
              }
              break;
          }
          //nif, comprobar: si socio alquilo, si multa, si stock producto; sumar a cantidadAlquilada
          if (productoAAlquilar != null) {
            System.out.println("Para proceder al alquiler del producto necesito su DNI");
            dni = sc.nextLine();
            String alquilado = sv.alquilarProducto(productoAAlquilar, dni);
            System.out.println(alquilado);
            if (alquilado.equals("Producto alquilado correctamente\n" +
                "MUCHAS GRACIAS")) {
              if (opcion == 1) {
                System.out.println("Recuerde que tiene " + Configuration.getDiasAlquilerVideojuego()
                    + " segundos para devolverlo sin ser sancionado.");
              } else {
                System.out.println("Recuerde que tiene " + Configuration.getDiasAlquilerPeliculas()
                    + " segundos para devolverlo sin ser sancionado.");
              }
            }
          }
          break;
        case 6:
          //6. Devolver
          System.out.println("Digame su DNI para poder proceder a la devolucion");
          dni = sc.nextLine();
          if (sv.getSocio(dni) != null) {
            System.out.println("Para proceder a la devolucion nos gustaria saber.\n" +
                "La puntuacion de 0 a 5 que daria al producto");
            int puntuacion;
            do {
              puntuacion = sc.nextInt();
              sc.nextLine();
            } while (puntuacion < 0 || puntuacion > 5);
            System.out.println("¿Volveria a alquilarlo?\n" +
                "1. Si\n" +
                "2. No");
            int respuesta;
            do {
              respuesta = sc.nextInt();
              sc.nextLine();
            } while (respuesta < 1 || respuesta > 2);
            boolean realquilar;
            realquilar = respuesta == 1;
            Encuesta encuesta = new Encuesta(puntuacion, realquilar);
            //Servicios -> mirarfecha para multa,sacarAlquilerSocio, acctualizar producto cantidadAlquilada, addEncuestaAProducto
            if (sv.devolverProducto(dni, encuesta)) {
              System.out.println("Devolucion realizada");
            } else {
              System.out.println("No tiene ningun alquiler con nosotros");
            }
            if (sv.getSocio(dni).isSancionado()) {
              System.out.println("Ha sido sancionado ya que ha devuelto el producto con retraso");
            }
          } else {
            System.out.println("No esta registrado, no puede tener ningun alquiler con nosotros");
          }
          break;
        case 7:
          //7. pagarMulta
          System.out.println("Digame su DNI");
          dni = sc.nextLine();
          if (sv.getSocio(dni) != null) {
            socio = sv.getSocio(dni);
            if (socio.isSancionado()) {
              System.out.println("Su multa ha sido pagada");
              socio.setSancionado(false);
            } else {
              System.out.println("Este usuario no tiene ninguna multa");
            }
          } else {
            System.out.println("Este usuario no esta registrado");
          }
          break;
        case 8:
          System.out.println("\n GRACIAS POR SU VISITA");
          seguir = false;
          break;
        default:
          System.out.println("opcion no valida");
      }
    } while (seguir);
  }

  private static void actualizarStock(int cantidadACambiar, Producto producto, ServiciosVideoclub sv) {
    if (cantidadACambiar < 0) {
      if ((cantidadACambiar * (-1)) > producto.getCantidad()) {
        System.out.println("No disponemos de tantas unidades de este producto, " +
            "revise los datos y vuelva a realizar la operacion");
      } else {
        sv.actualizarStockProducto(producto, cantidadACambiar);
      }
    } else {
      sv.actualizarStockProducto(producto, cantidadACambiar);
      System.out.println("Stock actualizado");
    }
    System.out.println("La cantidad ahora es: " + producto.getCantidad());
  }

  private static void registrarProducto(Producto p, ServiciosVideoclub sv) {
    if (sv.addProducto(p)) {
      System.out.println("Producto añadido correctamente");
    } else {
      System.out.println("El producto no se ha podido añadir");
    }
  }

  private static int elegirProducto(Scanner sc, int size) {
    System.out.println("Elige el producto deseado de la lista");
    int opcion = 0;
    do {
      opcion = sc.nextInt();
      sc.nextLine();
      if (opcion < 1 || opcion > size) {
        System.out.println("Por favor, elija una de las opciones disponibles");
      }
    } while (opcion < 1 || opcion > size);
    return opcion - 1;
  }

  private static int menuProducto(Scanner sc) {
    int opcion;
    do {
      System.out.println("Seleccione el tipo de producto:\n" +
          "1. Videojuego\n" +
          "2. Documental\n" +
          "3. Pelicula");
      opcion = sc.nextInt();
      sc.nextLine();
      if (opcion < 1 || opcion > 3) {
        System.out.println("Solo tenemos 3 tipos de productos, dime 1, 2 o 3, por favor.");
      }
    } while (opcion < 1 || opcion > 3);
    return opcion;
  }

  private static int elegirVideojuego(ServiciosVideoclub sv, Scanner sc) {
    for (Videojuego vj : sv.getTodosVideoJuegos()) {
      System.out.println((sv.getTodosVideoJuegos().indexOf(vj) + 1) + " " + vj.toString());
    }
    return elegirProducto(sc, sv.getTodosVideoJuegos().size());
  }

  private static int elegirDocumental(ServiciosVideoclub sv, Scanner sc) {
    for (Documental docu : sv.getTodosDocumentales()) {
      System.out.println((sv.getTodosDocumentales().indexOf(docu) + 1) + " " + docu.toString());
    }
    return elegirProducto(sc, sv.getTodosDocumentales().size());
  }

  private static int elegirPelicula(ServiciosVideoclub sv, Scanner sc) {
    for (Pelicula peli : sv.getTodasPeliculas()) {
      System.out.println((sv.getTodasPeliculas().indexOf(peli) + 1) + " " + peli.toString());
    }
    return elegirProducto(sc, sv.getTodasPeliculas().size());
  }

}
