package it.polimi.ingsw.pc22.client;


import it.polimi.ingsw.pc22.gamebox.LeaderCard;
import it.polimi.ingsw.pc22.player.Player;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * This class contains all the functions to update the resources
 * associated to the player in right way.
 * All the cards (even the leaders), all the points and all his/her assets.
 */

public class UpdatePlayer {

    public static void updatePlayerCharacters(Player player, GridPane characterPlayer)
    {
        if (player.getPlayerBoard().getCharacters().isEmpty())
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

    public static void updatePlayerVentures(Player player, GridPane venturePlayer)
    {
        if (player.getPlayerBoard().getVentures().isEmpty())
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

    public static void updatePlayerBuildings(Player player, GridPane buildingPlayer)
    {
        if (player.getPlayerBoard().getBuildings().isEmpty())
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

    public static void updatePlayerTerritories(Player player, GridPane territoryPlayer)
    {
        if (player.getPlayerBoard().getTerritories().isEmpty())
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

    public static void updateLeadersPlayed(Player player, GridPane leadersPlace)
    {
        if (player.getPlayerBoard().getLeaderCards().isEmpty())
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

    public static void updateLeadersHand(Player player, GridPane leaders)
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
}
