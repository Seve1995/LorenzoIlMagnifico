package it.polimi.ingsw.pc22.client;

import it.polimi.ingsw.pc22.exceptions.GenericException;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.messages.CommunicationMessage;
import it.polimi.ingsw.pc22.messages.Message;
import it.polimi.ingsw.pc22.player.Player;
import it.polimi.ingsw.pc22.rmi.RMIServerInterface;
import it.polimi.ingsw.pc22.states.GenericState;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Application 
{
	private static GenericState genericState;

	private static boolean stateChanged = true;

	private static Stage primaryStage;
	
	private static AnchorPane anchorPane;

	private static ClassLoader classLoader = Client.class.getClassLoader();

	private static RMIServerInterface rmiServerInterface;

	private static Long assignedID;

	private static Socket socket;
	
	private static Controller controller;
	
	private static String interfaceChoice;

	private static String networkChoice;

	private static GameBoard gameBoard;

	private static Player player;

	private static boolean stopped = false;

	public static synchronized GenericState getGenericState()
	{
		return genericState;
	}

	public static synchronized void setGenericState(GenericState genericState)
	{
		Client.genericState = genericState;
	}

	public static synchronized boolean isStateChanged() {
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

	public static RMIServerInterface getRmiServerInterface() {
		return rmiServerInterface;
	}

	public static void setRmiServerInterface(RMIServerInterface rmiServerInterface) {
		Client.rmiServerInterface = rmiServerInterface;
	}

	public static Long getAssignedID() {
		return assignedID;
	}

	public static void setAssignedID(Long assignedID) {
		Client.assignedID = assignedID;
	}


	public static String getNetworkChoice() {
		return networkChoice;
	}

	public static void setNetworkChoice(String networkChoice) {
		Client.networkChoice = networkChoice;
	}

	public static Socket getSocket() {
		return socket;
	}

	public static void setSocket(Socket socket)
	{
		Client.socket = socket;
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

	public static boolean isStopped() {
		return stopped;
	}

	public static void setStopped(boolean stopped) {
		Client.stopped = stopped;
	}

	@Override
	public void start(Stage primaryStage)
	{
		Client.primaryStage = primaryStage;

		primaryStage.setTitle("Main");
		initStartingChoice();

		Client.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				Platform.exit();
				System.exit(0);
			}
		});

		Client.primaryStage.resizableProperty().setValue(Boolean.FALSE);
	}
	
	public static void initStartingChoice()
	{ 
		try
		{
			FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(classLoader.getResource("GUI/StartingChoice.fxml"));
	        anchorPane = loader.load();
			Scene scene = new Scene(anchorPane);
	        primaryStage.setScene(scene);
		    primaryStage.show();		    
	        StartingChoiceController controller = loader.getController();
	        Client.controller = controller;


		} catch (IOException e)
		{
			throw new GenericException("Cannot Init Starting Choice", e);
		}
	}
	
	public static void launchClientAccess()
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
			throw new GenericException("Cannot Init Client access", e);
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
			throw new GenericException("Cannot lunch match", e);
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

			GameBoardController controller = loader.getController();
			Client.controller = controller;



		} catch (IOException e)
		{
			throw new GenericException("Cannot Lunch boards", e);
		}
	}

	public static void launchWaitingForTheMatch()
	{
		Message message = new CommunicationMessage("Match is starting. Please wait...");
		controller.updateScene(message);
	}

	public static void main(String[] args)
	{
		launch(args);
	}

	public static void socketSend(String string)
	{
		try
		{
			PrintWriter outSocket = new PrintWriter(socket.getOutputStream(), true);

			outSocket.println(string);

		} catch (IOException e)

		{
			throw new GenericException("Cannot write on socket", e);
		}
	}
}