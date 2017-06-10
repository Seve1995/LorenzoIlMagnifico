package it.polimi.ingsw.pc22.adapters;

import it.polimi.ingsw.pc22.actions.Action;
import it.polimi.ingsw.pc22.actions.ActionFactory;
import it.polimi.ingsw.pc22.actions.ServantsHandler;
import it.polimi.ingsw.pc22.connection.GameServer;
import it.polimi.ingsw.pc22.connection.User;
import it.polimi.ingsw.pc22.gamebox.*;
import it.polimi.ingsw.pc22.player.Player;
import it.polimi.ingsw.pc22.utils.CouncilPrivilege;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fandroid95 on 30/05/2017.
 */
public class SocketIOAdapter extends IOAdapter implements Runnable
{
    public Socket socket;

    private BufferedReader in;
    private PrintWriter out;

    public SocketIOAdapter(Socket socket)
    {
        this.socket = socket;

        try {

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream(), true);

        }
            catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void run()
    {
        authentication();
    }
    
    
    //TODO GESTIRE TIMEOUT
    @Override
    public List<Asset> chooseAssets(int numberOfAssets)
    {
        CouncilPrivilege councilPrivilege = new CouncilPrivilege();
        List<Asset> chosenAssets = new ArrayList<Asset>();
        
    	int i = 0;

        while (i<numberOfAssets)
        {
            this.printMessage("Choose one asset:" + '\n' + councilPrivilege);
           
            String choice = getMessage();
            
            if ("1".equals(choice) && councilPrivilege.getBonus1()!=null)
            {
            	chosenAssets.addAll(councilPrivilege.getBonus1());
            	
            	councilPrivilege.setBonus1(null);
            	
            	i++;
            }

            if ("2".equals(choice) && councilPrivilege.getBonus2()!=null)
            {
            	chosenAssets.addAll(councilPrivilege.getBonus2());
            	
            	councilPrivilege.setBonus2(null);
            	
            	i++;
            }

            if ("3".equals(choice) && councilPrivilege.getBonus3()!=null)
            {
            	chosenAssets.addAll(councilPrivilege.getBonus3());
            	
            	councilPrivilege.setBonus3(null);
            	
            	i++;
            }

            if ("4".equals(choice) && councilPrivilege.getBonus4()!=null)
            {
            	chosenAssets.addAll(councilPrivilege.getBonus4());
            	
            	councilPrivilege.setBonus4(null);
            	
            	i++;
            }

            if ("5".equals(choice) && councilPrivilege.getBonus5()!=null)
            {
            	chosenAssets.addAll(councilPrivilege.getBonus5());
            	
            	councilPrivilege.setBonus5(null);
            	
            	i++;
            }

        }

        return chosenAssets;
    }
    
