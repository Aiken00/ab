package kg.aiken.FirstProject.Service;

import kg.aiken.FirstProject.Entity.Account;
import kg.aiken.FirstProject.Exception.InsufficientFundsException;
import kg.aiken.FirstProject.Repository.AccountRepository;
import kg.aiken.FirstProject.Repository.TransactionRepository;
import kg.aiken.FirstProject.Repository.UserRepository;
import kg.aiken.FirstProject.enums.Currency;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private AccountService service;

    @Test
    void transferSuccess() {
        Account from = new Account();
        from.setId(1L);
        from.setBalance(BigDecimal.valueOf(1000));
        from.setCurrency(Currency.KGS);

        Account to = new Account();
        to.setId(2L);
        to.setBalance(BigDecimal.valueOf(500));
        to.setCurrency(Currency.KGS);

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(from));

        when(accountRepository.findById(2L))
                .thenReturn(Optional.of(to));

        service.transfer(1L, 2L, BigDecimal.valueOf(300));

        assert from.getBalance().equals(BigDecimal.valueOf(700));

        assert to.getBalance().equals(BigDecimal.valueOf(800));

        verify(accountRepository).save(from);
        verify(accountRepository).save(to);
        verify(transactionRepository).save(any());

    }

    @Test
    void transferInsufficientFunds() {
        Account from = new Account();
        from.setId(1L);
        from.setBalance(BigDecimal.valueOf(100));
        from.setCurrency(Currency.KGS);

        Account to = new Account();
        to.setId(2L);
        to.setBalance(BigDecimal.valueOf(500));
        to.setCurrency(Currency.KGS);

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(from));
        when(accountRepository.findById(2L))
                .thenReturn(Optional.of(to));

        assertThrows(InsufficientFundsException.class, () -> service.transfer(1L,2L,BigDecimal.valueOf(500)));

        verify(accountRepository,never()).save(any());
        verify(transactionRepository,never()).save(any());
    }

}
