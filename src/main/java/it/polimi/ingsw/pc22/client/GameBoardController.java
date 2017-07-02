package it.polimi.ingsw.pc22.client;

import it.polimi.ingsw.pc22.gamebox.*;
import it.polimi.ingsw.pc22.messages.*;
import it.polimi.ingsw.pc22.player.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.polimi.ingsw.pc22.client.Client.getClassLoader;

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
    private ToggleButton production;
    @FXML
    private ToggleButton harvest;

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
    private Label info;
    @FXML
    private ToggleButton black;
    @FXML
    private ToggleButton white;
    @FXML
    private ToggleButton orange;
    @FXML
    private ToggleButton neuter;
    @FXML
    private ToggleButton market0;

    @FXML
    private ToggleButton market1;

    @FXML
    private ToggleButton market2;

    @FXML
    private ToggleButton market3;

    @FXML
    private Button pass;
    @FXML
    private Button confirm;
    @FXML
    private Spinner<Integer> servantsSpinner;
    @FXML
    private AnchorPane gameBoardPane;
    @FXML
    private ImageView imageZoom;

    private GameBoard gameboard;

    private Player player;

    private String output;

    private String idLeader;

    @FXML
    private ToggleGroup GameBoardButtons;
    @FXML
    private ToggleGroup LeadersHand;
    @FXML
    private ToggleGroup LeadersPlaceToggle;

    private static final Logger LOGGER = Logger.getLogger(GameBoardController.class.getName());

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
    private void handleNeuterButton()
    {
        output = "neuter";
    }

    @FXML
    private void handleConfirm()
    {
    	ToggleButton selectedToggle = (ToggleButton) GameBoardButtons.getSelectedToggle();
    	if (selectedToggle==null || output=="")
		{
    		info.setText("Please complete your action! You must select a familiar member and a gameboard's zone");
    		return;
		}
		String id = selectedToggle.getId();
		String[] idSplitted = id.split(" ");
		switch (idSplitted[0]) {
		case "TOWER":
			Client.getGenericState().sendToServer("set tower " + output.toUpperCase() +
                    " " + servantsSpinner.getValue() + " " + idSplitted[1] + " " + idSplitted[2]);
			break;
		case "MARKET":
			Client.getGenericState().sendToServer("set market " + output.toUpperCase() + " " + servantsSpinner.getValue() + " " + idSplitted[1]);
			break;
		case "COUNCIL":
			Client.getGenericState().sendToServer("set council " + output.toUpperCase() + " " + servantsSpinner.getValue());
			break;
		case "HARVEST":
			Client.getGenericState().sendToServer("set harvest " + output.toUpperCase() + " " + servantsSpinner.getValue());
			break;
		case "PRODUCTION":
			Client.getGenericState().sendToServer("set production " + output.toUpperCase() + " " + servantsSpinner.getValue());
			break;
		default:
			break;
		}
	}
    

    @FXML
    private void handlePlay()
    { 
        ToggleButton selectedLeaderToggle = (ToggleButton) LeadersHand.getSelectedToggle();
        if (selectedLeaderToggle==null)
        	{
        	info.setText("You must select one leader card!");
        	return;
        	}
        idLeader = selectedLeaderToggle.getId();

        Client.getGenericState().sendToServer("play card " + idLeader);

    }

    @FXML
    private void handleDiscard()
    {
        ToggleButton selectedLeaderToggle = (ToggleButton) LeadersHand.getSelectedToggle();
        if (selectedLeaderToggle==null)
    	{
    	info.setText("You must select one leader card!");
    	return;
    	}
        idLeader = selectedLeaderToggle.getId();

        Client.getGenericState().sendToServer("discard card " + idLeader);
    }

    @FXML
    private void handleActivate()
    {
        ToggleButton selectedLeaderToggle = (ToggleButton) LeadersPlaceToggle.getSelectedToggle();
        if (selectedLeaderToggle==null)
    	{
    	info.setText("You must select one leader card!");        	
    	return;
    	}
        String id = selectedLeaderToggle.getId();

        Client.getGenericState().sendToServer("activate card " + id);
    }

    @FXML
    private void handlePassButton()
    {
        output = "pass";
        Client.getGenericState().sendToServer(output);
    }

    @FXML
    private void handleExitButton()
    {

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

        updateProduction();

        updateHarvest();

        updateCouncilPalace();
    }

    private void updateCouncilPalace()
    {
        CouncilPalace councilPalace = gameboard.getCouncilPalace();
        int x = 342;
        int delta = 15;
        int width = 15;
        int y = 552;

        for (int i = 0; i < councilPalace.getCouncilPalaceCells().length; i++)
        {

            ImageView imageView = new ImageView();
            imageView.setX(x + width + i * delta);
            imageView.setY(y);

            if (councilPalace.getCouncilPalaceCells()[i].getFamilyMember() != null)
            {
                ColorsEnum currFamiliarEnum = councilPalace.getCouncilPalaceCells()[i].getFamilyMember().getColor();
                PlayerColorsEnum currPlayerEnum = councilPalace.getCouncilPalaceCells()[i].getFamilyMember().getPlayerColor();
                ClassLoader classLoader = Client.class.getClassLoader();
                String path = "GUI/familiars/familiar_" + currPlayerEnum.toString().toLowerCase() + "_" + currFamiliarEnum.toString().toLowerCase() + ".png";
                Image image = new Image(classLoader.getResourceAsStream(path));
                imageView.setImage(image);
                imageView.setFitHeight(8.75);
                imageView.setFitWidth(13);
                gameBoardPane.getChildren().add(imageView);

            }
            else
            {
            	//imageView.setImage(null);
                gameBoardPane.getChildren().remove(imageView);
            }
        }
    }


    private void updateHarvest()
    {
        Harvest harvest = gameboard.getHarvest();
        int x = 153;
        int delta = 10;
        int width = 15;
        int y = 865;
        for (int i = 0; i < harvest.getHarvestCell().length; i++) {
            ImageView imageView = new ImageView();
            if (i == 0) {
                imageView.setX(62);
                imageView.setY(865);
            }
            else {
                imageView.setX(x + width + i * delta);
                imageView.setY(y);
            }

            if (harvest.getHarvestCell()[i].getFamilyMember() != null)
            {
                ColorsEnum currFamiliarEnum = harvest.getHarvestCell()[i].getFamilyMember().getColor();
                PlayerColorsEnum currPlayerEnum = harvest.getHarvestCell()[i].getFamilyMember().getPlayerColor();
                ClassLoader classLoader = Client.class.getClassLoader();
                String path = "GUI/familiars/familiar_" + currPlayerEnum.toString().toLowerCase() + "_" + currFamiliarEnum.toString().toLowerCase() + ".png";
                Image image = new Image(classLoader.getResourceAsStream(path));
                imageView.setImage(image);
                imageView.setFitHeight(8.75);
                imageView.setFitWidth(13);
                gameBoardPane.getChildren().add(imageView);
                checkHarvestButton();

            }
            else
            {
                gameBoardPane.getChildren().remove(imageView);
                checkHarvestButton();
            }
            
        }

    }

    private void checkProductionButton()
    {
        if (gameboard.getProduction().getProductionCell()[0].getFamilyMember() != null
                && gameboard.getProduction().getProductionCell()[1].isABlockedCell())
        {
            production.setDisable(true);
        }

        else
        {
            production.setDisable(false);
        }
    }

    private void checkHarvestButton()
    {
        if (gameboard.getHarvest().getHarvestCell()[0].getFamilyMember() != null
                && gameboard.getHarvest().getHarvestCell()[1].isABlockedCell())
        {
            harvest.setDisable(true);
        }

        else
        {
            harvest.setDisable(false);
        }

    }

    private void updateProduction()
    {
        Production production = gameboard.getProduction();
        int x = 153;
        int delta = 10;
        int width = 15;
        int y = 784;

        for (int i = 0; i < production.getProductionCell().length; i++) {
            ImageView imageView = new ImageView();
            if (i == 0) {
                imageView.setX(62);
                imageView.setY(784);
            }
            else {
                imageView.setX(x + width + i * delta);
                imageView.setY(y);
            }

            if (production.getProductionCell()[i].getFamilyMember() != null)
            {
                ColorsEnum currFamiliarEnum = production.getProductionCell()[i].getFamilyMember().getColor();
                PlayerColorsEnum currPlayerEnum = production.getProductionCell()[i].getFamilyMember().getPlayerColor();
                ClassLoader classLoader = Client.class.getClassLoader();
                String path = "GUI/familiars/familiar_" + currPlayerEnum.toString().toLowerCase() + "_" + currFamiliarEnum.toString().toLowerCase() + ".png";
                Image image = new Image(classLoader.getResourceAsStream(path));
                imageView.setImage(image);
                imageView.setFitHeight(8.75);
                imageView.setFitWidth(13);
                gameBoardPane.getChildren().add(imageView);
                checkProductionButton();

            }
            else
            {
                //imageView.setImage(null);
                gameBoardPane.getChildren().remove(imageView);
                checkProductionButton();
            }
        }
    }


    private void updateMarket()
    {

        for (int i=0; i < gameboard.getMarket().getMarketCells().size(); i++)
        {
            ImageView imageView = new ImageView();

            MarketCell currMarketCell = gameboard.getMarket().getMarketCells().get(i);

            List<ToggleButton> toggleButtons = new ArrayList<>();

            toggleButtons.add(market0);
            toggleButtons.add(market1);
            toggleButtons.add(market2);
            toggleButtons.add(market3);

            if (gameboard.getMarket().getMarketCells().get(i).getFamilyMember()!=null)
            {
                toggleButtons.get(i).setDisable(true);
                ColorsEnum currFamiliarEnum = currMarketCell.getFamilyMember().getColor();
                PlayerColorsEnum currPlayerEnum = currMarketCell.getFamilyMember().getPlayerColor();
                String familiarEnum = currFamiliarEnum.toString();
                String playerEnum = currPlayerEnum.toString();
                ClassLoader classLoader = Client.class.getClassLoader();
                String path = "GUI/familiars/familiar_" + playerEnum.toLowerCase() +"_" + familiarEnum.toLowerCase() +".png";
                Image image = new Image(classLoader.getResourceAsStream(path));
                imageView.setFitHeight(8.75);
                imageView.setFitWidth(13);

                if (i==0)
                {
                    imageView.setX(365);
                    imageView.setY(769);
                }
                if (i==1)
                {
                    imageView.setX(425);
                    imageView.setY(766);
                }
                if (i==2)
                {
                    imageView.setX(480);
                    imageView.setY(784);
                }
                if (i==3)
                {
                    imageView.setX(523);
                    imageView.setY(823);
                }

                imageView.setImage(image);

                gameBoardPane.getChildren().add(imageView);
            }


            if (gameboard.getMarket().getMarketCells().get(i).getFamilyMember()==null)
            {
                //imageViews.get(i).setImage(null);
                gameBoardPane.getChildren().remove(imageView);
                toggleButtons.get(i).setDisable(false);
            }

            if (gameboard.getMarket().getMarketCells().get(i).isABlockedCell())
            {
                toggleButtons.get(i).setDisable(true);
            }
        }



    }

    private void updateButtons()
    {
    	output = "";
    	
        if (gameboard.getDices().size() < 3)
            orange.setDisable(true);

        for (FamilyMember f : player.getFamilyMembers())
        {
            if (f.isPlayed())
            {
                if (f.getColor().equals(ColorsEnum.NEUTER))
                {
                    neuter.setDisable(true);
                }
                if (f.getColor().equals(ColorsEnum.BLACK))
                {
                    black.setDisable(true);
                }
                if (f.getColor().equals(ColorsEnum.ORANGE))
                {
                    orange.setDisable(true);
                }
                if (f.getColor().equals(ColorsEnum.WHITE))
                {
                    white.setDisable(true);
                }
            }
        }
        
        for (int i=0; i<4; i++)
        {

        	if (gameboard.getTowers()[0].getTowerCells().get(i)==null)
				{
					ToggleButton currToggle = (ToggleButton) territoryTower.getChildren().get(i);
					currToggle.setBackground(null);
					currToggle.setDisable(false);
				}
			if (gameboard.getTowers()[1].getTowerCells().get(i)==null)
				{
					ToggleButton currToggle = (ToggleButton) characterTower.getChildren().get(i);
					currToggle.setBackground(null);
					currToggle.setDisable(false);
				}
			if (gameboard.getTowers()[2].getTowerCells().get(i)==null)
			{
				ToggleButton currToggle = (ToggleButton) buildingTower.getChildren().get(i);
				currToggle.setBackground(null);
				currToggle.setDisable(false);
			}
			if (gameboard.getTowers()[3].getTowerCells().get(i)==null)
			{
				ToggleButton currToggle = (ToggleButton) ventureTower.getChildren().get(i);
				currToggle.setBackground(null);
				currToggle.setDisable(false);
			}
        }
    }

    private void updateBonusTile()
    {
        int currNumber = player.getPlayerBoard().getBonusTile().getNumber();
        ClassLoader classLoader = Client.class.getClassLoader();
        String path = "GUI/bonusTile/personalbonustile_" + currNumber + ".png";
        Image image = new Image(classLoader.getResourceAsStream(path));
        bonusTile.setImage(image);
    }

    private void updateCells(GridPane gridPane, Tower t)
    {

        int x1=439, x2=300, x3=181, x4=55;
        int y = 447;
        int shift = 126;



    	for (int i=0; i<=3; i++)
    		{
    			ToggleButton toggleButton = (ToggleButton) gridPane.getChildren().get(3-i);
                ImageView imageViewFamiliar = new ImageView();

    		    if (t.getTowerCells().get(i).getDevelopmentCard() != null)
    		    {
                    int currentCardNumber = t.getTowerCells().get(i).getDevelopmentCard().getCardNumber();
                    ClassLoader classLoader = Client.class.getClassLoader();
                    String path = "GUI/Cards/devcards_f_en_c_" + currentCardNumber + ".png";
                    Image image = new Image(classLoader.getResourceAsStream(path));
                    ImageView imageView = new ImageView(image);
                    imageView.setFitHeight(115);
                    imageView.setFitWidth(73);
                    toggleButton.setGraphic(imageView);
                    BackgroundSize backgroundSize = new BackgroundSize(73, 115, false, false, true, false);
                    BackgroundImage backgroundImage = new BackgroundImage(image, null, null, null, backgroundSize);
                    Background background = new Background(backgroundImage);
                    toggleButton.setBackground(background);
                    toggleButton.setOnMouseEntered(evt ->imageZoom.setImage(image));
                    toggleButton.setOnMouseExited(evt ->imageZoom.setImage(null));
                    toggleButton.setDisable(false);
                }


                if (t.getTowerCells().get(i).getFamilyMember() != null)
                {
                    toggleButton.setDisable(true);
                    toggleButton.setOnMouseEntered(evt ->imageZoom.setImage(null));
                    imageViewFamiliar.setFitHeight(20);
                    imageViewFamiliar.setFitWidth(40);
                    ColorsEnum currFamiliarEnum = t.getTowerCells().get(i).getFamilyMember().getColor();
                    PlayerColorsEnum currPlayerEnum = t.getTowerCells().get(i).getFamilyMember().getPlayerColor();
                    toggleButton.setOpacity(1);
                    ClassLoader classLoader = Client.class.getClassLoader();
                    String familiarEnum = currFamiliarEnum.toString();
                    String playerEnum = currPlayerEnum.toString();
                    String path = "GUI/familiars/familiar_" + playerEnum.toLowerCase() + "_" + familiarEnum.toLowerCase() +".png";
                    Image image = new Image(classLoader.getResourceAsStream(path));
                    if (t.getTowerType().equals(CardTypeEnum.VENTURE))
                    {
                        imageViewFamiliar.setX(x1);
                    }
                    if (t.getTowerType().equals(CardTypeEnum.CHARACTER))
                    {
                        imageViewFamiliar.setX(x3);
                    }
                    if (t.getTowerType().equals(CardTypeEnum.BUILDING))
                    {
                        imageViewFamiliar.setX(x2);
                    }
                    if (t.getTowerType().equals(CardTypeEnum.TERRITORY))
                    {
                        imageViewFamiliar.setX(x4);
                    }

                    imageViewFamiliar.setY(y - i*shift);
                    imageViewFamiliar.setImage(image);

                    gameBoardPane.getChildren().add(imageViewFamiliar);

                }

                if (t.getTowerCells().get(i).getDevelopmentCard() == null
                        && t.getTowerCells().get(i).getFamilyMember() != null)
                {
                    toggleButton.setOpacity(0);
                    toggleButton.setOnMouseEntered(evt ->imageZoom.setImage(null));
                }

                if (t.getTowerCells().get(i).getFamilyMember() == null)
                {
                    gameBoardPane.getChildren().remove(imageViewFamiliar);
                }
    		}
    }


    private void updateResources()
    {
        labelCoin.setText("x " + player.getCoins());
        labelFaithPoints.setText("x " + player.getFaithPoints());
        labelMilitaryPoints.setText("x " + player.getMilitaryPoints());
        labelServant.setText("x " + player.getServants());
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, player.getServants());
        servantsSpinner.setValueFactory(valueFactory);
        labelStone.setText("x " + player.getStones());
        labelVictoryPoints.setText("x " + player.getVictoryPoints());
        labelWood.setText("x " + player.getWoods());
    }

    private void updatePlayerBoard(){

        updateBonusTile();

        updateLeadersHand();

        updateLeadersPlayed();

        updateResources();

        updatePlayerCards();

    }

    private void updatePlayerCards(){

        updatePlayerCharacters();

        updatePlayerVentures();

        updatePlayerBuildings();

        updatePlayerTerritories();

    }

    private void updatePlayerCharacters()
    {
        if (player.getPlayerBoard().getCharacters().size() == 0)
            return;

        for (int i=0; i<player.getPlayerBoard().getCharacters().size(); i++)
        {
            ImageView imageView = (ImageView) characterPlayer.getChildren().get(i);
            int currentCardNumber = player.getPlayerBoard().getCharacters().get(i).getCardNumber();
            ClassLoader classLoader = Client.class.getClassLoader();
            String path = "GUI/Cards/devcards_f_en_c_" + currentCardNumber + ".png";
            Image image = new Image(classLoader.getResourceAsStream(path));
            imageView.setImage(image);
            imageView.setFitHeight(115);
            imageView.setFitWidth(73);
        }
    }

    private void updatePlayerVentures()
    {
        if (player.getPlayerBoard().getVentures().size()==0)
            return;

        for (int i=0; i< player.getPlayerBoard().getVentures().size(); i++)
        {
            ImageView imageView = (ImageView) venturePlayer.getChildren().get(i);
            int currentCardNumber = player.getPlayerBoard().getVentures().get(i).getCardNumber();
            ClassLoader classLoader = Client.class.getClassLoader();
            String path = "GUI/Cards/devcards_f_en_c_" + currentCardNumber + ".png";
            Image image = new Image(classLoader.getResourceAsStream(path));
            imageView.setImage(image);
            imageView.setFitHeight(115);
            imageView.setFitWidth(73);
        }
    }

    private void updatePlayerBuildings()
    {
        if (player.getPlayerBoard().getBuildings().size()==0)
            return;

        for (int i=0; i< player.getPlayerBoard().getBuildings().size(); i++)
        {
            ImageView imageView = (ImageView) buildingPlayer.getChildren().get(i);
            int currentCardNumber = player.getPlayerBoard().getBuildings().get(i).getCardNumber();
            ClassLoader classLoader = Client.class.getClassLoader();
            String path = "GUI/Cards/devcards_f_en_c_" + currentCardNumber + ".png";
            Image image = new Image(classLoader.getResourceAsStream(path));
            imageView.setImage(image);
            imageView.setFitHeight(115);
            imageView.setFitWidth(73);
        }
    }

    private void updatePlayerTerritories()
    {
        if (player.getPlayerBoard().getTerritories().size()==0)
            return;

        for (int i=0; i< player.getPlayerBoard().getTerritories().size(); i++)
        {
            ImageView imageView = (ImageView) territoryPlayer.getChildren().get(i);
            int currentCardNumber = player.getPlayerBoard().getTerritories().get(i).getCardNumber();
            ClassLoader classLoader = Client.class.getClassLoader();
            String path = "GUI/Cards/devcards_f_en_c_" + currentCardNumber + ".png";
            Image image = new Image(classLoader.getResourceAsStream(path));
            imageView.setImage(image);
            imageView.setFitHeight(115);
            imageView.setFitWidth(73);
        }
    }

    private void updateLeadersHand()
    {
        for (int i=0; i < player.getLeaderCards().size(); i++)
        {
            LeaderCard currLeaderCard = player.getLeaderCards().get(i);
            ToggleButton toggleButton = (ToggleButton) leaders.getChildren().get(i);

            if (currLeaderCard == null)
            {
                ClassLoader classLoader = Client.class.getClassLoader();
                String path = "GUI/leaders/leaders_b_c_0.jpg";
                Image image = new Image(classLoader.getResourceAsStream(path));
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(115);
                imageView.setFitWidth(73);
                toggleButton.setGraphic(imageView);
                toggleButton.setDisable(true);
                continue;
            }

            int currentLeaderNumber = currLeaderCard.getNumber();
            ClassLoader classLoader = Client.class.getClassLoader();
            String path = "GUI/leaders/leaders_f_c_" + currentLeaderNumber + ".jpg";
            Image image = new Image(classLoader.getResourceAsStream(path));
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(115);
            imageView.setFitWidth(73);

            toggleButton.setGraphic(imageView);
            toggleButton.setDisable(false);
        }
    }



    private void updateLeadersPlayed()
    {
        if (player.getPlayerBoard().getLeaderCards().size() == 0)
            return;

        for (int i=0; i<player.getPlayerBoard().getLeaderCards().size(); i++)
        {
            LeaderCard currLeaderCard = player.getLeaderCards().get(i);
            ToggleButton toggleButton = (ToggleButton) leadersPlace.getChildren().get(i);

            if (currLeaderCard.isFaceUp())
            {
                int currentLeaderNumber = player.getPlayerBoard().getLeaderCards().get(i).getNumber();
                ClassLoader classLoader = Client.class.getClassLoader();
                String path = "GUI/leaders/leaders_f_c_" + currentLeaderNumber + ".jpg";
                Image image = new Image(classLoader.getResourceAsStream(path));
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(115);
                imageView.setFitWidth(73);
                toggleButton.setOpacity(1);
                toggleButton.setGraphic(imageView);
                toggleButton.setDisable(false);
            }

            ClassLoader classLoader = Client.class.getClassLoader();
            String path = "GUI/leaders/leaders_f_c_0.jpg";
            Image image = new Image(classLoader.getResourceAsStream(path));
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(115);
            imageView.setFitWidth(73);
            toggleButton.setOpacity(1);
            toggleButton.setGraphic(imageView);
            toggleButton.setDisable(true);
        }
    }

    private void updateExcommunication(){

        for (int i=0; i < gameboard.getExcommunicationCards().size(); i++)
        {
            ImageView imageView = (ImageView) excommunications.getChildren().get(i);
            int currentExcommunicationNumber = gameboard.getExcommunicationCards().get(i).getNumber();
            int currentExcommunicationEra = gameboard.getExcommunicationCards().get(i).getAge();
            ClassLoader classLoader = Client.class.getClassLoader();
            String path = "GUI/excommunications/excomm_" + currentExcommunicationEra +
                    "_" + currentExcommunicationNumber + ".png";
            Image image = new Image(classLoader.getResourceAsStream(path));
            imageView.setFitHeight(91);
            imageView.setFitWidth(49);
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

    public boolean councilPrivilegeDialog(Object message) {
        try {

            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(getClassLoader().getResource("GUI/Privileges.fxml"));

            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();

            dialogStage.setTitle("Privilege");

            dialogStage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(page);

            dialogStage.setScene(scene);

            PrivilegeDialogController controller = loader.getController();

            controller.setDialogStage(dialogStage, message);

            dialogStage.showAndWait();

            return controller.isConfirmClicked();

        }
            catch (IOException e)
        {
            LOGGER.log(Level.INFO, "ERROR RECEIVE THREAD", e);

            return false;
        }
    }

    public boolean servantsDialog(Object message) {
        try {

            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(getClassLoader().getResource("GUI/ServantHandler.fxml"));

            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();

            dialogStage.setTitle("Choose servants");

            dialogStage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(page);

            dialogStage.setScene(scene);

            ServantsHandlerController controller = loader.getController();

            controller.setDialogStage(dialogStage, ((ChooseServantsMessage) message).getPlayer());

            dialogStage.showAndWait();

            return controller.isConfirmClicked();

        } catch (IOException e)
        {
            LOGGER.log(Level.INFO, "ERROR RECEIVE THREAD", e);

            return false;
        }

    }

    public boolean choiceCardDialog(Object message)

    {
        try {

            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(getClassLoader().getResource("GUI/TowerChoice.fxml"));

            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();

            dialogStage.setTitle("Choices");

            dialogStage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(page);

            dialogStage.setScene(scene);

            PrivilegeDialogController controller = loader.getController();

            controller.setDialogStage(dialogStage, message);

            dialogStage.showAndWait();

            return controller.isConfirmClicked();

        } catch (IOException e)
        {
            LOGGER.log(Level.INFO, "ERROR RECEIVE THREAD", e);

            return false;
        }

    }

    public boolean choiceCostAssetDialog(Object message) {

        try {

            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(getClassLoader().getResource("GUI/ChoiceCost&Assets.fxml"));

            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();

            dialogStage.setTitle("Choices");

            dialogStage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(page);

            dialogStage.setScene(scene);

            PrivilegeDialogController controller = loader.getController();

            controller.setDialogStage(dialogStage, message);

            dialogStage.showAndWait();

            return controller.isConfirmClicked();

        } catch (IOException e)
        {
            LOGGER.log(Level.INFO, "ERROR RECEIVE THREAD", e);

            return false;
        }
    }


    public boolean excommunicationDialog (Object message)
    {
        try {

            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(getClassLoader().getResource("GUI/ExcommunicationChoice.fxml"));

            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();

            dialogStage.setTitle("Choices");

            dialogStage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(page);

            dialogStage.setScene(scene);

            ExcommunicationController controller = loader.getController();

            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();

            return controller.isConfirmClicked();

        } catch (IOException e)
        {
            LOGGER.log(Level.INFO, "ERROR RECEIVE THREAD", e);

            return false;
        }
    }

    public boolean endMatchDialog(Object message)
    {

        try {

            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(getClassLoader().getResource("GUI/EndGame.fxml"));

            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();

            dialogStage.setTitle("Choices");

            dialogStage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(page);

            dialogStage.setScene(scene);

            EndMatchController controller = loader.getController();

            controller.setDialogStage(dialogStage, message);

            dialogStage.showAndWait();

            return controller.isConfirmClicked();

        } catch (IOException e)
        {
            LOGGER.log(Level.INFO, "ERROR RECEIVE THREAD", e);

            return false;
        }

    }

    @Override
    public void updateScene(Object message)
    {
    	if (message instanceof CommunicationMessage)
    	{
    		info.setText(((CommunicationMessage) message).getMessage());
    	}

    	if (message instanceof GameStatusMessage)
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

        if (message instanceof PickPrivilegeMessage)
        {
            councilPrivilegeDialog(message);

            updatePlayerBoard();
        }
        if (message instanceof ChooseAssetsMessage)
        {
            choiceCostAssetDialog(message);

            updatePlayerBoard();

            updateGameBoard();
        }

        if (message instanceof ChooseCardMessage)
        {
            choiceCardDialog(message);

            updateGameBoard();

            updatePlayerBoard();
        }

        if (message instanceof ChooseCostsMessage)
        {
            choiceCostAssetDialog(message);

            updatePlayerBoard();

            updateGameBoard();
        }

        if (message instanceof ChooseServantsMessage)
        {
            servantsDialog(message);

            updatePlayerBoard();

        }

        if (message instanceof ErrorMessage)
        {
        	System.out.println("Ciao l'ho ricevuto!");
            info.setText(((ErrorMessage) message).getMessage());
        }

        if (message instanceof ExcommunicationMessage)
        {
            excommunicationDialog(message);

            updatePlayerBoard();
        }

        if (message instanceof EndMatchMessage)
        {
            endMatchDialog(message);
        }

    }

}
