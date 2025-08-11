package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lombok.extern.log4j.Log4j2;
import model.entity.Bank;
import model.entity.Customer;
import model.entity.Payment;
import model.service.BankService;
import model.service.CustomerService;
import model.service.PaymentService;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Log4j2
public class BankController implements Initializable {

    @FXML
    private TextField idTxt, amountTxt;

    @FXML
    private Button saveBtn, deleteBtn, editBtn;

    @FXML
    private ComboBox<Customer> customerCmb;

    @FXML
    private ComboBox<Payment> paymentCmb;

    @FXML
    private TableView<Bank> bankTab;

    @FXML
    private TableColumn<Bank, String> idCol;

    @FXML
    private TableColumn<Bank, String> customerCol;

    @FXML
    private TableColumn<Bank, String> paymentCol;


    private List<Bank> bankList = new ArrayList<>();

    private static BankController instance;

    public BankController() {
        instance = this;
    }

    public static BankController getInstance() {
        return instance;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reloadCustomer();
        reloadPayment();

        saveBtn.setOnAction(e -> {
            Bank bank = buildBank();
            try {
                BankService.getService().save(bank);
                bankList = BankService.getService().findAll();
                showBankTab(bankList);
                resetForm();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).show();
            }
        });

        try {
            bankList = BankService.getService().findAll();
            showBankTab(bankList);
        } catch (Exception e) {
            log.error(e);
        }
    }

    public void reloadCustomer() {
        try {
            List<Customer> customerList = CustomerService.getService().findAll();
            customerCmb.setItems(FXCollections.observableArrayList(customerList));
        } catch (Exception e) {
            log.error(e);
        }
    }

    public void reloadPayment() {
        try {
            List<Payment> paymentList = PaymentService.getService().findAll();
            paymentCmb.setItems(FXCollections.observableArrayList(paymentList));
        } catch (Exception e) {
            log.error(e);
        }
    }

    private Bank buildBank() {
        return Bank.builder()
                .id(Integer.parseInt(idTxt.getText()))
                .customer(customerCmb.getValue())
                .payment(paymentCmb.getValue())
                .amount(Integer.parseInt(amountTxt.getText()))
                .build();
    }

    public void resetForm() {
        idTxt.clear();
        amountTxt.clear();
        customerCmb.setValue(null);
        paymentCmb.setValue(null);
    }

    public void showBankTab(List<Bank> bankList) {
        ObservableList<Bank> bankObservableList = FXCollections.observableList(bankList);
        bankTab.setItems(bankObservableList);
    }

}
