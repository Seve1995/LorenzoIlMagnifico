package it.polimi.ingsw.pc22.messages;

import it.polimi.ingsw.pc22.player.Player;

import java.util.List;

/**
 * Created by fandroid95 on 01/07/2017.
 */
public class EndMatchMessage extends Message
{
    //CLASSIFICA DI TUTTI I GIOCATORI ISCRITTI AL SERVER
    private List<Player> standing;

    private String winnerName;

    private int winnerVictoryPoints;
    
    public EndMatchMessage(List<Player> standing, String winnerName, int winnerVictoryPoints)
    {
        this.standing = standing;
        this.winnerName = winnerName;
        this.winnerVictoryPoints = winnerVictoryPoints;
    }

    public List<Player> getStanding()
    {
        return standing;
    }

    public String getWinnerName()
    {
        return winnerName;
    }
    
    public int getWinnerVictoryPoints()
    {
        return winnerVictoryPoints;
    }

	@Override
	public String toString() {
		StringBuilder output = new StringBuilder("The winner is " + winnerName + "\n");
		output.append("Victory points of the winner:" + winnerVictoryPoints);
		for (Player p : standing)
		{
			output.append(p.getUsername() + " W: " + p.getNumberOfMatchWon() + " L: " + p.getNumberOfMatchLost());
		}
		return output.toString();
	}
    
    
}
