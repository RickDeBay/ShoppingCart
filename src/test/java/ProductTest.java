import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    private static Stream<Arguments> testScaledCostFixture() {
        return Stream.of(
                Arguments.of("good", 1234, "12.34", true),
                Arguments.of("bad", 65478, "65.478", false),
                Arguments.of("ugly", 123, "123.00", false));
    }

    @ParameterizedTest
    @MethodSource("testScaledCostFixture")
    public void testScaledCost(String productName, int productCost, String scaledCost, boolean success)
    {
        Product testProduct = new Product(productName);
        testProduct.setCost(productCost);
        BigDecimal actual = testProduct.getScaledCost();
        BigDecimal scaledCostExpected = new BigDecimal(scaledCost);
        if (success)
        {
            assertEquals(scaledCostExpected, actual);
        }
        else
        {
            assertNotEquals(scaledCostExpected, actual);
        }
    }

    private static Stream<Arguments> testEqualsFixture() {
        return Stream.of(
                Arguments.of(new Product("Dove Soap"), new Product("Axe Deo"), false),
                Arguments.of(new Product("Dove Soap"), new Product("Dove Soap"), true),
                Arguments.of(new Product("Axe Deo"), new Object(), false),
                Arguments.of(new Product("blargh"), new Product(""), false),
                Arguments.of(new Product(""), new Product("blargh"), false));
    }

    @ParameterizedTest
    @MethodSource("testEqualsFixture")
    public void testEquals(Object product1, Object product2, boolean success)
    {
        if (success)
        {
            assertEquals(product1, product2);
        }
        else
        {
            assertNotEquals(product1, product2);
        }
    }
}