import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) throws UsuarioRepetidoException, UsuarioInvalidoException, PrestamoInvalidoException, UsuarioSancionadoException, LibroNoDisponibleException{
    int opcion=0;
    Scanner in = new Scanner(System.in);
    GestorBiblioteca gestorBiblioteca=new GestorBiblioteca();
    DateTimeFormatter formatoFecha =  DateTimeFormatter.ofPattern("dd/MM/yyyy");
      do {
        try{
          mostrarMenu();
          opcion=Integer.parseInt(in.nextLine());
          System.out.println();
          switch (opcion){
            case 1: {
              String nombre, email, numeroSocio;
              LocalDate fechaRegistro = null;

              System.out.println("Nombre: ");
              nombre = in.nextLine();

              System.out.println("Email: ");
              email = in.nextLine();

              System.out.println("Numero de Socio");
              numeroSocio = in.nextLine();
                try {
                  System.out.println("Fecha registro (dd/MM/yyyy):");
                  String textoFecha = in.nextLine();
                  fechaRegistro = LocalDate.parse(textoFecha, formatoFecha);
                } catch (DateTimeParseException e) {
                  System.out.println("Fecha no válida.");
                }
              Usuario usuarioNuevo = new Usuario(nombre, email, numeroSocio, fechaRegistro);
              gestorBiblioteca.registrarUsuario(usuarioNuevo);
              System.out.println("Usuario correctamente registrado");
              break;
            }
            case 2: {
              String codigoLibro, tituloLibro,numeroSocio;
              LocalDate fechaPrestamo;

              System.out.println("Código del libro: ");
              codigoLibro = in.nextLine();

              System.out.println("Título del libro: ");
              tituloLibro = in.nextLine();
              System.out.println("Numero de socio del usuario:");
              numeroSocio = in.nextLine();
              try{
                System.out.println("Fecha prestamo (dd/mm/aaaa):");
                fechaPrestamo = LocalDate.parse(in.nextLine(), formatoFecha);

                Usuario usuario = gestorBiblioteca.buscarUsuario(numeroSocio);

                gestorBiblioteca.realizarPrestamo(codigoLibro, tituloLibro, fechaPrestamo, usuario);
                System.out.println("Préstamo realizado.\nDevolución prevista: "+formatoFecha.format(fechaPrestamo.plusDays(14)));
              }
              catch (DateTimeParseException dtpe){
                System.out.println("Fecha no válida.");
              }
              break;
            }
            case 3:
              try {
                String codigoLibro;
                LocalDate fechaDevolucion;
                System.out.println("Código libro: ");
                codigoLibro = in.nextLine();
                System.out.println("Fecha devolución (dd/mm/aaaa): ");
                fechaDevolucion = LocalDate.parse(in.nextLine(), formatoFecha);
                gestorBiblioteca.devolverLibro(codigoLibro, fechaDevolucion);
              }
              catch (DateTimeParseException dte){
                System.out.println("Fecha no válida");
              }
              break;
            case 4:
              System.out.println("Numero de socio del usuario:");
              String numeroSocio = in.nextLine();

              Usuario usuario = gestorBiblioteca.buscarUsuario(numeroSocio);
              if(usuario==null){
                System.out.println("Este usuario no existe");
              }
              else if(usuario.estaSancionado()){
                System.out.println("Este usuario esta sancionado");
              }
              else {
                System.out.println("Este usuario no esta sancionado");
              }
              break;
            case 5:
              boolean hayPrestamos = false;
              Prestamo[] prestamos = gestorBiblioteca.getPrestamos();

              for (Prestamo p : prestamos) {
                if (p != null && p.getFechaDevolucionReal() == null) {
                  System.out.println(p);
                  hayPrestamos = true;
                }
              }
              if (!hayPrestamos) {
                System.out.println("No hay préstamos activos");
              }
              break;
            case 6:
              Usuario[] usuarios = gestorBiblioteca.getUsuarios();
              if(usuarios == null || usuarios.length == 0) {
                  System.out.println("No hay usuarios");
              }
              else{
                boolean haySancionados = false;

                for (Usuario u : usuarios) {
                  if (u != null && u.estaSancionado()) {
                    System.out.println(u.toString());
                    haySancionados = true;
                  }
                }
                if (!haySancionados) {
                  System.out.println("No hay usuarios sancionados");
                }
              }
              break;
            case 7:
              Usuario[] usuarioss = gestorBiblioteca.getUsuarios();
              if (usuarioss != null) {
                for (Usuario u : usuarioss) {
                  if (u != null
                      && u.estaSancionado()
                      && u.getFechaFinSancion() != null
                      && u.getFechaFinSancion().isBefore(LocalDate.now())){
                    u.levantarSancion();
                    System.out.println("Sanción actualizada");
                  }
                }
              }
              else {
                System.out.println("No hay usuarios");
              }
              break;
            case 8:
              System.out.println("Has salido");
              break;
            default:
              System.out.println("Opción no válida,\nSolo se admiten números del 1 al 8");
              break;
          }
        }
        catch (NumberFormatException num){
            System.out.println("Solo se admiten números del 1 al 8");
        }
      }while (opcion!=8);
  }
  public static void mostrarMenu(){
    System.out.println();
    System.out.println("=== SISTEMA GESTIÓN BIBLIOTECA ===");
    System.out.println("1. Registrar nuevo usuario");
    System.out.println("2. Realizar préstamo de libro");
    System.out.println("3. Devolver libro");
    System.out.println("4. Consultar estado de usuario");
    System.out.println("5. Mostrar préstamos activos");
    System.out.println("6. Mostrar usuarios sancionados");
    System.out.println("7. Actualizar sanciones");
    System.out.println("8. Salir");
    System.out.println();
    System.out.println("Escribe tu opción: ");
  }
}