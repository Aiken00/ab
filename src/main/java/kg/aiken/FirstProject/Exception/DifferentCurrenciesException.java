package kg.aiken.FirstProject.Exception;

public class DifferentCurrenciesException extends RuntimeException{
    public DifferentCurrenciesException() {
        super("Перевод с одной валюты на другую недоступен");
    }
}
