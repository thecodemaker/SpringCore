package app.repository;

import app.domain.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class JdbcTransactionRepository implements TransactionRepository {

    private JdbcTemplate jdbcTemplate;

    public JdbcTransactionRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int insert(Transaction transaction) {
        return jdbcTemplate.update(
                "INSERT INTO t_transaction (payer_account, beneficiary_account, money_amount, date) VALUES(?, ?, ?, ?)",
                transaction.getPayerAccountNumber(),
                transaction.getBeneficiaryAccountNumber(),
                transaction.getMoneyAmount(),
                transaction.getDate());
    }
}