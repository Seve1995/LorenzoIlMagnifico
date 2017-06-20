package it.polimi.ingsw.pc22.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class ClientAccessController {
	@FXML
    private Button login;
    @FXML
    private Button register;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML 
    private Label output;
    
    private Client client;
    
    public void setClient(Client client) {
        this.client = client;
    }
    
    @FXML
    private void handleLoginButton() {
    	output.setText(username.getText());
    }
    
    @FXML
    private void handleRegisterButton() {

        output.setText("Registrazione");
    }
}
