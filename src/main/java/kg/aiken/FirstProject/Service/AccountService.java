package kg.aiken.FirstProject.Service;

import kg.aiken.FirstProject.Entity.Account;
import kg.aiken.FirstProject.Entity.Transaction;
import kg.aiken.FirstProject.Exception.*;
import kg.aiken.FirstProject.Repository.TransactionRepository;
import kg.aiken.FirstProject.enums.Currency;
import kg.aiken.FirstProject.Entity.User;
import kg.aiken.FirstProject.Repository.AccountRepository;
import kg.aiken.FirstProject.Repository.UserRepository;
import kg.aiken.FirstProject.enums.TransactionStatus;
import kg.aiken.FirstProject.enums.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public Account createAccount(Long userId, Currency currency) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        long count = accountRepository.countByUserId(userId);

        if (count >= 3) {
            throw new SettlementAccountExceededLimitException();
        }

        Account account = new Account();

        account.setUser(user);
        account.setCurrency(currency);
        account.setBalance(BigDecimal.ZERO);
        account.setAccountNumber(UUID.randomUUID().toString());
        account.setCreatedAt(LocalDateTime.now());

        return accountRepository.save(account);
    }

    public Account getById(Long id) {
        return accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
    }

    public Page<Account> getAll(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

    public BigDecimal getBalance(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);
        return account.getBalance();
    }

    @Transactional
    public void deposit(Long accountId, BigDecimal amount) {

        Account account = accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);

        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
    }

    @Transactional
    public void withdraw(Long accountId, BigDecimal amount) {

        Account account = accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);


        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException();
        }

        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
    }

    @Transactional
    public void transfer(Long fromAccId, Long toAccId, BigDecimal amount) {

        Account from = accountRepository.findById(fromAccId).orElseThrow(AccountNotFoundException::new);

        Account to = accountRepository.findById(toAccId).orElseThrow(AccountNotFoundException::new);

        if (fromAccId.equals(toAccId)) {
            throw new TransferToSameAccountException();
        }

        if (from.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException();
        }

        if (from.getCurrency() != to.getCurrency()) {
            throw new DifferentCurrenciesException();
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException();
        }
        from.setBalance(from.getBalance().subtract(amount));
        to.setBalance(to.getBalance().add(amount));

        accountRepository.save(from);
        accountRepository.save(to);

        Transaction transaction = new Transaction();

        transaction.setFromAccountId(fromAccId);
        transaction.setToAccountId(toAccId);
        transaction.setAmount(amount);
        transaction.setType(TransactionType.TRANSFER);
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setCreatedAt(LocalDateTime.now());
        transactionRepository.save(transaction);
    }

    public List<Transaction> getHistory(Long accountId) {
        return transactionRepository.findByFromAccountIdOrToAccountId(accountId, accountId);
    }

    public List<Transaction> getHistoryByDate(Long accountId, LocalDateTime start, LocalDateTime end) {
        return transactionRepository.findHistoryByDate(accountId, start, end);
    }
}
