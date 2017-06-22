package it.polimi.ingsw.pc22.client;

import it.polimi.ingsw.pc22.adapters.IOAdapter;
import it.polimi.ingsw.pc22.gamebox.PlayerBoard;
import it.polimi.ingsw.pc22.messages.ErrorMessage;
import it.polimi.ingsw.pc22.messages.LoginMessage;
import it.polimi.ingsw.pc22.messages.Message;
import it.polimi.ingsw.pc22.messages.TimerMessage;
import it.polimi.ingsw.pc22.states.StartMatchState;
import it.polimi.ingsw.pc22.states.WaitingState;
import javafx.application.Platform;
import javafx.scene.Scene;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
				
				printOnClient(message.toString());
				
				if (message instanceof LoginMessage)
				{
					LoginMessage login = (LoginMessage) message;

					if (login.isUserLogged())
					{
						//printOnClient("Logged");

						Client.setGenericState(new StartMatchState());
						
						Platform.runLater(() -> { 
							Client.launchCreationMatch();
					              }); 
					}

					if (login.isMatchStarted())
					{
						Client.setGenericState(new WaitingState());
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
					//printOnClient(((ErrorMessage) message).getMessage());
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
	
	public void printOnClient(String string)
	{
		if ("GUI".equals(Client.getInterfaceChoice()))
			Platform.runLater(() -> { 
				Client.getController().updateScene(string);
		              });
		else
			System.out.println("Server: " + string);
	}
}

