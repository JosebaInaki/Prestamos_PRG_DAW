import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GestorBiblioteca {
  private static final int MAX_USUARIOS = 50;
  private static final int MAX_PRESTAMOS = 200;
  private Usuario[] usuarios;
  private Prestamo[] prestamos;
  private int numeroUsuarios;
  private int numeroPrestamos;

  public GestorBiblioteca() {
    usuarios = new Usuario[MAX_USUARIOS];
    prestamos = new Prestamo[MAX_PRESTAMOS];
    numeroUsuarios = 0;
    numeroPrestamos = 0;
  }
  public void registrarUsuario(Usuario usuario) throws UsuarioRepetidoException {
    for (int i = 0; i < numeroUsuarios; i++) {
      if (usuarios[i].getNumeroSocio().equals(usuario.getNumeroSocio())) {
        throw new UsuarioRepetidoException("Usuario repetido");
      }
    }
    usuarios[numeroUsuarios++] = usuario;
  }
  public Prestamo realizarPrestamo(String codigoLibro, String tituloLibro, LocalDate fechaPrestamo, Usuario usuario) throws PrestamoInvalidoException, UsuarioSancionadoException, LibroNoDisponibleException {
    if (usuario == null) {
      throw new PrestamoInvalidoException("Usuario no existe");
    }
    if (usuario.estaSancionado()) {
      throw new UsuarioSancionadoException("Usuario sancionado");
    }
    for (int i = 0; i < numeroPrestamos; i++) {
      if (prestamos[i].getCodigoLibro().equals(codigoLibro)
          && prestamos[i].getFechaDevolucionReal() == null) {
        throw new LibroNoDisponibleException("Libro no disponible. Ya está prestado.");
      }
    }
    Prestamo prestamoNuevo = new Prestamo(codigoLibro, tituloLibro, usuario, fechaPrestamo);
    prestamos[numeroPrestamos++] = prestamoNuevo;
    return prestamoNuevo;
  }
  public boolean devolverLibro(String codigoLibro, LocalDate fechaDevolucion) throws PrestamoInvalidoException {
    DateTimeFormatter formatoFecha =  DateTimeFormatter.ofPattern("dd/MM/yyyy");
    for (int i = 0; i < numeroPrestamos; i++) {

      if (prestamos[i].getCodigoLibro().equals(codigoLibro) && prestamos[i].getFechaDevolucionReal() == null) {

        prestamos[i].registrarDevolucion(fechaDevolucion);

        int diasRetraso = prestamos[i].calcularDiasRetraso();

        if (diasRetraso > 0) {
          System.out.println("Devolución registrada con "+diasRetraso+" días de retraso");
          System.out.println("Usuario sancionado por "+diasRetraso+" días (hasta el ("+formatoFecha.format(fechaDevolucion.plusDays(diasRetraso))+")");
          prestamos[i].getSocio().sancionar(diasRetraso, fechaDevolucion);
        }
        return true;
      }
    }
    return false;
  }
  public Usuario buscarUsuario(String numeroSocio) {

    for (int i = 0; i < numeroUsuarios; i++) {
      if (usuarios[i].getNumeroSocio().equals(numeroSocio)) {
        return usuarios[i];
      }
    }
    return null;
  }
  public Usuario[] getUsuarios() {
    return usuarios;
  }
  public Prestamo[] getPrestamos() {
    return prestamos;
  }
}