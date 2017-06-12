package it.polimi.ingsw.pc22.adapters;

import it.polimi.ingsw.pc22.actions.Action;
import it.polimi.ingsw.pc22.actions.ActionFactory;
import it.polimi.ingsw.pc22.connection.GameMatch;
import it.polimi.ingsw.pc22.connection.GameServer;
import it.polimi.ingsw.pc22.connection.User;
import it.polimi.ingsw.pc22.gamebox.*;
import it.polimi.ingsw.pc22.player.Player;
import it.polimi.ingsw.pc22.utils.CouncilPrivilege;
import it.polimi.ingsw.pc22.utils.PositionUtils;
import it.polimi.ingsw.pc22.utils.UserLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public Action askAction(GameBoard gameBoard, Player player)
    {
        Long maxTimeStamp = System.currentTimeMillis() + timeout;

        while (System.currentTimeMillis() < maxTimeStamp)
        {
            this.printMessage("Choose an action to execute:");

            if (!player.isFamiliarPositioned())
            {
                String actions = PositionUtils.getActionAvailableString(gameBoard);

                this.printMessage(actions);

                this.printMessage("Voi sacrificare servitori per aumentare il valore dell'azione? \n" +
                        "Indica un numero da 0 a " + player.getServants());

                this.printMessage("Familiari disponibili: " +
                        player.getUnusedFamiliarMembers().toString());
            }

            this.printMessage("- play card" + '\n' +
                "- discard card" + '\n' +
                "- turn card" + '\n' +
                "- pass" + '\n' +
                "- show cards" + '\n' + //questa in realtà si può sempre fare
                "- end game / exit game" + '\n'+
                "- show board"); //questa in realtà si può sempre fare

            String choice = getMessage();

            if (choice == null) continue;

            //COMMMENTO AI POSTERI V2 QUESTA COSA MI FA CACARE MA SINCERCAMENTE
            //PER ORA NON HA SENSO CREAE DELLE ACTIONS CHE FACCIANO STE COSE
            if ("show board".equals(choice))
            {
                PlayerBoard board = player.getPlayerBoard();

                player.getAdapter().printMessage(board.toString());

                continue;
            }


            if ("show cards".equals(choice))
            {
                PlayerBoard board = player.getPlayerBoard();

                IOAdapter adapter = player.getAdapter();

                adapter.printMessage(board.getTerritories().toString());
                adapter.printMessage(board.getBuildings().toString());
                adapter.printMessage(board.getCharacters().toString());
                adapter.printMessage(board.getVentures().toString());

                //adapter.printMessage(board.getLeaderCards().toString());

                continue;
            }

            Action action = ActionFactory.createAction(choice, player);

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

    public int askExcommunication() {

        Long maxTimeStamp = System.currentTimeMillis() + timeout;

        while(System.currentTimeMillis() < maxTimeStamp)
        {
            this.printMessage("Choose an action:\n"
                    + "1) Show your support to the Church (you'll lose all yours faith points & you'll gain victory points)\n"
                    + "2) Take the excommunication (you won't lose your faith points)");

            String value = getMessage();

            if (value == null) continue;

            Integer choice;

            try
            {
                choice = Integer.parseInt(value);
            }
            catch (NumberFormatException e)
            {
                this.printMessage("ERROR! You must enter a valid input");

                continue;
            }

            if (choice !=1 || choice !=2) continue;

            return choice;
        }

        printMessage("Timeout Azione terminato");

        return -1;

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

    void authenticate() throws IOException
    {
        User user = null;

        while (user == null)
        {
            this.printMessage("Write: <username> <password> l/s");

            String answer = getMessage();

            if (answer == null)
            {
                endConnection(null);

                return;
            }

            Pattern loginPattern = Pattern.compile("(^(\\w+) (\\w+) (L|S)$)");

            Matcher matcher = loginPattern.matcher(answer);

            if (!matcher.find())
            {
                printMessage("Invalid INPUT");

                continue;
            }

            String[] login = answer.split(" ");

            switch (login[2])
            {
                case "L":

                    user = loginUser(login[0],login[1]);

                    break;
                case "S":

                    user = signUp(login[0], login[1]);

                    break;

                default:

                        printMessage("ERROR! You must enter a valid input");

                    break;
            }
        }

        gameHandling(user);
    }

    private void gameHandling(User user) throws IOException
    {
        String playerName = user.getUsername();

        Player player = new Player();

        player.setName(playerName);

        boolean gameHandling = false;

        while (!gameHandling)
        {
            printMessage("Write: <gamename> C|J|R");
            printMessage
            (
                "C to create a new Game" + '\n' +
                "J to join an existing Game" + '\n' +
                "R to join a random Game" + '\n'
            );

            String answer = getMessage();

            Pattern gameMatcher = Pattern.compile("(^(\\w+) (C|J|R)$)");

            Matcher matcher = gameMatcher.matcher(answer);

            if (!matcher.find()) continue;

            String[] choice = answer.split(" ");

            Map<String, GameMatch> gameMatchMap = GameServer.getGameMatchMap();

            switch (choice[1])
            {
                case "C":

                    gameHandling = createNewGame
                            (gameMatchMap, choice[0], player);

                    break;
                case "J":

                    gameHandling = addToExistingGame
                            (gameMatchMap, choice[0], player);

                    break;

                case "R":


                    break;

                default:

                    printMessage("Non-valid input. Please retry... ");

                    break;
            }
        }

        updateJson();
    }

    private synchronized boolean createNewGame
        (Map<String, GameMatch> gameMatchMap, String gameName, Player player)
            throws IOException
    {
        boolean existingGameMatch = gameMatchMap.containsKey(gameName);

        if (existingGameMatch)
        {
            printMessage("A game match with the specified name already exists.");

            return false;
        }

        startNewGameMatch(gameName, player);

        printMessage("Player: " + player.getName() + " created GameMatch - " + gameName);

        return true;
    }

    private synchronized boolean addToExistingGame
        (Map<String, GameMatch> gameMatchMap, String gameName, Player player)
            throws IOException
    {
        boolean existingGameMatch = gameMatchMap.containsKey(gameName);

        if (!existingGameMatch)
        {
            printMessage("Game match not found.");

            return false;
        }

        GameMatch gameMatch = gameMatchMap.get(gameName);

        //TODO FARE IN MODO CHE SI POSSA RILOGGARE

        if (gameMatch.getStarted())
        {
            printMessage("Game already started");

            return false;
        }

        if (gameMatch.getMaxPlayersNumber() == gameMatch.getPlayerCounter())
        {
            printMessage("Game is full");

            return false;
        }

        player.setAdapter(this);

        List<Player> players = gameMatch.getPlayers();

        players.add(player);

        gameMatch.setPlayers(players);

        int counter = gameMatch.getPlayerCounter() + 1;

        gameMatch.setPlayerCounter(counter);

        if (gameMatch.getPlayerCounter() == 2)
            new Thread(gameMatch).start();

        printMessage("Player: " + player.getName() + " joined GameMatch - " + gameName);

        return true;
    }


    private synchronized User loginUser(String username, String password)
    {
        Map<String, User> usersMap = GameServer.getUsersMap();

        User user = usersMap.get(username);

        if (user == null)
        {
            printMessage("User not found");

            return null;
        }

        if (!user.getPassword().equals(password))
        {
            printMessage("Wrong password");

            return null;
        }

        if (user.isLogged())
        {
            printMessage("Invalid login");

            return null;
        }

        return user;
    }

    private synchronized User signUp(String username, String password) throws IOException
    {
        Map<String, User> usersMap = GameServer.getUsersMap();

        User user = usersMap.get(username);

        if (user != null)
        {
            printMessage("Invalid login");

            return null;
        }

        user = new User(username, password, false);

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

    synchronized private void updateJson() throws IOException
    {
        Map<String, User> usersMap = GameServer.getUsersMap();

        UserLoader.refreshJson(usersMap);
    }

}