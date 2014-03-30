package app.domain;

import java.math.BigDecimal;

public class Account extends Entity {

    private String number;
    private String name;
    private BigDecimal moneyAmount;

    public Account(String number, String name, BigDecimal moneyAmount) {
        this.number = number;
        this.name = name;
        this.moneyAmount = moneyAmount;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(BigDecimal moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    @Override
    public String toString() {
        return "Account{" +
                "number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", moneyAmount=" + moneyAmount +
                '}';
    }
}
