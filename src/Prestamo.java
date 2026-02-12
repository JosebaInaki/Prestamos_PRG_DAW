import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Prestamo {
  private String codigoLibro;
  private String tituloLibro;
  private Usuario socio;
  DateTimeFormatter formatoFecha =  DateTimeFormatter.ofPattern("dd/MM/yyyy");
  private LocalDate fechaPrestamo;
  private LocalDate fechaDevolucionPrevista;
  private LocalDate fechaDevolucionReal;
  public Prestamo(String codigoLibro,String tituloLibro,Usuario socio,LocalDate fechaPrestamo) throws PrestamoInvalidoException{
    this.codigoLibro=codigoLibro;
    this.tituloLibro=tituloLibro;
    this.socio=socio;
    this.fechaPrestamo=LocalDate.parse(formatoFecha.format(fechaPrestamo));
    this.fechaDevolucionPrevista= LocalDate.parse(formatoFecha.format(this.fechaPrestamo.plusDays(14)));
    if(!this.codigoLibro.matches("[A-Z]{3}\\d{4}")){
      throw new PrestamoInvalidoException("Código del libro incorrecto");
    }
    if(this.tituloLibro==null){
      throw new PrestamoInvalidoException("Título del libro incorrecto");
    }
    if(this.fechaPrestamo.isAfter(LocalDate.now()) || this.fechaPrestamo==null){
      throw new PrestamoInvalidoException("Fecha de registro incorrecta");
    }
  }
  public void registrarDevolucion(LocalDate fechaDevolucion) throws PrestamoInvalidoException{
    this.fechaDevolucionReal=LocalDate.parse(formatoFecha.format(fechaDevolucion));
    if(this.fechaDevolucionReal.isBefore(this.fechaPrestamo) || this.fechaDevolucionReal ==null){
      throw new PrestamoInvalidoException("Fecha de devolución incorrecta");
    }
  }
  public int calcularDiasRetraso(){
    if(this.fechaDevolucionReal!=null){
      return (int)(ChronoUnit.DAYS.between(this.fechaDevolucionPrevista, this.fechaDevolucionReal));
    }else {
      return (int)(ChronoUnit.DAYS.between(this.fechaDevolucionPrevista, LocalDate.now()));
    }
  }
  public boolean estaRetrasado(){
    return this.fechaDevolucionPrevista.isBefore(LocalDate.now());
  }
  @Override
  public String toString() {
    return "Código libro : "+this.codigoLibro+
        "\\n Título del libro: "+this.tituloLibro+
        "\\n Socio: "+this.socio+
        "\\n Fecha prestamo: "+this.fechaPrestamo+
        "\\n Fecha devolucion prevista: "+this.fechaDevolucionPrevista+
        "\\n Fecha devolucion real: "+this.fechaDevolucionReal;
  }
  public String getCodigoLibro() {
    return codigoLibro;
  }
}