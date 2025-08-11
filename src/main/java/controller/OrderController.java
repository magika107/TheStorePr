package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import model.entity.Customer;
import model.entity.Order;
import model.entity.Product;
import model.entity.User;
import model.entity.enums.OrderType;
import model.service.CustomerService;
import model.service.OrderService;
import model.service.ProductService;
import model.service.UserService;

import java.net.URL;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Log4j2
public class OrderController implements Initializable {

    @FXML
    private TableView<Order> orderTab;
    @FXML
    private TextField idTxt, orderSerialTxt, discountTxt, pureAmountTxt;
    @FXML
    private ComboBox<User> userCmb;
    @FXML
    private ComboBox<Customer> customerCmb;
    @FXML
    private ComboBox<OrderType> orderTypeCmb;
    @FXML
    private DatePicker orderDat;
    @FXML
    private TableColumn<Order, String> idCol, orderSerialCol, customerCol, userCol;
    @FXML
    private Button saveBtn, editBtn, deleteBtn;
    private List<Order> orderList = new ArrayList<>();

    private static OrderController instance;

    public OrderController() {
        instance = this;
    }

    public static OrderController getInstance() {
        return instance;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        reloadUserCombo();
        reloadCustomerCombo();

        for (OrderType orderType : OrderType.values()) {
            orderTypeCmb.getItems().add(orderType);
        }

        saveBtn.setOnAction(event -> {
            Order order = buildOrder();
            orderList.add(order);
            try {
                OrderService.getService().save(order);
                orderList = OrderService.getService().findAll();

                OrderItemController.getInstance().refreshOrderComboBox();
                PaymentController.getInstance().refreshPaymentComboBox();

                showOrderOnTable(orderList);
                new Alert(Alert.AlertType.INFORMATION, "Order Saved", ButtonType.OK).show();
                resetForm();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Failed to save order: " + e.getMessage()).show();
            }
        });
        deleteBtn.setOnAction(event -> {
            try {
                int id = Integer.parseInt(idTxt.getText());
                OrderService.getService().delete(id);
                orderList = OrderService.getService().findAll();
                showOrderOnTable(orderList);
                resetForm();
                new Alert(Alert.AlertType.CONFIRMATION, "delete order successfully", ButtonType.OK).show();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Failed to delete order: " + e.getMessage()).show();
            }
        });


        editBtn.setOnAction(event -> {
            Order order = buildOrder();
            try {
                OrderService.getService().edit(order);
                orderList = OrderService.getService().findAll();
                showOrderOnTable(orderList);
                new Alert(Alert.AlertType.CONFIRMATION, "edit order successfully", ButtonType.OK).show();
                resetForm();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Failed to edit order: " + e.getMessage()).show();
            }
        });


        EventHandler<Event> tableChangeEvent = event -> {
            Order selectedOrder = orderTab.getSelectionModel().getSelectedItem();
            if (selectedOrder != null) {
                idTxt.setText(String.valueOf(selectedOrder.getId()));
                orderSerialTxt.setText(String.valueOf(selectedOrder.getOrderSerial()));
                customerCmb.getSelectionModel().select(selectedOrder.getCustomer());
                userCmb.getSelectionModel().select(selectedOrder.getUser());
                orderTypeCmb.getSelectionModel().select(selectedOrder.getOrderType());
                discountTxt.setText(String.valueOf(selectedOrder.getDiscount()));
                pureAmountTxt.setText(String.valueOf(selectedOrder.getPureAmount()));
                orderDat.setValue(selectedOrder.getOrderTime().toLocalDate());
            }
        };
        orderTab.setOnMouseReleased(tableChangeEvent);
        orderTab.setOnKeyPressed(tableChangeEvent);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        orderSerialCol.setCellValueFactory(new PropertyValueFactory<>("orderSerial"));
        customerCol.setCellValueFactory(new PropertyValueFactory<>("customer"));
        userCol.setCellValueFactory(new PropertyValueFactory<>("user"));
        try {
            orderList = OrderService.getService().findAll();
            showOrderOnTable(orderList);
        } catch (Exception e) {
            log.error("Error finding orders: " + e.getMessage());
        }
    }

    private Order buildOrder() {
        int id = 0;
        if (idTxt.getText() != null && !idTxt.getText().trim().isEmpty()) {
            try {
                id = Integer.parseInt(idTxt.getText().trim());
            } catch (NumberFormatException e) {
                id = 0;
            }
        }

        int discount = 0;
        if (discountTxt.getText() != null && !discountTxt.getText().trim().isEmpty()) {
            try {
                discount = Integer.parseInt(discountTxt.getText().trim());
            } catch (NumberFormatException e) {
                discount = 0;
            }
        }

        int pureAmount = 0;
        if (pureAmountTxt.getText() != null && !pureAmountTxt.getText().trim().isEmpty()) {
            try {
                pureAmount = Integer.parseInt(pureAmountTxt.getText().trim());
            } catch (NumberFormatException e) {
                pureAmount = 0;
            }
        }

        return Order.builder()
                .id(id)
                .orderSerial(orderSerialTxt.getText())
                .customer(customerCmb.getValue())
                .user(userCmb.getValue())
                .orderType(orderTypeCmb.getValue())
                .discount(discount)
                .pureAmount(pureAmount)
                .orderTime(orderDat.getValue() != null ? orderDat.getValue().atStartOfDay() : LocalDateTime.now())
                .build();
    }

    public void showOrderOnTable(List<Order> orderList) {
        ObservableList<Order> orderObservableList = FXCollections.observableList(orderList);
        orderTab.setItems(orderObservableList);
    }

    public void resetForm() {
        idTxt.clear();
        orderSerialTxt.clear();
        discountTxt.clear();
        pureAmountTxt.clear();

        orderTypeCmb.getSelectionModel().clearSelection();
        customerCmb.getSelectionModel().clearSelection();
        userCmb.getSelectionModel().clearSelection();

        orderDat.setValue(null);
    }

    public void reloadUserCombo() {
        try {
            List<User> users = UserService.getService().findAll();
            userCmb.setItems(FXCollections.observableArrayList(users));
        } catch (Exception e) {
            log.error("Error refreshing product combo box: " + e.getMessage());
        }
    }

    public void reloadCustomerCombo() {
        try {
            List<Customer> customers = CustomerService.getService().findAll();
            customerCmb.setItems(FXCollections.observableArrayList(customers));
        } catch (Exception e) {
            log.error("Error refreshing product combo box: " + e.getMessage());
        }
    }
}
