package clients;

import clients.backDoor.*;
import clients.cashier.*;
import clients.warehousePick.*;

import java.util.Observable;

import static org.junit.jupiter.api.Assertions.*;

public class CompositeClientTest extends Observable {


    @org.junit.jupiter.api.BeforeEach
    void testInitialization() {
        CompositeClient compositeClient = new CompositeClient();
        assertNotNull(compositeClient);//this is done to check that the a new Composite View is instantiated and active
    }

    @org.junit.jupiter.api.Test
    void testCashierView() {
        assertEquals(CompositeClient.getCashierView(), CashierView.getCashierView());
    }

    @org.junit.jupiter.api.Test
    void testBackDoorView() {
        assertEquals(CompositeClient.getBackDoorView(), BackDoorView.getBackDoorView());
    }

    @org.junit.jupiter.api.Test
    void testPickView() {
        assertEquals(CompositeClient.getPickView(), PickView.getPickView());
    }// each test I have done is to check that the Composite view is correct when it uses the respective legacy Client views and implements them in the same Master Client.

}