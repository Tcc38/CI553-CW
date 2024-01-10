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

public class CompositeView extends JFrame  {
    public CompositeView() {
        initComponents();
    }
LocalMiddleFactory cvmf = new LocalMiddleFactory();// i created a new instance of the LocalMiddleFactory, specifically for the new clients(not sure if it was needed yet it works)
    private void initComponents() {


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
        setSize(1200, 300); // Adjust the size as needed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CompositeView());
    }
}
