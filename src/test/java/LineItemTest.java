import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LineItemTest {
    LineItem lineDove, lineAxe;
    Product productDove, productAxe;

    @BeforeEach
    void setUp() {
        productDove = new Product("Dove Soap");
        lineDove = new LineItem(productDove);
        lineDove.setQuantity((short)8);

        productAxe = new Product("Axe Deo");
        lineAxe = new LineItem(productAxe);
        lineAxe.setQuantity((short)8);
    }

    @Test
    void testEquals() {
        assertEquals(lineDove, lineDove);
        assertNotEquals(lineAxe, lineDove);
        assertNotEquals(lineDove, productDove);
    }
}