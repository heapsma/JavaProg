/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author bilaa
 */
public class FXMLDocumentController implements EventHandler, Initializable {

    @FXML
    private TextField txtName, txtID, txtAddress, txtPin, txtSearch;

    @FXML
    private TableView<Account> tblAccount;

    @FXML
    private TableColumn<Account, String> tblName;

    @FXML
    private TableColumn<Account, String> tblAddress;

    @FXML
    private TableColumn<Account, AccountType> tblType;

    @FXML
    private TableColumn<Account, String> tblID;

    @FXML
    private ComboBox<AccountType> cmbType;

    private IntegerProperty index = new SimpleIntegerProperty();

    // list to display on tableview
    private ObservableList<Account> accountList = FXCollections.observableArrayList(
            new Account("Robin Lansiquot", "1234", "150 Cindy Street", "9898", AccountType.SAVINGS),
            new Account("Jimmy John", "4321", "250 Cindy Street", "9898", AccountType.CHEQUEINGS),
            new Account("Robin Lansiquot", "1234", "150 Cindy Street", "9898", AccountType.SAVINGS),
            new Account("Jimmy John", "4321", "250 Cindy Street", "9898", AccountType.CHEQUEINGS),
            new Account("Robin Lansiquot", "1234", "150 Cindy Street", "9898", AccountType.SAVINGS),
            new Account("Jimmy John", "4321", "250 Cindy Street", "9898", AccountType.CHEQUEINGS)
    );

    @FXML
    private AnchorPane main, first;
    @FXML
    private TabPane transaction;

    @FXML
    private void add(ActionEvent event) {
        // Name must be only letters; address must be in the format: 4690 Landon Street L5M 4L6
        String validName = "^[a-zA-Z]*\\s[a-zA-Z]*";
        String validAddress = "^[0-9]*\\s[a-zA-Z]*[A-Z][0-9][A-Z]\\s[0-9][A-Z][0-9]";
        if (txtName.getText().matches(validName) && txtAddress.getText().matches(validAddress)) {
            if (txtAddress.getText().matches(validAddress)) {
                // Create new Business with user inputted values
                Account account = new Account(txtName.getText(), txtID.getText(), txtAddress.getText(), txtPin.getText(), cmbType.getValue());
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
        //index of accounts on the tableview list
        int i = index.get();
        //if index is greater than -1, remove account at the index, also clear 
        if (i>-1) {
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {

//        prompt text and dropdown menu
//        cmbType.setPromptText("Account Type");
//        cmbType.getItems().setAll(AccountType.values());
        
        
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
        }
        );

        //Setting the columns
        tblName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        tblID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        tblAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        tblType.setCellValueFactory(new PropertyValueFactory<>("Account Type"));
        tblAccount.setItems(accountList);

        //allowing the tableview to be editable
        tblAccount.setEditable(true);

//        Updates the name cell with input from the user
        tblName.setCellFactory(TextFieldTableCell.forTableColumn());
        tblName.setOnEditCommit(new EventHandler<CellEditEvent<Account, String>>() {
            @Override
            public void handle(CellEditEvent<Account, String> event) {
                ((Account) event.getTableView().getItems().get(event.getTablePosition().getRow())).setName(event.getNewValue());
            }
        });

//        Updates ID cell with input from the user
        tblID.setCellFactory(TextFieldTableCell.forTableColumn());
        tblID.setOnEditCommit(new EventHandler<CellEditEvent<Account, String>>() {
            @Override
            public void handle(CellEditEvent<Account, String> event) {
                ((Account) event.getTableView().getItems().get(event.getTablePosition().getRow())).setAddress(event.getNewValue());
            }
        });
////        
//        Updates the address cell with input from the user
        tblAddress.setCellFactory(TextFieldTableCell.forTableColumn());
        tblAddress.setOnEditCommit(new EventHandler<CellEditEvent<Account, String>>() {
            @Override
            public void handle(CellEditEvent<Account, String> event) {
                ((Account) event.getTableView().getItems().get(event.getTablePosition().getRow())).setAddress(event.getNewValue());
            }
        });

        //the tableview list index is set to -1
        index.set(-1);
        //updates table with input from the user
        tblAccount.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
                index.set(accountList.indexOf(newValue));
            }
        });

    }

    @Override
    public void handle(Event event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
