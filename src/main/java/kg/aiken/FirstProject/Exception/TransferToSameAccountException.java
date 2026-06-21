package kg.aiken.FirstProject.Exception;

public class TransferToSameAccountException extends RuntimeException{

    public TransferToSameAccountException () {
        super("Нельзя переводить на тот же счёт");
    }
}
