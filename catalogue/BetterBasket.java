package catalogue;

import java.io.Serializable;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Write a description of class BetterBasket here.
 *
 * @author  Your Name
 * @version 1.0
 */
public class BetterBasket extends Basket
{
  private static final long serialVersionUID = 1L;
  private int    theOrderNum = 0;          // Order number

  public BetterBasket()
  {
    theOrderNum = 0;
  }

  public String getDetails() {
    Locale uk = Locale.UK;
    StringBuilder sBuilder = new StringBuilder(256);
    String csign = (Currency.getInstance(uk)).getSymbol();
    double total = 0.00;
    if (theOrderNum != 0)
      sBuilder.append("Order number: ").append(theOrderNum).append("\n");

    if (this.size() > 0) {
      Map<String, List<Product>> groupedProducts = this.stream()
              .collect(Collectors.groupingBy(Product::getProductNum));

      for (Map.Entry<String, List<Product>> entry : groupedProducts.entrySet()) {
        int number = entry.getValue().stream().mapToInt(Product::getQuantity).sum();
        Product pr = entry.getValue().get(0);
        sBuilder.append(pr.getProductNum()).append(" ");
        sBuilder.append(pr.getDescription()).append(" ");
        sBuilder.append("(").append(number).append(") ");
        sBuilder.append(csign).append(pr.getPrice() * number).append("\n");
        total += pr.getPrice() * number;
      }
      sBuilder.append("----------------------------\n");
      sBuilder.append("Total ").append(csign).append(total).append("\n");
    }
    return sBuilder.toString();
  }
}