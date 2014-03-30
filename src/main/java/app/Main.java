package app;

import app.domain.Account;
import app.domain.Transaction;
import app.service.AccountManagerService;
import app.service.MoneyTransferService;
import app.service.TransferService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("/spring/application-config.xml","/spring/database-config.xml");

        Transaction transaction = new Transaction("12345678901234", "12345678909876", BigDecimal.TEN);
        TransferService transferService = context.getBean("transferService", MoneyTransferService.class);
        transferService.transferAmount(transaction);

        AccountManagerService accountService = context.getBean("accountService", AccountManagerService.class);
        Account payerAccount = accountService.findByNumber("12345678901234");
        Account beneficiaryAccount = accountService.findByNumber("12345678909876");

        System.out.println(payerAccount);
        System.out.println(beneficiaryAccount);
    }
}
