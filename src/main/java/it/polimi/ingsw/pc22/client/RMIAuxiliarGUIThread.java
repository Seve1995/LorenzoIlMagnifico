package it.polimi.ingsw.pc22.client;

import static it.polimi.ingsw.pc22.client.Client.getClassLoader;

import java.io.IOException;

import it.polimi.ingsw.pc22.messages.Message;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RMIAuxiliarGUIThread extends Application {
	
	private Message message;
	
	
	public RMIAuxiliarGUIThread(Message message) {
		super();
		this.message = message;
	}

	public boolean councilPrivilegeDialog(Object message) {
        try {
        	System.out.println("Wow sono nel thread ausiliario!");
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

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
    	System.out.println("Wow sono nel thread ausiliario!");
		councilPrivilegeDialog(message);
	}

}
