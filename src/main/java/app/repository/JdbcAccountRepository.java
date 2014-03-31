package app.repository;

import app.domain.Account;
import app.util.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class JdbcAccountRepository implements AccountRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcAccountRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Account> findByNumber(String number) {
        return jdbcTemplate.query(
                "SELECT * FROM t_account WHERE number = ?",
                new AccountMapper(),
                number);
    }

    @Override
    public int insert(Account account) {
        return jdbcTemplate.update(
                "INSERT INTO t_account (number, name, money_amount) VALUES(?, ?, ?)",
                account.getNumber(),
                account.getName(),
                account.getMoneyAmount());
    }

    @Override
    public int updateMoneyAmount(Account account) {
        return jdbcTemplate.update(
                "UPDATE t_account SET money_amount = ? WHERE id = ?",
                account.getMoneyAmount(),
                account.getEntityId()
        );
    }
}
