package kg.aiken.FirstProject.Exception;

public class SettlementAccountExceededLimitException extends IllegalStateException {
    public SettlementAccountExceededLimitException() {
        super("Вам доступно не более 3 расчётных счетов");
    }

}
