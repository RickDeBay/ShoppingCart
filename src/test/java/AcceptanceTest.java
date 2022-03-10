import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class AcceptanceTest
{
    Cart cart;
    LineItem lineDove, lineAxe, lineFoo;
    Product productDove, productAxe, productFoo;

    @BeforeEach
    void setUp() {
        // Dove Soap with a unit price of 39.99
        productDove = new Product("Dove Soap");
        productDove.setCost(3999);

        // Axe Deo with a unit price of 99.99
        productAxe = new Product("Axe Deo");
        productAxe.setCost(9999);

        cart = new Cart();
    }

    @Test
    public void addSingleProduct()
    {
        cart.setSalesTax(0);

        // The user adds a single Dove Soap to the shopping cart
        cart.addItem(productDove, (short)1);

        // hopping cart should a single line item
        Collection<LineItem> lines = cart.getLineItems();
        assertEquals(lines.size(), 1);

        // a single line item
        LineItem line = lines.iterator().next();
        assertEquals(line.getQuantity(), 1);

        // unit price of 39.99
        Product product = line.getProduct();
        // 1 Dove Soap
        assertEquals(product, productDove);
        assertEquals(product.getName(), "Dove Soap");

        // a unit price of 39.99
        BigDecimal answer = new BigDecimal("39.99");
        assertEquals(product.getScaledCost(), answer);

        // shopping cart's total price should equal 39.99
        BigDecimal total = cart.getTotalCost();
        assertEquals(total, answer);
    }

    @Test
    public void addManyProducts()
    {
        cart.setSalesTax(0);
        // adds 5 Dove Soaps
        cart.addItem(productDove, (short)5);
        // adds another 3 Dove Soaps
        cart.addItem(productDove, (short)3);

        // shopping cart should contain a single line item
        Collection<LineItem> lines = cart.getLineItems();
        assertEquals(lines.size(), 1);

        // shopping cart should contain 8 Dove Soaps
        LineItem line = lines.iterator().next();
        assertEquals(line.getQuantity(), 8);

        Product product = line.getProduct();
        // Dove Soap
        assertEquals(product, productDove);
        assertEquals(product.getName(), "Dove Soap");

        // a unit price of 39.99
        BigDecimal answer = new BigDecimal("39.99");
        assertEquals(product.getScaledCost(), answer);

        // shopping cart's total price should equal 319.92
        BigDecimal total = cart.getTotalCost();
        answer = new BigDecimal("319.92");
        assertEquals(total, answer);
    }

    @Test
    public void calculateTaxRateWithManyProducts()
    {
        cart.setSalesTax(125);
        // user adds 2 Dove Soaps
        cart.addItem(productDove, (short)2);
        // then adds 2 Axe Deos
        cart.addItem(productAxe, (short)2);

        Collection<LineItem> lines = cart.getLineItems();
        assertEquals(lines.size(), 2);

        BigDecimal doveCostAnswer = new BigDecimal("39.99");
        BigDecimal axeCostAnswer = new BigDecimal("99.99");
        for (LineItem line : lines)
        {
            Product product = line.getProduct();
            if (product.equals(productDove))
            {
                // contain a line item with 2 Dove Soaps
                assertEquals(line.getQuantity(), 2);
                assertEquals(product.getName(), "Dove Soap");
                // with a unit price of 39.99
                assertEquals(product.getScaledCost(), doveCostAnswer);
            }
            else if (product.equals(productAxe))
            {
                // contain a line item with 2 Axe Deos
                assertEquals(line.getQuantity(), 2);
                assertEquals(product.getName(), "Axe Deo");
                // unit price of 99.99
                assertEquals(product.getScaledCost(), axeCostAnswer);
            }
            else
            {
                fail(product.getName());
            }
        }

        // total sales tax amount for the shopping cart should equal 35.00
        BigDecimal taxAnswer = new BigDecimal("35.00");
        BigDecimal salesTax = cart.getTotalSalesTax();

        // shopping cart's total price should equal 314.96
        BigDecimal totalAnswer = new BigDecimal("314.96");
        BigDecimal total = cart.getTotalCost();
        assertEquals(total, totalAnswer);
    }
}
