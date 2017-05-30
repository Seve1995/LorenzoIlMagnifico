package it.polimi.ingsw.pc22.connection;

import it.polimi.ingsw.pc22.adapters.SocketGameAdapter;
import it.polimi.ingsw.pc22.player.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketAuthenticationHandler extends AuthenticationHandler implements Runnable {
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;

	public SocketAuthenticationHandler(Socket socket) {
		this.socket = socket;
	}

	public void run()
	{
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);

			User user = null;

			while (user == null)
			{
				out.println("Sign up or Login?");

				String answer = in.readLine().toLowerCase();

				switch (answer)
				{
					case "login":

						user = login();

						break;
					case "sign up":

						user = signUp();

						break;

					default:
						out.println("Non-valid input. Please retry... ");

						break;
				}
			}

			String playerName = user.getUsername();

			Player player = new Player();

			player.setName(playerName);


			boolean gameHandling = false;

			while (!gameHandling)
			{
				out.println("Choose an operation:\n"
						+ "(1) Create new game match\n"
						+ "(2) Join a friend's game match\n"
						+ "(3) Join a random game match"
				);

				String userChoice = in.readLine();

				if (userChoice.equals("1"))
				{
					gameHandling = createNewGame(player);
				}

				if (userChoice.equals("2"))
				{
					gameHandling = checkExistingGame(player);
				}

				if (userChoice.equals("3"))
				{

				}

				out.println("Non-valid input. Please retry... ");
			}

			updateJson();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected User getUserFromUserName() throws IOException
	{
		out.println("Type an existing username:");

		String username = in.readLine();

		User user = existingUsername(username);

		if (user != null) {
			out.println("Error: username not found! Please retry.");

			return user;
		}

		out.println("Error: username not found! Please retry.");

		return null;
	}

	@Override
	protected void checkPassword(User user) throws IOException
	{
		out.println("Username OK. Now type the password:");

		String password = in.readLine();

		if (user.getPassword().equals(password)) {
			out.println("Successful logged in!");

			user.setLogged(true);

			return;
		}

		out.println("Error: wrong password! Please retry.");

		user.setLogged(false);
	}

	protected User registerNewUser() throws IOException
	{
		out.println("Type an username:");
		String username = in.readLine();

		out.println("Type a password");

		String password = in.readLine();

		Boolean invalidUsername =
				GameServer.getUsersMap().containsKey(username);

		if (invalidUsername) {
			out.println("The specified username already exist! Please type a new username.");
			return null;
		}

		out.println("User created!");

		return new User(username, password, true);
	}

	protected boolean createNewGame(Player player) throws IOException
	{
		out.println("Type a name for the new game match:");

		String gameName = in.readLine();

		out.println("Game name: " + gameName);

		boolean existingGameMatch =
				GameServer.getGameMatchMap().containsKey(gameName);

		if (existingGameMatch)
		{
			out.println("A game match with the specified name already exists.");

			return false;
		}

		SocketGameAdapter adapter = new SocketGameAdapter(socket);

		startNewGameMatch(gameName, player, adapter);

		out.println("Player: " + player.getName() + " created GameMatch - " + gameName);

		return true;
	}

	protected boolean checkExistingGame(Player player) throws IOException
	{
		out.println("Type the name of the chosen game match:");

		String gameName = in.readLine();

		out.println("Game name: " + gameName);

		boolean existingGameMatch =
				GameServer.getGameMatchMap().containsKey(gameName);

		if (!existingGameMatch) {
			out.println("Game match not found.");

			return false;
		}

		SocketGameAdapter adapter = new SocketGameAdapter(socket);

		insertIntoExistingGameMatch(gameName, player, adapter);

		out.println("Player: " + player.getName() + " joined GameMatch - " + gameName);

		return true;
	}

}

