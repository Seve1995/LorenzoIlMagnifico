package it.polimi.ingsw.pc22.client;

import it.polimi.ingsw.pc22.exceptions.GenericException;
import it.polimi.ingsw.pc22.rmi.RMIAuthenticationService;
import it.polimi.ingsw.pc22.rmi.RMIClientStreamService;

import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Client
{
	private static final int RMI_PORT = 5252;

	private static final int SOCKET_PORT = 9001;

	public static void main(String[] args)
	{
		while (true)
		{
			System.out.println("Scelgi tipologia di connessione: rmi o socket");

			Scanner scanner = new Scanner(System.in);

			String choice = scanner.nextLine();

			if (choice.equals("rmi"))
			{
				loadRMIConnection();

				break;
			}

			if (choice.equals("socket"))
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
}