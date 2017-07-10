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
 * This controller is used to handle the request of the
 * effect "pick tower card". There are two spinners: one for the type
 * of the card to choose and one for the floor where it is.
 * When the type of the card is given the first one is disabled.
 * (Actually the two spinners are on only for the card "Badessa")
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
                "BUILDING", "CHARACTER", "TERRITORY", "VENTURE");

        SpinnerValueFactory valueFactoryFloor = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,4);

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
        Integer tmp = spinnerFloor.getValue();

        tmp--;

        output = spinnerTower.getValue();

        if (onlyTheFloor)
        {
            Client.getGenericState().sendToServer(tmp.toString());
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
