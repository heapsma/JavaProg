/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Kenny
 */
public class FXMLDocumentControllerLogIn implements Initializable {
    @FXML
    private Label lblError;
    
    @FXML
    private AnchorPane main;
    @FXML
    private TextField txtAdmin;
    @FXML
    private PasswordField passPass;

    @FXML
    private void loadSecond(ActionEvent Event) throws IOException {
        if("Admin".equals(txtAdmin.getText()) && "password".equals(passPass.getText())) {
        
        TabPane pane = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        main.getChildren().setAll(pane);
        
     
        }else{
            lblError.setText("Incorrect username or password");
            
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
