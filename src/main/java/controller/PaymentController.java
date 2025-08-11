package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.log4j.Log4j2;
import model.entity.Customer;
import model.entity.Order;
import model.entity.Payment;
import model.entity.User;
import model.entity.enums.PaymentType;
import model.entity.enums.TransactionType;
import model.service.CustomerService;
import model.service.OrderService;
import model.service.PaymentService;
import model.service.UserService;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Log4j2
public class PaymentController implements Initializable {
    @FXML
    private TextField idTxt, amountTxt;
    @FXML
    private ComboBox<Customer> customerCmb;
    @FXML
    private ComboBox<Order> orderCmb;
    @FXML
    private ComboBox<TransactionType> transactionTypeCmb;
    @FXML
    private ComboBox<PaymentType> paymentTypeCmb;
    @FXML
    private ComboBox<User> userCmb;
    @FXML
    private TableView<Payment> paymentTab;
    @FXML
    private TableColumn<Payment, String> idCol, transactiontypeCol, paymenttypeCol;
    @FXML
    private Button saveBtn, deleteBtn, editBtn;
    private List<Payment> paymentList = new ArrayList<>();


    private static PaymentController instance;

    public PaymentController() {
        instance = this;
    }

    public static PaymentController getInstance() {
        return instance;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (TransactionType transactionType : TransactionType.values()) {
            transactionTypeCmb.getItems().add(transactionType);
        }
        for (PaymentType paymentType : PaymentType.values()) {
            paymentTypeCmb.getItems().add(paymentType);
        }
        refreshPaymentComboBox();

        saveBtn.setOnAction(event -> {
            Payment payment = buildPayment();
            try {
                PaymentService.getService().save(payment);
                paymentList = PaymentService.getService().findAll();
                showPayment(paymentList);
                new Alert(Alert.AlertType.INFORMATION, "Payment saved successfully").show();
                clearForm();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Failed to save: " + e.getMessage()).show();
            }
        });

        deleteBtn.setOnAction(event -> {
            try {
                int id = Integer.parseInt(idTxt.getText());
                PaymentService.getService().delete(id);
                paymentList = PaymentService.getService().findAll();
                showPayment(paymentList);
                clearForm();
                new Alert(Alert.AlertType.INFORMATION, "Payment deleted successfully").show();
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Payment could not be deleted").show();
            }
        });

        editBtn.setOnAction(event -> {
            Payment payment = buildPayment();
            try {
                PaymentService.getService().edit(payment);
                paymentList = PaymentService.getService().findAll();
                showPayment(paymentList);
                new Alert(Alert.AlertType.INFORMATION, "Payment edited successfully").show();
                clearForm();
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Payment could not be edited").show();
            }

        });
        paymentTab.setOnMouseReleased(event -> loadSelectedPayment());
        paymentTab.setOnKeyPressed(event -> loadSelectedPayment());
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        transactiontypeCol.setCellValueFactory(new PropertyValueFactory<>("transactionType"));
        paymenttypeCol.setCellValueFactory(new PropertyValueFactory<>("paymentType"));
        try {
            paymentList = PaymentService.getService().findAll();
        } catch (Exception e) {

            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
        }
    }

    private void loadSelectedPayment() {
        Payment selected = paymentTab.getSelectionModel().getSelectedItem();
        if (selected != null) {
            idTxt.setText(String.valueOf(selected.getId()));
            transactionTypeCmb.getSelectionModel().select(selected.getTransactionType());
            paymentTypeCmb.getSelectionModel().select(selected.getPaymentType());
        }
    }

    private Payment buildPayment() {
        return Payment.builder()
                .id(Integer.parseInt(idTxt.getText()))
                .transactionType(transactionTypeCmb.getValue())
                .paymentType(paymentTypeCmb.getValue())
                .order(orderCmb.getValue())
                .customer(customerCmb.getValue())
                .user(userCmb.getValue())
                .paymentDateTime(LocalDateTime.now())
                .build();
    }

    private void clearForm() {
        idTxt.clear();
        amountTxt.clear();
        transactionTypeCmb.setValue(null);
        paymentTypeCmb.setValue(null);
        orderCmb.setValue(null);
        customerCmb.setValue(null);
        userCmb.setValue(null);
        paymentTab.getSelectionModel().clearSelection();
    }

    public void showPayment(List<Payment> paymentList) {
        ObservableList<Payment> observableList = FXCollections.observableList(paymentList);
        paymentTab.setItems(observableList);
    }

    public void refreshPaymentComboBox() {
        try {
            List<Order> orders = OrderService.getService().findAll();
            orderCmb.setItems(FXCollections.observableList(orders));
            List<User> users = UserService.getService().findAll();
            userCmb.setItems(FXCollections.observableList(users));
            List<Customer> customers = CustomerService.getService().findAll();
            customerCmb.setItems(FXCollections.observableList(customers));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
