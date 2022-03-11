import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class LineItemTest {
    private static Stream<Arguments> testEqualsFixture() {
        return Stream.of(
                Arguments.of(new Product("Dove Soap"), (short)3, new Product("Axe Deo"), (short)3, false),
                Arguments.of(new Product("Dove Soap"), (short)5, new Product("Dove Soap"), (short)99, true),
                Arguments.of(new Product("Axe Deo"), (short)77, new Product("Dove Soap"), (short)77, false),
                Arguments.of(new Product("bar"), (short)5, new Product("baz"), (short)4, false));
    }

    @ParameterizedTest
    @MethodSource("testEqualsFixture")
    public void testEquals(Product product1, short qtyLine1, Product product2, short qtyLine2, boolean success)
    {
        LineItem line1 = new LineItem(product1);
        line1.setQuantity(qtyLine1);
        LineItem line2 = new LineItem(product2);
        line1.setQuantity(qtyLine2);
        if (success)
        {
            assertEquals(line1, line2);
        }
        else
        {
            assertNotEquals(line1, line2);
        }
    }
}