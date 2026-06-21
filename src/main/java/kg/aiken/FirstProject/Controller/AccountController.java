package kg.aiken.FirstProject.Controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kg.aiken.FirstProject.Dto.AmountRequest;
import kg.aiken.FirstProject.Dto.TransferRequest;
import kg.aiken.FirstProject.Entity.Account;
import kg.aiken.FirstProject.Entity.Transaction;
import kg.aiken.FirstProject.Service.AccountService;
import kg.aiken.FirstProject.enums.Currency;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService service;

    @Operation(
            summary = "Создать новый счет пользователю",
            description = "Создает новый банковский счет для указанного пользователя. Максимально допустимо 3 счета на одного пользователя."
    )
    @PostMapping
    public Account create(@RequestParam Long userId, @RequestParam Currency currency) {
        return service.createAccount(userId, currency);
    }

    @Operation(
            summary = "Получить список всех счетов",
            description = "Возвращает список всех счетов с поддержкой пагинации."
    )
    @GetMapping
    public Page<Account> getAll(Pageable pageable) {
        return service.getAll(pageable);
    }

    @Operation(
            summary = "Получить счет по идентификатору",
            description = "Возвращает информацию о счете по его уникальному идентификатору."
    )
    @GetMapping("{id}")
    public Account getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @Operation(
            summary = "Получить баланс счета",
            description = "Возвращает текущий баланс указанного счета."
    )
    @GetMapping("/balance/{id}")
    public BigDecimal getBalance(@PathVariable Long id) {
        return service.getBalance(id);
    }

    @Operation(
            summary = "Пополнить баланс счета",
            description = "Увеличивает баланс счета на указанную сумму. Сумма должна быть больше нуля."
    )
    @PostMapping("/deposit/{id}")
    public void deposit(@PathVariable Long id, @Valid @RequestBody AmountRequest request) {
        service.deposit(id, request.amount());
    }

    @Operation(
            summary = "Списать средства со счета",
            description = "Уменьшает баланс счета на указанную сумму. Баланс счета не может стать отрицательным."
    )
    @PostMapping("/withdraw/{id}")
    public void withdraw(@PathVariable Long id, @Valid @RequestBody AmountRequest request) {
        service.withdraw(id, request.amount());
    }

    @Operation(
            summary = "Перевод между счетами",
            description = """
                    Выполняет перевод денежных средств между счетами.
                    
                    Проверяется:
                    - существование счетов;
                    - достаточность средств;
                    - сумма операции больше нуля;
                    - запрет перевода на тот же счет;
                    - атомарность операции через транзакцию.
                    """
    )
    @PostMapping("/transfer")
    public void transfer(@Valid @RequestBody TransferRequest request) {
        service.transfer(request.fromAccountId(), request.toAccountId(), request.amount());
    }

    @Operation(
            summary = "Получить историю операций",
            description = "Возвращает полный список операций по указанному счету."
    )
    @GetMapping("/history/{id}")
    public List<Transaction> getHistory(@PathVariable Long id) {
        return service.getHistory(id);
    }

    @Operation(
            summary = "Получить историю операций за период",
            description = "Возвращает список операций по счету за указанный диапазон дат."
    )
    @GetMapping("/history/filter/{id}")
    public List<Transaction> getHistory(@PathVariable Long id, @RequestParam LocalDateTime start, @RequestParam LocalDateTime end) {
        return service.getHistoryByDate(id, start, end);
    }
}