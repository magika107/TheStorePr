package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.log4j.Log4j2;
import model.entity.Inventory;
import model.entity.Product;
import model.service.InventoryService;
import model.service.ProductService;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Log4j2
public class InventoryController implements Initializable {
    @FXML
    private TextField idTxt, quantityTxt;
    @FXML
    private ComboBox<Product> productCmb;
    @FXML
    private Button saveBtn, editBtn, deleteBtn;
    @FXML
    private TableView<Inventory> inventoryTab;
    @FXML
    private TableColumn<Inventory, Integer> idCol, quantityCol;
    @FXML
    private TableColumn<Inventory, Product> productCol;

    private List<Inventory> inventoryList = new ArrayList<>();

    private static InventoryController instance;

    public InventoryController() {
        instance = this;
    }

    public static InventoryController getInstance() {
        return instance;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshProductComboBox();


        saveBtn.setOnAction(event -> {
            Inventory inventory = buildInventoryForm();
            try {
                InventoryService.getService().save(inventory);
                inventoryList = InventoryService.getService().findAll();
                showInventoryOnTable(inventoryList);
                new Alert(Alert.AlertType.INFORMATION, "Inventory saved").show();
                resetForm();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Failed to save: " + e.getMessage()).show();
            }
        });

        editBtn.setOnAction(event -> {
            Inventory inventory = buildInventoryForm();
            try {
                InventoryService.getService().edit(inventory);
                inventoryList = InventoryService.getService().findAll();
                showInventoryOnTable(inventoryList);
                new Alert(Alert.AlertType.INFORMATION, "Inventory edited").show();
                resetForm();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Failed to edit: " + e.getMessage()).show();
            }
        });

        deleteBtn.setOnAction(event -> {
            try {
                int id = Integer.parseInt(idTxt.getText());
                InventoryService.getService().delete(id);
                inventoryList = InventoryService.getService().findAll();
                showInventoryOnTable(inventoryList);
                resetForm();
                new Alert(Alert.AlertType.INFORMATION, "Inventory deleted").show();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Failed to delete: " + e.getMessage()).show();
            }
        });

        inventoryTab.setOnMouseReleased(event -> loadSelectedInventory());
        inventoryTab.setOnKeyReleased(event -> loadSelectedInventory());

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        productCol.setCellValueFactory(new PropertyValueFactory<>("product"));

        try {
            inventoryList = InventoryService.getService().findAll();
            showInventoryOnTable(inventoryList);
        } catch (Exception e) {
            log.error("Error loading inventory list: " + e.getMessage());
        }
    }

    private Inventory buildInventoryForm() {
        return Inventory.builder()
                .id(Integer.parseInt(idTxt.getText()))
                .quantity(Integer.parseInt(quantityTxt.getText()))
                .product(productCmb.getValue())
                .build();
    }

    private void loadSelectedInventory() {
        Inventory selected = inventoryTab.getSelectionModel().getSelectedItem();
        if (selected != null) {
            idTxt.setText(String.valueOf(selected.getId()));
            quantityTxt.setText(String.valueOf(selected.getQuantity()));
            productCmb.getSelectionModel().select(selected.getProduct());
        }
    }

    public void resetForm() {
        refreshProductComboBox();
        idTxt.clear();
        quantityTxt.clear();
        productCmb.getSelectionModel().clearSelection();
        try {
            inventoryList = InventoryService.getService().findAll();
            showInventoryOnTable(inventoryList);
        } catch (Exception e) {
            log.error("Form reset error: " + e.getMessage());
        }
    }

    public void showInventoryOnTable(List<Inventory> inventoryList) {
        ObservableList<Inventory> observableList = FXCollections.observableArrayList(inventoryList);
        inventoryTab.setItems(observableList);
    }

    public void refreshProductComboBox() {
        try {
            List<Product> products = ProductService.getService().findAll();
            productCmb.setItems(FXCollections.observableArrayList(products));
        } catch (Exception e) {
            log.error("Error refreshing product combo box: " + e.getMessage());
        }
    }
}
