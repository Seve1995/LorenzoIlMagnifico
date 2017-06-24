package it.polimi.ingsw.pc22.client;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.gamebox.Tower;
import it.polimi.ingsw.pc22.messages.Message;
import it.polimi.ingsw.pc22.messages.StartTurnMessage;

/**
 * Created by matteo on 22/06/17.
 */
public class GameBoardController implements Controller {

    @FXML
    private GridPane characterTower;
    @FXML
    private GridPane territoryTower;
    @FXML
    private GridPane ventureTower;
    @FXML
    private GridPane buildingTower;
    @FXML
    private ImageView blackDice;
    @FXML
    private ImageView orangeDice; //can be null
    @FXML
    private ImageView whiteDice;
    @FXML
    private GridPane excommunications;
    @FXML
    private GridPane characterPlayer;
    @FXML
    private GridPane buildingPlayer;
    @FXML
    private GridPane territoryPlayer;
    @FXML
    private GridPane venturePlayer;

    @FXML
    private ImageView bonusTile;
    @FXML
    private Label labelCoin;
    @FXML
    private Label labelWood;
    @FXML
    private Label labelStone;
    @FXML
    private Label labelServant;
    @FXML
    private Label labelVictoryPoints;
    @FXML
    private Label labelMilitaryPoints;
    @FXML
    private Label labelFaithPoints;

    private GameBoard gameboard;
    
    private void updateTowers(){
    	Tower[] towers = gameboard.getTowers();
    	for (Tower t : towers)
    		switch (t.getTowerType()) {
			case TERRITORY:
				updateCells(territoryTower, t);
				break;
			case BUILDING:
				updateCells(buildingTower, t);
				break;
			case VENTURE:
				updateCells(ventureTower, t);
				break;
			case CHARACTER:
				updateCells(characterTower, t);
				break;
			default:
				break;
			}
    }

    private void updateCells(GridPane gridPane, Tower t){
    	for (int i=0; i<=3; i++)
    		{
    			ImageView imageView = (ImageView) gridPane.getChildren().get(i);
    			int currentCardNumber = t.getTowerCells().get(i).getDevelopmentCard().getCardNumber();
    			ClassLoader classLoader = Client.class.getClassLoader();
    			String path = "GUI/Cards/devcards_f_en_c_" + currentCardNumber + ".png";
    			Image image = new Image(classLoader.getResourceAsStream(path));
    			imageView.setImage(image);
    		}
    }

    private void updateResources(){

    }
    private void updatePlayerBoard(){

    }
    private void updateLeaders(){

    }
    private void updateLeadersHand(){

    }
    private void updateExcommunication(){

    }

    @Override
    public void updateScene(Object message) {
    	if (message instanceof StartTurnMessage)
    	{
    		gameboard = Client.getGameBoard();
    		updateTowers();
    	}
    }
    
 /*   private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == 0 && GridPane.getRowIndex(node) == 1) {
                return node;
            }
        }
        return null;
    }
*/

}
