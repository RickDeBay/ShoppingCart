import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class AcceptanceTest
{
    Cart cart;
    Product productDove, productAxe;
    Product productDove3for2;

    @BeforeEach
    void setUp() {
        // Dove Soap with a unit price of 39.99
        productDove = new Product("Dove Soap");
        productDove.setCost(3999);

        // Dove Soap with a unit price of 39.99
        // and buy 3 one is free discount
        productDove3for2 = new Product("Dove Soap");
        productDove3for2.setCost(3999);
        // and buy 3 one is free discount
        //productDove.???

        // Axe Deo with a unit price of 99.99
        productAxe = new Product("Axe Deo");
        productAxe.setCost(9999);

        // TODO refactor to accept sales tax
        cart = new Cart();
    }

    @Test
    public void ac0_AddSingleProduct()
    {
        cart.setSalesTax(0);

        // The user adds a single Dove Soap to the shopping cart
        cart.addItem(productDove, (short)1);

        // shopping cart should a single line item
        Collection<LineItem> lines = cart.getLineItems();
        assertEquals(1, lines.size());

        // a single line item
        LineItem line = lines.iterator().next();
        assertEquals(1, line.getQuantity());

        // unit price of 39.99
        Product product = line.getProduct();
        // 1 Dove Soap
        assertEquals(product, productDove);
        assertEquals("Dove Soap", product.getName() );

        // a unit price of 39.99
        BigDecimal expected = new BigDecimal("39.99");
        assertEquals(expected, product.getScaledCost());

        // shopping cart's total price should equal 39.99
        BigDecimal actual = cart.getTotalCost();
        assertEquals(expected, actual);
    }

    @Test
    public void ac1_AddManyProducts()
    {
        cart.setSalesTax(0);
        // adds 5 Dove Soaps
        cart.addItem(productDove, (short)5);
        // adds another 3 Dove Soaps
        cart.addItem(productDove, (short)3);

        // shopping cart should contain a single line item
        Collection<LineItem> lines = cart.getLineItems();
        assertEquals(1, lines.size());

        // shopping cart should contain 8 Dove Soaps
        LineItem line = lines.iterator().next();
        assertEquals(8, line.getQuantity());

        Product product = line.getProduct();
        // Dove Soap
        assertEquals(product, productDove);
        assertEquals("Dove Soap", product.getName());

        // a unit price of 39.99
        BigDecimal expected = new BigDecimal("39.99");
        assertEquals(expected, product.getScaledCost());

        // shopping cart's total price should equal 319.92
        BigDecimal actual = cart.getTotalCost();
        expected = new BigDecimal("319.92");
        assertEquals(expected, actual);
    }

    @Test
    public void ac2_CalculateTaxRateWithManyProducts()
    {
        // sales tax of 12.5%
        cart.setSalesTax(125);
        // user adds 2 Dove Soaps
        cart.addItem(productDove, (short)2);
        // then adds 2 Axe Deos
        cart.addItem(productAxe, (short)2);

        Collection<LineItem> lines = cart.getLineItems();
        assertEquals(2, lines.size());

        BigDecimal doveCostExpected = new BigDecimal("39.99");
        BigDecimal axeCostExpected = new BigDecimal("99.99");
        for (LineItem line : lines)
        {
            Product product = line.getProduct();
            if (product.equals(productDove))
            {
                // contain a line item with 2 Dove Soaps
                assertEquals(2, line.getQuantity());
                assertEquals("Dove Soap", product.getName());
                // with a unit price of 39.99
                assertEquals(doveCostExpected, product.getScaledCost());
            }
            else if (product.equals(productAxe))
            {
                // contain a line item with 2 Axe Deos
                assertEquals(2, line.getQuantity());
                assertEquals("Axe Deo", product.getName());
                // unit price of 99.99
                assertEquals(axeCostExpected, product.getScaledCost());
            }
            else
            {
                fail(product.getName());
            }
        }

        // total sales tax amount for the shopping cart should equal 35.00
        BigDecimal taxExpected = new BigDecimal("35.00");
        BigDecimal taxActual = cart.getTotalSalesTax();
        assertEquals(taxExpected, taxActual);

        // shopping cart's total price should equal 314.96
        BigDecimal totalExpected = new BigDecimal("314.96");
        BigDecimal totalActual = cart.getTotalCost();
        assertEquals(totalExpected, totalActual);
    }

    @Test
    public void acExt2_RemoveSingleItem()
    {
        // sales tax of 12.5%
        cart.setSalesTax(125);
        cart.addItem(productDove, (short)4);
        cart.addItem(productAxe, (short)2);

        // remove 1 dove soap
        cart.addItem(productDove, (short)-1);

        // TODO implies Cart.getLineItem(Product p) might be helpful
        Collection<LineItem> lines = cart.getLineItems();
        for (LineItem line : lines) {
            Product product = line.getProduct();
            if (product.equals(productDove)) {
                assertEquals(3, line.getQuantity());
            }
        }

        // total sales tax amount for the shopping cart should equal 35.00
        BigDecimal taxExpected = new BigDecimal("39.99");
        BigDecimal taxActual = cart.getTotalSalesTax();
        assertEquals(taxExpected, taxActual);

        // shopping cart's total price should equal 359.94
        BigDecimal totalExpected = new BigDecimal("359.94");
        BigDecimal totalActual = cart.getTotalCost();
        assertEquals(totalExpected, totalActual);
    }

    @Test
    public void acExt3a_LineLevelDiscount() {
        // tax 12.5%
        cart.setSalesTax(125);
        // user adds 2 Dove Soaps
        cart.addItem(productDove3for2, (short) 2);

        LineItem line = cart.getLineItems().iterator().next();
        Product product = line.getProduct();

        // total discount amount for the shopping cart should equal 47.49
        BigDecimal discountExpected = new BigDecimal("0.00");
        BigDecimal discountActual = BigDecimal.ZERO;
        assertEquals(discountExpected, discountActual);

        // total sales tax amount for the shopping cart should equal 10.00
        BigDecimal taxExpected = new BigDecimal("10.00");
        BigDecimal taxActual = cart.getTotalSalesTax();
        assertEquals(taxExpected, taxActual);

        // shopping cart's total price should equal 89.98
        BigDecimal totalExpected = new BigDecimal("89.98");
        BigDecimal totalActual = cart.getTotalCost();
        assertEquals(totalExpected, totalActual);
    }

    @Test
    public void acExt3b_LineLevelDiscount() {
        cart.setSalesTax(125);
        // user adds 2 Dove Soaps
        cart.addItem(productDove3for2, (short) 3);
        // then adds 2 Axe Deos
        cart.addItem(productAxe, (short) 3);

        // total discount amount for the shopping cart should equal 47.49
        BigDecimal discountExpected = new BigDecimal("44.99");
        BigDecimal discountActual = BigDecimal.ZERO;
        assertEquals(discountExpected, discountActual);

        // total sales tax amount for the shopping cart should equal 47.49
        BigDecimal taxExpected = new BigDecimal("47.49");
        BigDecimal taxActual = cart.getTotalSalesTax();
        assertEquals(taxExpected, taxActual);

        // shopping cart's total price should equal 427.44
        BigDecimal totalExpected = new BigDecimal("427.44");
        BigDecimal totalActual = cart.getTotalCost();
        assertEquals(totalActual, totalActual);
    }

    @Test
    public void acExt3c_LineLevelDiscount()
    {
        cart.setSalesTax(125);
        // user adds 2 Dove Soaps
        cart.addItem(productDove3for2, (short)6);

        // total discount amount for the shopping cart should equal 47.49
        BigDecimal discountExpected = new BigDecimal("89.98");
        BigDecimal discountActual = BigDecimal.ZERO;
        assertEquals(discountExpected, discountActual);

        // total sales tax amount for the shopping cart should equal 47.49
        BigDecimal taxExpected = new BigDecimal("20.00");
        BigDecimal taxActual = cart.getTotalSalesTax();
        assertEquals(taxExpected, taxActual);

        // shopping cart's total price should equal 179.96
        BigDecimal totalExpected = new BigDecimal("179.96");
        BigDecimal totalActual = cart.getTotalCost();
        assertEquals(totalExpected, totalActual);
    }

    @Test
    public void acExt4a_CartLevelDiscount() {
        // assume there is sales tax
        cart.setSalesTax(125);
        cart.addItem(productAxe, (short) 9);
        // shopping cart's total price should equal 9 * 99.99 = 899.91 + 112.49 = 1012.40 - 101.24 = 911.16
        BigDecimal totalExpected = new BigDecimal("911.16");
        BigDecimal totalActual = cart.getTotalCost();
        assertEquals(totalExpected, totalActual);
    }

    @Test
    public void acExt4b_CartLevelDiscount() {
        cart.setSalesTax(125);
        cart.addItem(productAxe, (short)9);
        cart.addItem(productDove3for2, (short)3);

        // shopping cart's total price should equal
        // 9 * 99.99 + 2 * 39.99 = 979.89
        // 979.89 * 1.125 = 1,102.37625 = 1,102.38
        // 1,102.38 - 110.24 = 992.14
        BigDecimal totalExpected = new BigDecimal("992.14");
        BigDecimal totalActual = cart.getTotalCost();
        assertEquals(totalExpected, totalActual);
    }
}
