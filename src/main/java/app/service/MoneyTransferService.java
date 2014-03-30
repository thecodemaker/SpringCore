package app.service;

import app.domain.Account;
import app.domain.Transaction;
import app.util.TransactionValidator;

import java.math.BigDecimal;

public class MoneyTransferService implements TransferService {

    private AccountService accountService;
    private TransactionService transactionService;

    public MoneyTransferService(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

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
