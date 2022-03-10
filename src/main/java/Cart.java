import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.HashMap;

public class Cart
{
    public static int TOTAL_SCALE = 2;
    public static int SALES_TAX_SCALE = 3;
    public static int SALES_TAX_100PCT = (int)Math.pow(10, SALES_TAX_SCALE);
    private HashMap<LineItem,LineItem> lineItems;
    private int salesTax;

    public Cart()
    {
        this.salesTax = 0;
        lineItems = new HashMap<LineItem,LineItem>();
    }

    public int getSalesTax() {
        return salesTax;
    }

    public void setSalesTax(int salesTax)
    {
        this.salesTax = salesTax;
    }

    public Collection<LineItem> getLineItems()
    {
        return lineItems.values();
    }

    public void addItem(Product product, short quantity)
    {
        LineItem newLineItem = new LineItem(product);
        LineItem oldLineItem = lineItems.get(newLineItem);
        if (oldLineItem == null)
        {
            newLineItem.setQuantity(quantity);
            lineItems.put(newLineItem,newLineItem);
        }
        else
        {
            oldLineItem.incrQuantity(quantity);
        }
    }

    public BigDecimal getTotalSalesTax()
    {
        int totalCost = 0;
        for ( LineItem lineItem: lineItems.keySet() )
        {
            totalCost += lineItem.getQuantity() * lineItem.getProduct().getCost();
        }

        // apply sales tax: 1 + tax percentage
        return new BigDecimal(totalCost * salesTax)
                // shift to apply scaling for products and sales tax
                .movePointLeft(Product.COST_SCALE + SALES_TAX_SCALE)
                // round and scale for cart output
                .setScale(TOTAL_SCALE, RoundingMode.HALF_UP);
    }

    // TODO use getTotalSalesTax; that will error with products selling in
    //  fractions of a cent like gasoline, but that wasn't a requirement
    //  Should be easy, only diff is SALES_TAX_100PCT so just combine them
    public BigDecimal getTotalCost()
    {
        int totalCost = 0;
        for ( LineItem lineItem: lineItems.keySet() )
        {
            totalCost += lineItem.getQuantity() * lineItem.getProduct().getCost();
        }

        // apply sales tax: 1 + tax percentage
        return new BigDecimal(totalCost * (SALES_TAX_100PCT + salesTax))
                // shift to apply scaling for products and sales tax
                .movePointLeft(Product.COST_SCALE + SALES_TAX_SCALE)
                // round and scale for cart output
                .setScale(TOTAL_SCALE, RoundingMode.HALF_UP);
    }
}
