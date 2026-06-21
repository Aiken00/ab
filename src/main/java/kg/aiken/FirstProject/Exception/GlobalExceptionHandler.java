package kg.aiken.FirstProject.Exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handle(UserNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", e.getMessage()));
    }

    @ExceptionHandler(SettlementAccountExceededLimitException.class)
    public ResponseEntity<Map<String, String>> handle(SettlementAccountExceededLimitException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("error", e.getMessage()));
    }

    @ExceptionHandler(UserEmailAlreadyExistException.class)
    public ResponseEntity<Map<String, String>> handle(UserEmailAlreadyExistException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("error", e.getMessage()));
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Map<String, String>> handle(AccountNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", e.getMessage()));

    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<Map<String, String>> handle(InsufficientFundsException e) {
        return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(Map.of("error", e.getMessage()));
    }

    @ExceptionHandler(TransferToSameAccountException.class)
    public ResponseEntity<Map<String, String>> handle(TransferToSameAccountException e) {
        return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(Map.of("error", e.getMessage()));
    }

    @ExceptionHandler(DifferentCurrenciesException.class)
    public ResponseEntity<Map<String, String>> handle(DifferentCurrenciesException e) {
        return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(Map.of("error", e.getMessage()));
    }




}
