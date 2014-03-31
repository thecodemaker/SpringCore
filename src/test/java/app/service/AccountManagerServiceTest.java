package app.service;

import app.domain.Account;
import app.repository.AccountRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountManagerServiceTest {

    private static String ACCOUNT_NUMBER = "12345678901234";
    private static String ACCOUNT_NAME = "NAME";
    private static BigDecimal ACCOUNT_MONEY_AMOUNT = new BigDecimal("100.00");
    private static Account ACCOUNT = new Account(ACCOUNT_NUMBER, ACCOUNT_NAME, ACCOUNT_MONEY_AMOUNT);

    @InjectMocks
    private AccountManagerService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testFindByNumberSuccess() throws Exception {

        when(accountRepository.findByNumber(ACCOUNT_NUMBER)).thenReturn(Arrays.asList(ACCOUNT));

        Account actualAccount = accountService.findByNumber(ACCOUNT_NUMBER);

        assertThat(actualAccount, is(ACCOUNT));
        verify(accountRepository, times(1)).findByNumber(ACCOUNT_NUMBER);
    }

    @Test
    public void testFindByNumberFail() throws Exception {
        exception.expect(EmptyResultDataAccessException.class);
        exception.expectMessage("No account found for number " + ACCOUNT_NUMBER);

        when(accountRepository.findByNumber(ACCOUNT_NUMBER)).thenReturn(Collections.<Account>emptyList());

        accountService.findByNumber(ACCOUNT_NUMBER);
    }

    @Test
    public void testSaveSuccess() throws Exception {

        when(accountRepository.insert(ACCOUNT)).thenReturn(1);

        accountService.save(ACCOUNT);

        verify(accountRepository, times(1)).insert(ACCOUNT);
    }

    @Test
    public void testSaveFailNoUpdate() throws Exception {
        exception.expect(IncorrectResultSizeDataAccessException.class);
        exception.expectMessage("Account not saved.");

        when(accountRepository.insert(ACCOUNT)).thenReturn(0);

        accountService.save(ACCOUNT);

    }

    @Test
    public void testSaveFailMoreThanOneFieldUpdate() throws Exception {
        exception.expect(IncorrectResultSizeDataAccessException.class);
        exception.expectMessage("More than once account was saved.");

        when(accountRepository.insert(ACCOUNT)).thenReturn(2);

        accountService.save(ACCOUNT);
    }

    @Test
    public void testUpdateMoneyAmountSuccess() throws Exception {

        when(accountRepository.updateMoneyAmount(ACCOUNT)).thenReturn(1);

        accountService.updateMoneyAmount(ACCOUNT);

        verify(accountRepository, times(1)).updateMoneyAmount(ACCOUNT);
    }

    @Test
    public void testUpdateMoneyAmountFailNoUpdate() throws Exception {
        exception.expect(IncorrectResultSizeDataAccessException.class);
        exception.expectMessage("No accounts were updated");

        when(accountRepository.updateMoneyAmount(ACCOUNT)).thenReturn(0);

        accountService.updateMoneyAmount(ACCOUNT);
    }

    @Test
    public void testUpdateMoneyAmountMoreThanOneFieldUpdate() throws Exception {
        exception.expect(IncorrectResultSizeDataAccessException.class);
        exception.expectMessage("More than once account was updated");

        when(accountRepository.updateMoneyAmount(ACCOUNT)).thenReturn(2);

        accountService.updateMoneyAmount(ACCOUNT);
    }
}