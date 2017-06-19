package it.polimi.ingsw.pc22.client;

import it.polimi.ingsw.pc22.exceptions.GenericException;
import it.polimi.ingsw.pc22.rmi.RMIAuthenticationService;
import it.polimi.ingsw.pc22.rmi.RMIClientStreamService;
import it.polimi.ingsw.pc22.states.GenericState;
import it.polimi.ingsw.pc22.states.LoginState;

import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Client
{
	private static final int RMI_PORT = 5252;

	private static final int SOCKET_PORT = 9001;

	private static GenericState genericState;

	private static boolean stateChanged = true;

	public static void main(String[] args)
	{
		while (true)
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
}