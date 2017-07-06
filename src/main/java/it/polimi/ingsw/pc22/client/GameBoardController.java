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
import static it.polimi.ingsw.pc22.client.UpdateUtils.updateCells;


public class GameBoardController implements Controller
{
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
    private ToggleButton productionButton;
    @FXML
    private ToggleButton harvestButton;
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
    private TextArea info;
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
    private ImageView imageViewMarket0;
    @FXML
    private ToggleButton market1;
    @FXML
    private ImageView imageViewMarket1;
    @FXML
    private ToggleButton market2;
    @FXML
    private ImageView imageViewMarket2;
    @FXML
    private ToggleButton market3;
    @FXML
    private ImageView imageViewMarket3;
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
    
    private Stage dialogStage;
    
    private ArrayList<ImageView> councilImageViewList = new ArrayList<ImageView>();

    private ArrayList<ImageView> harvestImageViewList = new ArrayList<ImageView>();
    
    private ArrayList<ImageView> productionImageViewList = new ArrayList<ImageView>();

    private ArrayList<ImageView> marketImageViewList = new ArrayList<>();

    private ArrayList<ImageView> towerImageViewList = new ArrayList<ImageView>();

    private ArrayList<ToggleButton> towersButton = new ArrayList<>();

    private final Logger LOGGER = Logger.getLogger(GameBoardController.class.getName());


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
    		info.appendText("Please complete your action! You must select a familiar member and a gameboard's zone\n");
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
        	info.appendText("You must select one leader card!\n" );
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
    	info.appendText("You must select one leader card!\n");
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
    	info.appendText("You must select one leader card!\n");        	
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


    private void updateTowers()
    {
    	Tower[] towers = gameboard.getTowers();
    	
    	gameBoardPane.getChildren().removeAll(towerImageViewList);
    	
    	towerImageViewList.clear();

    	for (Tower t : towers)
    		switch (t.getTowerType()) {
			case TERRITORY:
				updateCells(territoryTower, t, imageZoom, towerImageViewList);
				break;
			case BUILDING:
				updateCells(buildingTower, t, imageZoom, towerImageViewList);
				break;
			case VENTURE:
				updateCells(ventureTower, t, imageZoom, towerImageViewList);
				break;
			case CHARACTER:
				updateCells(characterTower, t, imageZoom, towerImageViewList);
				break;
			default:
				break;
			}
    	
        gameBoardPane.getChildren().addAll(towerImageViewList);
    }

    private void updateGameBoard()
    {
        updateTowers();

        UpdateUtils.updateDices(gameboard, blackDice, whiteDice, orangeDice);

        UpdateUtils.updateExcommunication(gameboard, excommunications);

        updateButtons();

        updateMarket();

        UpdateUtils.updateProduction(gameboard, gameBoardPane, productionImageViewList, productionButton);

        UpdateUtils.updateHarvest(gameboard, gameBoardPane, harvestImageViewList, harvestButton);

        UpdateUtils.updateCouncilPalace(gameboard, gameBoardPane, councilImageViewList);
    }

    private void updateMarket()
    {
        List<ToggleButton> toggleButtons = new ArrayList<>();

        toggleButtons.add(market0);
        toggleButtons.add(market1);
        toggleButtons.add(market2);
        toggleButtons.add(market3);

        UpdateUtils.updateMarketHandler(gameboard, gameBoardPane, marketImageViewList, toggleButtons);
    }

    private void updateButtons()
    {
    	output = "";
    	
        if (gameboard.getDices().size() < 3)
            orange.setDisable(true);

        for (FamilyMember f : player.getFamilyMembers())
        {
        	switch (f.getColor()) {
			case NEUTER:
				if (f.isPlayed())       
					neuter.setDisable(true);
				else 
					neuter.setDisable(false);
				break;
			case BLACK:
				if (f.isPlayed())       
					black.setDisable(true);
				else 
					black.setDisable(false);
				break;
			case ORANGE:
				if (f.isPlayed())       
					orange.setDisable(true);
				else 
					orange.setDisable(false);
				break;
			case WHITE:
				if (f.isPlayed())       
					white.setDisable(true);
				else 
					white.setDisable(false);
				break;
				
			default:
				break;
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

        UpdatePlayer.updateLeadersHand(player, leaders);

        UpdatePlayer.updateLeadersPlayed(player, leadersPlace);

        updateResources();

        updatePlayerCards();

    }

    private void updatePlayerCards(){

        UpdatePlayer.updatePlayerCharacters(player, characterPlayer);

        UpdatePlayer.updatePlayerVentures(player, venturePlayer);

        UpdatePlayer.updatePlayerBuildings(player, buildingPlayer);

        UpdatePlayer.updatePlayerTerritories(player, territoryPlayer);

    }


    @Override
    public void updateScene(Object message)
    {
    	if (message instanceof CommunicationMessage)
    	{
    		info.appendText(((CommunicationMessage) message).getMessage()+"\n");
    	}

    	if (message instanceof GameStatusMessage)
    	{
    		GameStatusMessage gameStatusMessage = (GameStatusMessage) message;
            if ("finished".equals(gameStatusMessage.getState()))
            {
            	if (dialogStage!=null)
            		dialogStage.close();
            }

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
            GameBoardDialogs.councilPrivilegeDialog(message);

            updatePlayerBoard();
        }
        if (message instanceof ChooseAssetsMessage)
        {
            GameBoardDialogs.choiceCostAssetDialog(message);

            updatePlayerBoard();

            updateGameBoard();
        }

        if (message instanceof ChooseCardMessage)
        {
            GameBoardDialogs.choiceCardDialog(message);

            updateGameBoard();

            updatePlayerBoard();
        }

        if (message instanceof ChooseCostsMessage)
        {
            GameBoardDialogs.choiceCostAssetDialog(message);

            updatePlayerBoard();

            updateGameBoard();
        }

        if (message instanceof ChooseServantsMessage)
        {
            GameBoardDialogs.servantsDialog(message);

            updatePlayerBoard();

        }

        if (message instanceof ErrorMessage)
        {
            info.appendText(((ErrorMessage) message).getMessage()+"\n");
        }

        if (message instanceof ExcommunicationMessage)
        {
            GameBoardDialogs.excommunicationDialog(message);

            updatePlayerBoard();
        }

        if (message instanceof ChooseFamiliarMessage)
        {
            GameBoardDialogs.chooseFamiliarDialog(gameboard.getDices().size());

            updateGameBoard();

            updatePlayerBoard();
        }

        if (message instanceof EndMatchMessage)
        {
            GameBoardDialogs.endMatchDialog(message);
        }

    }

}
