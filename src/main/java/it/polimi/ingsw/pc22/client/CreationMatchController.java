package it.polimi.ingsw.pc22.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import static it.polimi.ingsw.pc22.client.Client.getOutSocket;

import it.polimi.ingsw.pc22.messages.CommunicationMessage;
import it.polimi.ingsw.pc22.messages.GameStatusMessage;
import it.polimi.ingsw.pc22.messages.Message;
import it.polimi.ingsw.pc22.messages.StartTurnMessage;

public class CreationMatchController implements Controller{

	@FXML
	private Label Label;
	@FXML
	private TextField Text;
	@FXML
	private Button create;
	@FXML
	private Button join;
	@FXML 
	private Button random;
	
    private String textString;
    
    @FXML
    private void handleCreateButton()
	{
    	textString = Text.getText();
    	getOutSocket().println(textString + " C");
	}
    
    @FXML
    private void handleJoinButton()
	{
		textString = Text.getText();
    	getOutSocket().println(textString + " J");
	}
    
    @FXML
    private void handleRandomButton()
	{
    	getOutSocket().println("R");

	}

	@Override
	public void updateScene(Object message) {
    	if (message instanceof CommunicationMessage)
    	{
    		create.setDisable(true);
    		join.setDisable(true);
    		random.setDisable(true);
    	}
    	else
        	Label.setText(message.toString());

	}
}
