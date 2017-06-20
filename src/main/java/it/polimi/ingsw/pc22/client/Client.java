package it.polimi.ingsw.pc22.client;

import it.polimi.ingsw.pc22.exceptions.GenericException;
import it.polimi.ingsw.pc22.rmi.RMIAuthenticationService;
import it.polimi.ingsw.pc22.rmi.RMIClientStreamService;
import it.polimi.ingsw.pc22.states.GenericState;
import it.polimi.ingsw.pc22.states.LoginState;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Client extends Application
{
	private static final int RMI_PORT = 5252;

	private static final int SOCKET_PORT = 9001;

	private static GenericState genericState;

	private static boolean stateChanged = true;

	private Stage primaryStage;
	
	private AnchorPane anchorPane;
	
	private BorderPane border;
		
	private ClassLoader classLoader = this.getClass().getClassLoader();

	@Override
	public void start(Stage primaryStage) {
		 this.primaryStage = primaryStage;
	     this.primaryStage.setTitle("Main");
	     initStartingChoiche();
	}
	
	public void initStartingChoiche()
	{ 
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(classLoader.getResource("GUI/StartingChoiche.fxml"));
	        anchorPane = (AnchorPane) loader.load();
			Scene scene = new Scene(anchorPane);
	        primaryStage.setScene(scene);
		    primaryStage.show();
		    
			
			/*FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(Main.class.getResource("GameBoard.fxml"));
	        anchorPane = (AnchorPane) loader.load();
	        Scene scene = new Scene(anchorPane);
	        primaryStage.setScene(scene);
		    primaryStage.show();*/
	        // Give the controller access to the main app.
	        StartingChoicheController controller = loader.getController();
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
	        anchorPane = (AnchorPane) loader.load();
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
		/*while (true)
		{
			System.out.println("Scelgi tipologia di connessione: rmi o socket");

			Scanner scanner = new Scanner(System.in);

			String choice = scanner.nextLine();

			if ("rmi".equals(choice))
			{
				loadRMIConnection();

				break;
			}

			if ("socket".equals(choice))
			{
				loadSocketConnection();

				break;
			}

		}

		System.out.println("Client running. Type 'EXIT' to exit.");
	}

	private static void loadRMIConnection()
	{
		try
		{
			Registry registry = LocateRegistry.getRegistry(RMI_PORT);

			RMIAuthenticationService authenticationService = (RMIAuthenticationService)
					registry.lookup("auth");

			//TODO TIMEOUT

			RMIClientStreamServiceImpl streamService =
					new RMIClientStreamServiceImpl(30000L);

			RMIClientStreamService stub = (RMIClientStreamService)
					UnicastRemoteObject.exportObject(streamService, 0);

			registry.rebind("client", stub);

			authenticationService.login();

		} catch (RemoteException | NotBoundException e)
		{
			throw new GenericException(e);
		}
	}

	private static void loadSocketConnection()
	{
		Socket socket;

		try
		{
			System.out.println("Client running.");

			socket = new Socket("localhost", SOCKET_PORT);

			genericState = new LoginState();

			SendThread sendThread = new SendThread(socket);
			Thread thread = new Thread(sendThread);
			thread.start();


			ReceiveThread receiveThread = new ReceiveThread(socket);
			Thread thread2 = new Thread(receiveThread);
			thread2.start();

		}
		catch (Exception e)
		{
			throw new GenericException(e);
		}
*/
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