package it.polimi.ingsw.pc22.client;

import it.polimi.ingsw.pc22.messages.ChooseAssetsMessage;
import it.polimi.ingsw.pc22.messages.ChooseCostsMessage;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

/**
 * This controller is used in two ways:
 * - to choose between two costs
 * - to choose between two effects
 * In so doing, in a single screen, whose labels change dinamically
 * we manage two different situation, characterized by the same patter
 * "Do you want A or B?"
 */
public class ChoiceCostAssetController implements Controller {

    private boolean confirmClicked = false;
    private Stage dialogStage;
    private String output ;
    @FXML
    private Label startingLabel;

    @FXML
    private ToggleButton choice1;
    @FXML
    private ToggleButton choice2;

    @FXML
    private void handleChoice1()
    {
        output = "0";
    }

    @FXML
    private void handleChoice2()
    {
        output = "1";
    }

    public boolean isConfirmClicked() {
        return confirmClicked;
    }


    @FXML
    private void handleConfirm()
    {
        Client.getGenericState().sendToServer(output);

        confirmClicked = true;

        dialogStage.close();
    }

    @FXML
    public void setDialogStage(Stage dialogStage, Object message)
    {
        this.dialogStage = dialogStage;

        startingLabel.setText("Apparently, an error occurred!");

        if (message instanceof ChooseAssetsMessage)
        {
            startingLabel.setText("Choose one asset:");
        }

        if (message instanceof ChooseCostsMessage)
        {
            startingLabel.setText("Do you want to spend military \n" +
                    "points or other Assets \n" + "shown on the card?");
        }

    }

    @Override
    public void updateScene(Object message) {}


}
