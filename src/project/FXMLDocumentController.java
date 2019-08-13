/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author bilaa
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField txtID, txtAddress, txtPin, txtName, txtSearch;

    @FXML
    private TableView<Account> tblAccount;

    @FXML
    private TableColumn<Account, String> tblName;

    @FXML
    private TableColumn<Account, String> tblAddress;

    @FXML
    private TableColumn<Account, AccountType> tblType;

    @FXML
    private ComboBox<AccountType> cmbType;

    // List to be displayed on the TableView
    private ObservableList<Account> accountList = FXCollections.observableArrayList();

    @FXML
    private void add(ActionEvent event) {
        // Name must be only letters; address must be in the format: 4690 Landon Street L5M 4L6
        String validName = "^[a-zA-Z]*\\s[a-zA-Z]*";
        String validAddress = "^[0-9]*\\s[a-zA-Z]*[A-Z][0-9][A-Z]\\s[0-9][A-Z][0-9]";
        if (txtName.getText().matches(validName) && txtAddress.getText().matches(validAddress)) {
            if (txtAddress.getText().matches(validAddress)) {
                // Create new Business with user inputted values
                Account account = new Account(txtName.getText(), Integer.parseInt(txtID.getText()), txtAddress.getText(), txtPin.getText(), cmbType.getValue());
                // Add to the account list
                accountList.add(account);
                // Clear TextFields and ComboBoxes after user clicks Add button
                txtName.clear();
                txtID.clear();
                txtAddress.clear();
                txtPin.clear();
                cmbType.getSelectionModel().clearSelection();
            }
        } else {
            // Tell user that format is incorrect
            alertError("Name: Bob Bob");
            txtAddress.setPromptText("Address: 4690 Landon Street L5M 4L6");
        }
    }

    @FXML
    private void remove(ActionEvent event) {

    }

    @FXML
    private void transaction(ActionEvent event) {

    }
    
    public void alertError(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Value Entered");
        alert.setContentText(text);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
