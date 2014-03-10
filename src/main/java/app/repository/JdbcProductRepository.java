package app.repository;


import app.domain.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * User: Bogdan Apetrei
 * Date: 3/10/14
 * Time: 3:41 PM
 */

public class JdbcProductRepository extends SimpleJdbcDaoSupport implements ProductRepository {

    /** Logger for this class and subclasses */
    private final Logger logger = LoggerFactory.getLogger(JdbcProductRepository.class);

    @Override
    public List<Product> getProductList() {
        logger.debug("JdbcProductRepository -> getProductList");
        List<Product> products = getSimpleJdbcTemplate().query(
                "select id, description, price from products",
                new ProductMapper());
        return products;
    }

    @Override
    public void saveProduct(Product prod) {
        logger.debug("JdbcProductRepository -> saveProduct: {}", prod.getDescription());
        int count = getSimpleJdbcTemplate().update(
                "update products set description = :description, price = :price where id = :id",
                new MapSqlParameterSource().addValue("description", prod.getDescription())
                        .addValue("price", prod.getPrice())
                        .addValue("id", prod.getId()));
        logger.debug("JdbcProductRepository -> Rows affected: {}", count);
    }

    private static class ProductMapper implements ParameterizedRowMapper<Product> {

        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product prod = new Product();
            prod.setId(rs.getInt("id"));
            prod.setDescription(rs.getString("description"));
            prod.setPrice(new Double(rs.getDouble("price")));
            return prod;
        }
    }
}
