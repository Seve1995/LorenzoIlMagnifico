package it.polimi.ingsw.pc22.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class GameServer 
{
	private String connectionType;
	private Map gamesMatchesMap;
	private int port;
	
	public void startServer(){
		gamesMatchesMap = new ConcurrentHashMap<>();
		ExecutorService executor=Executors.newCachedThreadPool();
		ServerSocket serversocket;
	    try{
	    	serversocket=new ServerSocket(port);
	    }
	    catch(IOException e)
	    {
	    	System.err.println(e.getMessage());
	    	return;
	    }
	    System.out.println("Server Ready");
	    while(true){
	    	try{
	    		Socket socket=serversocket.accept();
	    		//executor.submit(new GameServerManager(socket));	
	    	}
	    	catch (IOException e) {
	    		break;
	    	}
	    }
	    try {
			serversocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    executor.shutdown();
	}
}
