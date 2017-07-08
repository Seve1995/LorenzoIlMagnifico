package it.polimi.ingsw.pc22.client;

import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * This is the controller of the Excommunication screen:
 * here you can (if you have enought faith points) support the pope or not.
 * It is up to you...
 */
public class ExcommunicationController implements Controller {

    private Stage dialogStage;
    private String output;

    private boolean confirmClicked;

    @Override
    public void updateScene(Object message) {}

    public boolean isConfirmClicked() {
        return confirmClicked;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void handleYes()
    {
        output = "1";
    }

    @FXML
    private void handleNo()
    {
        output = "2";
    }

    @FXML
    private void handleConfirm()
    {
        confirmClicked = true;

        Client.getGenericState().sendToServer(output);

        dialogStage.close();
    }
}
