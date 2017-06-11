package it.polimi.ingsw.pc22.adapters;

import it.polimi.ingsw.pc22.actions.Action;
import it.polimi.ingsw.pc22.actions.ActionFactory;
import it.polimi.ingsw.pc22.actions.ServantsHandler;
import it.polimi.ingsw.pc22.connection.GameMatch;
import it.polimi.ingsw.pc22.connection.GameServer;
import it.polimi.ingsw.pc22.connection.User;
import it.polimi.ingsw.pc22.gamebox.*;
import it.polimi.ingsw.pc22.player.Player;
import it.polimi.ingsw.pc22.utils.CouncilPrivilege;
import it.polimi.ingsw.pc22.utils.UserLoader;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by fandroid95 on 30/05/2017.
 */
public abstract class IOAdapter
{
    private Long timeout;

    public abstract void endConnection(Player player) throws IOException;

    public abstract void  printMessage(String message);

    public abstract String getMessage();

    public Long getTimeout()
    {
        return timeout;
    }

    public void setTimeout(Long timeout)
    {
        this.timeout = timeout;
    }

    public Action askAction(FamilyMember familyMember, Asset servant)
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

            if (servant.getValue() > 0 && action != null)
                return new ServantsHandler(action, servant);

            return action;
        }

        printMessage("Timeout Azione terminato");

        return null;
    }

    public int askFloor() {
    	
        Long maxTimeStamp = System.currentTimeMillis() + timeout;

        while(System.currentTimeMillis() < maxTimeStamp)
        {
	            this.printMessage("Ok, now select the tower floor:");
	
	            String value = getMessage();
	
	            if (value == null) continue;
	
	            Integer floor;
	
	            try
	            {
	                floor = Integer.parseInt(value);
	            }
	            catch (NumberFormatException e)
	            {
	                this.printMessage("ERROR! You must enter a valid input");
	
	                continue;
	            }
	
	            if (floor <0 || floor > 3) continue;
	
	            return floor;
        }
        
        printMessage("Timeout Azione terminato");

        return -1;
    
    }

    public CardTypeEnum askForCardType() {
    	
        Long maxTimeStamp = System.currentTimeMillis() + timeout;

        while(System.currentTimeMillis() < maxTimeStamp)
        {
	            this.printMessage("Select a Card Type to pick:");
	
	            String value = getMessage();
	            
	            if (value == null) continue;
	
	            CardTypeEnum cardType = CardTypeEnum.valueOf(value);
	            
	            if (cardType == null) continue;
	            
	            return cardType;
        }
        
        printMessage("Timeout expired");

        return null;
    
    }

    public FamilyMember askFamiliarMember(Player player)
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

    public FamilyMember askFamiliarMemberForBonus(Player player) 
    {
        List<FamilyMember> familyMembers =
                player.getFamilyMembers();
        
        Long maxTimeStamp = System.currentTimeMillis() + timeout;

        while(System.currentTimeMillis() < maxTimeStamp)
        {
        	   this.printMessage("Choose the familiar member for the bonus:");

	            //TODO SISTEMARE STA COSA; BISOGNA CHIAMARE UNA FUNZIONE STATICA ESTERNA!!
	            
	            this.printMessage(familyMembers.toString());
	
	            String choice = this.getMessage();
	
	            if (choice == null) continue;
	
	            ColorsEnum color = ColorsEnum.getColorFromString(choice);
	
	            if (color == null) continue;
	
	            FamilyMember member = player.getFamilyMemberByColor(color);
	
	            if (member == null) continue;
	
	            return member;
        
        }
        
        printMessage("Timeout Azione terminato");

        return null;
    }
    
    public Asset askServants(Player player)
    {

        Long maxTimeStamp = System.currentTimeMillis() + timeout;

        while(System.currentTimeMillis() < maxTimeStamp)
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
	                this.printMessage("ERROR! You must enter a valid input");
	
	                continue;
	            }
	
	            if (servantNumber > player.getServants()) continue;
	
	            return new Asset(servantNumber, AssetType.SERVANT);
        }
        
        printMessage("Timeout Azione terminato");

        return null;
    }

    //SISTEMARE IL CONCETTO DI PRIVILEGIO DEL CONSIGLIO, MEGLIO GESTIRE CON UNA MAPPA
    // O UNA ENUM INVECE CHE CON I NULL IN QUESTO MODO SI VA ANCHE A TOGLIERE LA NECESSITÀ
    // DEGLI IF, POI VISTO CHE VIENE USATO SOLO QUI MEGLIO CREARE UNA CLASSE
    // UTILIÀ CHE GESTISCA IL PRIVILEGIO INVECE CHE UN OGGETTO DA INSTANZIARE OGNI VOLTA,
    //PS COMUNQUE E GIUSTA L'IDEA, NON MI RICORDAVO PIÙ CHE CI FOSSE DA SCEGLIERE TRAI PRIVILEGI
    public List<Asset> chooseCouncilPrivileges(int numberOfAssets)
    {
        CouncilPrivilege councilPrivilege = new CouncilPrivilege();
        List<Asset> chosenAssets = new ArrayList<>();

        int i = 0;
        Long maxTimeStamp = System.currentTimeMillis() + timeout;

        while(System.currentTimeMillis() < maxTimeStamp || i < numberOfAssets)
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

                if (i==numberOfAssets)
                	
                	return chosenAssets;
            }
        }
        
        printMessage("Timeout Azione terminato");
        
        return null;

    }
    
    public List<Asset> chooseAssets(int numberOfAssets, List<Asset> assets)
    {
        List<Asset> chosenAssets = new ArrayList<Asset>();
        
    	int i = 0;
    	
        Long maxTimeStamp = System.currentTimeMillis() + timeout;

        while(System.currentTimeMillis() < maxTimeStamp || i < numberOfAssets)
        {
            this.printMessage("Choose one asset:" + '\n');
            for (int j=0; j<assets.size(); j++)
            {
            	this.printMessage(j + ")" + assets.get(j).toString());
            }
            
            try{
            	String choice = getMessage();
            	
            	int choiceInt = Integer.parseInt(choice);
            	
                if (choiceInt<0 || choiceInt>assets.size()) throw new NumberFormatException();
                
                chosenAssets.add(assets.get(choiceInt));
                
                assets.remove(choiceInt);
                
                i++;
                
                if (i==numberOfAssets)
                	
                	return chosenAssets;
                
            } catch(NumberFormatException e) {
            	
            	this.printMessage("ERROR! You must enter a valid input");
            	
            	continue;
            }

        }

        printMessage("Timeout Azione terminato");

        return null;
    }
    
    public int chooseCost(Asset militaryPointsRequired, Asset militaryPointsCost, List<Asset> resourcesCost){
    	this.printMessage("You have to choose one cost between:");
    	this.printMessage("1) You must have" + militaryPointsRequired.getValue() + "and you will pay" + militaryPointsCost.getValue());	
    	this.printMessage("2) Pay these resources:" + resourcesCost.toString());		
        Long maxTimeStamp = System.currentTimeMillis() + timeout;

        while(System.currentTimeMillis() < maxTimeStamp)
	    {
	    	try{
	        	String choice = getMessage();
	        	
	        	int choiceInt = Integer.parseInt(choice);
	        	
	            if (choiceInt!=1 || choiceInt!=2) throw new NumberFormatException();
	            
		    	return choiceInt;

	    	} catch(NumberFormatException e) {
	        	
	        	this.printMessage("ERROR! You must enter a valid input");
	        	
	        	continue;
	        }
	    	
	    }
        
        printMessage("Timeout Azione terminato");

        return -1;

    }
    
    void authentication()
    {
        try
        {
            User user = null;

            while (user == null)
            {
                this.printMessage("Sign up or Login?");

                String answer = getMessage();

                if (answer == null)
                {
                    endConnection(null);

                    return;
                }

                switch (answer)
                {
                    case "login":

                        user = loginUser();

                        break;
                    case "sign up":

                        user = signUp();

                        break;

                    default:

                        printMessage("ERROR! You must enter a valid input");

                        break;
                }
            }

            String playerName = user.getUsername();

            Player player = new Player();

            player.setName(playerName);


            boolean gameHandling = false;

            while (!gameHandling)
            {
                printMessage("Choose an operation:\n"
                        + "(1) Create new game match\n"
                        + "(2) Join a friend's game match\n"
                        + "(3) Join a random game match"
                );

                String userChoice = getMessage();

                if ("1".equals(userChoice))
                {
                    gameHandling = createNewGame(player);
                }

                if ("2".equals(userChoice))
                {
                    gameHandling = checkExistingGame(player);
                }

                //TODO
                /*if ("3".equals(userChoice))
                {
                }*/
            }

            updateJson();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    
    private void checkPassword(User user) throws IOException
    {
        String password = getMessage();

        if (password == null)
        {
            printMessage("Timeout expired retry");

            user.setLogged(false);
        }

        if (user.getPassword().equals(password))
        {
            printMessage("Successful logged in!");

            user.setLogged(true);

            return;
        }

        printMessage("Error: wrong password! Please retry.");

        user.setLogged(false);
    }

    private User getUserFromUserName() throws IOException
    {
        printMessage("Type an existing username:");

        String username = getMessage();

        User user = existingUsername(username);

        if (user != null)
        {
            printMessage("Username OK. Now type the password:");

            return user;
        }

        printMessage("Error: username not found! Please retry.");

        return null;
    }

    private User registerNewUser() throws IOException
    {
        printMessage("Type an username:");
        String username = getMessage();

        printMessage("Type a password");

        String password = getMessage();

        Boolean invalidUsername =
                GameServer.getUsersMap().containsKey(username);

        if (invalidUsername)
        {
            printMessage("The specified username already exist! Please type a new username.");
            return null;
        }

        printMessage("User created!");

        return new User(username, password, true);
    }

    private boolean createNewGame(Player player) throws IOException
    {
        printMessage("Type a name for the new game match:");

        String gameName = getMessage();

        if (gameName == null) return false;

        printMessage("Game name: " + gameName);

        boolean existingGameMatch =
                GameServer.getGameMatchMap().containsKey(gameName);

        if (existingGameMatch)
        {
            printMessage("A game match with the specified name already exists.");

            return false;
        }

        startNewGameMatch(gameName, player);

        printMessage("Player: " + player.getName() + " created GameMatch - " + gameName);

        return true;
    }

    private boolean checkExistingGame(Player player) throws IOException
    {
        printMessage("Type the name of the chosen game match:");

        String gameName = getMessage();

        if (gameName == null) return false;

        printMessage("Game name: " + gameName);

        boolean existingGameMatch =
                GameServer.getGameMatchMap().containsKey(gameName);

        if (!existingGameMatch) {
            printMessage("Game match not found.");

            return false;
        }

        insertIntoExistingGameMatch(gameName, player);

        printMessage("Player: " + player.getName() + " joined GameMatch - " + gameName);

        return true;
    }


    private User loginUser() throws IOException
    {
        User user = null;

        while(user == null)
        {
            user = getUserFromUserName();
        }

        while(!user.isLogged())
        {
            checkPassword(user);
        }

        return user;
    }

    synchronized private User existingUsername(String username)
    {
        Map<String, User> usersMap = GameServer.getUsersMap();

        User user = usersMap.get(username);

        if (user == null) return null;

        if (user.isLogged()) return null;

        return  user;
    }

    private User signUp() throws IOException
    {
        User user = null;

        while (user == null)
        {
            user = registerNewUser();
        }

        Map<String, User> usersMap = GameServer.getUsersMap();

        usersMap.put(user.getUsername(), user);

        return user;
    }

    //TODO FAR SI CHE I VALORI VENGANO GESTITI DAL PARSER JSON
    //TODO2 DISTINGUERE IL TIMEOUT DELL'AVVIO PARTITA DAL TIMEOUT DELL'AZIONE (ce ne vogliono 2 distinti)
    private void startNewGameMatch(String gameName, Player player)
    {
        GameMatch gameMatch = new GameMatch(15000L, 4);

        Map<String, GameMatch> gameMatchMap = GameServer.getGameMatchMap();

        gameMatchMap.put(gameName, gameMatch);

        player.setAdapter(this);

        List<Player> players = new ArrayList<>();

        players.add(player);

        gameMatch.setPlayers(players);

        int counter = gameMatch.getPlayerCounter() + 1;

        gameMatch.setPlayerCounter(counter);
    }


    synchronized private void insertIntoExistingGameMatch
            (String gameName, Player player)
    {
        Map<String, GameMatch> gameMatchMap = GameServer.getGameMatchMap();

        GameMatch gameMatch = gameMatchMap.get(gameName);

        player.setAdapter(this);

        List<Player> players = gameMatch.getPlayers();

        players.add(player);

        gameMatch.setPlayers(players);

        int counter = gameMatch.getPlayerCounter() + 1;

        gameMatch.setPlayerCounter(counter);

        if (gameMatch.getPlayerCounter() == 2)
            new Thread(gameMatch).start();

    }

    synchronized private void updateJson() throws IOException
    {
        Map<String, User> usersMap = GameServer.getUsersMap();

        UserLoader.refreshJson(usersMap);
    }

}