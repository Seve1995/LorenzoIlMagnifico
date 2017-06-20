package it.polimi.ingsw.pc22.client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import it.polimi.ingsw.pc22.exceptions.GenericException;
import it.polimi.ingsw.pc22.rmi.RMIAuthenticationService;
import it.polimi.ingsw.pc22.rmi.RMIClientStreamService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

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
    
    private Client client;
    
    private PrintWriter printWriter;
    
    @FXML
    private void initialize() {
		try {
			printWriter = new PrintWriter(Client.getSocket().getOutputStream(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    public void setClient(Client client) {
    	
        this.client = client;
        
    }
    
    @FXML
    private void handleLoginButton() {
    	String output = username.getText() + " " + password.getText() + " L";
    	printWriter.println(output);
    }
    
    @FXML
    private void handleRegisterButton() {
    	String output = username.getText() + " " + password.getText() + " R";
    	printWriter.println(output);
        
    }

	@Override
	public void updateScene(String string) {
		output.setText(string);
	}
}
