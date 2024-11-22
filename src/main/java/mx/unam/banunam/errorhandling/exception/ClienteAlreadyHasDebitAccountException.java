package mx.unam.banunam.errorhandling.exception;

public class ClienteAlreadyHasDebitAccountException extends RuntimeException{
    public ClienteAlreadyHasDebitAccountException(Integer noCliente){
        super("Ya existe una cuenta de débito para el cliente " + noCliente);
    }
}
