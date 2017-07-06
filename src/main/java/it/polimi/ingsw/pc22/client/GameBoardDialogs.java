package it.polimi.ingsw.pc22.client;


import it.polimi.ingsw.pc22.messages.ChooseServantsMessage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.polimi.ingsw.pc22.client.Client.getClassLoader;

public class GameBoardDialogs {


    private static Stage dialogStage;

    private static final Logger LOGGER = Logger.getLogger(GameBoardDialogs.class.getName());

    public static boolean councilPrivilegeDialog(Object message) {
        try {

            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(getClassLoader().getResource("GUI/Privileges.fxml"));

            AnchorPane page = (AnchorPane) loader.load();

            dialogStage = new Stage();

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

    public static boolean chooseFamiliarDialog(int size) {

        try {

            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(getClassLoader().getResource("GUI/FamilyMemberDialog.fxml"));

            AnchorPane page = (AnchorPane) loader.load();

            dialogStage = new Stage();

            dialogStage.setTitle("Choices");

            dialogStage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(page);

            dialogStage.setScene(scene);

            ChooseFamiliarController controller = loader.getController();

            controller.setDialogStage(dialogStage, size);

            dialogStage.showAndWait();

            return controller.isConfirmClicked();

        } catch (IOException e) {
            LOGGER.log(Level.INFO, "ERROR RECEIVE THREAD", e);

            return false;
        }

    }

    public static boolean endMatchDialog(Object message)
    {

        try {

            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(getClassLoader().getResource("GUI/EndGame.fxml"));

            AnchorPane page = (AnchorPane) loader.load();

            dialogStage = new Stage();

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

    public static boolean excommunicationDialog (Object message)
    {
        try {

            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(getClassLoader().getResource("GUI/ExcommunicationChoice.fxml"));

            AnchorPane page = (AnchorPane) loader.load();

            dialogStage = new Stage();

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

    public static boolean servantsDialog(Object message) {
        try {

            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(getClassLoader().getResource("GUI/ServantHandler.fxml"));

            AnchorPane page = (AnchorPane) loader.load();

            dialogStage = new Stage();

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

    public static boolean choiceCardDialog(Object message)

    {
        try {

            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(getClassLoader().getResource("GUI/TowerChoice.fxml"));

            AnchorPane page = (AnchorPane) loader.load();

            dialogStage = new Stage();

            dialogStage.setTitle("Choices");

            dialogStage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(page);

            dialogStage.setScene(scene);

            TowerChoiceController controller = loader.getController();

            controller.setDialogStage(dialogStage, message);

            dialogStage.showAndWait();

            return controller.isConfirmClicked();

        } catch (IOException e)
        {
            LOGGER.log(Level.INFO, "ERROR RECEIVE THREAD", e);

            return false;
        }

    }

    public static boolean choiceCostAssetDialog(Object message) {

        try {

            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(getClassLoader().getResource("GUI/ChoiceCost&Assets.fxml"));

            AnchorPane page = (AnchorPane) loader.load();

            dialogStage = new Stage();

            dialogStage.setTitle("Choices");

            dialogStage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(page);

            dialogStage.setScene(scene);

            ChoiceCostAssetController controller = loader.getController();

            controller.setDialogStage(dialogStage, message);

            dialogStage.showAndWait();

            return controller.isConfirmClicked();

        } catch (IOException e)
        {
            LOGGER.log(Level.INFO, "ERROR RECEIVE THREAD", e);

            return false;
        }
    }

}
