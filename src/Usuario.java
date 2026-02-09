import java.time.Duration;
import java.time.LocalDate;
public class Usuario {
  private String nombre;
  private String email="*@*.*";
  private String numeroSocio="SOC\\d{5}";
  private LocalDate fechaRegistro;
  private boolean sancionado;
  private LocalDate fechaFinSancion;
  public Usuario(String nombre,String email,String numeroSocio,LocalDate fechaRegistro) throws UsuarioInvalidoException {
    try {
      this.nombre = nombre;
      this.email = email;
      this.numeroSocio = numeroSocio;
      this.fechaRegistro = fechaRegistro;
    } catch (Exception e) {
        if (this.nombre == null) {
        throw new UsuarioInvalidoException("Nombre incorrecto");
        }
        if (this.email == null) {
          throw new UsuarioInvalidoException("Email incorrecto");
        }
        if (this.numeroSocio == null) {
          throw new UsuarioInvalidoException("Numero socio incorrecto");
        }
        if (this.fechaRegistro == null) {
          throw new UsuarioInvalidoException("Fecha registro incorrecta");
        }
    }
  }
  public void sancionar(int diasSancion){
    fechaFinSancion= LocalDate.of(1,1,1111).plusDays(diasSancion);
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
        "\\n NumeroSocio: "+this.numeroSocio+
        "\\n Fecha registro: "+this.fechaRegistro+
        "\\n Sancionado: "+this.sancionado+
        "\\n Fecha fin sanción: "+this.fechaFinSancion;
  }
}