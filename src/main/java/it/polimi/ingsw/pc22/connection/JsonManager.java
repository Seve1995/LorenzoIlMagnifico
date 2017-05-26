package it.polimi.ingsw.pc22.connection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class JsonManager
{
	private static final String FILEPATH = "users/UsersJson.json";
	
	public static List<User> returnList() throws IOException
	{
		ClassLoader classLoader = JsonManager.class.getClassLoader();

		File usersFile = new File(classLoader.getResource(FILEPATH).getFile());

		JsonReader reader = new JsonReader(new FileReader(usersFile));
		Gson gson = new Gson();
		Type type = new TypeToken<List<User>>(){}.getType();
		List<User> users = Collections.synchronizedList(gson.fromJson(reader, type));
		return users;
	}
	
	public static void refreshJson(List<User> users) throws IOException
	{
		ClassLoader classLoader = JsonManager.class.getClassLoader();

		File usersFile = new File(classLoader.getResource(FILEPATH).getFile());

		FileWriter fileWriter = new FileWriter(usersFile);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Type type = new TypeToken<List<User>>(){}.getType();
		String jsonString = gson.toJson(users, type);
		System.out.println(jsonString);
		fileWriter.write(jsonString);
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
