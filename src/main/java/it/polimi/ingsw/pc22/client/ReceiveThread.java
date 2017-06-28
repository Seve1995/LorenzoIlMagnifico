package it.polimi.ingsw.pc22.client;

import it.polimi.ingsw.pc22.messages.*;
import it.polimi.ingsw.pc22.states.*;
import javafx.application.Platform;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReceiveThread implements Runnable
{
	private Socket socket=null;
	private ObjectInputStream inSocket=null;
	
	private static final Logger LOGGER = Logger.getLogger(ReceiveThread.class.getName());
	
	public ReceiveThread(Socket socket) {
		this.socket = socket;
	}

	public void run() 
	{
		try
		{
			inSocket = new ObjectInputStream(this.socket.getInputStream());

			while(true)
			{
				Message message = (Message) inSocket.readObject();

				if (message == null)
				{
					Thread.sleep(100);

					continue;
				}

				if (message instanceof LoginMessage)
				{
					LoginMessage login = (LoginMessage) message;

					if (login.isUserLogged())
					{
						printOnClient(LoginMessage.getLoggedMessage());

						Client.setGenericState(new StartMatchState());

						Platform.runLater(() -> {
							Client.launchCreationMatch();
					              });
					}

					if (login.isMatchStarted())
					{
						printOnClient(LoginMessage.getStarded());
						
						Client.setGenericState(new WaitingState());
						
						Platform.runLater(() -> {
							Client.launchWaitingForTheMatch();
					              });
					}


					Client.setStateChanged(true);
				}

				if (message instanceof TimerMessage)
				{
					printOnClient(((TimerMessage) message).getMessage());

					Client.setGenericState(new WaitingState());

					Client.setStateChanged(true);
				}

				if (message instanceof ErrorMessage)
				{
					printOnClient(((ErrorMessage) message).getMessage());
				}

				if (message instanceof GameStatusMessage)
				{
					GameStatusMessage gameStatusMessage = (GameStatusMessage) message;

					Client.setPlayer(gameStatusMessage.getPlayer());

					Client.setGameBoard(gameStatusMessage.getGameBoard());

					printOnClient(message);

					if ("started".equals(gameStatusMessage.getState()))
					{
						Client.setGenericState(new PlayState());

						Platform.runLater(() -> {
							Client.launchGameBoard();
						});

						printOnClient(message);

					}

					if ("finished".equals(gameStatusMessage.getState()))
						Client.setGenericState(new IdleState());

					Client.setStateChanged(true);
				}

				if (message instanceof PickPrivilegeMessage)
				{
					int numberOFPrivileges =
							((PickPrivilegeMessage) message).getNumberOfPrivilege();

					Client.setGenericState(new PickCouncilState(numberOFPrivileges));

					Client.setStateChanged(true);
					
					printOnClient(message);

				}

				if (message instanceof ExecutedAction)
				{
					printOnClient(message);

				}


				
				if (message instanceof CommunicationMessage)
				{
					printOnClient(((CommunicationMessage) message).getMessage());
				}
				
				/*
				if ("show board".equals(msgReceived))
				{
					//PlayerBoard board = player.getPlayerBoard();

					//player.getAdapter().printMessage(board.toString());

					//continue;
				}


				if ("show cards".equals(msgReceived))
				{
					//PlayerBoard board = player.getPlayerBoard();

					//IOAdapter adapter = player.getAdapter();

					//adapter.printMessage(board.getTerritories().toString());
					//adapter.printMessage(board.getBuildings().toString());
					//adapter.printMessage(board.getCharacters().toString());
					//adapter.printMessage(board.getVentures().toString());

					//adapter.printMessage(board.getLeaderCards().toString());

					continue;
				}

				if("EXIT".equalsIgnoreCase(msgReceived))
				{
					System.out.println("Shutting down input");

					socket.close();

					break;
				}*/
			}
		}
		catch(Exception e)
		{
			LOGGER.log(Level.INFO, "ERROR RECEIVE THREAD", e);
		}
	}
	
	public void printOnClient(Object message)
	{
		if ("GUI".equals(Client.getInterfaceChoice()))
			Platform.runLater(() ->
			{
				Client.getController().updateScene(message);
			});
		else
			{
				System.out.println("Server: " + message.toString());
			}
	}
}

