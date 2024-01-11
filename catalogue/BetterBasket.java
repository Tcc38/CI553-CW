package catalogue;

import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The new BetterBasket Class is essentially an extension of the Basket Class
 *  it provides a better output of the basket with multiples of products
 * @author  Calin Ciuperca
 * @version 1.0
 */
public class BetterBasket extends Basket
{
  public static int    theOrderNum;          // initialise the order number

  public String getDetails() {
    Locale uk = Locale.UK;
    StringBuilder sBuilder = new StringBuilder(256);//string builder to format the output of the basket
    String gbp = (Currency.getInstance(uk)).getSymbol();//set the local currency as GBP
    double total = 0.00;

    if (this.size() > 0) {
      Map<String, List<Product>> groupedProducts = this.stream()
              .collect(Collectors.groupingBy(Product::getProductNum));
      sBuilder.append("Order number: ").append(theOrderNum).append("\n");
      for (Map.Entry<String, List<Product>> entry : groupedProducts.entrySet()) {
        int number = entry.getValue().stream().mapToInt(Product::getQuantity).sum();
        Product pr = entry.getValue().get(0);
        sBuilder.append(pr.getProductNum()).append("  ");
        sBuilder.append(pr.getDescription()).append("  ");
        sBuilder.append("(").append(number).append(") ");
        sBuilder.append(gbp).append(pr.getPrice() * number).append("\n");
        total += pr.getPrice() * number;
      }
      sBuilder.append("----------------------------\n");
      sBuilder.append("Total ").append(gbp).append(total).append("\n");
    }
    return sBuilder.toString();
  }
}
