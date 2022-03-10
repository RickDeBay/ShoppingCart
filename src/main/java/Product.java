import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Product
{
    public static int COST_SCALE = 2;
    private int cost;

    public int getCost() {
        return cost;
    }

    public BigDecimal getScaledCost()
    {
        return new BigDecimal(cost)
                .movePointLeft(COST_SCALE)
                // not needed by design, but let's stay safe
                .setScale(COST_SCALE, RoundingMode.HALF_UP);
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Product(String name)
    {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return name.equals(product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
