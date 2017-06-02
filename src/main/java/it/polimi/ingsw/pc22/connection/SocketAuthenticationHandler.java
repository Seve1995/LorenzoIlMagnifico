package it.polimi.ingsw.pc22.connection;

import it.polimi.ingsw.pc22.adapters.SocketGameAdapter;
import it.polimi.ingsw.pc22.player.Player;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

public class SocketAuthenticationHandler extends AuthenticationHandler implements Runnable {

	private Socket socket;

	private SocketGameAdapter adapter;

	public SocketAuthenticationHandler(Socket socket)
	{
		this.socket = socket;

		this.adapter = new SocketGameAdapter(socket);
	}

	public void run()
	{
		authentication(adapter);
	}
}

