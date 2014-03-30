package app.domain;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction extends Entity {

    private String payerAccountNumber;
    private String beneficiaryAccountNumber;
    private BigDecimal moneyAmount;
    private Date date;

    public Transaction(String payerAccountNumber, String beneficiaryAccount, BigDecimal moneyAmount) {
        this.payerAccountNumber = payerAccountNumber;
        this.beneficiaryAccountNumber = beneficiaryAccount;
        this.moneyAmount = moneyAmount;
        this.date = new Date();
    }

    public String getPayerAccountNumber() {
        return payerAccountNumber;
    }

    public String getBeneficiaryAccountNumber() {
        return beneficiaryAccountNumber;
    }

    public BigDecimal getMoneyAmount() {
        return moneyAmount;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                ", payerAccountNumber='" + payerAccountNumber + '\'' +
                ", beneficiaryAccountNumber='" + beneficiaryAccountNumber + '\'' +
                ", moneyAmount=" + moneyAmount +
                ", date=" + date +
                '}';
    }
}
