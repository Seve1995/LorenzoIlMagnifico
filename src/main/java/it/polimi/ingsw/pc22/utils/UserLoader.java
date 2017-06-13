package it.polimi.ingsw.pc22.utils;

import it.polimi.ingsw.pc22.connection.User;
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

public class UserLoader
{
	private static final String FILEPATH = "users/UsersJson.json";

	private static final Logger LOGGER = Logger.getLogger(UserLoader.class.getName());

	public static Map<String, User> generateUserMap() throws IOException, JSONException
	{
		ClassLoader classLoader = UserLoader.class.getClassLoader();

		File usersFile = new File(classLoader.getResource(FILEPATH).getFile());

		StringBuilder builder = new StringBuilder();

		try
		{
			Files.lines(usersFile.toPath()).forEach(s -> builder.append(s));
		}
		catch (IOException e)
		{
			LOGGER.log(Level.INFO, "User not loaded", e);
		}

		JSONArray usersList = new JSONArray(builder.toString());

		usersList.toString();

		Map<String, User> usersMap = new HashMap<>();

		for (int i = 0; i < usersList.length(); i++)
		{
			JSONObject jsonUser = usersList.getJSONObject(i);

			String userName = jsonUser.getString("username");

			String password = jsonUser.getString("password");

			User user = new User(userName,password, false);

			usersMap.put(userName, user);
		}

		return usersMap;
	}
	
	public static void refreshJson(Map<String, User> users) throws IOException
	{
		ClassLoader classLoader = UserLoader.class.getClassLoader();

		File usersFile = new File(classLoader.getResource(FILEPATH).getFile());

		FileWriter fileWriter = null;

		try
		{
			fileWriter = new FileWriter(usersFile);

			JSONArray userArray = new JSONArray();

			for(User user : users.values())
			{
				JSONObject jsonUser = new JSONObject();

				jsonUser.put("username", user.getUsername());
				jsonUser.put("password", user.getPassword());

				userArray.put(jsonUser);
			}

			fileWriter.write(userArray.toString());
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
	
	
	/*public static void main(String[] args) throws IOException {
		JsonReader reader = new JsonReader(new FileReader(FILEPATH));
		Gson gson = new Gson();
		Type type = new TypeToken<List<Users>>(){}.getType();
		List<Users> users = gson.fromJson(reader, type);
		//for (int i=0; i<users.size(); i++)
		//	System.out.println(users.get(i));
	}*/
}
