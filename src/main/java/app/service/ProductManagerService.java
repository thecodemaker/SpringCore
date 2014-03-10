package app.service;

import app.domain.Product;

import java.util.List;

/**
 * User: Bogdan Apetrei
 * Date: 3/10/14
 * Time: 3:14 PM
 */
public interface ProductManagerService {

   void  increasePrice(int percentage);

   List<Product> getProducts();

    void saveProduct(Product prod);
}
