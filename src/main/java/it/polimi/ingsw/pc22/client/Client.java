package it.polimi.ingsw.pc22.client;

import it.polimi.ingsw.pc22.rmi.RMIAuthenticationService;

import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client 
{
	private static final int RMI_PORT = 5252;

	private static final int SOCKET_PORT = 9001;

	public static void main(String[] args)
	{
		System.out.println("Scelgi tipologia di connessione: rmi o socket");

		while (true)
		{
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

			RMIAuthenticationService stub = (RMIAuthenticationService) registry.lookup("auth");

			stub.login(registry);

		} catch (RemoteException | NotBoundException e)
		{
			e.printStackTrace();
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
			e.printStackTrace();
		}
	}
}
