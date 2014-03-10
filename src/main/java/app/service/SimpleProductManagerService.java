package app.service;

import app.domain.Product;
import app.repository.ProductRepository;

import java.util.List;

/**
 * User: Bogdan Apetrei
 * Date: 3/10/14
 * Time: 3:14 PM
 */
public class SimpleProductManagerService implements ProductManagerService {

    private ProductRepository productRepository;

    @Override
    public List<Product> getProducts() {
        return productRepository.getProductList();
    }

    @Override
    public void increasePrice(int percentage) {
        List<Product> products = productRepository.getProductList();
        if (products != null) {
            for (Product product : products) {
                double newPrice = product.getPrice().doubleValue() * (100 + percentage)/100;
                product.setPrice(newPrice);
                productRepository.saveProduct(product);
            }
        }
    }

    @Override
    public void saveProduct(Product product) {
        productRepository.saveProduct(product);
    }

    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
}
