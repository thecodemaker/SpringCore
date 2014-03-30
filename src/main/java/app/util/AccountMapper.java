package app.util;

import app.domain.Account;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountMapper  implements RowMapper<Account> {

    @Override
    public Account mapRow(ResultSet resultSet, int i) throws SQLException {
        Account account = new Account(
                resultSet.getString("number"),
                resultSet.getString("name"),
                resultSet.getBigDecimal("money_amount", 2));
        account.setEntityId(resultSet.getLong("id"));
        return account;
    }
}
