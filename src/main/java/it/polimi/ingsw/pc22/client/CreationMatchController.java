package it.polimi.ingsw.pc22.client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import static it.polimi.ingsw.pc22.client.Client.getOutSocket;

public class CreationMatchController implements Controller{

	@FXML
	private Label Label;
	@FXML
	private TextField Text;
	    
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
	public void updateScene(String string) {
    	Label.setText(string);
	}
}
