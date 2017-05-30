package it.polimi.ingsw.pc22.player;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.AssetType;
import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.gamebox.LeaderCard;
import it.polimi.ingsw.pc22.gamebox.PlayerBoard;

import java.util.List;

public class Player 
{	
	private String name;
	private int numberOfMatch;
	private int woods;
	private int stones;
	private int servants;
	private int coins;
	private int militaryPoints;
	private int faithPoints;
	private int victoryPoints;
	private int priority;
	private List<LeaderCard> leaderCards;
	private List<CardModifier> cardModifiers;
	private int haverstValueModifier; //Serve per gestire l'effetto AddHarvestValueBonus
	private int productionValueModifier; //Serve per gestire l'effetto AddProductionValueBonus
	private boolean militaryPointsMalus; //Serve per gestire l'effetto della tessera scomunica
	private boolean coinMalus; //Serve per gestire l'effetto della tessera scomunica
	private boolean servantMalus; //Serve per gestire l'effetto della tessera scomunica
	private boolean woodMalus; //Serve per gestire l'effetto della tessera scomunica
	private boolean stoneMalus; //Serve per gestire l'effetto della tessera scomunica
	private boolean familyMemberMalus; //Serve per gestire l'effetto della tessera scomunica
	private boolean disableMarket; //Serve per gestire l'effetto della tessera scomunica
	private boolean servantsHandlerMalus; //Serve per gestire l'effetto della tessera scomunica
	private boolean noFirstAction; //Serve per gestire l'effetto della tessera scomunica
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
		return woods;
	}
	public void setWoods(int woods) {
		this.woods = woods;
	}
	public int getStones() {
		return stones;
	}
	public void setStones(int stones) {
		this.stones = stones;
	}
	public int getServants() {
		return servants;
	}
	public void setServants(int servants) {
		this.servants = servants;
	}
	public int getCoins() {
		return coins;
	}
	public void setCoins(int coins) {
		this.coins = coins;
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
	public List<CardModifier> getCardModifier() {
		return cardModifiers;
	}
	public void setCardModifier(List<CardModifier> cardModifiers) {
		this.cardModifiers = cardModifiers;
	}
	public int getHaverstValueModifier() {
		return haverstValueModifier;
	}
	public void setHaverstValueModifier(int haverstValueModifier) {
		this.haverstValueModifier = haverstValueModifier;
	}
	public int getProductionValueModifier() {
		return productionValueModifier;
	}
	public void setProductionValueModifier(int productionValueModifier) {
		this.productionValueModifier = productionValueModifier;
	}
	public boolean isMilitaryPointsMalus() {
		return militaryPointsMalus;
	}
	public void setMilitaryPointsMalus(boolean militaryPointsMalus) {
		this.militaryPointsMalus = militaryPointsMalus;
	}
	public boolean isCoinMalus() {
		return coinMalus;
	}
	public void setCoinMalus(boolean coinMalus) {
		this.coinMalus = coinMalus;
	}
	public boolean isServantMalus() {
		return servantMalus;
	}
	public void setServantMalus(boolean servantMalus) {
		this.servantMalus = servantMalus;
	}
	public boolean isWoodMalus() {
		return woodMalus;
	}
	public void setWoodMalus(boolean woodMalus) {
		this.woodMalus = woodMalus;
	}
	public boolean isStoneMalus() {
		return stoneMalus;
	}
	public void setStoneMalus(boolean stoneMalus) {
		this.stoneMalus = stoneMalus;
	}
	public boolean isFamilyMemberMalus() {
		return familyMemberMalus;
	}
	public void setFamilyMemberMalus(boolean familyMemberMalus) {
		this.familyMemberMalus = familyMemberMalus;
	}
	public boolean isDisableMarket() {
		return disableMarket;
	}
	public void setDisableMarket(boolean disableMarket) {
		this.disableMarket = disableMarket;
	}
	public boolean isServantsHandlerMalus() {
		return servantsHandlerMalus;
	}
	public void setServantsHandlerMalus(boolean servantsHandlerMalus) {
		this.servantsHandlerMalus = servantsHandlerMalus;
	}
	public boolean isNoFirstAction() {
		return noFirstAction;
	}
	public void setNoFirstAction(boolean noFirstAction) {
		this.noFirstAction = noFirstAction;
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
	
	public List<LeaderCard> getLeaderCards() {
		return leaderCards;
	}
	public void setLeaderCards(List<LeaderCard> leaderCards) {
		this.leaderCards = leaderCards;
	}
	public int getAsset(AssetType assetType) 
	{
		String assetTypeString = assetType.toString().toLowerCase();
		
		switch (assetTypeString) {
		
		case "coin":
			return this.coins;
			
		case "wood":
			return this.woods;
			
		case "stone":
			return this.stones;
			
		case "servant":
			return this.servants;
			
		case "faithPoint":
			return this.faithPoints;
			
		case "victoryPoint":
			return this.victoryPoints;
			
		case "militaryPoint":
			return this.militaryPoints;
			
		default:
			return -1;
		}
		
	}
	
	public void addAsset(Asset asset) 
	{
		String assetType = asset.getType().toString().toLowerCase();
		
		int value = asset.getValue();
		
		switch (assetType) {
		
		case "coin":
			this.coins += value;
			break;
			
		case "wood":
			this.woods += value;
			break;
			
		case "stone":
			this.stones += value;
			break;
			
		case "servant":
			this.servants += value;
			break;
			
		case "faithPoint":
			this.faithPoints += value;
			break;
			
		case "victoryPoint":
			this.faithPoints += value;
			break;
			
		case "militaryPoint":
			this.militaryPoints += value;
			
		default:
			break;
		}
		
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + coins;
		result = prime * result + servants;
		result = prime * result + stones;
		result = prime * result + woods;
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
		if (coins != other.coins)
			return false;
		if (servants != other.servants)
			return false;
		if (stones != other.stones)
			return false;
		if (woods != other.woods)
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
		return "Player [name=" + name + ", numberOfMatch=" + numberOfMatch + ", Woods=" + woods + ", Stones=" + stones
				+ ", Servants=" + servants + ", Coins=" + coins + ", militaryPoints=" + militaryPoints
				+ ", faithPoints=" + faithPoints + ", victoryPoints=" + victoryPoints + ", priority=" + priority
				+ ", newAction=" + newAction + ", familyMember=" + familyMember + ", gameBoard=" + gameBoard
				+ ", playerBoard=" + playerBoard + "]";
	}
}
