package it.polimi.ingsw.pc22.client;

import it.polimi.ingsw.pc22.messages.ExecutedAction;
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
    
	@FXML
	private SpinnerValueFactory<Integer> valueFactory;
	@FXML
	private Spinner<Integer> spinner;
    @FXML
    private void handleConfirm()
    {
        confirmClicked = true;
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

    public void setDialogStage(Stage dialogStage, SpinnerValueFactory<Integer> valueFactory)
    {
        this.dialogStage = dialogStage;
        this.valueFactory = valueFactory;
        spinner.setValueFactory(valueFactory);
    }
    
}
