import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) throws UsuarioRepetidoException, UsuarioInvalidoException, PrestamoInvalidoException, UsuarioSancionadoException, LibroNoDisponibleException {
    int opcion=0;
    Scanner in = new Scanner(System.in);
    GestorBiblioteca gestorBiblioteca=new GestorBiblioteca();
    DateTimeFormatter formatoFecha =  DateTimeFormatter.ofPattern("dd/MM/yyyy");
    try{
      do {
        mostrarMenu();
        opcion=Integer.parseInt(in.nextLine());
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
            boolean fechaValida = false;
            while (!fechaValida) {
              try {
                System.out.println("Fecha registro (dd/MM/yyyy):");
                String textoFecha = in.nextLine();
                fechaRegistro = LocalDate.parse(textoFecha, formatoFecha);
                fechaValida = true;
              } catch (DateTimeParseException e) {
                System.out.println("Formato incorrecto. Usa dd/MM/yyyy");
              }
            }
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
            try{
              System.out.println("Fecha prestamo (dd/mm/aaaa):");
              fechaPrestamo = LocalDate.parse(in.nextLine(), formatoFecha);

              System.out.println("Numero de socio del usuario:");
              String numeroSocio = in.nextLine();

              Usuario usuario = gestorBiblioteca.buscarUsuario(numeroSocio);

              gestorBiblioteca.realizarPrestamo(codigoLibro, tituloLibro, fechaPrestamo, usuario);
            }
            catch (DateTimeParseException dtpe){
              System.out.println("Fomato de fecha incorrecto, \nDebe ser dd/mm/yyyy");
            }
            break;
          }
          case 3:
            String codigoLibro;
            LocalDate fechaDevolucion;
            System.out.println("Código libro: ");
            codigoLibro=in.nextLine();
            System.out.println("Fecha devolución (dd/mm/aaaa): ");
            fechaDevolucion=LocalDate.parse(in.nextLine(), formatoFecha);
            gestorBiblioteca.devolverLibro(codigoLibro,fechaDevolucion);
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
            try{
              Prestamo[] prestamos = gestorBiblioteca.getPrestamos();
              for (Prestamo p : prestamos) {
                System.out.println(p.toString());
              }
            }
            catch (NullPointerException npe){
              System.out.println("No hay prestamos");
          }
            break;
          case 6:
            try {
              Usuario[] usuarios = gestorBiblioteca.getUsuarios();
              for (Usuario u : usuarios) {
                if (u.estaSancionado()) {
                  System.out.println(u.toString());
                }
              }
            }
            catch (NullPointerException npe){
              System.out.println("No hay usuarios");
            }
            break;
          case 7:
            try{
              Usuario[] usuarios = gestorBiblioteca.getUsuarios();
              usuarios= gestorBiblioteca.getUsuarios();
              for (int i=0;i<usuarios.length;i++)
              {
                if(usuarios[i].estaSancionado() && usuarios[i].getFechaFinSancion().isBefore(LocalDate.now()))
                {
                  usuarios[i].levantarSancion();
                }
              }
            }
            catch (NullPointerException npe){
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
      }while (opcion!=8);
    }
    catch (NumberFormatException num){
      System.out.println("Solo se admiten números del 1 al 8");
    }
  }
  public static void mostrarMenu(){
    System.out.println();
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