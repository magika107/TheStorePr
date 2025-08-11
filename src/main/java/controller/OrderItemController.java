package controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.log4j.Log4j2;
import model.entity.Order;
import model.entity.OrderItem;
import model.entity.Product;
import model.service.OrderItemService;
import model.service.OrderService;
import model.service.ProductService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Log4j2
public class OrderItemController implements Initializable {

    @FXML
    private TextField idTxt, quantityTxt, priceTxt;
    @FXML
    private Button saveBtn, editBtn, deleteBtn;
    @FXML
    private ComboBox<Product> productCmb;
    @FXML
    private ComboBox<Order> orderCmb;
    @FXML
    private TableView<OrderItem> orderItemTab;
    @FXML
    private TableColumn<OrderItem, Integer> idCol, quantityCol, priceCol;
    @FXML
    private TableColumn<OrderItem, String> productCol;
    @FXML
    private TableColumn<OrderItem, Integer> orderCol;

    private static OrderItemController instance;

    public OrderItemController() {
        instance = this;
    }

    public static OrderItemController getInstance() {
        return instance;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshProductComboBox();
        refreshOrderComboBox();

        try {
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            productCol.setCellValueFactory(data ->
                    new SimpleStringProperty(data.getValue().getProduct().getTitle())
            );
            orderCol.setCellValueFactory(data ->
                    new SimpleIntegerProperty(data.getValue().getOrder().getId()).asObject()
            );

            showOrderItems();

        } catch (Exception e) {
            log.error("Init error: " + e.getMessage());
        }

        saveBtn.setOnAction(event -> {
            try {
                OrderItem orderItem = buildOrderItemForm();
                OrderItemService.getService().save(orderItem);
                showOrderItems();
                resetForm();
                new Alert(Alert.AlertType.INFORMATION, "Order item saved", ButtonType.OK).show();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Error saving: " + ex.getMessage()).show();
            }
        });

        editBtn.setOnAction(event -> {
            try {
                OrderItem orderItem = buildOrderItemForm();
                OrderItemService.getService().edit(orderItem);
                showOrderItems();
                resetForm();
                new Alert(Alert.AlertType.INFORMATION, "Order item edited", ButtonType.OK).show();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Error editing: " + ex.getMessage()).show();
            }
        });

        deleteBtn.setOnAction(event -> {
            try {
                int id = Integer.parseInt(idTxt.getText());
                OrderItemService.getService().delete(id);
                showOrderItems();
                resetForm();
                new Alert(Alert.AlertType.INFORMATION, "Order item deleted", ButtonType.OK).show();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Error deleting: " + ex.getMessage()).show();
            }
        });

        orderItemTab.setOnMouseClicked(event -> {
            OrderItem selected = orderItemTab.getSelectionModel().getSelectedItem();
            if (selected != null) {
                fillForm(selected);
            }
        });
    }

    private OrderItem buildOrderItemForm() {
        return OrderItem.builder()
                .id(Integer.parseInt(idTxt.getText()))
                .product(productCmb.getValue())
                .quantity(Integer.parseInt(quantityTxt.getText()))
                .price(Integer.parseInt(priceTxt.getText()))
                .order(orderCmb.getValue())
                .build();
    }

    private void showOrderItems() {
        try {
            List<OrderItem> items = OrderItemService.getService().findAll();
            ObservableList<OrderItem> observableList = FXCollections.observableArrayList(items);
            orderItemTab.setItems(observableList);
        } catch (Exception e) {
            log.error("Load error: " + e.getMessage());
        }
    }

    private void fillForm(OrderItem item) {
        idTxt.setText(String.valueOf(item.getId()));
        quantityTxt.setText(String.valueOf(item.getQuantity()));
        priceTxt.setText(String.valueOf(item.getPrice()));
        productCmb.setValue(item.getProduct());
        orderCmb.setValue(item.getOrder());
    }

    private void resetForm() {
        idTxt.clear();
        quantityTxt.clear();
        priceTxt.clear();
        productCmb.getSelectionModel().clearSelection();
        orderCmb.getSelectionModel().clearSelection();
    }

    public void refreshProductComboBox() {
        try {
            List<Product> products = ProductService.getService().findAll();
            productCmb.setItems(FXCollections.observableArrayList(products));
        } catch (Exception e) {
            log.error("Error refreshing product combo box: " + e.getMessage());
        }
    }

    public void refreshOrderComboBox() {
        try {
            List<Order> orders = OrderService.getService().findAll();
            orderCmb.setItems(FXCollections.observableArrayList(orders));
        } catch (Exception e) {
            log.error("Error refreshing product combo box: " + e.getMessage());
        }
    }

}
