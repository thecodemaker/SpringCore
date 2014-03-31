package app.repository;

import app.domain.Transaction;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class JdbcTransactionRepositoryIT {

    private static final String PAYER_ACCOUNT_NUMBER = "12345678901234";
    private static final String BENEFICIARY_ACCOUNT_NUMBER = "12345678909876";
    private static final BigDecimal MONEY_AMOUNT = new BigDecimal("10.00");

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Test
    @DirtiesContext
    public void testInsertSuccess() throws Exception {

        transactionRepository.insert(new Transaction(PAYER_ACCOUNT_NUMBER, BENEFICIARY_ACCOUNT_NUMBER, MONEY_AMOUNT));

        List<Transaction> transactions = jdbcTemplate.query(
                "SELECT * FROM t_transaction WHERE payer_account = ? and beneficiary_account = ? and money_amount = ?",
                new RowMapper<Transaction>() {
                    @Override
                    public Transaction mapRow(ResultSet resultSet, int i) throws SQLException {
                        Transaction transaction = new Transaction(
                                resultSet.getString("payer_account"),
                                resultSet.getString("beneficiary_account"),
                                resultSet.getBigDecimal("money_amount", 2));
                        transaction.setEntityId(resultSet.getLong("id"));
                        return transaction;
                    }
                },
                PAYER_ACCOUNT_NUMBER, BENEFICIARY_ACCOUNT_NUMBER, MONEY_AMOUNT);

        assertThat(transactions, is(notNullValue()));
        assertThat(transactions.size(), is(1));

        Transaction transaction = transactions.get(0);
        assertThat(transaction.getEntityId(), is(0L));
        assertThat(transaction.getPayerAccountNumber(), is(PAYER_ACCOUNT_NUMBER));
        assertThat(transaction.getBeneficiaryAccountNumber(), is(BENEFICIARY_ACCOUNT_NUMBER));
        assertThat(transaction.getMoneyAmount(), is(MONEY_AMOUNT));
        assertThat(transaction.getDate(), is(CoreMatchers.notNullValue()));
    }

    @Test
    public void testInsertFailed() throws Exception {
        exception.expect(DataIntegrityViolationException.class);

        transactionRepository.insert(new Transaction(null, BENEFICIARY_ACCOUNT_NUMBER, MONEY_AMOUNT));

    }
}
