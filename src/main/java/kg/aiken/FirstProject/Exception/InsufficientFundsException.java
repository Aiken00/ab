package kg.aiken.FirstProject.Exception;

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException() {
        super("Недостаточно средств");
    }

}
