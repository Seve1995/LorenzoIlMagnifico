package it.polimi.ingsw.pc22.player;

import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.gamebox.PlayerBoard;

import java.util.List;

public class Player 
{
	private String name;
	private int numberOfMatch;
	private int Woods;
	private int Stones;
	private int Servants;
	private int Coins;
	private int militaryPoints;
	private int faithPoints;
	private int victoryPoints;
	private int priority;
	private boolean newAction; 
	private boolean removeTowerBonus;
	private List<FamilyMember> familyMember;
	private GameBoard gameBoard;
	private PlayerBoard playerBoard;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNumberOfMatch() {
		return numberOfMatch;
	}
	public void setNumberOfMatch(int numberOfMatch) {
		this.numberOfMatch = numberOfMatch;
	}
	public int getWoods() {
		return Woods;
	}
	public void setWoods(int woods) {
		Woods = woods;
	}
	public int getStones() {
		return Stones;
	}
	public void setStones(int stones) {
		Stones = stones;
	}
	public int getServants() {
		return Servants;
	}
	public void setServants(int servants) {
		Servants = servants;
	}
	public int getCoins() {
		return Coins;
	}
	public void setCoins(int coins) {
		Coins = coins;
	}
	public int getMilitaryPoints() {
		return militaryPoints;
	}
	public void setMilitaryPoints(int militaryPoints) {
		this.militaryPoints = militaryPoints;
	}
	public int getFaithPoints() {
		return faithPoints;
	}
	public void setFaithPoints(int faithPoints) {
		this.faithPoints = faithPoints;
	}
	public int getVictoryPoints() {
		return victoryPoints;
	}
	public void setVictoryPoints(int victoryPoints) {
		this.victoryPoints = victoryPoints;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public boolean isNewAction() {
		return newAction;
	}
	public void setNewAction(boolean newAction) {
		this.newAction = newAction;
	}
	
	public boolean isRemoveTowerBonus() {
		return removeTowerBonus;
	}
	
	public void setRemoveTowerBonus(boolean removeTowerBonus) {
		this.removeTowerBonus = removeTowerBonus;
	}
	
	public List<FamilyMember> getFamilyMember() {
		return familyMember;
	}
	public void setFamilyMember(List<FamilyMember> familyMember) {
		this.familyMember = familyMember;
	}
	public GameBoard getGameBoard() {
		return gameBoard;
	}
	public void setGameBoard(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
	}
	public PlayerBoard getPlayerBoard() {
		return playerBoard;
	}
	public void setPlayerBoard(PlayerBoard playerBoard) {
		this.playerBoard = playerBoard;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Coins;
		result = prime * result + Servants;
		result = prime * result + Stones;
		result = prime * result + Woods;
		result = prime * result + faithPoints;
		result = prime * result + ((familyMember == null) ? 0 : familyMember.hashCode());
		result = prime * result + ((gameBoard == null) ? 0 : gameBoard.hashCode());
		result = prime * result + militaryPoints;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (newAction ? 1231 : 1237);
		result = prime * result + numberOfMatch;
		result = prime * result + ((playerBoard == null) ? 0 : playerBoard.hashCode());
		result = prime * result + priority;
		result = prime * result + victoryPoints;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (Coins != other.Coins)
			return false;
		if (Servants != other.Servants)
			return false;
		if (Stones != other.Stones)
			return false;
		if (Woods != other.Woods)
			return false;
		if (faithPoints != other.faithPoints)
			return false;
		if (familyMember == null) {
			if (other.familyMember != null)
				return false;
		} else if (!familyMember.equals(other.familyMember))
			return false;
		if (gameBoard == null) {
			if (other.gameBoard != null)
				return false;
		} else if (!gameBoard.equals(other.gameBoard))
			return false;
		if (militaryPoints != other.militaryPoints)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (newAction != other.newAction)
			return false;
		if (numberOfMatch != other.numberOfMatch)
			return false;
		if (playerBoard == null) {
			if (other.playerBoard != null)
				return false;
		} else if (!playerBoard.equals(other.playerBoard))
			return false;
		if (priority != other.priority)
			return false;
		if (victoryPoints != other.victoryPoints)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Player [name=" + name + ", numberOfMatch=" + numberOfMatch + ", Woods=" + Woods + ", Stones=" + Stones
				+ ", Servants=" + Servants + ", Coins=" + Coins + ", militaryPoints=" + militaryPoints
				+ ", faithPoints=" + faithPoints + ", victoryPoints=" + victoryPoints + ", priority=" + priority
				+ ", newAction=" + newAction + ", familyMember=" + familyMember + ", gameBoard=" + gameBoard
				+ ", playerBoard=" + playerBoard + "]";
	}
}
