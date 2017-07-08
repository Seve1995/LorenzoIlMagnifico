package it.polimi.ingsw.pc22.client;

import it.polimi.ingsw.pc22.messages.EndMatchMessage;
import it.polimi.ingsw.pc22.player.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * This is the controller of the last screen of the Match.
 * Here you can see the winner of this match, and the global ranking
 * of our server.
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

        int winnerVictoryPoints = ((EndMatchMessage) message).getWinnerVictoryPoints();
        
        winnerLabel.setText("The winner is " + winnerName + "\n" +
        		"Winner Victory points: " + winnerVictoryPoints
        );

        java.util.List<Player> wholePlayers = ((EndMatchMessage) message).getStanding();

        StringBuilder output = new StringBuilder();
        
        for (Player p : wholePlayers)
        {
            output.append(p.getUsername() + " W: " + p.getNumberOfMatchWon() + " L: " + p.getNumberOfMatchLost() + "\n");
        }

        listLabel.setText(output.toString());
    }

    @FXML
    private void handleConfirm()
    {
        confirmClicked = true;

        dialogStage.close();
        
        Client.launchCreationMatch();
    }
}
