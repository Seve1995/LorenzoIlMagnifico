package it.polimi.ingsw.pc22.client;

import it.polimi.ingsw.pc22.exceptions.GenericException;
import it.polimi.ingsw.pc22.rmi.RMIServerInterface;
import it.polimi.ingsw.pc22.states.AuthenticationState;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

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

    //Predefinito: GUI + Socket
    @FXML
    private void handleConfirmButton()
	{
		Client.setGenericState(new AuthenticationState());
    	if (RMI.isSelected())
    	{
    		Client.setNetworkChoice("rmi");
    		loadRMIConnection();
    	}
    	
    	if (Socket.isSelected())
    	{
			Client.setNetworkChoice("socket");
    		loadSocketConnection();
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
				Client.setInterfaceChoice("CLI");

				new Thread(new ViewThread()).start();

				Stage stage = (Stage) Client.getPrimaryStage().getScene().getWindow();
				stage.close();
			}

			if (GUI.isSelected())
			{
				Client.setInterfaceChoice("GUI");

				Client.launchClientAccess();
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

			RMIServerInterface rmiServerInterface = (RMIServerInterface)
					registry.lookup("auth");

			RMIClientStreamServiceImpl streamService =
					new RMIClientStreamServiceImpl(30000L);

			UnicastRemoteObject.exportObject(streamService, 0);

			Long id = rmiServerInterface.registerClient(streamService);

			if (id == null)
			{
				System.out.println("PROBLEM IN BINDING RMI");

				return;
			}

			Client.setAssignedID(id);

			Client.setRmiServerInterface(rmiServerInterface);

			//SISTEMARE QUESTA RIPETIZIONE

			if (CLI.isSelected())
			{
				Client.setInterfaceChoice("CLI");

				new Thread(new ViewThread()).start();

				Stage stage = (Stage) Client.getPrimaryStage().getScene().getWindow();
				stage.close();
			}

			if (GUI.isSelected())
			{
				Client.setInterfaceChoice("GUI");

				Client.launchClientAccess();
			}

		} catch (RemoteException | NotBoundException e)
		{
			throw new GenericException(e);
		}
	}

	@Override
	public void updateScene(Object message) {
		// TODO Auto-generated method stub
		
	}

}
