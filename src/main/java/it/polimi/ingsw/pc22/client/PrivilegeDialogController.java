package it.polimi.ingsw.pc22.client;

import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * Created by matteo on 26/06/17.
 */
public class PrivilegeDialogController implements Controller {

    private boolean confirmClicked = false;
    private Stage dialogStage;

    @Override
    public void updateScene(Object message)
    {

    }

    @FXML
    public void handleConfirm()
    {
        confirmClicked = true;
        dialogStage.close();

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
