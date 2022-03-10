import java.util.Objects;

public class LineItem
{
    private Product product;
    private short quantity;

    public LineItem(Product product)
    {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(short q)
    {
        this.quantity = q;
    }

    public void incrQuantity(short i)
    {
        this.quantity += i;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineItem lineItem = (LineItem) o;
        return product.equals(lineItem.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product);
    }
}
