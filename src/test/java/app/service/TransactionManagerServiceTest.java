package app.service;

import app.domain.Transaction;
import app.repository.TransactionRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

import java.math.BigDecimal;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionManagerServiceTest {

    private static final String PAYER_ACCOUNT_NUMBER = "12345678901234";
    private static final String BENEFICIARY_ACCOUNT_NUMBER = "12345678909876";
    private static final BigDecimal MONEY_AMOUNT = new BigDecimal("10.00");
    private static final Transaction TRANSACTION = new Transaction(PAYER_ACCOUNT_NUMBER, BENEFICIARY_ACCOUNT_NUMBER, MONEY_AMOUNT);

    @InjectMocks
    private TransactionManagerService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Rule
    public ExpectedException exception = ExpectedException.none();


    @Test
    public void testSaveSuccess() throws Exception {

        when(transactionRepository.insert(TRANSACTION)).thenReturn(1);

        transactionService.save(TRANSACTION);

        verify(transactionRepository, times(1)).insert(TRANSACTION);
    }

    @Test
    public void testSaveFailNoUpdate() throws Exception {
        exception.expect(IncorrectResultSizeDataAccessException.class);
        exception.expectMessage("Transaction not saved.");

        when(transactionRepository.insert(TRANSACTION)).thenReturn(0);

        transactionService.save(TRANSACTION);
    }

    @Test
    public void testSaveFailMoreThanOneFieldUpdate() throws Exception {
        exception.expect(IncorrectResultSizeDataAccessException.class);
        exception.expectMessage("More than once transaction was saved.");

        when(transactionRepository.insert(TRANSACTION)).thenReturn(2);

        transactionService.save(TRANSACTION);
    }
}
