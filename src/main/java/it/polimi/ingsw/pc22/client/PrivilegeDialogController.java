package it.polimi.ingsw.pc22.client;

import it.polimi.ingsw.pc22.messages.ExecutedAction;
import it.polimi.ingsw.pc22.messages.PickPrivilegeMessage;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

/**
 * Created by matteo on 26/06/17.
 */
public class PrivilegeDialogController implements Controller {

    private boolean confirmClicked = false;
    private Stage dialogStage;
    private int privilegesLeft;

    @FXML
    private Label startingLabel;
    @FXML
    private ToggleButton toggle1;
    @FXML
    private ToggleButton toggle2;
    @FXML
    private ToggleButton toggle3;
    @FXML
    private ToggleButton toggle4;
    @FXML
    private ToggleButton toggle5;

    private String output;

    @FXML
    public void handleConfirm()
    {

        confirmClicked = true;
        dialogStage.close();
        Client.getController().updateScene(new ExecutedAction("action performed"));
    }

    private void disableAll()
    {
        toggle1.setDisable(true);
        toggle2.setDisable(true);
        toggle3.setDisable(true);
        toggle4.setDisable(true);
        toggle5.setDisable(true);
    }

    private void checkForOtherPrivileges()
    {
        privilegesLeft--;
        if (privilegesLeft == 0)
        {
            disableAll();
            startingLabel.setText("You have " + privilegesLeft + " left");
        }
        else
        {
            output += "-";
        }
    }

    @FXML
    public void handle1()
    {

        output = "1";
        toggle1.setDisable(true);
        checkForOtherPrivileges();
        Client.getGenericState().sendToServer(output);

    }

    @FXML
    public void handle2()
    {
        output = "2";
        toggle2.setDisable(true);
        checkForOtherPrivileges();
        Client.getGenericState().sendToServer(output);
    }

    @FXML
    public void handle3()
    {
        output = "3";
        toggle3.setDisable(true);
        checkForOtherPrivileges();
        Client.getGenericState().sendToServer(output);
    }

    @FXML
    public void handle4()
    {
        output = "4";
        toggle4.setDisable(true);
        checkForOtherPrivileges();
        Client.getGenericState().sendToServer(output);
    }

    @FXML
    public void handle5()
    {
        output = "5";
        toggle5.setDisable(true);
        checkForOtherPrivileges();
        Client.getGenericState().sendToServer(output);
    }

    public boolean isConfirmClicked()
    {
        return confirmClicked;
    }

    public void setConfirmClicked(boolean confirmClicked) {
        this.confirmClicked = confirmClicked;
    }

    public void setDialogStage(Stage dialogStage, Object message)
    {
        this.dialogStage = dialogStage;
        privilegesLeft = ((PickPrivilegeMessage) message).getNumberOfPrivilege();
        startingLabel.setText("You have " + ((PickPrivilegeMessage) message).getNumberOfPrivilege() + " left");

        if (((PickPrivilegeMessage) message).getNumberOfPrivilege() == 0)
            dialogStage.close();
    }

	@Override
	public void updateScene(Object message) {
		
	}
}
