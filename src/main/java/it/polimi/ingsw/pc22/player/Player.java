package it.polimi.ingsw.pc22.player;

import it.polimi.ingsw.pc22.adapters.GameAdapter;
import it.polimi.ingsw.pc22.effects.AddEndGameVictoryPoints;
import it.polimi.ingsw.pc22.gamebox.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Player 
{	
	private String name;
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
	private List<LeaderCard> leaderCards;
	private List<CardModifier> cardModifiers;
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
	private PlayerBoard playerBoard;
	private GameAdapter adapter;


	@Override
	public String toString() {
		return "Player{" +
				"name='" + name + '\'' +
				", numberOfMatch=" + numberOfMatch +
				", woods=" + woods +
				", stones=" + stones +
				", servants=" + servants +
				", coins=" + coins +
				", militaryPoints=" + militaryPoints +
				", faithPoints=" + faithPoints +
				", victoryPoints=" + victoryPoints +
				", priority=" + priority +
				", leaderCards=" + leaderCards +
				", cardModifiers=" + cardModifiers +
				", addEndGameVictoryPoints=" + addEndGameVictoryPoints +
				", haverstValueModifier=" + harvestValueModifier +
				", productionValueModifier=" + productionValueModifier +
				", militaryPointsMalus=" + militaryPointsMalus +
				", coinMalus=" + coinMalus +
				", servantMalus=" + servantMalus +
				", woodMalus=" + woodMalus +
				", stoneMalus=" + stoneMalus +
				", familyMemberMalus=" + familyMemberMalus +
				", disableMarket=" + disableMarket +
				", servantsHandlerMalus=" + servantsHandlerMalus +
				", noFirstAction=" + noFirstAction +
				", noMilitaryPointsForTerritories=" + noMilitaryPointsForTerritories +
				", dontCareOccupiedPlaces=" + dontCareOccupiedPlaces +
				", dontPayThreeCoinsInTowers=" + dontPayThreeCoinsInTowers +
				", playWithThePope=" + playWithThePope +
				", santaRita=" + santaRita +
				", sistoIV=" + sistoIV +
				", newAction=" + newAction +
				", removeTowerBonus=" + removeTowerBonus +
				", familyMembers=" + familyMembers +
				", playerBoard=" + playerBoard +
				", adapter=" + adapter +
				'}';
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
		if (name != null ? !name.equals(player.name) : player.name != null) return false;
		if (leaderCards != null ? !leaderCards.equals(player.leaderCards) : player.leaderCards != null) return false;
		if (cardModifiers != null ? !cardModifiers.equals(player.cardModifiers) : player.cardModifiers != null)
			return false;
		if (addEndGameVictoryPoints != null ? !addEndGameVictoryPoints.equals(player.addEndGameVictoryPoints) : player.addEndGameVictoryPoints != null)
			return false;
		if (familyMembers != null ? !familyMembers.equals(player.familyMembers) : player.familyMembers!= null)
			return false;
		if (playerBoard != null ? !playerBoard.equals(player.playerBoard) : player.playerBoard != null) return false;
		return adapter != null ? adapter.equals(player.adapter) : player.adapter == null;
	}

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + numberOfMatch;
		result = 31 * result + woods;
		result = 31 * result + stones;
		result = 31 * result + servants;
		result = 31 * result + coins;
		result = 31 * result + militaryPoints;
		result = 31 * result + faithPoints;
		result = 31 * result + victoryPoints;
		result = 31 * result + priority;
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
		return result;
	}

	public GameAdapter getAdapter() {

		return adapter;
	}

	public void setAdapter(GameAdapter adapter) {
		this.adapter = adapter;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public PlayerColorsEnum getPlayerColorsEnum() {
		return playerColorsEnum;
	}

	public void setPlayerColorsEnum(PlayerColorsEnum playerColorsEnum) {
		this.playerColorsEnum = playerColorsEnum;
	}

	public List<CardModifier> getCardModifiers() {
		return cardModifiers;
	}

	public void setCardModifiers(List<CardModifier> cardModifiers) {
		this.cardModifiers = cardModifiers;
	}

	public int getHarvestValueModifier() {
		return harvestValueModifier;
	}

	public void setHarvestValueModifier(int harvestValueModifier) {
		this.harvestValueModifier = harvestValueModifier;
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
		return harvestValueModifier;
	}
	public void setHaverstValueModifier(int haverstValueModifier) {
		this.harvestValueModifier = haverstValueModifier;
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
	
	public List<LeaderCard> getLeaderCards() {
		return leaderCards;
	}
	public void setLeaderCards(List<LeaderCard> leaderCards) {
		this.leaderCards = leaderCards;
	}
	
	public boolean isSistoIV() {
		return sistoIV;
	}
	public void setSistoIV (boolean sistoIV) {
		this.sistoIV = sistoIV;
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
	
	public boolean isNoMilitaryPointsForTerritories() {
		return noMilitaryPointsForTerritories;
	}
	public void setNoMilitaryPointsForTerritories(boolean noMilitaryPointsForTerritories) {
		this.noMilitaryPointsForTerritories = noMilitaryPointsForTerritories;
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
	
	public List<AddEndGameVictoryPoints> getAddEndGameVictoryPoints() {
		return addEndGameVictoryPoints;
	}
	public void setAddEndGameVictoryPoints(List<AddEndGameVictoryPoints> addEndGameVictoryPoints) {
		this.addEndGameVictoryPoints = addEndGameVictoryPoints;
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

	public void setFamiliarToPlayer(List<Dice> dices)
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
	}

	public void removeFamilyMember (FamilyMember f)
	
	{
	
		for(int i=0; i < familyMembers.size(); i++)
		{
		
			if (this.familyMembers.get(i).getColor().equals(f.getColor()))
				
			{
			
				this.familyMembers.remove(i);
				
			}
		
		}
	
	}

}
