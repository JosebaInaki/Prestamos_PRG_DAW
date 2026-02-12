import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) throws UsuarioRepetidoException, UsuarioInvalidoException, PrestamoInvalidoException, UsuarioSancionadoException, LibroNoDisponibleException {
    int opcion=0;
    Scanner in = new Scanner(System.in);
    GestorBiblioteca gestorBiblioteca=new GestorBiblioteca();
    DateTimeFormatter formatoFecha =  DateTimeFormatter.ofPattern("dd/MM/yyyy");
    do {
      mostrarMenu();
      opcion=Integer.parseInt(in.nextLine());
      switch (opcion){
        case 1: {
          String nombre, email, numeroSocio;
          LocalDate fechaRegistro;

          System.out.println("Nombre: ");
          nombre = in.nextLine();

          System.out.println("Email: ");
          email = in.nextLine();

          System.out.println("Numero de Socio");
          numeroSocio = in.nextLine();

          System.out.println("Fecha registro (dd/mm/aaaa):");
          fechaRegistro = LocalDate.parse(in.nextLine(), formatoFecha);

          Usuario usuarioNuevo = new Usuario(nombre, email, numeroSocio, fechaRegistro);
          gestorBiblioteca.registrarUsuario(usuarioNuevo);

          System.out.println("Usuario correctamente registrado");
          break;
        }

        case 2: {
          String codigoLibro, tituloLibro;
          LocalDate fechaPrestamo;

          System.out.println("Código del libro: ");
          codigoLibro = in.nextLine();

          System.out.println("Título del libro: ");
          tituloLibro = in.nextLine();

          System.out.println("Fecha prestamo (dd/mm/aaaa):");
          fechaPrestamo = LocalDate.parse(in.nextLine(), formatoFecha);

          System.out.println("Numero de socio del usuario:");
          String numeroSocio = in.nextLine();

          Usuario usuario = gestorBiblioteca.buscarUsuario(numeroSocio);

          gestorBiblioteca.realizarPrestamo(codigoLibro, tituloLibro, fechaPrestamo, usuario);
          break;
        }
        case 3:
          break;
        case 4:
          break;
        case 5:
          break;
        case 6:
          break;
        case 7:
          break;
        case 8:
          System.out.println("Has salido");
          break;
        default:
          System.out.println("Opción no válida");
          break;
      }
    }while (opcion!=8);
  }
  public static void mostrarMenu(){
    System.out.println("1. Registrar nuevo usuario");
    System.out.println("2. Realizar préstamo de libro");
    System.out.println("3. Devolver libro");
    System.out.println("4. Consultar estado de usuario");
    System.out.println("5. Mostrar préstamos activos");
    System.out.println("6. Mostrar usuarios sancionados");
    System.out.println("7. Actualizar sanciones");
    System.out.println("8. Salir");
  }
}