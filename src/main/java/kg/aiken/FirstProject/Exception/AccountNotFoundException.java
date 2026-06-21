package kg.aiken.FirstProject.Exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException() {
        super("Данный счёт не найден");
    }
}
