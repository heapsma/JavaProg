/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author bilaa
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField txtID, txtAddress, txtPin, txtName, txtSearch;

    @FXML
    private Button btnRemove, btnTransaction;
    @FXML
    private TableView<Account> tblAccount;

    @FXML
    private TableColumn<Account, String> tblName;

    @FXML
    private TableColumn<Account, String> tblAddress;

    @FXML
    private TableColumn<Account, String> tblID;

    @FXML
    private TableColumn<Account, AccountType> tblType;

    @FXML
    private ComboBox<AccountType> cmbType;

    // Index of Businesses on the list on the TableView
    private IntegerProperty index = new SimpleIntegerProperty();

    // List to be displayed on the TableView
    private ObservableList<Account> accountList = FXCollections.observableArrayList(
            new Account("Bilaal Rashid", 101, "4690 Landon Street L5M 4L6", AccountType.CHEQUEINGS),
            new Account("Kenny Heaps", 102, "4148  Yonge Street M4W 1J7", AccountType.CHEQUEINGS),
            new Account("Robin Lansiquot", 103, "2091 Speers Road L6J 3X4", AccountType.CHEQUEINGS)
    );
    
    @FXML
    private void add(ActionEvent event) {
        clearFields();
        lockFields(true);
        btnRemove.setDisable(true);
        btnTransaction.setDisable(true);
        // Name must be only letters; address must be in the format: 4690 Landon Street L5M 4L6
        String validName = "^[a-zA-Z]*\\s[a-zA-Z]*";
        String validAddress = "^[0-9]*\\s[a-zA-Z]*\\s[a-zA-Z]*\\s[A-Z][0-9][A-Z]\\s[0-9][A-Z][0-9]";
        if (!txtName.getText().matches(validName)) {
            alertError("Name: Bob Bob");
        } else if (!txtAddress.getText().matches(validAddress)) {
            alertError("Address: 4690 Landon Street L5M 4L6");
        } else {
            // Create new Business with user inputted values
            Account account = new Account(txtName.getText(), Integer.parseInt(txtID.getText()), txtAddress.getText(), cmbType.getValue());
            // Add to the account list
            accountList.add(account);
            // Clear TextFields and ComboBoxes after user clicks Add button
            txtName.clear();
            txtID.clear();
            txtAddress.clear();
            txtPin.clear();
            cmbType.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void remove(ActionEvent event) {
        // Getting index of Account on the list on the TableView
        int i = index.get();
        // If the index is greater than -1, then remove Account at that index, and clear the selection
        if (i > -1) {
            accountList.remove(i);
            tblAccount.getSelectionModel().clearSelection();
        }
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

    public void clearFields() {
        txtID.clear();
        txtName.clear();
        txtAddress.clear();
        txtPin.clear();
    }

    // Method for locking all editable fields
    public void lockFields(boolean lock) {
        txtID.setEditable(lock);
        txtName.setEditable(lock);
        txtAddress.setEditable(lock);
        txtPin.setEditable(lock);
        cmbType.setEditable(lock);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lockFields(false);
        // Setting the prompt text and drop-down menus for the ComboBoxes
        cmbType.setPromptText("Account Type");
        cmbType.getItems().setAll(AccountType.values());

        // Setting the columns
        tblName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tblAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        tblID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblType.setCellValueFactory(new PropertyValueFactory<>("accountType"));
        tblAccount.setItems(accountList);

    }

}
