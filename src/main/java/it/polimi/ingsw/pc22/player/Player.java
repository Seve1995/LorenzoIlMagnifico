package it.polimi.ingsw.pc22.player;

import it.polimi.ingsw.pc22.adapters.IOAdapter;
import it.polimi.ingsw.pc22.effects.AddEndGameVictoryPoints;
import it.polimi.ingsw.pc22.gamebox.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Player implements Serializable
{
	private String username;

	private String password;

	private boolean isLogged = false;

	private boolean isSuspended = false;

	private PlayerColorsEnum playerColorsEnum;
	private int numberOfMatch;
	private int woods = 2;
	private int stones = 2;
	private int servants = 3;
	private int coins;
	private int militaryPoints;
	private int faithPoints;
	private int victoryPoints;
	private int priority;
	private int endGameVictoryPoints;
	private List<LeaderCard> leaderCards = new ArrayList<>();
	private List<CardModifier> cardModifiers = new ArrayList<>();
	private List<AddEndGameVictoryPoints> addEndGameVictoryPoints;
	private int harvestValueModifier; //Serve per gestire l'effetto AddHarvestValueBonus
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
	private boolean noMilitaryPointsForTerritories;//Serve per gestire l'effetto della carta Leader Cesare Borgia
	private boolean dontCareOccupiedPlaces; //Serve per l'effetto del leader Ludovico Ariosto
	private boolean dontPayThreeCoinsInTowers; //Serve per l'effetto del leader Filippo Brunelleschi
	private boolean playWithThePope; //Serve per l'effetto di Sisto IV-> da modellare alla fine di ogni era
	private boolean santaRita;
	private boolean sistoIV;
	private boolean newAction;
	private boolean removeTowerBonus;
	private List<FamilyMember> familyMembers = new ArrayList<>();
	private PlayerBoard playerBoard = new PlayerBoard();
	private transient IOAdapter adapter;

	private boolean hasPassed = false;
	private boolean familiarPositioned = false;

	public Player(String username, String password, boolean isLogged)
	{
		this.username = username;
		this.password = password;
		this.isLogged = isLogged;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isLogged() {
		return isLogged;
	}

	public void setLogged(boolean logged) {
		isLogged = logged;
	}

	public boolean isSuspended() {
		return isSuspended;
	}

	public void setSuspended(boolean suspended) {
		isSuspended = suspended;
	}

	public PlayerColorsEnum getPlayerColorsEnum() {
		return playerColorsEnum;
	}

	public void setPlayerColorsEnum(PlayerColorsEnum playerColorsEnum) {
		this.playerColorsEnum = playerColorsEnum;
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

	public int getEndGameVictoryPoints() {
		return endGameVictoryPoints;
	}

	public void setEndGameVictoryPoints(int endGameVictoryPoints) {
		this.endGameVictoryPoints = endGameVictoryPoints;
	}

	public List<LeaderCard> getLeaderCards() {
		return leaderCards;
	}

	public void setLeaderCards(List<LeaderCard> leaderCards) {
		this.leaderCards = leaderCards;
	}

	public List<CardModifier> getCardModifiers() {
		return cardModifiers;
	}

	public void setCardModifiers(List<CardModifier> cardModifiers) {
		this.cardModifiers = cardModifiers;
	}

	public List<AddEndGameVictoryPoints> getAddEndGameVictoryPoints() {
		return addEndGameVictoryPoints;
	}

	public void setAddEndGameVictoryPoints(List<AddEndGameVictoryPoints> addEndGameVictoryPoints) {
		this.addEndGameVictoryPoints = addEndGameVictoryPoints;
	}

	public int getHarvestValueModifier() {
		return harvestValueModifier;
	}

	public void setHarvestValueModifier(int harvestValueModifier) {
		this.harvestValueModifier = harvestValueModifier;
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

	public boolean isNoMilitaryPointsForTerritories() {
		return noMilitaryPointsForTerritories;
	}

	public void setNoMilitaryPointsForTerritories(boolean noMilitaryPointsForTerritories) {
		this.noMilitaryPointsForTerritories = noMilitaryPointsForTerritories;
	}

	public boolean isDontCareOccupiedPlaces() {
		return dontCareOccupiedPlaces;
	}

	public void setDontCareOccupiedPlaces(boolean dontCareOccupiedPlaces) {
		this.dontCareOccupiedPlaces = dontCareOccupiedPlaces;
	}

	public boolean isDontPayThreeCoinsInTowers() {
		return dontPayThreeCoinsInTowers;
	}

	public void setDontPayThreeCoinsInTowers(boolean dontPayThreeCoinsInTowers) {
		this.dontPayThreeCoinsInTowers = dontPayThreeCoinsInTowers;
	}

	public boolean isPlayWithThePope() {
		return playWithThePope;
	}

	public void setPlayWithThePope(boolean playWithThePope) {
		this.playWithThePope = playWithThePope;
	}

	public boolean isSantaRita() {
		return santaRita;
	}

	public void setSantaRita(boolean santaRita) {
		this.santaRita = santaRita;
	}

	public boolean isSistoIV() {
		return sistoIV;
	}

	public void setSistoIV(boolean sistoIV) {
		this.sistoIV = sistoIV;
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

	public List<FamilyMember> getFamilyMembers() {
		return familyMembers;
	}

	public void setFamilyMembers(List<FamilyMember> familyMembers) {
		this.familyMembers = familyMembers;
	}

	public PlayerBoard getPlayerBoard() {
		return playerBoard;
	}

	public void setPlayerBoard(PlayerBoard playerBoard) {
		this.playerBoard = playerBoard;
	}

	public IOAdapter getAdapter() {
		return adapter;
	}

	public void setAdapter(IOAdapter adapter) {
		this.adapter = adapter;
	}

	public boolean isHasPassed() {
		return hasPassed;
	}

	public void setHasPassed(boolean hasPassed) {
		this.hasPassed = hasPassed;
	}

	public boolean isFamiliarPositioned() {
		return familiarPositioned;
	}

	public void setFamiliarPositioned(boolean familiarPositioned) {
		this.familiarPositioned = familiarPositioned;
	}

	public int getAsset(AssetType assetType)
	{
		String assetTypeString = assetType.toString().toLowerCase();
		
		switch (assetTypeString)
		{
		
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

		switch (assetType)
		{
			case "coin":
				this.coins += value;
				if (coins<0) coins=0;
				break;

			case "wood":
				this.woods += value;
				if (woods<0) woods=0;
				break;

			case "stone":
				this.stones += value;
				if (stones<0) stones=0;
				break;

			case "servant":
				this.servants += value;
				if (servants<0) servants=0;
				break;

			case "faithpoint":
				this.faithPoints += value;
				if (faithPoints<0) faithPoints=0;
				break;

			case "victorypoint":
				this.victoryPoints += value;
				if (victoryPoints < 0) victoryPoints=0;
				break;

			case "militarypoint":
				this.militaryPoints += value;
				if (militaryPoints<0) militaryPoints=0;
				break;

			default:
				break;
		}
		
	}

	public List<FamilyMember> getUnusedFamiliarMembers()
	{
		return familyMembers.parallelStream().
			filter(familyMember -> familyMember.isPlayed() == false)
			.collect(Collectors.toList());
	}

	public FamilyMember getUnusedFamilyMemberByColor(ColorsEnum color)
	{
		List<FamilyMember> unusedFamilyMembers = getUnusedFamiliarMembers();

		for (FamilyMember familyMember : unusedFamilyMembers)
		{
			if (familyMember.getColor() == color)
				return familyMember;
		}

		return null;
	}

	public FamilyMember getFamilyMemberByColor(ColorsEnum color)
	{

		for (FamilyMember familyMember : familyMembers)
		{
			if (familyMember.getColor() == color)
				return familyMember;
		}

		return null;
	}

	public void setFamiliarToPlayer(List<Dice> dices)
	{
		if (familyMembers.isEmpty())
		{
			for (Dice dice : dices)
			{
				FamilyMember familyMember = new FamilyMember();
	
				familyMember.setFamiliarValue(dice.getNumber());
				familyMember.setColor(dice.getColor());
				familyMember.setPlayerColor(playerColorsEnum);
	
				familyMember.setPlayed(false);
	
				familyMembers.add(familyMember);
			}

			FamilyMember familiarNeuter = new FamilyMember();
	
			familiarNeuter.setFamiliarValue(0);
			familiarNeuter.setColor(ColorsEnum.NEUTER);
			familiarNeuter.setPlayerColor(playerColorsEnum);
	
			familiarNeuter.setPlayed(false);
	
			familyMembers.add(familiarNeuter);
		}
		
		else
		{
			for(int i=0; i<dices.size(); i++)
			{//HANDLE THE COLOURED:
				familyMembers.get(i).setFamiliarValue(dices.get(i).getNumber());
				familyMembers.get(i).setPlayed(false);
			}
			//Handle the NEUTER:
			familyMembers.get(familyMembers.size()-1).setFamiliarValue(0);
			familyMembers.get(familyMembers.size()-1).setPlayed(false);
		}
	}

	@Override
	public String toString() {
		return "Name:" + username + '|'
				+ "NumOfMatch:" + numberOfMatch +'|'
				+ "Woods:" + woods + '|'
				+ "Stones:" + stones + '|'
				+ "Servants:" + servants + '|'
				+ "Coins:" + coins + '|'
				+ "MilPoints:" + militaryPoints + '|'
				+ "FaithPoints:" + faithPoints + '|'
				+ "VicPoints:" + victoryPoints + '|';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Player player = (Player) o;

		if (numberOfMatch != player.numberOfMatch) return false;
		if (woods != player.woods) return false;
		if (stones != player.stones) return false;
		if (servants != player.servants) return false;
		if (coins != player.coins) return false;
		if (militaryPoints != player.militaryPoints) return false;
		if (faithPoints != player.faithPoints) return false;
		if (victoryPoints != player.victoryPoints) return false;
		if (priority != player.priority) return false;
		if (endGameVictoryPoints != player.endGameVictoryPoints) return false;
		if (harvestValueModifier != player.harvestValueModifier) return false;
		if (productionValueModifier != player.productionValueModifier) return false;
		if (militaryPointsMalus != player.militaryPointsMalus) return false;
		if (coinMalus != player.coinMalus) return false;
		if (servantMalus != player.servantMalus) return false;
		if (woodMalus != player.woodMalus) return false;
		if (stoneMalus != player.stoneMalus) return false;
		if (familyMemberMalus != player.familyMemberMalus) return false;
		if (disableMarket != player.disableMarket) return false;
		if (servantsHandlerMalus != player.servantsHandlerMalus) return false;
		if (noFirstAction != player.noFirstAction) return false;
		if (noMilitaryPointsForTerritories != player.noMilitaryPointsForTerritories) return false;
		if (dontCareOccupiedPlaces != player.dontCareOccupiedPlaces) return false;
		if (dontPayThreeCoinsInTowers != player.dontPayThreeCoinsInTowers) return false;
		if (playWithThePope != player.playWithThePope) return false;
		if (santaRita != player.santaRita) return false;
		if (sistoIV != player.sistoIV) return false;
		if (newAction != player.newAction) return false;
		if (removeTowerBonus != player.removeTowerBonus) return false;
		if (hasPassed != player.hasPassed) return false;
		if (familiarPositioned != player.familiarPositioned) return false;
		if (username != null ? !username.equals(player.username) : player.username != null) return false;
		if (playerColorsEnum != player.playerColorsEnum) return false;
		if (leaderCards != null ? !leaderCards.equals(player.leaderCards) : player.leaderCards != null) return false;
		if (cardModifiers != null ? !cardModifiers.equals(player.cardModifiers) : player.cardModifiers != null)
			return false;
		if (addEndGameVictoryPoints != null ? !addEndGameVictoryPoints.equals(player.addEndGameVictoryPoints) : player.addEndGameVictoryPoints != null)
			return false;
		if (familyMembers != null ? !familyMembers.equals(player.familyMembers) : player.familyMembers != null)
			return false;
		if (playerBoard != null ? !playerBoard.equals(player.playerBoard) : player.playerBoard != null) return false;
		return adapter != null ? adapter.equals(player.adapter) : player.adapter == null;
	}

	@Override
	public int hashCode() {
		int result = username != null ? username.hashCode() : 0;
		result = 31 * result + (playerColorsEnum != null ? playerColorsEnum.hashCode() : 0);
		result = 31 * result + numberOfMatch;
		result = 31 * result + woods;
		result = 31 * result + stones;
		result = 31 * result + servants;
		result = 31 * result + coins;
		result = 31 * result + militaryPoints;
		result = 31 * result + faithPoints;
		result = 31 * result + victoryPoints;
		result = 31 * result + priority;
		result = 31 * result + endGameVictoryPoints;
		result = 31 * result + (leaderCards != null ? leaderCards.hashCode() : 0);
		result = 31 * result + (cardModifiers != null ? cardModifiers.hashCode() : 0);
		result = 31 * result + (addEndGameVictoryPoints != null ? addEndGameVictoryPoints.hashCode() : 0);
		result = 31 * result + harvestValueModifier;
		result = 31 * result + productionValueModifier;
		result = 31 * result + (militaryPointsMalus ? 1 : 0);
		result = 31 * result + (coinMalus ? 1 : 0);
		result = 31 * result + (servantMalus ? 1 : 0);
		result = 31 * result + (woodMalus ? 1 : 0);
		result = 31 * result + (stoneMalus ? 1 : 0);
		result = 31 * result + (familyMemberMalus ? 1 : 0);
		result = 31 * result + (disableMarket ? 1 : 0);
		result = 31 * result + (servantsHandlerMalus ? 1 : 0);
		result = 31 * result + (noFirstAction ? 1 : 0);
		result = 31 * result + (noMilitaryPointsForTerritories ? 1 : 0);
		result = 31 * result + (dontCareOccupiedPlaces ? 1 : 0);
		result = 31 * result + (dontPayThreeCoinsInTowers ? 1 : 0);
		result = 31 * result + (playWithThePope ? 1 : 0);
		result = 31 * result + (santaRita ? 1 : 0);
		result = 31 * result + (sistoIV ? 1 : 0);
		result = 31 * result + (newAction ? 1 : 0);
		result = 31 * result + (removeTowerBonus ? 1 : 0);
		result = 31 * result + (familyMembers != null ? familyMembers.hashCode() : 0);
		result = 31 * result + (playerBoard != null ? playerBoard.hashCode() : 0);
		result = 31 * result + (adapter != null ? adapter.hashCode() : 0);
		result = 31 * result + (hasPassed ? 1 : 0);
		result = 31 * result + (familiarPositioned ? 1 : 0);
		return result;
	}
}