    /* DA TOGLIERE?, era la vecchia implementazione del chooseOneAsset fatta da fabio 
    @Override
    public List<Asset> chooseOneAsset()
    {
        List<Asset> assets = new ArrayList<>();

        //TODO GESTIRE TIMEOUT
        while (true)
        {
            this.printMessage("Choose one asset among the available //one's:" + '\n'
            + "1) One stone & One wood" + '\n'
            + "2) Two servants" + '\n'
            + "3) Two coins" + '\n'
            + "4) Two military points" + '\n'
            + "5) Two faith points");

            String choice = getMessage();
            
            if ("1".equals(choice))
            {
                Asset wood = new Asset(1, AssetType.WOOD);
                Asset stone = new Asset(1, AssetType.STONE);

                assets.add(wood);
                assets.add(stone);

                break;
            }

            if ("2".equals(choice))
            {
                Asset servants = new Asset(1, AssetType.SERVANT);

                assets.add(servants);

                break;
            }

            if ("3".equals(choice))
            {
                Asset coins = new Asset(1, AssetType.COIN);

                assets.add(coins);

                break;
            }

            if ("4".equals(choice))
            {
                Asset military = new Asset(1, AssetType.MILITARYPOINT);

                assets.add(military);

                break;
            }

            if ("5".equals(choice))
            {
                Asset faith = new Asset(1, AssetType.FAITHPOINT);

                assets.add(faith);

                break;
            }

        }

        return assets;
    }
     */
    
    
    @Override
    public FamilyMember askFamiliarMember(Player player, Long timeout)
    {
        List<FamilyMember> unusedFamiliarMembers =
                player.getUnusedFamiliarMembers();

        Long maxTimeStamp = System.currentTimeMillis() + timeout;

        while(System.currentTimeMillis() < maxTimeStamp)
        {
            this.printMessage("Scegli un familiare tra quelli disponibili:");

            //TODO SISTEMARE STA COSA; BISOGNA CHIAMARE UNA FUNZIONE STATICA ESTERNA!!
            
            this.printMessage(unusedFamiliarMembers.toString());

            String choice = this.getMessage();

            if (choice == null) continue;

            ColorsEnum color = ColorsEnum.getColorFromString(choice);

            if (color == null) continue;

            FamilyMember member = player.getUnusedFamilyMemberByColor(color);

            if (member == null) continue;

            return member;
        }

        printMessage("Timeout Azione terminato");

        return null;
    }

    @Override
    public Asset askServants(Player player, Long timeout)
    {
        Long maxTimeStamp = System.currentTimeMillis() + timeout;

        while (System.currentTimeMillis() < maxTimeStamp)
        {
            this.printMessage("Voi sacrificare servitori per aumentare il valore dell'azione? \n" +
                    "Indica un numero da 0 a " + player.getServants());

            String value = getMessage();

            if (value == null) continue;

            Integer servantNumber;

            try
            {
                servantNumber = Integer.parseInt(value);
            }
            catch (NumberFormatException e)
            {
                this.printMessage("inserire numero valido");

                continue;
            }

            if (servantNumber > player.getServants()) continue;

            return new Asset(servantNumber, AssetType.SERVANT);
        }

        printMessage("Timeout Azione terminato");

        return null;
    }

    @Override
    public Action askAction(FamilyMember familyMember, Asset servant ,Long timeout)
    {
        Long maxTimeStamp = System.currentTimeMillis() + timeout;

        while (System.currentTimeMillis() < maxTimeStamp)
        {
            this.printMessage("Che azione vuoi eseguire?");

            this.printMessage("Seleziona operazione:\n" +
                    "- Metti familiare su torre (es: set tower BUILDING 3 con numberOfFloor tra 0 e 3)\n" +
                    "- Metti familiare su market (es: set market ZONA con zona tra 0 e 3)\n" +
                    "- Metti familiare nella produzione (es: set production)\n" +
                    "- Metti familiare nella raccolto (es: set harvest)\n" +
                    "- Metti familiare nel concilio (es: set council)");

            String choice = getMessage();

            if (choice == null) continue;

            Action action = ActionFactory.createAction(familyMember, choice);

            if (servant.getValue() > 0)
                return new ServantsHandler(action, servant);

            return action;
        }

        printMessage("Timeout Azione terminato");

        return null;
    }

    @Override
    public void endConnection(Player player) throws IOException
    {
        if (player != null)
        {
            String userName = player.getName();

            User user = GameServer.getUsersMap().get(userName);

            user.setLogged(false);
        }

        PrintWriter printWriter =
                new PrintWriter(socket.getOutputStream(), true);

        printWriter.println("EXIT");

        socket.close();
    }

    @Override
    public void printMessage(String message)
    {
        out.println(message);
    }

    @Override
    public String getMessage()
    {
        String answer;

        try
        {
            answer = in.readLine();

        }
            catch (IOException e)
        {

            e.printStackTrace();

            return null;
        }

        return  answer;
    }

	@Override
	public int askFloor() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public CardTypeEnum askForCardType() {
		// TODO Auto-generated method stub
		return null;
	}
}
