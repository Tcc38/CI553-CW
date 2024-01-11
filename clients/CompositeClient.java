package clients;
import clients.backDoor.BackDoorController;
import clients.backDoor.BackDoorModel;
import clients.backDoor.BackDoorView;
import clients.cashier.CashierController;
import clients.cashier.CashierModel;
import clients.cashier.CashierView;
import clients.warehousePick.PickController;
import clients.warehousePick.PickModel;
import clients.warehousePick.PickView;
import middle.LocalMiddleFactory;


import javax.swing.*;

public class CompositeClient extends JFrame  {
    public CompositeClient() {
        initComponents();
    }
LocalMiddleFactory cvmf = new LocalMiddleFactory();// I created a new instance of the LocalMiddleFactory, specifically for the new clients(not sure if it was needed yet it works)
     void initComponents() {


        CashierView cashierView = new CashierView(this, cvmf, 0, 0);
        CashierModel cashierModel = new CashierModel(cvmf);
        CashierController cashierController = new CashierController(cashierModel, cashierView);
        cashierView.setController(cashierController);
        cashierModel.addObserver(cashierView);

        BackDoorView backDoorView = new BackDoorView(this, cvmf, 0, 0);
        BackDoorModel backDoorModel = new BackDoorModel(cvmf);
        BackDoorController backDoorController = new BackDoorController(backDoorModel, backDoorView);
        backDoorView.setController(backDoorController);
        backDoorModel.addObserver(backDoorView);


        PickView pickView = new PickView(this, cvmf, 0, 0);
        PickModel pickModel = new PickModel(cvmf);
        PickController pickController = new PickController(pickModel, pickView);
        pickView.setController(pickController);
        pickModel.addObserver(pickView);
        //as seen above, I have made new constructors for a merged View of the 3 business clients


        setTitle("Composite View");
        setSize(1200, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CompositeClient());
    }
    //the next 3 lines are used for testing as explained in the  'CompositeClientTest' class
    public static String getCashierView() {
         return CashierView.getCashierView();
    }

    public static String getBackDoorView() {
         return BackDoorView.getBackDoorView();
    }

    public static String getPickView() {
        return PickView.getPickView();
    }
}
