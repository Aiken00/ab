package kg.aiken.FirstProject.Exception;

public class UserEmailAlreadyExistException extends RuntimeException{

    public UserEmailAlreadyExistException() {
        super("Данный email уже существует");
    }
}
