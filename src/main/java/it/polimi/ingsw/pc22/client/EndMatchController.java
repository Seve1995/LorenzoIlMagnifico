package it.polimi.ingsw.pc22.client;

import it.polimi.ingsw.pc22.messages.EndMatchMessage;
import it.polimi.ingsw.pc22.player.Player;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.awt.*;
import java.util.*;

/**
 * Created by matteo on 02/07/17.
 */
public class EndMatchController implements Controller {

    private Stage dialogStage;
    @FXML
    private Label winnerLabel;
    @FXML
    private Label listLabel;

    private boolean confirmClicked;


    public boolean isConfirmClicked() {
        return confirmClicked;
    }



    @Override
    public void updateScene(Object message) {

    }


    public void setDialogStage(Stage dialogStage, Object message)
    {
        this.dialogStage = dialogStage;

        String winnerName = ((EndMatchMessage) message).getWinnerName();

        winnerLabel.setText("The winner is " + winnerName);

        java.util.List<Player> wholePlayers = ((EndMatchMessage) message).getStanding();

        String output = null;

        for (Player p : wholePlayers)
        {
            output += "" + p.getUsername() + " W: " + p.getNumberOfMatchWon() + " L: " + p.getNumberOfMatchLost() + "\n";
        }

        listLabel.setText(output);

    }

    @FXML
    private void handleConfirm()
    {
        confirmClicked = true;

        dialogStage.close();
    }
}
