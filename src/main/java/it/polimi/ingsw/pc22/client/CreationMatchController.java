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
		updateScene("The Match is starting...");
	}
    
    @FXML
    private void handleJoinButton()
	{
		textString = Text.getText();
    	getOutSocket().println(textString + " J");
		updateScene("The Match is starting...");
	}
    
    @FXML
    private void handleRandomButton()
	{
    	getOutSocket().println("R");
    	updateScene("The Match is starting...");

	}

	@Override
	public void updateScene(String string) {
    	Label.setText(string);
	}
}
