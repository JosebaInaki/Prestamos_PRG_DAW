import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Prestamo {
  private String codigoLibro;
  private String tituloLibro;
  private Usuario socio;
  private LocalDate fechaPrestamo;
  private LocalDate fechaDevolucionPrevista;
  private LocalDate fechaDevolucionReal;

  public Prestamo(String codigoLibro, String tituloLibro, Usuario socio, LocalDate fechaPrestamo) throws PrestamoInvalidoException {

    if (codigoLibro == null || !codigoLibro.matches("[A-Z]{3}\\d{4}")) {
      throw new PrestamoInvalidoException("Código del libro incorrecto,\nDebe tener este formato: 3 letras mayúsculas + 4 dígitos");
    }
    if (tituloLibro == null || tituloLibro.isBlank()) {
      throw new PrestamoInvalidoException("Título del libro incorrecto");
    }
    if (fechaPrestamo == null) {
      throw new PrestamoInvalidoException("Fecha nula");
    }
    if (fechaPrestamo.isAfter(LocalDate.now())) {
      throw new PrestamoInvalidoException("Fecha posterior a la actual");
    }

    this.codigoLibro = codigoLibro;
    this.tituloLibro = tituloLibro;
    this.socio = socio;
    this.fechaPrestamo = fechaPrestamo;
    this.fechaDevolucionPrevista = fechaPrestamo.plusDays(14);
    this.fechaDevolucionReal = null;
  }
  public void registrarDevolucion(LocalDate fechaDevolucion) throws PrestamoInvalidoException {
    if (fechaDevolucion == null) {
      throw new PrestamoInvalidoException("Fecha de devolución nula");
    }
    if (fechaDevolucion.isBefore(fechaPrestamo)) {
      throw new PrestamoInvalidoException("Fecha anterior a la de préstamo");
    }
    this.fechaDevolucionReal = fechaDevolucion;
  }
  public int calcularDiasRetraso() {
    LocalDate referencia = (fechaDevolucionReal != null) ? fechaDevolucionReal : LocalDate.now();
    long dias = ChronoUnit.DAYS.between(fechaDevolucionPrevista, referencia);

    return dias > 0 ? (int) dias : 0;
  }
  public boolean estaRetrasado() {
    if (fechaDevolucionReal != null) {
      return fechaDevolucionReal.isAfter(fechaDevolucionPrevista);
    }
    return LocalDate.now().isAfter(fechaDevolucionPrevista);
  }
  public String getCodigoLibro() {
    return codigoLibro;
  }
  public LocalDate getFechaDevolucionReal() {
    return fechaDevolucionReal;
  }
  public Usuario getSocio() {
    return socio;
  }
  @Override
  public String toString() {
    DateTimeFormatter formatoFecha =  DateTimeFormatter.ofPattern("dd/MM/yyyy");
    return "Código libro: " + codigoLibro +
        "\nTítulo: " + tituloLibro +
        "\nSocio: " + socio.getNumeroSocio() +
        "\nFecha préstamo: " + formatoFecha.format(fechaPrestamo) +
        "\nFecha devolución prevista: " + formatoFecha.format(fechaDevolucionPrevista);
  }
}