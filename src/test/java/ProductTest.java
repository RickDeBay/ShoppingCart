import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    Product productDove, productAxe, productRoundUp, productRoundDown;
    Object notaProduct;

    @BeforeEach
    void setUp() {
        productDove = new Product("Dove Soap");
        productDove.setCost(3999);
        productAxe = new Product("Axe Deo");
        productRoundUp = new Product("Round Up");
        productRoundDown = new Product("Round Down");
    }

    @Test
    void testScaledCost()
    {
        BigDecimal doveCostAnswer = new BigDecimal("39.99");
        assertEquals(doveCostAnswer, productDove.getScaledCost());
    }

    @Test
    void testEquals() {
        assertEquals(productDove, productDove);
        assertNotEquals(productAxe, productDove);
        assertNotEquals(productDove, notaProduct);
    }
}