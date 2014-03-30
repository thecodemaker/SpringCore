package app.repository;

import app.domain.Account;
import app.util.AccountMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class JdbcAccountRepositoryTestIT {

    private static String ACCOUNT_NUMBER = "12345678901234";
    private static String ACCOUNT_NAME = "NAME";
    private static BigDecimal ACCOUNT_MONEY_AMOUNT = new BigDecimal("100.00");

    @Autowired
    private AccountRepository accountRepository;

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
    public void testFindByNumberReturnOnceResult() throws Exception {

        jdbcTemplate.update(
                "INSERT INTO t_account (number, name, money_amount) VALUES(?, ?, ?)",
                ACCOUNT_NUMBER, ACCOUNT_NAME, ACCOUNT_MONEY_AMOUNT);

        List<Account> accounts = accountRepository.findByNumber(ACCOUNT_NUMBER);

        assertThat(accounts, is(notNullValue()));
        assertThat(accounts.size(), is(1));

        Account account = accounts.get(0);
        assertThat(account.getEntityId(), is(0L));
        assertThat(account.getName(), is(ACCOUNT_NAME));
        assertThat(account.getNumber(), is(ACCOUNT_NUMBER));
        assertThat(account.getMoneyAmount(), is(ACCOUNT_MONEY_AMOUNT));
    }

    @Test
    public void testFindByNumberReturnEmptyList() throws Exception {

        List<Account> accounts = accountRepository.findByNumber(ACCOUNT_NUMBER);

        assertThat(accounts, is(notNullValue()));
        assertThat(accounts.size(), is(0));
    }

    @Test
    @DirtiesContext
    public void testInsertSuccess() throws Exception {

        accountRepository.insert(new Account(ACCOUNT_NUMBER, ACCOUNT_NAME, ACCOUNT_MONEY_AMOUNT));

        List<Account> accounts = jdbcTemplate.query(
                "SELECT * FROM t_account WHERE number = ?",
                new AccountMapper(),
                ACCOUNT_NUMBER);

        assertThat(accounts, is(notNullValue()));
        assertThat(accounts.size(), is(1));

        Account account = accounts.get(0);
        assertThat(account.getEntityId(), is(0L));
        assertThat(account.getName(), is(ACCOUNT_NAME));
        assertThat(account.getNumber(), is(ACCOUNT_NUMBER));
        assertThat(account.getMoneyAmount(), is(ACCOUNT_MONEY_AMOUNT));
    }

    @Test
    public void testInsertFailed() throws Exception {
        exception.expect(DataIntegrityViolationException.class);

        accountRepository.insert(new Account(null, ACCOUNT_NAME, ACCOUNT_MONEY_AMOUNT));

    }

    @Test
    @DirtiesContext
    public void testUpdateMoneyAmountSuccess() throws Exception {

        jdbcTemplate.update(
                "INSERT INTO t_account (number, name, money_amount) VALUES(?, ?, ?)",
                ACCOUNT_NUMBER, ACCOUNT_NAME, ACCOUNT_MONEY_AMOUNT);

        List<Account> accounts = jdbcTemplate.query("SELECT * FROM t_account WHERE number = ?",
                new AccountMapper(),
                ACCOUNT_NUMBER);

        BigDecimal NEW_MONEY_AMOUNT = ACCOUNT_MONEY_AMOUNT.add(BigDecimal.TEN);
        Account account = accounts.get(0);
        account.setMoneyAmount(NEW_MONEY_AMOUNT);

        accountRepository.updateMoneyAmount(account);

        BigDecimal moneyAmount = jdbcTemplate.queryForObject(
                "SELECT money_amount FROM t_account WHERE id = ?", BigDecimal.class, account.getEntityId());
        moneyAmount = moneyAmount.setScale(2);

        assertThat(moneyAmount, is(NEW_MONEY_AMOUNT));
    }

    @Test
    public void testUpdateMoneyAmountFail() throws Exception {

        Account account = new Account(ACCOUNT_NUMBER, ACCOUNT_NAME, ACCOUNT_MONEY_AMOUNT);
        account.setEntityId(0L);

        accountRepository.updateMoneyAmount(account);
    }
}