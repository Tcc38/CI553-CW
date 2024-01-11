package clients;

import clients.Main;
import clients.backDoor.*;
import clients.cashier.*;
import clients.warehousePick.*;

import java.util.Observable;

import static org.junit.jupiter.api.Assertions.*;

public class CompositeViewTest extends Observable {


    @org.junit.jupiter.api.BeforeEach
    void testInitialization() {
        CompositeView compositeView = new CompositeView();
        assertNotNull(compositeView);//this is done to check that the a new Composite View is instantiated and active
    }

    @org.junit.jupiter.api.Test
    void testCashierView() {
        String cashierView = CompositeView.getCashierView();
        assertEquals(cashierView, CashierView.getCashierView());
    }

    @org.junit.jupiter.api.Test
    void testBackDoorView() {
        assertEquals(CompositeView.getBackDoorView(), BackDoorView.getBackDoorView());
    }

    @org.junit.jupiter.api.Test
    void testPickView() {
        assertEquals(CompositeView.getPickView(), PickView.getPickView());
    }// each test I have done is to check that the Composite view is correct when it uses the respective legacy Client views and implements them in the same Master View.

}