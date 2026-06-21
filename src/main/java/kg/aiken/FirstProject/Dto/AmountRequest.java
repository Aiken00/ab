package kg.aiken.FirstProject.Dto;


import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record AmountRequest(@Positive BigDecimal amount) {

}
