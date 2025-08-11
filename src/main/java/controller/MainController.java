package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MainController {

    @FXML
    private Tab userTab, productTab, customerTab, orderTab, inventoryTab, orderItemTab, bankTab, paymentTab;

    public void initialize() {
        try {
            // ست کردن FXML هر تب
            userTab.setContent(FXMLLoader.load(getClass().getResource("/view/UserView.fxml")));
            productTab.setContent(FXMLLoader.load(getClass().getResource("/view/ProductView.fxml")));
            customerTab.setContent(FXMLLoader.load(getClass().getResource("/view/CustomerView.fxml")));
            orderTab.setContent(FXMLLoader.load(getClass().getResource("/view/OrderView.fxml")));
            inventoryTab.setContent(FXMLLoader.load(getClass().getResource("/view/InventoryView.fxml")));
            orderItemTab.setContent(FXMLLoader.load(getClass().getResource("/view/OrderItemView.fxml")));
            bankTab.setContent(FXMLLoader.load(getClass().getResource("/view/BankView.fxml")));
            paymentTab.setContent(FXMLLoader.load(getClass().getResource("/view/PaymentView.fxml")));

            setTabIcon(userTab, "/user.png");
            setTabIcon(productTab, "/product.png");
            setTabIcon(customerTab, "/customer.png");
            setTabIcon(orderTab, "/order.png");
            setTabIcon(inventoryTab, "/inventory.png");
            setTabIcon(orderItemTab, "/orderitem.png");
            setTabIcon(bankTab, "/bank.png");
            setTabIcon(paymentTab, "/wallet.png");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTabIcon(Tab tab, String iconPath) {
        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream(iconPath)));
        icon.setFitWidth(18);  // عرض آیکون
        icon.setFitHeight(18); // ارتفاع آیکون
        tab.setGraphic(icon);
    }
}
