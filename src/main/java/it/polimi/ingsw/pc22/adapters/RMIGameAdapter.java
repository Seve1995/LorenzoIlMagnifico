package it.polimi.ingsw.pc22.adapters;

import it.polimi.ingsw.pc22.actions.Action;
import it.polimi.ingsw.pc22.actions.SettingFamiliarMemberOnCouncilPalace;
import it.polimi.ingsw.pc22.actions.SettingFamiliarMemberOnMarket;
import it.polimi.ingsw.pc22.connection.GameServer;
import it.polimi.ingsw.pc22.connection.User;
import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;
import it.polimi.ingsw.pc22.rmi.RMIClientStreamService;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

/**
 * Created by fandroid95 on 30/05/2017.
 */
public class RMIGameAdapter implements GameAdapter
{
    Registry registry;

    RMIClientStreamService streamService;

    public RMIGameAdapter(Registry registry)
    {
        this.registry = registry;

        try
        {
            streamService = (RMIClientStreamService) registry.lookup("client");
        }
        catch (RemoteException | NotBoundException e)
        {
                e.printStackTrace();
        }
    }

    @Override
    public void endConnection(Player player) throws IOException
    {
        String userName = player.getName();

        User user = GameServer.getUsersMap().get(userName);

        user.setLogged(false);
    }

    @Override
    public void printMessage(String message)
    {
        try
        {
            streamService.printMessage(message);
        }
            catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public String getMessage()
    {
        String message = null;

        try
        {
            message = streamService.getMessage();
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }

        return message;
    }

	
	//public Action askAction(FamilyMember familyMember, Player p, GameBoard gameBoard) {
		/*
		 * [PARTE IL TIMEOUT]
		OPERAZIONE 1:
		0) FamilyMember White, Value: x
		1) FamilyMember Blue, Value: 
		...
		
		da 0 a massimo valore
		[CONTROLLA IL TIMEOUT]
		
		
		OPERAZIONE 2:
		-> Inserire numero di servitori da scartare (0-> Nessun servitore, nMaxServitori)
		
		OPERAZIONE 3:
		Seleziona operazione:
		- Metti familiare su torre (es: set tower BUILDING 3 con numberOfFloor tra 0 e 3)
		- Metti familiare su market (es: set market ZONA con zona tra 0 e 3)
		- Metti familiare nella produzione (es: set production)
		- Metti familiare nella raccolto (es: set harvest)
		- Metti familiare nel concilio (es: set council)
		 */
		
		//this.printMessage("WE ZIO DIMMI L'AZIONE!");
		//String risposta = getMessage();
		//if (risposta.equalsIgnoreCase("set market"))
		//	return new SettingFamiliarMemberOnMarket(familyMember, gameBoard.getMarket(), zone) ;
	//}

	@Override
	public Action askAction() {
		// TODO Auto-generated method stub
		return null;
	}
}
