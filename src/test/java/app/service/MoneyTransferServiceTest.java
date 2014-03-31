package app.service;

import app.domain.Account;
import app.domain.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MoneyTransferServiceTest {

    private static String PAYER_ACCOUNT_NUMBER = "12345678901234";
    private static String BENEFICIARY_ACCOUNT_NUMBER = "12345678909876";
    private static BigDecimal MONEY_AMOUNT = BigDecimal.TEN;
    private static Account PAYER_ACCOUNT = new Account(PAYER_ACCOUNT_NUMBER, "NAME1", new BigDecimal("100.00"));
    private static Account BENEFICIARY_ACCOUNT = new Account(BENEFICIARY_ACCOUNT_NUMBER, "NAME2", new BigDecimal("100.00"));
    private static Transaction TRANSACTION = new Transaction(PAYER_ACCOUNT_NUMBER, BENEFICIARY_ACCOUNT_NUMBER, MONEY_AMOUNT);

    @InjectMocks
    private MoneyTransferService transferService;

    @Mock
    private TransactionService transactionService;

    @Mock
    private AccountService accountService;

    @Before
    public void setUp() throws Exception {

        when(accountService.findByNumber(PAYER_ACCOUNT_NUMBER)).thenReturn(PAYER_ACCOUNT);
        when(accountService.findByNumber(BENEFICIARY_ACCOUNT_NUMBER)).thenReturn(BENEFICIARY_ACCOUNT);
    }

    @Test
    public void testTransferAmount() throws Exception {

        transferService.transferAmount(TRANSACTION);

        verify(accountService,times(1)).updateMoneyAmount(PAYER_ACCOUNT);
        verify(accountService,times(1)).updateMoneyAmount(BENEFICIARY_ACCOUNT);

        verify(transactionService, times(1)).save(TRANSACTION);
    }

}
