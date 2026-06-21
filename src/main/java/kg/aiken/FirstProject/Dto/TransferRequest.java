package kg.aiken.FirstProject.Dto;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransferRequest(Long fromAccountId, Long toAccountId, @Positive BigDecimal amount) {

}
