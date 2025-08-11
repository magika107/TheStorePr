package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.log4j.Log4j2;
import model.entity.Customer;
import model.entity.Order;
import model.entity.enums.Gender;
import model.service.CustomerService;
import model.service.OrderService;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Log4j2
public class CustomerController implements Initializable {
    @FXML
    private TextField idTxt, nameTxt, familyTxt, phoneTxt;
    @FXML
    private DatePicker datePick;
    @FXML
    private ComboBox<Gender> genderCombo;
    @FXML
    private Button saveBtn, deleteBtn, editBtn;
    @FXML
    private TableView<Customer> customerTab;
    @FXML
    private TableColumn<Customer, String> idCol, nameCol, familyCol;

    private List<Customer> customerList = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        for (Gender gender : Gender.values()) {
            genderCombo.getItems().add(gender);
        }

        saveBtn.setOnAction(e -> {
            Customer customer = buildCustomerForm();
            customerList.add(customer);
            try {
                CustomerService.getService().save(customer);
                customerList = CustomerService.getService().findAll();
                OrderController.getInstance().reloadCustomerCombo();
                PaymentController.getInstance().refreshPaymentComboBox();
                showCustomerOnTable(customerList);
                new Alert(Alert.AlertType.INFORMATION, "User created", ButtonType.OK).show();
                resetForm();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Failed to save user: " + ex.getMessage()).show();
            }
        });

        editBtn.setOnAction(e -> {
            Customer customer = buildCustomerForm();
            try {
                CustomerService.getService().edit(customer);
                customerList = CustomerService.getService().findAll();
                showCustomerOnTable(customerList);
                new Alert(Alert.AlertType.INFORMATION, "User edited", ButtonType.OK).show();
                resetForm();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Failed to edit user: " + ex.getMessage()).show();
            }
        });

        deleteBtn.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idTxt.getText());
                CustomerService.getService().delete(id);
                customerList = CustomerService.getService().findAll();
                showCustomerOnTable(customerList);
                resetForm();
                new Alert(Alert.AlertType.INFORMATION, "User deleted", ButtonType.OK).show();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Failed to delete user: " + ex.getMessage()).show();
            }
        });
        EventHandler<Event> tableChangeEvent = event -> {
            Customer selectedPerson = customerTab.getSelectionModel().getSelectedItem();
            if (selectedPerson != null) {
                idTxt.setText(String.valueOf(selectedPerson.getId()));
                nameTxt.setText(selectedPerson.getName());
                familyTxt.setText(selectedPerson.getFamily());
                genderCombo.getSelectionModel().select(selectedPerson.getGender());
                datePick.setValue(selectedPerson.getBirthDate());
                phoneTxt.setText(selectedPerson.getPhoneNumber());
            }
        };

        customerTab.setOnMouseReleased(tableChangeEvent);
        customerTab.setOnKeyReleased(tableChangeEvent);

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        familyCol.setCellValueFactory(new PropertyValueFactory<>("family"));

        try {
            customerList = CustomerService.getService().findAll();
            showCustomerOnTable(customerList);
        } catch (Exception e) {
            log.error("Error loading person list: " + e.getMessage());
        }
    }


    private Customer buildCustomerForm() {
        return Customer.builder()
                .id(Integer.parseInt(idTxt.getText()))
                .name(nameTxt.getText())
                .family(familyTxt.getText())
                .gender(genderCombo.getValue())
                .birthDate(datePick.getValue())
                .phoneNumber(phoneTxt.getText())
                .build();
    }

    public void resetForm() {
        idTxt.clear();
        nameTxt.clear();
        familyTxt.clear();
        phoneTxt.clear();
        datePick.setValue(LocalDate.now());
        try {
            customerList = CustomerService.getService().findAll();
            showCustomerOnTable(customerList);
        } catch (Exception e) {
            log.error("Form Reset Error: " + e.getMessage());
        }
    }

    public void showCustomerOnTable(List<Customer> customerList) {
        ObservableList<Customer> observableList = FXCollections.observableArrayList(customerList);
        customerTab.setItems(observableList);
    }


//    private void searchUser() {
//        try {
//            List<Customer> results = CustomerService.getService().findByNameAndFamily(name, family);
//            showCustomerOnTable(results);
//        } catch (Exception e) {
//            log.error("Search error: " + e.getMessage());
//        }
//    }
}
