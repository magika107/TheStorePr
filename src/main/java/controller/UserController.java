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
import model.entity.Order;
import model.entity.User;
import model.entity.enums.Gender;
import model.service.OrderService;
import model.service.UserService;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Log4j2
public class UserController implements Initializable {
    @FXML
    private TextField idTxt, nameTxt, familyTxt, phoneTxt, userTxt, nameSerTxt, familySerTxt, userSerTxt;
    @FXML
    private Button saveBtn, editBtn, deleteBtn;
    @FXML
    private DatePicker datePick;
    @FXML
    private ComboBox<Gender> genderCombo;
    @FXML
    private PasswordField passPass;
    @FXML
    private TableView<User> userTab;
    @FXML
    private TableColumn<User, String> idCol, nameCol, familyCol, usernameCol;


    private List<User> userList = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Gender gender : Gender.values()) {
            genderCombo.getItems().add(gender);
        }

        saveBtn.setOnAction(e -> {
            User user = buildUserForm();
            userList.add(user);
            try {
                UserService.getService().save(user);
                userList = UserService.getService().findAll();

                OrderController.getInstance().reloadUserCombo();
                PaymentController.getInstance().refreshPaymentComboBox();
                showUserOnTable(userList);
                new Alert(Alert.AlertType.INFORMATION, "User created", ButtonType.OK).show();
                resetForm();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Failed to save user: " + ex.getMessage()).show();
            }
        });

        editBtn.setOnAction(e -> {
            User user = buildUserForm();
            try {
                UserService.getService().edit(user);
                userList = UserService.getService().findAll();
                showUserOnTable(userList);
                new Alert(Alert.AlertType.INFORMATION, "User edited", ButtonType.OK).show();
                resetForm();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Failed to edit user: " + ex.getMessage()).show();
            }
        });

        deleteBtn.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idTxt.getText());
                UserService.getService().delete(id);
                userList = UserService.getService().findAll();
                showUserOnTable(userList);
                resetForm();
                new Alert(Alert.AlertType.INFORMATION, "User deleted", ButtonType.OK).show();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Failed to delete user: " + ex.getMessage()).show();
            }
        });
        EventHandler<Event> tableChangeEvent = event -> {
            User selectedPerson = userTab.getSelectionModel().getSelectedItem();
            if (selectedPerson != null) {
                idTxt.setText(String.valueOf(selectedPerson.getId()));
                nameTxt.setText(selectedPerson.getName());
                familyTxt.setText(selectedPerson.getFamily());
                genderCombo.getSelectionModel().select(selectedPerson.getGender());
                datePick.setValue(selectedPerson.getBirthDate());
                phoneTxt.setText(selectedPerson.getPhoneNumber());
                passPass.setText(selectedPerson.getPassword());
                userTxt.setText(selectedPerson.getUsername());
            }
        };

        userTab.setOnMouseReleased(tableChangeEvent);
        userTab.setOnKeyReleased(tableChangeEvent);

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        familyCol.setCellValueFactory(new PropertyValueFactory<>("family"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));

        try {
            userList = UserService.getService().findAll();
            showUserOnTable(userList);
        } catch (Exception e) {
            log.error("Error loading person list: " + e.getMessage());
        }
    }


    private User buildUserForm() {
        return User.builder()
                .id(Integer.parseInt(idTxt.getText()))
                .name(nameTxt.getText())
                .family(familyTxt.getText())
                .gender(genderCombo.getValue())
                .birthDate(datePick.getValue())
                .phoneNumber(phoneTxt.getText())
                .username(userTxt.getText())
                .password(passPass.getText())
                .build();
    }

    public void resetForm() {
        idTxt.clear();
        nameTxt.clear();
        familyTxt.clear();
        phoneTxt.clear();
        passPass.clear();
        userTxt.clear();
        datePick.setValue(LocalDate.now());
        nameSerTxt.clear();
        familySerTxt.clear();
        userSerTxt.clear();

        try {
            userList = UserService.getService().findAll();
            showUserOnTable(userList);
        } catch (Exception e) {
            log.error("Form Reset Error: " + e.getMessage());
        }
    }

    public void showUserOnTable(List<User> userList) {
        ObservableList<User> observableList = FXCollections.observableArrayList(userList);
        userTab.setItems(observableList);
    }


    private void searchUser() {
        try {
            String name = nameSerTxt.getText().trim();
            String family = familySerTxt.getText().trim();
            List<User> results = UserService.getService().findByNameAndFamily(name, family);
            showUserOnTable(results);
        } catch (Exception e) {
            log.error("Search error: " + e.getMessage());
        }
    }
}

