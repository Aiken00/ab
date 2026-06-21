package kg.aiken.FirstProject.Exception;

public class InvalidAmountException extends RuntimeException {
    public InvalidAmountException () {
        super("Сумма должна быть больше 0");
    }
}
