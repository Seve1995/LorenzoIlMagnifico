package it.polimi.ingsw.pc22.client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CreationMatchController implements Controller{

	@FXML
	private Label Label;
	@FXML
	private TextField Text;
	
    private Client client;
    
    private String textString;
    
    public void setClient(Client client) {
        this.client = client;
    }
    
    @FXML
    private void handleCreateButton()
	{
    	textString = Text.getText();
    	Client.getOutSocket().println(textString + " C");
	}
    
    @FXML
    private void handleJoinButton()
	{
    	Client.getOutSocket().println(textString + " J");
	}
    
    @FXML
    private void handleRandomButton()
	{
    	Client.getOutSocket().println("R");

	}

	@Override
	public void updateScene(String string) {
    	Label.setText(string);
	}
}
