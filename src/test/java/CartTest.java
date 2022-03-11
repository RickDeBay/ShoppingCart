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
    }

    @Test
    void testGetTotalSalesTax_TaxRoundedUp() {
        cart.setSalesTax(125);
        cart.addItem(productDove, (short)22);
        cart.addItem(productAxe, (short)11);

        // 39.99 * 22 + 99.99 * 11 = 1979.67
        // 1979.67 * 0.125 = 247.45875
        BigDecimal expected = new BigDecimal("247.46");
        BigDecimal actual = cart.getTotalSalesTax();
        assertEquals(expected, actual);
    }

    @Test
    void testGetTotalCost_WithTaxRoundedUp() {
        cart.setSalesTax(125);
        cart.addItem(productDove, (short)22);
        cart.addItem(productAxe, (short)11);

        // 39.99 * 22 + 99.99 * 11 = 1979.67
        // 1979.67 * 1.125 = 2227.12875
        BigDecimal expected = new BigDecimal("2227.13");
        BigDecimal actual = cart.getTotalCost();
        assertEquals(expected, actual);
    }

    @Test
    void testGetTotalSalesTax_TaxRoundedDown() {
        cart.setSalesTax(125);
        cart.addItem(productDove, (short)4);
        cart.addItem(productAxe, (short)1);

        // 39.99 * 4 + 99.99 * 1 = 259.95
        // 259.95 * 0.125 = 32.49375
        BigDecimal expected = new BigDecimal("32.49");
        BigDecimal actual = cart.getTotalSalesTax();
        assertEquals(expected, actual);
    }

    @Test
    void testGetTotalCost_WithTaxRoundedDown() {
        cart.setSalesTax(125);
        cart.addItem(productDove, (short)4);
        cart.addItem(productAxe, (short)1);

        // 39.99 * 4 + 99.99 * 1 = 259.95
        // 259.95 * 1.125 = 292.44375
        BigDecimal expected = new BigDecimal("292.44");
        BigDecimal actual = cart.getTotalCost();
        assertEquals(expected, actual);
    }

    @Test
    void testGetTotalSalesTax_ZeroTax() {
        cart.setSalesTax(0);
        cart.addItem(productDove, (short)4);
        cart.addItem(productAxe, (short)1);

        BigDecimal expected = new BigDecimal("0.00");
        BigDecimal actual = cart.getTotalSalesTax();
        assertEquals(expected, actual);
    }

    @Test
    void testGetTotalSalesTax_ProveScalingTest() {
        cart.setSalesTax(0);
        cart.addItem(productDove, (short)4);

        // sanity test scaling tests
        BigDecimal expected = new BigDecimal("0");
        BigDecimal actual = cart.getTotalSalesTax();
        assertNotEquals(expected, actual);
    }

    @Test
    void testGetTotalCost_WithoutTax() {
        cart.setSalesTax(0);
        cart.addItem(productDove, (short)7);
        cart.addItem(productAxe, (short)5);

        // 39.99 * 7 + 99.99 * 5 = 1979.67
        // 1979.67 * 1.000 = 2227.12875
        BigDecimal expected = new BigDecimal("779.88");
        BigDecimal actual = cart.getTotalCost();
        assertEquals(expected, actual);
    }

    @Test
    void testAddLineItem_AddTwoLinesTwoProducts_GetTwoLinesTwoProducts() {
        cart.addItem(productDove, (short)22);
        cart.addItem(productAxe, (short)11);

        Collection<LineItem> lines = cart.getLineItems();
        assertEquals(2, lines.size());

        for (LineItem line : lines)
        {
            Product product = line.getProduct();
            if (product.equals(productDove))
            {
                assertEquals(22, line.getQuantity());
            }
            else if (product.equals(productAxe))
            {
                assertEquals(11, line.getQuantity());
            }
            else
            {
                // unknown product in cart
                fail(product.getName());
            }
        }
    }

    @Test
    void testAddLineItem_AddProductTwice_GetOneLinesSummedProducts() {
        cart.addItem(productDove, (short)3);
        cart.addItem(productDove, (short)7);

        Collection<LineItem> lines = cart.getLineItems();
        assertEquals(1, lines.size());

        for (LineItem line : lines)
        {
            Product product = line.getProduct();
            if (product.equals(productDove))
            {
                assertEquals(10, line.getQuantity());
            }
            else
            {
                // unknown product in cart
                fail(product.getName());
            }
        }
    }
}