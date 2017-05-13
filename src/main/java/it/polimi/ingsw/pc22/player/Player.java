package it.polimi.ingsw.pc22.player;

public class Player {
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
	private FamilyMember[] familyMember;
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
	public FamilyMember[] getFamilyMember() {
		return familyMember;
	}
	public void setFamilyMember(FamilyMember[] familyMember) {
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
	
}
