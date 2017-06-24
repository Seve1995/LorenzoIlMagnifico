package it.polimi.ingsw.pc22.client;

import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.messages.CommunicationMessage;
import it.polimi.ingsw.pc22.messages.Message;
import it.polimi.ingsw.pc22.player.Player;
import it.polimi.ingsw.pc22.states.GenericState;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.registry.Registry;

public class Client extends Application 
{
	private static GenericState genericState;

	private static boolean stateChanged = true;

	private static Stage primaryStage;
	
	private static AnchorPane anchorPane;
	
	private static BorderPane border;
		
	private static ClassLoader classLoader = Client.class.getClassLoader();

	private static Registry registry;
	
	private static Socket socket;
	
	private static Controller controller;
	
	private static String interfaceChoice;
	
	private static PrintWriter outSocket;

	private static GameBoard gameBoard;

	private static Player player;

	@Override
	public void start(Stage primaryStage)
	{
		Client.primaryStage = primaryStage;
		primaryStage.setTitle("Main");
		initStartingChoice();
	}
	
	public static void initStartingChoice()
	{ 
		try
		{	
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(classLoader.getResource("GUI/StartingChoice.fxml"));
	        anchorPane = loader.load();
			Scene scene = new Scene(anchorPane);
	        primaryStage.setScene(scene);
		    primaryStage.show();		    
	        StartingChoiceController controller = loader.getController();
	        Client.controller = controller;
	        //controller.setClient(this);

		} catch (IOException e)
		{
			 e.printStackTrace();
		}
	}
	
	public static void launchClientAccess(String choice)
	{
		try {
			// Load root layout from fxml file.
			primaryStage.setTitle("Starting Choice");
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(classLoader.getResource("GUI/ClientAccess.fxml"));
	        anchorPane = loader.load();
			Scene scene = new Scene(anchorPane);
	        primaryStage.setScene(scene);
	        // Give the controller access to the main app.
	        ClientAccessController controller = loader.getController();
	        Client.controller = controller;
		} catch (IOException e)
		{
			 e.printStackTrace();
		}
	}
	
	public static void launchCreationMatch()
	{
		try {
			// Load root layout from fxml file.
			primaryStage.setTitle("Creation Match");
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(classLoader.getResource("GUI/CreationMatch.fxml"));
	        anchorPane = loader.load();
			Scene scene = new Scene(anchorPane);
	        primaryStage.setScene(scene);
	        // Give the controller access to the main app.
	        CreationMatchController controller = loader.getController();
	        Client.controller = controller;
		} catch (IOException e)
		{
			 e.printStackTrace();
		}
	}

	public static void launchGameBoard(){
		try {
			// Load root layout from fxml file.
			primaryStage.setTitle("Lorenzo il Magnifico");
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(classLoader.getResource("GUI/GameBoard.fxml"));
			anchorPane = loader.load();
			Scene scene = new Scene(anchorPane);
			primaryStage.setScene(scene);
			// Give the controller access to the main app.
			//TODO:
			GameBoardController controller = loader.getController();
			Client.controller = controller;
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void launchWaitingForTheMatch() {
		Message message = new CommunicationMessage("Match is starting. Please wait...");
		controller.updateScene(message);
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
	
	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void setPrimaryStage(Stage primaryStage) {
		Client.primaryStage = primaryStage;
	}

	public static Registry getRegistry() {
		return registry;
	}

	public static void setRegistry(Registry registry) {
		Client.registry = registry;
	}
	
	public static Socket getSocket() {
		return socket;
	}

	public static void setSocket(Socket socket) {
		Client.socket = socket;
		try {
			Client.outSocket = new PrintWriter(Client.getSocket().getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Controller getController() {
		return controller;
	}

	public static void setController(Controller controller) {
		Client.controller = controller;
	}

	public static String getInterfaceChoice() {
		return interfaceChoice;
	}

	public static void setInterfaceChoice(String interfaceChoice) {
		Client.interfaceChoice = interfaceChoice;
	}

	public static PrintWriter getOutSocket() {
		return outSocket;
	}

	public static GameBoard getGameBoard() {
		return gameBoard;
	}

	public static void setGameBoard(GameBoard gameBoard) {
		Client.gameBoard = gameBoard;
	}

	public static Player getPlayer() {
		return player;
	}

	public static void setPlayer(Player player) {
		Client.player = player;
	}

	public static ClassLoader getClassLoader() {
		return classLoader;
	}

	public static void setClassLoader(ClassLoader classLoader) {
		Client.classLoader = classLoader;
	}
	
}