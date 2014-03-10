package app;

import app.domain.Product;
import app.service.ProductManagerService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * User: Bogdan Apetrei
 * Date: 3/10/14
 * Time: 4:37 PM
 */
public class Application {

    public void run() {
        ApplicationContext context = new ClassPathXmlApplicationContext("/META-INF/applicationContext.xml");

        ProductManagerService productManager = (ProductManagerService) context.getBean("productManager");

        Product product = new Product();
        product.setPrice(new Double(100));
        product.setDescription("First product");

        productManager.saveProduct(product);

        List<Product> products = productManager.getProducts();

    }
}
