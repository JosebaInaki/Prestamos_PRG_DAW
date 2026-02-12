import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Usuario {
  private String nombre;
  private String email;
  private String numeroSocio;
  private LocalDate fechaRegistro;
  DateTimeFormatter formatoFecha =  DateTimeFormatter.ofPattern("dd/MM/yyyy");
  private boolean sancionado;
  private LocalDate fechaFinSancion;
  public Usuario(String nombre,String email,String numeroSocio,LocalDate fechaRegistro) throws UsuarioInvalidoException {
      this.nombre = nombre;
      this.email = email;
      this.numeroSocio = numeroSocio;
      this.fechaRegistro = LocalDate.parse(formatoFecha.format(fechaRegistro));
      if (this.nombre == null) {
        throw new UsuarioInvalidoException("Nombre incorrecto");
      }
      if (!this.email.matches(".+@.+\\..+")){
        throw new UsuarioInvalidoException("Email incorrecto");
      }
      if (!this.numeroSocio.matches("SOC\\d{5}")) {
        throw new UsuarioInvalidoException("Numero de socio incorrecto");
      }
      if (this.fechaRegistro == null) {
        throw new UsuarioInvalidoException("Fecha de registro incorrecta");
      }
  }
  public void sancionar(int diasSancion, LocalDate inicioSancion){
    fechaFinSancion= LocalDate.parse(formatoFecha.format(inicioSancion.plusDays(diasSancion)));
  }
  public void levantarSancion(){
    this.sancionado=false;
    this.fechaFinSancion=null;
  }
  public boolean estaSancionado(){
    return  sancionado;
  }
  @Override
  public String toString() {
    return "Nombre : "+this.nombre+
        "\\n Email: "+this.email+
        "\\n Numero Socio: "+this.numeroSocio+
        "\\n Fecha registro: "+this.fechaRegistro+
        "\\n Sancionado: "+this.sancionado+
        "\\n Fecha fin sanción: "+this.fechaFinSancion;
  }
  public String getNumeroSocio() {
    return numeroSocio;
  }
}