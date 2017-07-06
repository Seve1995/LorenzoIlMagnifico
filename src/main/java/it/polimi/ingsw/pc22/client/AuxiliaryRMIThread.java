package it.polimi.ingsw.pc22.client;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.CardTypeEnum;
import it.polimi.ingsw.pc22.states.*;

import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuxiliaryRMIThread implements Runnable
{
	private String string;

	private List<Asset> assetList;

	private CardTypeEnum cardType;

	private Integer numberOfPrivileges;

	private static final Logger LOGGER = Logger.getLogger(AuxiliaryRMIThread.class.getName());

	public AuxiliaryRMIThread(String string, List<Asset> assetList,
		CardTypeEnum cardType, Integer numberOfPrivileges)
	{
		this.string = string;
		this.assetList = assetList;
		this.cardType = cardType;
		this.numberOfPrivileges = numberOfPrivileges;
	}

	@Override
	public void run()
	{
		 try
         {
         	if (Client.getGenericState() instanceof GameCreationState)
			{
				Client.getRmiServerInterface().matchHandling
						(string, Client.getAssignedID());
			}

         	if (Client.getGenericState() instanceof AuthenticationState)
			{
				Client.getRmiServerInterface().login(string, Client.getAssignedID());
			}

			if (Client.getGenericState() instanceof ChooseAssetsState)
			{
				Client.getRmiServerInterface().takeAssetDecision
						(string, Client.getAssignedID(), assetList,
								Client.getGameBoard().getGameMatchName());
			}
			if (Client.getGenericState() instanceof ChooseCardState)
			{
				Client.getRmiServerInterface().takeCardDecision
						(string, Client.getAssignedID(), cardType,
								Client.getGameBoard().getGameMatchName());
			}

		 	if (Client.getGenericState() instanceof ChooseCostsState)
		 	{
			 	Client.getRmiServerInterface().takeCostsDecision
					 	(string, Client.getAssignedID(),
								Client.getGameBoard().getGameMatchName());
		 	}

		 	if (Client.getGenericState() instanceof  ChooseServantsState)
			{
				Client.getRmiServerInterface().takeServantsDecision
						(string, Client.getAssignedID(),
								Client.getGameBoard().getGameMatchName());
			}

			if (Client.getGenericState() instanceof ExcommunicateState)
			{
				Client.getRmiServerInterface().takeExcommunicationDecision
						(string, Client.getAssignedID(),
								Client.getGameBoard().getGameMatchName());
			}

			if (Client.getGenericState() instanceof PickCouncilState)
			{
				Client.getRmiServerInterface().takeCouncilDecision
						(string, Client.getAssignedID(), numberOfPrivileges,
								Client.getGameBoard().getGameMatchName());
			}

			if (Client.getGenericState() instanceof ChooseFamiliarState)
			{
				Client.getRmiServerInterface().takeFamiliarDecision
						(string, Client.getAssignedID(),
								Client.getGameBoard().getGameMatchName());
			}

			if (Client.getGenericState() instanceof PlayState)
			{
				Client.getRmiServerInterface().doAction(string, Client.getAssignedID(),
						Client.getGameBoard().getGameMatchName());
			}

         }
         catch (RemoteException e)
         {
			 LOGGER.log(Level.INFO, "CANNOT SEND ITEM TO CLIENT RETRY", e);
         }
	}
	
}
