package it.polimi.ingsw.pc22.client;

import it.polimi.ingsw.pc22.messages.CommunicationMessage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * This is the controller of the creation match.
 * Here you can type a name of a match, and create it (if it does not exists already)
 * or join it. Or you can join a random match
 *
 */

public class CreationMatchController implements Controller{

	@FXML
	private Label info;
	@FXML
	private TextField text;
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
    	textString = text.getText();
		Client.getGenericState().sendToServer(textString + " C");
	}
    
    @FXML
    private void handleJoinButton()
	{
		textString = text.getText();
		Client.getGenericState().sendToServer(textString + " J");
	}
    
    @FXML
    private void handleRandomButton()
	{
		Client.getGenericState().sendToServer("R");
	}

	@Override
	public void updateScene(Object message) {
    	if (message instanceof CommunicationMessage)
    	{
    		CommunicationMessage communicationMessage = (CommunicationMessage) message;
    		if("Match is starting. Please wait...".equals(communicationMessage.getMessage()))
    		{
    			create.setDisable(true);
	    		join.setDisable(true);
	    		random.setDisable(true);
	    		text.setDisable(true);
    		}
    	}
    	else
        	info.setText(message.toString());
	}
}
