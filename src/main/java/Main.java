import app.domain.Account;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("/spring/applicationConfig.xml");

        Account account = (Account) context.getBean("account");
        System.out.println(account.getName());
    }

}
