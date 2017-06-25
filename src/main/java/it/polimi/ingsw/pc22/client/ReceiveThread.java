package it.polimi.ingsw.pc22.client;

import it.polimi.ingsw.pc22.adapters.IOAdapter;
import it.polimi.ingsw.pc22.gamebox.PlayerBoard;
import it.polimi.ingsw.pc22.messages.*;
import it.polimi.ingsw.pc22.states.IdleState;
import it.polimi.ingsw.pc22.states.PlayState;
import it.polimi.ingsw.pc22.states.StartMatchState;
import it.polimi.ingsw.pc22.states.WaitingState;
import javafx.application.Platform;
import javafx.scene.Scene;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.Serializable;
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

				//TODO sistemare questa cosa, non tutti hanno qualcosa da visualizzzare
				//printOnClient(message.toString());
				
				if (message instanceof LoginMessage)
				{
					LoginMessage login = (LoginMessage) message;

					if (login.isUserLogged())
					{
						printOnClient("Logged");

						Client.setGenericState(new StartMatchState());

						Platform.runLater(() -> {
							Client.launchCreationMatch();
					              });
					}

					if (login.isMatchStarted())
					{
						printOnClient("Match is starting. Please wait...");
						
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

				if (message instanceof StartTurnMessage)
				{
					Client.setPlayer(((StartTurnMessage) message).getPlayer());

					Client.setGameBoard(((StartTurnMessage) message).getGameBoard());

					Client.setGenericState(new PlayState());

					Client.setStateChanged(true);
					
					printOnClient(((StartTurnMessage) message));
				}
				
				if (message instanceof StartMatchMessage)
				{
					Platform.runLater(() -> {
						Client.launchGameBoard();
				              });
				}

				if (message instanceof ExecutedAction)
				{
					printOnClient((ExecutedAction) message);

				}

				if (message instanceof GameStatusMessage)
				{
					printOnClient(((GameStatusMessage) message).getPlayer().toString());
					
					printOnClient(((GameStatusMessage) message).getGameBoard().toString());

					Client.setGenericState(new IdleState());

					Client.setStateChanged(true);
				}
				
				if (message instanceof CommunicationMessage)
				{
					printOnClient(((CommunicationMessage) message).getMessage());
				}
				
				/*if ("started".equalsIgnoreCase(msgReceived))
				{




					Client.setStateChanged(true);
				}

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
				if (message instanceof StartTurnMessage)
					
					{
						System.out.println("Server: " + ((StartTurnMessage) message).getPlayer().toString());
						System.out.println("Server: " + ((StartTurnMessage) message).getGameBoard().toString());			
					}
				
				System.out.println("Server: " + message.toString());

			}
	}
}

