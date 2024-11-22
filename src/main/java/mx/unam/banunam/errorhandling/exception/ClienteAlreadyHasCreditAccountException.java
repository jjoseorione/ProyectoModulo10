package mx.unam.banunam.errorhandling.exception;

public class ClienteAlreadyHasCreditAccountException extends RuntimeException{
    public ClienteAlreadyHasCreditAccountException(Integer noCliente){
        super("Ya existe una cuenta de crédito para el cliente " + noCliente);
    }
}
