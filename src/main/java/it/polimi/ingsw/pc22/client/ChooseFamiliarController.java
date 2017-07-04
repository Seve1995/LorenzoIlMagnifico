package it.polimi.ingsw.pc22.client;


import it.polimi.ingsw.pc22.gamebox.CardTypeEnum;
import it.polimi.ingsw.pc22.messages.ChooseCardMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

public class ChooseFamiliarController implements Controller {

    private Stage dialogStage;

    @FXML
    private Spinner familiarSpinner;

    private boolean confirmClicked;

    public boolean isConfirmClicked() {
        return confirmClicked;
    }

    @Override
    public void updateScene(Object message) {

    }

    public void setDialogStage(Stage dialogStage, int size)
    {
        this.dialogStage = dialogStage;

        ObservableList<String> typeOfFamiliars = FXCollections.observableArrayList(
                "NEUTER", "BLACK", "WHITE");

        if (size == 3)
        {
            typeOfFamiliars.add("ORANGE");
        }

        SpinnerValueFactory valueFactoryFamiliar = new SpinnerValueFactory.ListSpinnerValueFactory(typeOfFamiliars);

        familiarSpinner.setValueFactory(valueFactoryFamiliar);

    }

    public void handleConfirm()
    {
       String output = (String) familiarSpinner.getValue();

       Client.getGenericState().sendToServer(output);

       confirmClicked = true;

       dialogStage.close();
    }










}
