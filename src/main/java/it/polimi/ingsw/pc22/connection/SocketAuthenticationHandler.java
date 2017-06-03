package it.polimi.ingsw.pc22.connection;

import it.polimi.ingsw.pc22.adapters.SocketGameAdapter;

import java.net.Socket;

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

