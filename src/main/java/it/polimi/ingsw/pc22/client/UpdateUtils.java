package it.polimi.ingsw.pc22.client;

import it.polimi.ingsw.pc22.gamebox.*;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.List;


public class UpdateUtils {

    public static void updateExcommunication(GameBoard gameboard, GridPane excommunications){

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

    public static void updateDices(GameBoard gameboard, ImageView blackDice, ImageView whiteDice, ImageView orangeDice)
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

    public static void updateCouncilPalace(GameBoard gameboard, AnchorPane gameBoardPane, List<ImageView> councilImageViewList)
    {
        CouncilPalace councilPalace = gameboard.getCouncilPalace();
        int x = 342;
        int delta = 15;
        int width = 15;
        int y = 552;

        gameBoardPane.getChildren().removeAll(councilImageViewList);

        councilImageViewList.clear();

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
                councilImageViewList.add(imageView);

            }
        }

        gameBoardPane.getChildren().addAll(councilImageViewList);

    }

    public static void updateHarvest(GameBoard gameboard, AnchorPane gameBoardPane, List<ImageView> harvestImageViewList, ToggleButton harvestButton)
    {
        Harvest harvest = gameboard.getHarvest();
        int x = 153;
        int delta = 10;
        int width = 15;
        int y = 865;

        gameBoardPane.getChildren().removeAll(harvestImageViewList);

        harvestImageViewList.clear();

        if ((harvest.getHarvestCell()[0].getFamilyMember() != null) && harvest.getHarvestCell()[1].isABlockedCell())
        {
            harvestButton.setDisable(true);
        }

        else
        {
            harvestButton.setDisable(false);
        }

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
                harvestImageViewList.add(imageView);
            }
        }

        gameBoardPane.getChildren().addAll(harvestImageViewList);

    }

    public static void updateProduction(GameBoard gameboard, AnchorPane gameBoardPane, List<ImageView> productionImageViewList, ToggleButton productionButton)
    {
        Production production = gameboard.getProduction();
        int x = 153;
        int delta = 10;
        int width = 15;
        int y = 784;

        gameBoardPane.getChildren().removeAll(productionImageViewList);

        productionImageViewList.clear();

        if ((production.getProductionCell()[0].getFamilyMember() != null) && production.getProductionCell()[1].isABlockedCell())
        {
            productionButton.setDisable(true);
        }

        else
        {
            productionButton.setDisable(false);
        }

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
                productionImageViewList.add(imageView);

            }
        }

        gameBoardPane.getChildren().addAll(productionImageViewList);

    }

    public static void updateMarketHandler(GameBoard gameboard, AnchorPane gameBoardPane,
                                           List<ImageView> marketImageViewList, List<ToggleButton> toggleButtons)
    {

        gameBoardPane.getChildren().removeAll(marketImageViewList);

        marketImageViewList.clear();

        for (int i=0; i < gameboard.getMarket().getMarketCells().size(); i++)
        {
            ImageView imageView = new ImageView();

            MarketCell currMarketCell = gameboard.getMarket().getMarketCells().get(i);

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

                marketImageViewList.add(imageView);

            }

            else
            {
                toggleButtons.get(i).setDisable(false);

                if (currMarketCell.isABlockedCell())
                {
                    toggleButtons.get(i).setDisable(true);
                }
            }


        }

        gameBoardPane.getChildren().addAll(marketImageViewList);

    }

    public static void updateCells(GridPane gridPane, Tower t, ImageView imageZoom, List<ImageView> towerImageViewList)
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

                toggleButton.setOpacity(1);
            }



            if (t.getTowerCells().get(i).getFamilyMember() != null)
            {
                toggleButton.setDisable(true);
                toggleButton.setOnMouseEntered(evt ->imageZoom.setImage(null));
                imageViewFamiliar.setFitHeight(20);
                imageViewFamiliar.setFitWidth(40);
                ColorsEnum currFamiliarEnum = t.getTowerCells().get(i).getFamilyMember().getColor();
                PlayerColorsEnum currPlayerEnum = t.getTowerCells().get(i).getFamilyMember().getPlayerColor();
                //toggleButton.setOpacity(1);
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

                towerImageViewList.add(imageViewFamiliar);

            }

            if (t.getTowerCells().get(i).getDevelopmentCard() == null
                    && t.getTowerCells().get(i).getFamilyMember() != null)
            {
                toggleButton.setOpacity(0);
                toggleButton.setOnMouseEntered(evt ->imageZoom.setImage(null));
            }

            if (t.getTowerCells().get(i).getDevelopmentCard() == null
                    && t.getTowerCells().get(i).getFamilyMember() == null)
            {
                toggleButton.setOpacity(0);
                toggleButton.setOnMouseEntered(evt -> imageZoom.setImage(null));
            }

        }
    }

}
