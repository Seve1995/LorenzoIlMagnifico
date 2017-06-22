package it.polimi.ingsw.pc22.client;

import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import it.polimi.ingsw.pc22.exceptions.GenericException;
import it.polimi.ingsw.pc22.rmi.RMIAuthenticationService;
import it.polimi.ingsw.pc22.rmi.RMIClientStreamService;
import it.polimi.ingsw.pc22.states.LoginState;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

public class StartingChoiceController implements Controller {

	@FXML
    private RadioButton GUI;
    @FXML
    private RadioButton CLI;
    @FXML
    private RadioButton Socket;
    @FXML
    private RadioButton RMI;

    private static final int RMI_PORT = 5439;

	private static final int SOCKET_PORT = 9001;
	
    private String networkChoice;
    
    private String interfaceChoice;
    

    //Predefinito: GUI + Socket
    @FXML
    private void handleConfirmButton()
	{
		Client.setGenericState(new LoginState());
    	if (RMI.isSelected())
    	{
    		loadRMIConnection();
    		networkChoice = "rmi";
    	}
    	
    	if (Socket.isSelected())
    	{
    		loadSocketConnection();
    		networkChoice = "socket";
    	}
    }
    
    private void loadSocketConnection()
	{
		Socket socket;

		try
		{
			socket = new Socket("localhost", SOCKET_PORT);
			
			Client.setSocket(socket);
			
			ReceiveThread receiveThread = new ReceiveThread(socket);
			Thread thread2 = new Thread(receiveThread);
			thread2.start();

			if (CLI.isSelected())
			{
				interfaceChoice = "CLI";
				ViewThread viewThread = new ViewThread(socket);
				Thread thread = new Thread(viewThread);
				thread.start();
				Stage stage = (Stage) Client.getPrimaryStage().getScene().getWindow();
				stage.close();
		    	Client.setInterfaceChoice(interfaceChoice);
			}

			if (GUI.isSelected())
			{
				interfaceChoice = "GUI";
				Client.launchClientAccess(networkChoice);
		    	Client.setInterfaceChoice(interfaceChoice);
			}

		}
		catch (Exception e)
		{
			throw new GenericException(e);
		}
	}
    
    private void loadRMIConnection()
	{
		try
		{
			Registry registry = LocateRegistry.getRegistry(RMI_PORT);
			
			Client.setRegistry(registry);

			RMIAuthenticationService authenticationService = (RMIAuthenticationService)
					registry.lookup("auth");

			//TODO TIMEOUT

			RMIClientStreamServiceImpl streamService =
					new RMIClientStreamServiceImpl(30000L);
			
			RMIClientStreamService stub = (RMIClientStreamService)
					UnicastRemoteObject.exportObject(streamService, 0);

			registry.rebind("client", stub);
			
			if (CLI.isSelected())
			{
				Stage stage = (Stage) Client.getPrimaryStage().getScene().getWindow();
				stage.close();
			}
			
			authenticationService.login();

			if (GUI.isSelected())
			{
				Client.launchClientAccess(networkChoice);
			}

		} catch (RemoteException | NotBoundException e)
		{
			throw new GenericException(e);
		}
	}

	@Override
	public void updateScene(String string) {
		// TODO Auto-generated method stub
		
	}

}
