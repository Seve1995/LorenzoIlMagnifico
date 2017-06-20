package it.polimi.ingsw.pc22.client;

import it.polimi.ingsw.pc22.states.GenericState;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Client extends Application
{
	private static GenericState genericState;

	private static boolean stateChanged = true;

	private Stage primaryStage;
	
	private AnchorPane anchorPane;
	
	private BorderPane border;
		
	private ClassLoader classLoader = this.getClass().getClassLoader();

	@Override
	public void start(Stage primaryStage)
	{
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Main");
		initStartingChoice();
	}
	
	public void initStartingChoice()
	{ 
		try
		{
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(classLoader.getResource("GUI/StartingChoiche.fxml"));
	        anchorPane = loader.load();
			Scene scene = new Scene(anchorPane);
	        primaryStage.setScene(scene);
		    primaryStage.show();


	        StartingChoiceController controller = loader.getController();
	        controller.setClient(this);

		} catch (IOException e)
		{
			 e.printStackTrace();
		}
	}
	
	public void launchStartingChoice(String choice)
	{
		try {
			// Load root layout from fxml file.
			this.primaryStage.setTitle(choice);
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(classLoader.getResource("GUI/ClientAccess.fxml"));
	        anchorPane = loader.load();
			Scene scene = new Scene(anchorPane);
	        primaryStage.setScene(scene);
	        // Give the controller access to the main app.
	        ClientAccessController controller = loader.getController();
	        controller.setClient(this);
		} catch (IOException e)
		{
			 e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
	
	public static GenericState getGenericState()
	{
		return genericState;
	}

	public static synchronized void setGenericState(GenericState genericState)
	{
		Client.genericState = genericState;
	}

	public static boolean isStateChanged() {
		return stateChanged;
	}

	public static synchronized void setStateChanged(boolean stateChanged)
	{
		Client.stateChanged = stateChanged;
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
}