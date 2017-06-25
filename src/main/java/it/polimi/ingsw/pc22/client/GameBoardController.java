package it.polimi.ingsw.pc22.client;

import it.polimi.ingsw.pc22.gamebox.*;
import it.polimi.ingsw.pc22.messages.ExecutedAction;
import it.polimi.ingsw.pc22.player.Player;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.pc22.messages.Message;
import it.polimi.ingsw.pc22.messages.StartTurnMessage;

import static it.polimi.ingsw.pc22.client.Client.getOutSocket;

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
    private GridPane leaders;
    @FXML
    private GridPane leadersPlace;
    @FXML
    private GridPane excommunications;
    @FXML
    private ImageView blackDice;
    @FXML
    private ImageView orangeDice; //can be null
    @FXML
    private ImageView whiteDice;
    @FXML
    private GridPane characterPlayer;
    @FXML
    private GridPane buildingPlayer;
    @FXML
    private GridPane territoryPlayer;
    @FXML
    private GridPane venturePlayer;
    @FXML
    private GridPane market;
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
    @FXML
    private Button black;
    @FXML
    private Button white;
    @FXML
    private Button orange;
    @FXML
    private Button pass;

    private GameBoard gameboard;

    private Player player;

    private String output;

    @FXML
    private void handleBlackButton()
    {
        output = "black";
    }

    @FXML
    private void handleOrangeButton()
    {
        output = "orange";
    }

    @FXML
    private void handleWhiteButton()
    {
        output = "white";
    }

    @FXML
    private void handlePassButton()
    {
        output = "pass";
        getOutSocket().println(output);
    }
    
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

    private void updateGameBoard()
    {
        updateTowers();

        updateDices();

        updateExcommunication();

        updateButtons();

        updateMarket();

    }

    private void updateMarket()
    {
        for (int i=0; i<gameboard.getMarket().getMarketCells().size(); i++)
        {
            MarketCell currMarketCell = gameboard.getMarket().getMarketCells().get(i);
            ToggleButton toggleButton = (ToggleButton) market.getChildren().get(i);
            if (currMarketCell.getFamilyMember() == null)
            {
            	toggleButton.setOpacity(0);
            }

            //set family member
            //toggleButton.setDisable(true);

        }
    }

    private void updateButtons()
    {
        if (gameboard.getDices().size() < 3)
            orange.setDisable(true);


    }

    private void updateBonusTile() {

        int currNumber = player.getPlayerBoard().getBonusTile().getNumber();
        ClassLoader classLoader = Client.class.getClassLoader();
        String path = "GUI/bonusTile/personalbonustile_" + currNumber + ".png";
        Image image = new Image(classLoader.getResourceAsStream(path));
        bonusTile.setImage(image);
    }

    private void updateCharacters(GridPane characterPlayer, List<CharacterCard> characterZone)
    {
        if (characterZone.size()==0)
            return;

        for (int i=0; i<= characterZone.size(); i++)
        {
            ImageView imageView = (ImageView) characterPlayer.getChildren().get(i);
            int currentCardNumber = characterZone.get(i).getCardNumber();
            ClassLoader classLoader = Client.class.getClassLoader();
            String path = "GUI/Cards/devcards_f_en_c_" + currentCardNumber + ".png";
            Image image = new Image(classLoader.getResourceAsStream(path));
            imageView.setImage(image);
        }
    }

    /*private void updateCharacters(GridPane characterPlayer, List<CharacterCard> characterZone)
    {
        if (characterZone.size()==0)
            return;

        for (int i=0; i<= characterZone.size(); i++)
        {
            ImageView imageView = (ImageView) characterPlayer.getChildren().get(i);
            int currentCardNumber = characterZone.get(i).getCardNumber();
            ClassLoader classLoader = Client.class.getClassLoader();
            String path = "GUI/Cards/devcards_f_en_c_" + currentCardNumber + ".png";
            Image image = new Image(classLoader.getResourceAsStream(path));
            imageView.setImage(image);
        }
    }

    private void updateCharacters(GridPane characterPlayer, List<CharacterCard> characterZone)
    {
        if (characterZone.size()==0)
            return;

        for (int i=0; i<= characterZone.size(); i++)
        {
            ImageView imageView = (ImageView) characterPlayer.getChildren().get(i);
            int currentCardNumber = characterZone.get(i).getCardNumber();
            ClassLoader classLoader = Client.class.getClassLoader();
            String path = "GUI/Cards/devcards_f_en_c_" + currentCardNumber + ".png";
            Image image = new Image(classLoader.getResourceAsStream(path));
            imageView.setImage(image);
        }
    }

    private void updateCharacters(GridPane buildingPlayer, List<BuildingCard> buildingZone)
    {
        if (buildingZone.size()==0)
            return;

        for (int i=0; i<= buildingZone.size(); i++)
        {
            ImageView imageView = (ImageView) buildingPlayer.getChildren().get(i);
            int currentCardNumber = buildingZone.get(i).getCardNumber();
            ClassLoader classLoader = Client.class.getClassLoader();
            String path = "GUI/Cards/devcards_f_en_c_" + currentCardNumber + ".png";
            Image image = new Image(classLoader.getResourceAsStream(path));
            imageView.setImage(image);
        }
    }*/

    /*private void updatePlayerCards()
    {
        updateCharacters(characterPlayer, player.getPlayerBoard().getCharacters());


    }
*/


    private void updateCells(GridPane gridPane, Tower t)
    {
    	for (int i=0; i<=3; i++)
    		{
    			ToggleButton toggleButton = (ToggleButton) gridPane.getChildren().get(i);

    		    if (t.getTowerCells().get(i).getDevelopmentCard() != null)
    		    {

                    int currentCardNumber = t.getTowerCells().get(i).getDevelopmentCard().getCardNumber();
                    ClassLoader classLoader = Client.class.getClassLoader();
                    String path = "GUI/Cards/devcards_f_en_c_" + currentCardNumber + ".png";
                    Image image = new Image(classLoader.getResourceAsStream(path));
                   // BackgroundImage backgroundImage = new BackgroundImage(image, null, null, null, null);
                   // Background background = new Background(backgroundImage);
                    ImageView imageView = new ImageView(image);
                    imageView.setFitHeight(115);
                    imageView.setFitWidth(73);
                    toggleButton.setGraphic(imageView);
                    //toggleButton.setBackground(background);
                }

                if (t.getTowerCells().get(i).getFamilyMember() != null)
                {
                    //todo gestire i family-member images

                }

           //     else
           //     {
            //        toggleButton.setOpacity(0);
           //     }

    		}
    }


    private void updateResources()
    {

        labelCoin.setText("x " + player.getCoins());
        labelFaithPoints.setText("x " + player.getFaithPoints());
        labelMilitaryPoints.setText("x " + player.getMilitaryPoints());
        labelServant.setText("x " + player.getServants());
        labelStone.setText("x " + player.getStones());
        labelVictoryPoints.setText("x " + player.getVictoryPoints());
        labelWood.setText("x " + player.getWoods());
    }

    private void updatePlayerBoard(){

        updateBonusTile();

        updateLeadersHand();

        updateLeadersPlayed();

        updateResources();

        //TODO: updatePlayerCards();

        //todo: implementa il loader delle carte nella pb
        //implementa i familiars
        //implementa le azioni
        //implementa i dialoghi

    }

    private void updateLeadersHand()
    {
        for (int i=0; i < player.getLeaderCards().size(); i++)
        {
            LeaderCard currLeaderCard = player.getLeaderCards().get(i);

            if (currLeaderCard.isPlayed())
            {
                ImageView imageView = (ImageView) leaders.getChildren().get(i);
                ClassLoader classLoader = Client.class.getClassLoader();
                String path = "GUI/leaders/leaders_f_c_0.jpg";
                Image image = new Image(classLoader.getResourceAsStream(path));
                imageView.setImage(image);
            }

            ImageView imageView = (ImageView) leaders.getChildren().get(i);
            int currentLeaderNumber = player.getLeaderCards().get(i).getNumber();
            ClassLoader classLoader = Client.class.getClassLoader();
            String path = "GUI/leaders/leaders_f_c_" + currentLeaderNumber + ".jpg";
            Image image = new Image(classLoader.getResourceAsStream(path));
            imageView.setImage(image);
        }
    }



    private void updateLeadersPlayed()
    {
        if (player.getPlayerBoard().getLeaderCards().size() == 0)
            return;

        for (int i=0; i<player.getPlayerBoard().getLeaderCards().size(); i++)
        {
            LeaderCard currLeaderCard = player.getLeaderCards().get(i);

            if (currLeaderCard.isFaceUp())
            {
                ImageView imageView = (ImageView) leadersPlace.getChildren().get(i);
                int currentLeaderNumber = player.getPlayerBoard().getLeaderCards().get(i).getNumber();
                ClassLoader classLoader = Client.class.getClassLoader();
                String path = "GUI/leaders/leaders_f_c_" + currentLeaderNumber + ".jpg";
                Image image = new Image(classLoader.getResourceAsStream(path));
                imageView.setImage(image);
            }

            ImageView imageView = (ImageView) leadersPlace.getChildren().get(i);
            int currentLeaderNumber = player.getPlayerBoard().getLeaderCards().get(i).getNumber();
            ClassLoader classLoader = Client.class.getClassLoader();
            String path = "GUI/leaders/leaders_f_c_0.jpg";
            Image image = new Image(classLoader.getResourceAsStream(path));
            imageView.setImage(image);
        }
    }

    private void updateExcommunication(){

        for (int i=0; i<=2; i++)
        {
            ImageView imageView = (ImageView) excommunications.getChildren().get(i);
            int currentExcommunicationNumber = gameboard.getExcommunicationCards().get(i).getNumber();
            int currentExcommunicationEra = gameboard.getExcommunicationCards().get(i).getAge();
            ClassLoader classLoader = Client.class.getClassLoader();
            String path = "GUI/excommunications/excomm_" + currentExcommunicationEra +
                    "_" + currentExcommunicationNumber + ".png";
            Image image = new Image(classLoader.getResourceAsStream(path));
            imageView.setImage(image);
        }

    }
    private void updateDices()
    {

        for (int i=0; i < gameboard.getDices().size(); i++)
        {
            int currDiceValue = gameboard.getDices().get(i).getNumber();
            ClassLoader classLoader = Client.class.getClassLoader();
            String path = "GUI/numbers/" + currDiceValue + ".png";
            Image image = new Image(classLoader.getResourceAsStream(path));
            if (i==0)
                blackDice.setImage(image);
            if (i==1)
                whiteDice.setImage(image);
            if (i==2)
                orangeDice.setImage(image);
        }

    }

    @Override
    public void updateScene(Object message)
    {
    	if (message instanceof StartTurnMessage)
    	{
    	    player = Client.getPlayer();
            gameboard = Client.getGameBoard();

    		updateGameBoard();
    		updatePlayerBoard();

    	}

    	if (message instanceof ExecutedAction)
        {
            updateGameBoard();
            updatePlayerBoard();
        }
    }
}
