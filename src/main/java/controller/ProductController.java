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
import model.entity.Inventory;
import model.entity.Product;
import model.service.InventoryService;
import model.service.ProductService;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Log4j2
public class ProductController implements Initializable {

    @FXML
    private TextField idTxt, titleTxt, brandTxt, modelTxt, serialNumberTxt, buyPriceTxt;

    @FXML
    private Button saveBtn, deleteBtn, editBtn;

    @FXML
    private TableView<Product> productTab;

    @FXML
    private TableColumn<Product, Integer> idCol;

    @FXML
    private TableColumn<Product, String> titleCol, brandCol, modelCol, serialNumberCol;

    private List<Product> productList = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        brandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
        modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
        serialNumberCol.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));

        try {
            productList = ProductService.getService().findAll();
            showProductOnTable(productList);
        } catch (Exception e) {
            log.error("Error loading product list: " + e.getMessage(), e);
        }

        saveBtn.setOnAction(e -> {
            try {
                Product product = buildProductForm();
                ProductService.getService().save(product);
//

                InventoryController.getInstance().refreshProductComboBox();
                OrderItemController.getInstance().refreshProductComboBox();

//

                productList = ProductService.getService().findAll();
                showProductOnTable(productList);
                new Alert(Alert.AlertType.INFORMATION, "Product saved", ButtonType.OK).show();
                resetForm();
            } catch (NumberFormatException nfe) {
                new Alert(Alert.AlertType.ERROR, "Invalid number format in Id or BuyPrice", ButtonType.OK).show();
                log.error("Save Error - invalid number format", nfe);
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Failed to save product: " + ex.getMessage(), ButtonType.OK).show();
                log.error("Save Error: " + ex.getMessage(), ex);
            }
        });

        editBtn.setOnAction(e -> {
            try {
                Product product = buildProductForm();
                ProductService.getService().edit(product);
                productList = ProductService.getService().findAll();
                showProductOnTable(productList);
                new Alert(Alert.AlertType.INFORMATION, "Product edited", ButtonType.OK).show();
                resetForm();
            } catch (NumberFormatException nfe) {
                new Alert(Alert.AlertType.ERROR, "Invalid number format in Id or BuyPrice", ButtonType.OK).show();
                log.error("Edit Error - invalid number format", nfe);
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Failed to edit product: " + ex.getMessage(), ButtonType.OK).show();
                log.error("Edit Error: " + ex.getMessage(), ex);
            }
        });

        deleteBtn.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idTxt.getText());
                ProductService.getService().delete(id);
                productList = ProductService.getService().findAll();
                InventoryController.getInstance().refreshProductComboBox();

                showProductOnTable(productList);
                new Alert(Alert.AlertType.INFORMATION, "Product deleted", ButtonType.OK).show();
                resetForm();
            } catch (NumberFormatException nfe) {
                new Alert(Alert.AlertType.ERROR, "Invalid Id format", ButtonType.OK).show();
                log.error("Delete Error - invalid number format", nfe);
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Failed to delete product: " + ex.getMessage(), ButtonType.OK).show();
                log.error("Delete Error: " + ex.getMessage(), ex);
            }
        });

        EventHandler<Event> tableChangeEvent = event -> {
            Product selectedProduct = productTab.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                idTxt.setText(String.valueOf(selectedProduct.getId()));
                titleTxt.setText(selectedProduct.getTitle());
                brandTxt.setText(selectedProduct.getBrand());
                modelTxt.setText(selectedProduct.getModel());
                serialNumberTxt.setText(selectedProduct.getSerialNumber());
                buyPriceTxt.setText(String.valueOf(selectedProduct.getBuyPrice()));
            }
        };

        productTab.setOnMouseReleased(tableChangeEvent);
        productTab.setOnKeyReleased(tableChangeEvent);
    }

    private Product buildProductForm() {
        return Product.builder()
                .id(Integer.parseInt(idTxt.getText()))
                .title(titleTxt.getText())
                .brand(brandTxt.getText())
                .model(modelTxt.getText())
                .serialNumber(serialNumberTxt.getText())
                .buyPrice(Integer.parseInt(buyPriceTxt.getText()))
                .build();
    }

    public void resetForm() {
        idTxt.clear();
        titleTxt.clear();
        brandTxt.clear();
        modelTxt.clear();
        serialNumberTxt.clear();
        buyPriceTxt.clear();
    }

    public void showProductOnTable(List<Product> productList) {
        ObservableList<Product> observableList = FXCollections.observableArrayList(productList);
        productTab.setItems(observableList);
    }
}
