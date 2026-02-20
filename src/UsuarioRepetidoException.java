public class UsuarioRepetidoException extends Exception {
  public UsuarioRepetidoException(String mensaje) {
    super("ERROR: "+mensaje);
  }
}
