-- =====================================================
-- 1. Получить всех пользователей, у которых суммарный
-- баланс всех счетов больше 10 000
-- =====================================================

SELECT u.*
FROM users u
         JOIN accounts a ON u.id = a.user_id
GROUP BY u.id
HAVING SUM(a.balance) > 10000;


-- =====================================================
-- 2. Найти пользователя по email
-- =====================================================

SELECT *
FROM users
WHERE email = 'test@test.com';


-- =====================================================
-- 3. Получить общую сумму всех средств в системе
-- =====================================================

SELECT SUM(balance) AS total_balance
FROM accounts;


-- =====================================================
-- 4. Вывести пользователей, у которых нет ни одного счета
-- =====================================================

SELECT u.*
FROM users u
         LEFT JOIN accounts a
                   ON u.id = a.user_id
WHERE a.id IS NULL;


-- =====================================================
-- 5. Найти топ-5 пользователей с самым большим
-- суммарным балансом
-- =====================================================

SELECT
    u.id,
    u.full_name,
    SUM(a.balance) AS total_balance
FROM users u
         JOIN accounts a
              ON u.id = a.user_id
GROUP BY u.id, u.full_name
ORDER BY total_balance DESC
    LIMIT 5;


-- =====================================================
-- 6. Получить список счетов с ФИО владельца и балансом
-- =====================================================

SELECT
    a.id,
    a.account_number,
    a.balance,
    u.full_name
FROM accounts a
         JOIN users u
              ON a.user_id = u.id;


-- =====================================================
-- 7. Получить все переводы за последние 7 дней
-- с именем отправителя и получателя
-- =====================================================

SELECT
    t.id,
    sender.full_name   AS sender_name,
    receiver.full_name AS receiver_name,
    t.amount,
    t.created_at
FROM transactions t

         JOIN accounts from_acc
              ON t.from_account_id = from_acc.id

         JOIN users sender
              ON from_acc.user_id = sender.id

         JOIN accounts to_acc
              ON t.to_account_id = to_acc.id

         JOIN users receiver
              ON to_acc.user_id = receiver.id

WHERE t.created_at >= NOW() - INTERVAL '7 DAY';


-- =====================================================
-- 8. Найти пользователей, у которых более 2 счетов
-- =====================================================

SELECT
    u.id,
    u.full_name,
    COUNT(a.id) AS account_count
FROM users u
         JOIN accounts a
              ON u.id = a.user_id
GROUP BY u.id, u.full_name
HAVING COUNT(a.id) > 2;


-- =====================================================
-- 9. Получить средний баланс счетов по каждому
-- пользователю
-- =====================================================

SELECT
    u.id,
    u.full_name,
    AVG(a.balance) AS average_balance
FROM users u
         JOIN accounts a
              ON u.id = a.user_id
GROUP BY u.id, u.full_name;