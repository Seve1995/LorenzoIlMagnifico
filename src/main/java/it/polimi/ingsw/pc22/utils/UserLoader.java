package it.polimi.ingsw.pc22.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.pc22.connection.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Stream;

public class UserLoader
{
	private static final String FILEPATH = "users/UsersJson.json";

	public static Map<String, User> generateUserMap() throws IOException
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
			e.printStackTrace();
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

		FileWriter fileWriter = new FileWriter(usersFile);

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
	
	
	/*public static void main(String[] args) throws IOException {
		JsonReader reader = new JsonReader(new FileReader(FILEPATH));
		Gson gson = new Gson();
		Type type = new TypeToken<List<Users>>(){}.getType();
		List<Users> users = gson.fromJson(reader, type);
		//for (int i=0; i<users.size(); i++)
		//	System.out.println(users.get(i));
	}*/
}
