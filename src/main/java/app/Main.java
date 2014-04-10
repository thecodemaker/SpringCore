package app;

import app.aop.security.SecurityContext;
import app.domain.Account;
import app.domain.Transaction;
import app.service.AccountService;
import app.service.SecurityService;
import app.service.TransferService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;

public class Main {

    private static final String PAYER_ACCOUNT = "12345678901234";
    private static final String BENEFICIARY_ACCOUNT = "12345678909876";

    private ApplicationContext context;

    private TransferService transferService;
    private AccountService accountService;
    private SecurityService securityService;

    public static void main(String[] args) {
        Main app = new Main();
        app.loadContext();
        app.loadServices();

        app.login("guest", "endava");
        app.printAccountsBalance();
        app.transferMoney(BigDecimal.TEN);
        app.printAccountsBalance();

        app.cleanUp();
    }

    private void loadContext() {
        context = new ClassPathXmlApplicationContext("/spring/application-config.xml",
                "/spring/aop-config.xml",
                "/spring/tx-config.xml",
                "/spring/database-config.xml");
    }

    private void loadServices() {
        transferService = context.getBean(TransferService.class);
        accountService = context.getBean(AccountService.class);
        securityService = context.getBean(SecurityService.class);
    }

    private void login(String username, String password) {
        securityService.authenticate(username, password);
    }

    private void transferMoney(BigDecimal amount) {
        // create transaction
        Transaction transaction = new Transaction(PAYER_ACCOUNT, BENEFICIARY_ACCOUNT, amount);
        transferService.transferAmount(transaction);
    }

    private void printAccountsBalance() {
        // check accounts
        Account payerAccount = accountService.findByNumber(PAYER_ACCOUNT);
        Account beneficiaryAccount = accountService.findByNumber(BENEFICIARY_ACCOUNT);

        // print details
        System.out.println("--- ACCOUNT BALANCE ---");
        System.out.println(payerAccount);
        System.out.println(beneficiaryAccount);
    }

    private void cleanUp() {
        SecurityContext.clear();
    }

}
