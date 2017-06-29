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

    @Override
    public void updateScene(Object message)
    {
        startingLabel.setText("You have " + ((PickPrivilegeMessage) message).getNumberOfPrivilege() + " left");

        if (((PickPrivilegeMessage) message).getNumberOfPrivilege() == 0)
            dialogStage.close();
    }

    @FXML
    public void handleConfirm()
    {

        confirmClicked = true;
        dialogStage.close();
        Client.getController().updateScene(new ExecutedAction("action performed"));
    }

    @FXML
    public void handle1()
    {
        output = "1";
        toggle1.setDisable(true);
        //getOutSocket().println(output);

    }

    @FXML
    public void handle2()
    {
        output = "2";
        toggle2.setDisable(true);
        //getOutSocket().println(output);
    }

    @FXML
    public void handle3()
    {
        output = "3";
        toggle3.setDisable(true);
        //getOutSocket().println(output);
    }

    @FXML
    public void handle4()
    {
        output = "4";
        toggle4.setDisable(true);
        //getOutSocket().println(output);
    }

    @FXML
    public void handle5()
    {
        output = "5";
        toggle5.setDisable(true);
        //getOutSocket().println(output);

    }

    public boolean isConfirmClicked()
    {
        return confirmClicked;
    }

    public void setConfirmClicked(boolean confirmClicked) {
        this.confirmClicked = confirmClicked;
    }

    public void setDialogStage(Stage dialogStage)
    {
        this.dialogStage = dialogStage;
    }
}
