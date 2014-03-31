package app.util;

import app.domain.Transaction;

import java.math.BigDecimal;

public class TransactionValidator {

    private static final String ACCOUNT_NUMBER_FORMAT_REGEXP = "\\d{14}";

    public static void validate(Transaction transaction) {

        BigDecimal amount = transaction.getMoneyAmount();
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Invalid transaction, amount value is null or less than zero.");
        }
        String payerAccount = transaction.getPayerAccountNumber();
        if (payerAccount == null || !isValidAccountNumberFormat(payerAccount)) {
            throw new IllegalArgumentException("Invalid transaction, payer account has an invalid format.");
        }
        String beneficiaryAccount = transaction.getBeneficiaryAccountNumber();
        if (beneficiaryAccount == null || !isValidAccountNumberFormat(beneficiaryAccount)) {
            throw new IllegalArgumentException("Invalid transaction, beneficiary account has an invalid format.");
        }
    }

    private static boolean isValidAccountNumberFormat(String accountNumber) {
        return accountNumber.matches(ACCOUNT_NUMBER_FORMAT_REGEXP);
    }
}
