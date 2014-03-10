package app.domain;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * User: Bogdan Apetrei
 * Date: 3/10/14
 * Time: 3:21 PM
 */

public class ProductTests {

    private Product product;

    @Before
    public void setUp() throws Exception {
        product = new Product();
    }

    @Test
    public void testSetAndGetDescription() {
        String testDescription = "aDescription";
        assertThat(product.getDescription(), is(nullValue()));
        product.setDescription(testDescription);
        assertThat(product.getDescription(), is(testDescription));
    }

    public void testSetAndGetPrice() {
        double testPrice = 100.00;
        assertThat(product.getPrice(), is(0.0));
        product.setPrice(testPrice);
        assertThat(product.getPrice(), is(testPrice));
    }
}