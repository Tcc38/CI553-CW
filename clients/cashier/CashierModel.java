package clients.cashier;

import catalogue.Basket;
import catalogue.BetterBasket;
import catalogue.Product;
import debug.DEBUG;
import middle.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;

import static javax.swing.UIManager.getInt;

/**
 * Implements the Model of the cashier client
 * @author  Mike Smith University of Brighton
 * @version 1.0
 */
public class CashierModel extends Observable
{
  private enum State { process, checked }

  private State       theState   = State.process;   // Current state
  private Product     theProduct = null;            // Current product
  private BetterBasket      theBasket  = null;            // Changed it to better basket

  private String      pn = "";                      // Product being processed

  private StockReadWriter theStock     = null;
  private OrderProcessing theOrder     = null;

  /**
   * Construct the model of the Cashier
   * @param mf The factory to create the connection objects
   */

  public CashierModel(MiddleFactory mf)
  {
    try                                           // 
    {      
      theStock = mf.makeStockReadWriter();        // Database access
      theOrder = mf.makeOrderProcessing();        // Process order
    } catch ( Exception e )
    {
      DEBUG.error("CashierModel.constructor\n%s", e.getMessage() );
    }
    theState   = State.process;                  // Current state
  }
  
  /**
   * Get the Basket of products
   * @return basket
   */
  public Basket getBasket()
  {
    return theBasket;
  }

  /**
   * Check if the product is in Stock
   * @param productNum The product number
   */
  public void doCheck(String productNum )
  {
    String theAction = "";
    theState  = State.process;                  // State process
    pn  = productNum.trim();                    // Product no.
    int    amount  = 1;                         //  & quantity
    try
    {
      if ( theStock.exists( pn ) )              // Stock Exists?
      {                                         // T
        Product pr = theStock.getDetails(pn);   //  Get details
        if ( pr.getQuantity() >= amount )       //  In stock?
        {                                       //  T
          theAction =                           //   Display 
            String.format( "%s : %7.2f (%2d) ", //
              pr.getDescription(),              //    description
              pr.getPrice(),                    //    price
              pr.getQuantity() );               //    quantity     
          theProduct = pr;                      //   Remember prod.
          theProduct.setQuantity( amount );     //    & quantity
          theState = State.checked;             //   OK await BUY 
        } else {                                //  F
          theAction =                           //   Not in Stock
            pr.getDescription() +" not in stock";
        }
      } else {                                  // F Stock exists
        theAction =                             //  Unknown
          "Unknown product number " + pn;       //  product no.
      }
    } catch( StockException e )
    {
      DEBUG.error( "%s\n%s", 
            "CashierModel.doCheck", e.getMessage() );
      theAction = e.getMessage();
    }
    setChanged(); notifyObservers(theAction);
  }

  /**
   * Buy the product
   */
  public void doBuy()
  {
    makeBasket();
    String theAction = "";
    int    amount  = 1;                         //  & quantity
    try
    {
      if ( theState != State.checked )          // Not checked
      {                                         //  with customer
        theAction = "Check if OK with customer first";
      } else {
        boolean stockBought =                   // Buy
          theStock.buyStock(                    //  however
            theProduct.getProductNum(),         //  may fail              
            theProduct.getQuantity() );         //
        if ( stockBought )                      // Stock bought
        {                                       // T
          makeBasketIfReq();                    //  new Basket ?
          theBasket.add( theProduct );          //  Add to bought
          theAction = "Purchased " +            //    details
                  theProduct.getDescription();  //
        } else {                                // F
          theAction = "!!! Not in stock";       //  Now no stock
        }
      }
    } catch( StockException e )
    {
      DEBUG.error( "%s\n%s", 
            "CashierModel.doBuy", e.getMessage() );
      theAction = e.getMessage();
    }
    theState = State.process;                   // All Done
    setChanged(); notifyObservers(theAction);
  }
  public void setPrintReceipt(boolean printReceipt){
  }
  public void doReceipt() {
    Basket basket = getBasket();;
      if (basket != null) {
        getInt(BetterBasket.theOrderNum++);
        LocalDateTime timeStamp = LocalDateTime.now();
        DateTimeFormatter receiptFormat = DateTimeFormatter.ofPattern("dd_MM_yy--HH-mm-ss");// initially did not use the seconds in the format, but I now do since it will not work with multiple orders.
        String receiptTag = receiptFormat.format(timeStamp);
        try {
          File receiptDir = new File("savedReceipts");
          if (! receiptDir.exists()) {
            receiptDir.mkdir();
          }
          String receiptID = "savedReceipts/" + receiptTag + ".txt";
          FileWriter receiptPrinter = new FileWriter(receiptID);
          receiptPrinter.write(("Thank you for shopping with us! \n \n" + basket.getDetails() + "\nTimestamp for purchase: " + receiptTag));
          receiptPrinter.close();

        } catch (IOException e) {
          System.out.println("Printing error");
        }
    }
    theBasket = null ;
  }
  /**
   * Customer pays for the contents of the basket
   */
  public void doBought(boolean printReceipt) {


      String theAction = "";
      try {
        if (theBasket != null &&
                !theBasket.isEmpty())            // items > 1
        {                                       // T
          theOrder.newOrder(theBasket);       //  Process order

        }                                       //
        theAction = "Next customer";            // New Customer
        theState = State.process;               // All Done
        if (printReceipt && theBasket!=null) {
          doReceipt();
        }
      } catch (OrderException e) {
        DEBUG.error("%s\n%s",
                "CashierModel.doCancel", e.getMessage());
        theAction = e.getMessage();
      }
      theBasket = null;
      setChanged();
      notifyObservers(theAction); // Notify
    }

  /**
   * ask for update of view called at start of day
   * or after system reset
   */
  public void askForUpdate()
  {
    setChanged(); notifyObservers("Welcome");
  }
  
  /**
   * make a Basket when required
   */
  private void makeBasketIfReq()
  {
    if ( theBasket == null )
    {
      try
      {
        int uon   = theOrder.uniqueNumber();     // Unique order num.
        theBasket = makeBasket();                //  basket list
        theBasket.setOrderNum( uon );            // Add an order number
      } catch ( OrderException e )
      {
        DEBUG.error( "Comms failure\n" +
                     "CashierModel.makeBasket()\n%s", e.getMessage() );
      }
    }
  }

  /**
   * return an instance of a new Basket
   * @return an instance of a new Basket
   */
  protected BetterBasket makeBasket()
  {

    return new BetterBasket();

  }
}
  
