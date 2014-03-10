package app.repository;

import app.domain.Product;

import java.util.List;

/**
 * User: Bogdan Apetrei
 * Date: 3/10/14
 * Time: 3:40 PM
 */

public interface ProductRepository {

    List<Product> getProductList();

    void saveProduct(Product prod);

}
