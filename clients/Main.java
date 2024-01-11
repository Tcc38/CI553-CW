package clients;



/**
 * Starts all the clients  as a single application.
 * Good for testing the system using a single application but no use of RMI.
 * @author  Mike Smith University of Brighton
 * @version 2.0
 */
class Main {

  public static void main(String args[]) {
    new Main().begin();
  }

  /**
   * Starts test system (Non distributed)
   */
  public void begin() {
    //DEBUG.set(true); /* Lots of debug info */
    new CompositeClient();
    new CompositeClientCustomer();
    //I overly-simplified the main class, removing a lot of "legacy" content


  }
}