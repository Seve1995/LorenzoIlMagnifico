package it.polimi.ingsw.pc22.client;

import it.polimi.ingsw.pc22.messages.CommunicationMessage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
		Client.getGenericState().sendToServer(textString + " C");
	}
    
    @FXML
    private void handleJoinButton()
	{
		textString = Text.getText();
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
    		create.setDisable(true);
    		join.setDisable(true);
    		random.setDisable(true);
    	}
    	else
        	Label.setText(message.toString());

	}
}
