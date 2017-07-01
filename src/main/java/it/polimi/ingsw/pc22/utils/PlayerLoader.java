package it.polimi.ingsw.pc22.utils;

import it.polimi.ingsw.pc22.player.Player;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerLoader
{
	private static final String FILEPATH = "users/UsersJson.json";

	private static final Logger LOGGER = Logger.getLogger(PlayerLoader.class.getName());

	public static Map<String, Player> generatePlayerMap() throws IOException, JSONException
	{
		ClassLoader classLoader = PlayerLoader.class.getClassLoader();

		File playerFile = new File(classLoader.getResource(FILEPATH).getFile());

		StringBuilder builder = new StringBuilder();

		try
		{
			Files.lines(playerFile.toPath()).forEach(s -> builder.append(s));
		}
		catch (IOException e)
		{
			LOGGER.log(Level.INFO, "User not loaded", e);
		}

		JSONArray playerList = new JSONArray(builder.toString());

		Map<String, Player> playerMap = new HashMap<>();

		for (int i = 0; i < playerList.length(); i++)
		{
			JSONObject jsonUser = playerList.getJSONObject(i);

			String userName = jsonUser.getString("username");

			String password = jsonUser.getString("password");

			Player player = new Player(userName,password, false);

			playerMap.put(userName, player);
		}

		return playerMap;
	}
	
	public static void refreshJson(Map<String, Player> users) throws IOException
	{
		ClassLoader classLoader = PlayerLoader.class.getClassLoader();

		File playerFile = new File(classLoader.getResource(FILEPATH).getFile());

		FileWriter fileWriter = null;

		try
		{
			fileWriter = new FileWriter(playerFile);

			JSONArray playerArray = new JSONArray();

			for(Player player : users.values())
			{
				JSONObject jsonPlayer = new JSONObject();

				jsonPlayer.put("username", player.getUsername());
				jsonPlayer.put("password", player.getPassword());

				playerArray.put(jsonPlayer);
			}

			fileWriter.write(playerArray.toString());
			fileWriter.close();
		}
		finally
		{
			if (fileWriter != null)
			{
				fileWriter.close();
			}
		}
	}
}
