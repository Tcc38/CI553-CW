package clients;



import clients.collection.CollectController;
import clients.collection.CollectModel;
import clients.collection.CollectView;
import clients.customer.CustomerController;
import clients.customer.CustomerModel;
import clients.customer.CustomerView;
import clients.shopDisplay.DisplayController;
import clients.shopDisplay.DisplayModel;
import clients.shopDisplay.DisplayView;
import middle.LocalMiddleFactory;

import javax.swing.*;

public class CompositeViewCustomer extends JFrame {
    public CompositeViewCustomer() {
        initComponents();
    }

    LocalMiddleFactory cvmf = new LocalMiddleFactory();

    private void initComponents() {

        CollectView collectView = new CollectView(this, cvmf, 1, 1);
        CollectModel collectModel = new CollectModel(cvmf);
        CollectController collectController = new CollectController(collectModel, collectView);
        collectView.setController(collectController);
        collectModel.addObserver(collectView);

        CustomerView customerView = new CustomerView(this, cvmf, 1, 1);
        CustomerModel customerModel = new CustomerModel(cvmf);
        CustomerController customerController = new CustomerController(customerModel, customerView);
        customerView.setController(customerController);
        customerModel.addObserver(customerView);

        DisplayView displayView = new DisplayView(this, cvmf, 1, 1);
        DisplayModel displayModel = new DisplayModel(cvmf);
        DisplayController displayController = new DisplayController(displayModel, displayView);
        displayView.setController(displayController);
        displayModel.addObserver(displayView);

        setTitle("Composite View Customer");
        setSize(1200, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CompositeViewCustomer());
    }
}