import java.time.LocalDate;

public class GestorBiblioteca {
  private final static int MAX_USUARIOS=50;
  private final static int MAX_PRESTAMOS=200;
  private Usuario[] usuarios;
  private Prestamo[] prestamos;
  private int numeroUsuarios;
  private int numeroPrestamos;
  public GestorBiblioteca(){
    usuarios= new Usuario[MAX_USUARIOS];
    prestamos= new Prestamo[MAX_PRESTAMOS];
    numeroUsuarios=0;
    numeroPrestamos=0;
  }
  public void registrarUsuario(Usuario usuario) throws UsuarioRepetidoException{
    for(int i=0;i<numeroUsuarios;i++){
      if(usuarios[i]==usuario){
        throw new UsuarioRepetidoException("Este usuario ya esta registrado");
      }
    }
    usuarios[numeroUsuarios]=usuario;
    numeroUsuarios++;
  }
  public Prestamo realizarPrestamo(String codigoLibro, String tituloLibro, LocalDate fechaPrestamo, Usuario usuario) throws PrestamoInvalidoException,UsuarioSancionadoException,LibroNoDisponibleException{
    if(usuario.estaSancionado()){
      throw new UsuarioSancionadoException("Este usuario está sancionado");
    }
    for(int i=0;i<numeroPrestamos;i++){
      if(prestamos[i].getCodigoLibro().equals(codigoLibro)){
        throw new LibroNoDisponibleException("Este libro no está disponible");
      }
    }
    return new Prestamo(codigoLibro,tituloLibro,usuario,fechaPrestamo);
  }
  public Usuario buscarUsuario(String numeroSocio){
    Usuario usuarioBuscado=null;
    for(int i=0;i<numeroUsuarios;i++){
      if(usuarios[i].getNumeroSocio().equals(numeroSocio)){
        usuarioBuscado=usuarios[i];
      }
    }
    return usuarioBuscado;
  }
  public void getPrestamos(){
    for(int i=0;i<numeroPrestamos;i++){
        prestamos[i].toString();
      System.out.println();
    }
  }
  public void getUsuarios(){
    for(int i=0;i<numeroUsuarios;i++){
      usuarios[i].toString();
      System.out.println();
    }
  }
  @Override
  public String toString() {
    this.getUsuarios();
    this.getPrestamos();
    return "Numero de usuarios : "+this.numeroUsuarios+
        "\\n Numero de prestamos: "+this.numeroPrestamos;
  }
}