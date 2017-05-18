package it.polimi.ingsw.pc22.connection;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class JsonManager {
	private static final String FILEPATH = "src/Server/UsersJson.json";
	
	public static List<User> returnList() throws IOException{
		JsonReader reader = new JsonReader(new FileReader(FILEPATH));
		Gson gson = new Gson();
		Type type = new TypeToken<List<User>>(){}.getType();
		List<User> users = Collections.synchronizedList(gson.fromJson(reader, type));
		return users;
	}
	
	public static void refreshJson(List<User> users) throws IOException{
		FileWriter fileWriter = new FileWriter(FILEPATH);
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
