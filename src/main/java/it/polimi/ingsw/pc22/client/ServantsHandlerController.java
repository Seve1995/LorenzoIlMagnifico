package it.polimi.ingsw.pc22.client;

import it.polimi.ingsw.pc22.messages.ExecutedAction;
import it.polimi.ingsw.pc22.player.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

/**
 * Created by matteo on 27/06/17.
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
