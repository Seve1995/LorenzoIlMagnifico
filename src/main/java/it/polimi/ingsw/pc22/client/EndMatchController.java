package it.polimi.ingsw.pc22.client;

import it.polimi.ingsw.pc22.messages.EndMatchMessage;
import it.polimi.ingsw.pc22.player.Player;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.awt.*;

/**
 * Created by matteo on 02/07/17.
 */
public class EndMatchController implements Controller {

    private Stage dialogStage;
    @FXML
    private Label winnerLabel;

    private boolean confirmClicked;


    public boolean isConfirmClicked() {
        return confirmClicked;
    }

    @FXML
    private Label tryLabel;

    @Override
    public void updateScene(Object message) {

    }


    public void setDialogStage(Stage dialogStage, Object message)
    {
        this.dialogStage = dialogStage;

        int currVictoryPoints = 0;

        String winnerName = null;

        for (Player p : ((EndMatchMessage) message).getStanding())
        {
            if (p.getVictoryPoints() > currVictoryPoints)
            {
                winnerName = p.getUsername();
            }

        }

        winnerLabel.setText("The winner is " + winnerName + " with " + currVictoryPoints + "VictoryPoints");

    }

    @FXML
    private void handleConfirm()
    {
        confirmClicked = true;

        dialogStage.close();
    }
}
