package mx.unam.banunam.errorhandling.exception;

public class TipoUsuarioNotExistsException extends RuntimeException{
    public TipoUsuarioNotExistsException(String tipoUsuario){
        super("No existe el tipoUsuario " + tipoUsuario);
    }
}
