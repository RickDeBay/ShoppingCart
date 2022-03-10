import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {
    Cart cart;
    LineItem lineDove, lineAxe, lineFoo;
    Product productDove, productAxe, productFoo;

    @BeforeEach
    void setUp() {
        productDove = new Product("Dove Soap");
        productDove.setCost(3999);
        lineDove = new LineItem(productDove);

        productAxe = new Product("Axe Deo");
        productAxe.setCost(9999);
        lineAxe = new LineItem(productAxe);

        productFoo = new Product("Foo Fighter");
        productFoo.setCost(3141);
        lineFoo = new LineItem(productFoo);

        cart = new Cart();
        cart.setSalesTax(125);
    }

    @Test
    void getTotalCost() {
        cart.addItem(productDove, (short)22);
        cart.addItem(productAxe, (short)11);

        // 39.99 * 22 + 99.99 * 11 = 1979.67
        // 1979.67 * 1.125 = 2227.12875
        BigDecimal answer = new BigDecimal("2227.13");
        BigDecimal total = cart.getTotalCost();
        assertEquals(total, answer);
    }

    @Test
    void addLineItem() {
        cart.addItem(productDove, (short)22);
        cart.addItem(productAxe, (short)11);

        Collection<LineItem> lines = cart.getLineItems();
        assertEquals(lines.size(), 2);

        for (LineItem line : lines)
        {
            Product product = line.getProduct();
            if (product.equals(productDove))
            {
                assertEquals(line.getQuantity(), 22);
            }
            else if (product.equals(productAxe))
            {
                assertEquals(line.getQuantity(), 11);
            }
            else
            {
                fail(product.getName());
            }
        }
    }

    @Test
    void getTotalSalesTax()
    {
        cart.addItem(productDove, (short)22);
        cart.addItem(productAxe, (short)11);

        // 39.99 * 22 + 99.99 * 11 = 1979.67
        // 1979.67 * 0.125 = 247.45875
        BigDecimal answer = new BigDecimal("247.46");
        BigDecimal total = cart.getTotalSalesTax();
        assertEquals(total, answer);
    }
}