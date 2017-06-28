package it.polimi.ingsw.pc22.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.PrintWriter;

public class ClientAccessController implements Controller {
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
    
    private PrintWriter printWriter;
    
    @FXML
    private void handleLoginButton() throws InterruptedException {
    	String output = username.getText() + " " + password.getText() + " L";
    	//getOutSocket().println(output);
    	//if (Client.getGenericState() instanceof GameCreationState)
    	//	Client.launchCreationMatch();
    }
    
    @FXML
    private void handleRegisterButton() {
    	String output = username.getText() + " " + password.getText() + " R";
    	//getOutSocket().println(output);
        
    }

	@Override
	public void updateScene(Object message) {
		output.setText(message.toString());
	}
}
