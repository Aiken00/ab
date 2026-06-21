package kg.aiken.FirstProject.Repository;

import kg.aiken.FirstProject.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {


    List<Transaction> findByFromAccountIdOrToAccountId(Long fromAccountId, Long toAccountId);

    @Query("""
SELECT t
FROM Transaction t
WHERE
(t.fromAccountId = :accountId
 OR t.toAccountId = :accountId)
AND
t.createdAt BETWEEN :start AND :end
""")
    List<Transaction> findHistoryByDate(
            Long accountId,
            LocalDateTime start,
            LocalDateTime end
    );

}
