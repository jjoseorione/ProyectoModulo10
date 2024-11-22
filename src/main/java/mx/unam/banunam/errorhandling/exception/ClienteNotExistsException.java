package mx.unam.banunam.errorhandling.exception;

public class ClienteNotExistsException extends RuntimeException{
    public ClienteNotExistsException(Integer cliente){
        super("No existe el cliente número " + cliente);
    }
}
