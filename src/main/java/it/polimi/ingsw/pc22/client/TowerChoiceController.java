package it.polimi.ingsw.pc22.client;

import it.polimi.ingsw.pc22.gamebox.CardTypeEnum;
import it.polimi.ingsw.pc22.messages.ChooseCardMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

/**
 * Created by matteo on 01/07/17.
 */
public class TowerChoiceController implements Controller
{
    @FXML
    private Spinner<String> spinnerTower;
    @FXML
    private Spinner<Integer> spinnerFloor;
    @FXML
    private Stage dialogStage;

    private String output;

    private boolean onlyTheFloor;

    private boolean confirmClicked;


    @Override
    public void updateScene(Object message) {

    }

    public void setDialogStage(Stage dialogStage, Object message)
    {
        this.dialogStage = dialogStage;

        ObservableList<String> typeOfTowers = FXCollections.observableArrayList(
                "BUILDING", "CHARACTER", "TERRITORY", "VENTURES");

        SpinnerValueFactory valueFactoryFloor = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,4);

        SpinnerValueFactory valueFactoryTower = new SpinnerValueFactory.ListSpinnerValueFactory(typeOfTowers);

        spinnerFloor.setValueFactory(valueFactoryFloor);

        spinnerTower.setValueFactory(valueFactoryTower);

        if (!((ChooseCardMessage) message).getCardType().equals(CardTypeEnum.ANY))
        {
            spinnerTower.setDisable(true);
            onlyTheFloor = true;
        }

    }

    @FXML
    private void handleConfirm()
    {
        int tmp = spinnerFloor.getValue();

        tmp--;

        output = spinnerTower.getValue();

        if (onlyTheFloor)
        {
            Client.getGenericState().sendToServer("" + tmp);
        }

        else
        {
            Client.getGenericState().sendToServer(output + " " + tmp);
        }

        confirmClicked = true;

        dialogStage.close();

    }

    public boolean isConfirmClicked() {
        return confirmClicked;
    }
}
