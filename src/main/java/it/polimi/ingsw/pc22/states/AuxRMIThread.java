package it.polimi.ingsw.pc22.states;

import java.rmi.RemoteException;

import it.polimi.ingsw.pc22.client.Client;

public class AuxRMIThread implements Runnable{

	private String string;
	
	public AuxRMIThread(String string) {
		this.string = string;
	}


	@Override
	public void run() {
		 try
         {
             Client.getRmiServerInterface().doAction(string, Client.getAssignedID());
         }
         catch (RemoteException e)
         {
             e.printStackTrace();
         }
	}
	
}
