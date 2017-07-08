package it.polimi.ingsw.pc22.client;

import it.polimi.ingsw.pc22.messages.ExecutedAction;
import it.polimi.ingsw.pc22.player.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

/**
 * This is the controller of the screen that aske the player
 * if he/she wants to increment the value of his/her action using servants.
 * It has a simple spinner whose values go from 0 to the number of servants of the
 * player. As always there is a confirm button.

 */
public class ServantsHandlerController implements Controller {

    private boolean confirmClicked = false;
    private Stage dialogStage;
    private Integer output;
    

	@FXML
	private Spinner<Integer> spinner;
    @FXML
    private void handleConfirm()
    {
        confirmClicked = true;

        output = spinner.getValue();

        Client.getGenericState().sendToServer(output.toString());

        dialogStage.close();

        Client.getController().updateScene(new ExecutedAction("action performed"));

    }

    @Override
    public void updateScene(Object message) {

    }

    public boolean isConfirmClicked()
    {
        return confirmClicked;
    }
    
    public void setConfirmClicked(boolean confirmClicked) {
        this.confirmClicked = confirmClicked;
    }

    public void setDialogStage(Stage dialogStage, Player p)
    {
        this.dialogStage = dialogStage;
        SpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0 , p.getServants());
        spinner.setValueFactory(valueFactory);
    }
    
}
