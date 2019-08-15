/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author bilaa
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField txtID, txtAddress, txtPin, txtName, txtSearch;

    @FXML
    private Button btnRemove, btnTransaction, btnExit, btnSave, btnAdd;
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

    @FXML
    private AnchorPane main;

    // Index of Businesses on the list on the TableView
    private IntegerProperty index = new SimpleIntegerProperty();

    // List to be displayed on the TableView
    private ObservableList<Account> accountList = FXCollections.observableArrayList(
            new Account("Bilaal Rashid", 101, "4690 Landon Street L5M 4L6", AccountType.CHEQUEINGS),
            new Account("Kenny Heaps", 102, "4148  Yonge Street M4W 1J7", AccountType.CHEQUEINGS),
            new Account("Robin Lansiquot", 103, "2091 Speers Road L6J 3X4", AccountType.CHEQUEINGS)
    );

    @FXML
    private void save(ActionEvent event) {

        // Enabling all buttons except save buttons
        btnRemove.setDisable(false);
        btnTransaction.setDisable(false);
        btnTransaction.setDisable(false);
        btnExit.setDisable(false);
        btnAdd.setDisable(false);
        btnSave.setDisable(true);

        // Disabling all fields
        disableields(true);

        // Validating for Name 
        Account account = new Account();
        AccountList accountList = new AccountList();
        if (!account.isValidName(txtName.getText())) {
            alertError("Name Example: Bob Bob");
        }

        // Validating for Address 
        if (!account.isAddressValid(txtAddress.getText())) {
            alertError("Address Example: 4690 Landon Street L5M 4L6");
        }

        // Validating for Pin if empty
        if (txtPin.getText().isEmpty()) {
            alertError("Enter a Pin");
        }

        // Validating for ID if one already exists 
        // if (!txtID.getText().equals(accountList.get(0))) {
        // alertError("ID taken");
        // } 
        // Validating for ID if empty
        if (txtID.getText().isEmpty()) {
            alertError("Enter an ID");
        }

        // Validating for Account Type if empty 
        if (!cmbType.getSelectionModel().isEmpty()) {
            alertError("Enter an Account Type");
        } // Validation passed
        else {
            // Create new Business with user inputted values
            account = new Account(txtName.getText(), Integer.parseInt(txtID.getText()), txtAddress.getText(), cmbType.getValue());

            // Add to the account list
            accountList.add(account);

            // Clearing TextFields and ComboBoxes
            clearFields();
        }
    }

    @FXML
    private void add(ActionEvent event) {
        disableields(false);
        btnRemove.setDisable(true);
        btnTransaction.setDisable(true);
        btnTransaction.setDisable(true);
        btnExit.setDisable(false);
        btnSave.setDisable(false);
        btnAdd.setDisable(true);
    }

    @FXML
    private void exit(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("FXMLDocumentLogin.fxml"));
        main.getChildren().setAll(pane);
    }

    @FXML
    private void remove(ActionEvent event) {
        //index of accounts on the tableview list
        int i = index.get();
        //if index is greater than -1, remove account at the index, also clear 
        if (i > -1) {
            accountList.remove(i);
            tblAccount.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void transaction(ActionEvent event) throws IOException {
        TabPane pane = FXMLLoader.load(getClass().getResource("FXMLTransaction.fxml"));
        main.getChildren().setAll(pane);
    }

    public void alertError(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Value Entered");
        alert.setContentText(text);
        alert.showAndWait();
    }

    // Method for clearing all TextFeilds
    public void clearFields() {
        txtID.clear();
        txtName.clear();
        txtAddress.clear();
        txtPin.clear();
        cmbType.getSelectionModel().clearSelection();
    }

    // Method for locking all editable fields
    public void disableields(boolean disable) {
        txtID.setDisable(disable);
        txtName.setDisable(disable);
        txtAddress.setDisable(disable);
        txtPin.setDisable(disable);
        cmbType.setDisable(disable);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Enabling and Disabling Buttons
        btnSave.setDisable(true);
        disableields(true);

        //searching through any of the 4 columns
        txtSearch.textProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable o) {
                //if the user did not search, then display list of accounts
                if (txtSearch.textProperty().get().isEmpty()) {
                    tblAccount.setItems(accountList);
                    return;
                }
                ObservableList<Account> items = FXCollections.observableArrayList();
                ObservableList<TableColumn<Account, ?>> columns = tblAccount.getColumns();
                //search through rows
                for (int i = 0; i < accountList.size(); i++) {
                    for (int j = 0; j < columns.size(); j++) {
                        TableColumn column = columns.get(j);
                        String cellValue = column.getCellData(accountList.get(i)).toString();
                        cellValue = cellValue.toLowerCase();
                        //if no match then leave row in list
                        if (cellValue.contains(txtSearch.textProperty().get().toLowerCase())) {
                            items.add(accountList.get(i));
                            break;
                        }
                    }
                }
                tblAccount.setItems(items);
            }
        });

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
