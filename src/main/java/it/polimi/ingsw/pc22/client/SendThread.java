package it.polimi.ingsw.pc22.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SendThread implements Runnable
{	
	private Socket socket = null;
	private PrintWriter outSocket = null;
	private BufferedReader inKeyboard = null;
	private BufferedReader inSocket=null;

	private static final Logger LOGGER = Logger.getLogger(SendThread.class.getName());
	
	public SendThread(Socket socket)
	{
		this.socket = socket;
	}
	
	public void run()
	{
		try
		{
			inKeyboard = new BufferedReader(new InputStreamReader(System.in));
			outSocket = new PrintWriter(socket.getOutputStream(), true);

			inSocket = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));


			authenticate();

			while(true)
			{
				if (socket.isClosed()) break;

				if (!inKeyboard.ready()) Thread.sleep(100);

				String msgToServerString = inKeyboard.readLine();

				outSocket.println(msgToServerString);
			}

			System.out.println("Shutting down output");

			inKeyboard.close();
			outSocket.close();
		}
			catch(Exception e)
		{
			LOGGER.log(Level.INFO, "ERROR RECEIVE THREAD", e);
		}
	}

	private void authenticate()
	{

		boolean isLogged = false;

		while (!isLogged)
		{
			try
			{
				System.out.println("Write: <username> <password> l/s");

				String answer = inKeyboard.readLine();

				if (answer == null)
                {
                    System.out.println("Invalid");

                    return;
                }

				Pattern loginPattern = Pattern.compile("(^(\\w+) (\\w+) (L|S)$)");

				Matcher matcher = loginPattern.matcher(answer);

				if (!matcher.find())
                {
                    System.out.println("Invalid INPUT");

                    continue;
                }

				outSocket.println(answer);

				String read = inSocket.readLine();

				System.out.println(read);

				if ("true".equals(read)) break;

			} catch (IOException e)
			{


			}
		}
	}
}

