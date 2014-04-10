package app.service;

import app.aop.security.Authenticated;
import app.domain.Account;
import app.domain.Transaction;
import app.util.TransactionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class TransferServiceImpl implements TransferService {

    private AccountService accountService;
    private TransactionService transactionService;

    @Autowired
    public TransferServiceImpl(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @Authenticated
    @Transactional(timeout = 5)
    @Override
    public void transferAmount(Transaction transaction) {

        TransactionValidator.validate(transaction);

        Account payerAccount = accountService.findByNumber(transaction.getPayerAccountNumber());
        Account beneficiaryAccount = accountService.findByNumber(transaction.getBeneficiaryAccountNumber());

        BigDecimal amount = transaction.getMoneyAmount();
        payerAccount.setMoneyAmount(payerAccount.getMoneyAmount().subtract(amount));
        beneficiaryAccount.setMoneyAmount(beneficiaryAccount.getMoneyAmount().add(amount));

        accountService.updateMoneyAmount(payerAccount);
        accountService.updateMoneyAmount(beneficiaryAccount);

        transactionService.save(transaction);
    }

}
