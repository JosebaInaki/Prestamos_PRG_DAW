import java.time.LocalDate;

public class Usuario {
  private String nombre;
  private String email;
  private String numeroSocio;
  private LocalDate fechaRegistro;
  private boolean sancionado;
  private LocalDate fechaFinSancion;

  public Usuario(String nombre, String email, String numeroSocio, LocalDate fechaRegistro) throws UsuarioInvalidoException {

    if (nombre == null || nombre.isBlank()) {
      throw new UsuarioInvalidoException("Nombre incorrecto");
    }
    if (email == null || !email.matches(".+@.+\\..+")) {
      throw new UsuarioInvalidoException("Email incorrecto");
    }
    if (numeroSocio == null || !numeroSocio.matches("SOC\\d{5}")) {
      throw new UsuarioInvalidoException("Numero de socio incorrecto,\n Debe tener este formato: SOC + 5 dígitos");
    }
    if (fechaRegistro == null) {
      throw new UsuarioInvalidoException("Fecha de registro incorrecta");
    }

    this.nombre = nombre;
    this.email = email;
    this.numeroSocio = numeroSocio;
    this.fechaRegistro = fechaRegistro;
    this.sancionado = false;
    this.fechaFinSancion = null;
  }
  public void sancionar(int diasSancion, LocalDate inicioSancion) {
    this.sancionado = true;
    this.fechaFinSancion = inicioSancion.plusDays(diasSancion);
  }
  public void levantarSancion() {
    this.sancionado = false;
    this.fechaFinSancion = null;
  }
  public boolean estaSancionado() {
    if (!sancionado || fechaFinSancion == null) return false;
    return !LocalDate.now().isAfter(fechaFinSancion);
  }
  @Override
  public String toString() {
    return "Nombre: " + nombre +
        "\nEmail: " + email +
        "\nNumero Socio: " + numeroSocio +
        "\nFecha registro: " + fechaRegistro +
        "\nSancionado: " + estaSancionado() +
        "\nFecha fin sanción: " + fechaFinSancion;
  }
  public String getNumeroSocio() {
    return numeroSocio;
  }
  public LocalDate getFechaFinSancion() {
    return fechaFinSancion;
  }
  public boolean getSancionado(){
    return sancionado;
  }
}