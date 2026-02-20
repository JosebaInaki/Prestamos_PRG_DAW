public class PrestamoInvalidoException extends Exception {
  public PrestamoInvalidoException(String mensaje) {
    super("ERROR: "+mensaje);
  }
}
