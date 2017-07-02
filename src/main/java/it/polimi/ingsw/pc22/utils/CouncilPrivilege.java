package it.polimi.ingsw.pc22.utils;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.AssetType;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CouncilPrivilege
{
	private List<Asset> bonus1;
    private List<Asset> bonus2;
    private List<Asset> bonus3;
    private List<Asset> bonus4;
    private List<Asset> bonus5;
    
    public CouncilPrivilege()
    {
    	bonus1 = new ArrayList<>();
        Asset wood = new Asset(1, AssetType.WOOD);
        Asset stone = new Asset(1, AssetType.STONE);
        bonus1.add(wood);
        bonus1.add(stone);
        bonus2 = new ArrayList<>();
        Asset servants = new Asset(2, AssetType.SERVANT);
        bonus2.add(servants);
        bonus3 = new ArrayList<>();
        Asset coins = new Asset(2, AssetType.COIN);
        bonus3.add(coins);
        bonus4 = new ArrayList<>();
        Asset military = new Asset(2, AssetType.MILITARYPOINT);
        bonus4.add(military);
        bonus5 = new ArrayList<>();
        Asset faith = new Asset(1, AssetType.FAITHPOINT);
        bonus5.add(faith);
    }

	public List<Asset> getBonus1() {
		return bonus1;
	}

	public void setBonus1(List<Asset> bonus1) {
		this.bonus1 = bonus1;
	}

	public List<Asset> getBonus2() {
		return bonus2;
	}

	public void setBonus2(List<Asset> bonus2) {
		this.bonus2 = bonus2;
	}

	public List<Asset> getBonus3() {
		return bonus3;
	}

	public void setBonus3(List<Asset> bonus3) {
		this.bonus3 = bonus3;
	}

	public List<Asset> getBonus4() {
		return bonus4;
	}

	public void setBonus4(List<Asset> bonus4) {
		this.bonus4 = bonus4;
	}

	public List<Asset> getBonus5() {
		return bonus5;
	}

	public void setBonus5(List<Asset> bonus5) {
		this.bonus5 = bonus5;
	}

	public List<Asset> getBonusFromNumberString(String number)
	{
		switch (number)
		{
			case "1":
				return bonus1;

			case "2":
				return bonus2;

			case "3":
				return bonus3;

			case "4":
				return bonus4;

			case "5":
				return bonus5;

			default:
				return null;
		}
	}

	public boolean validateBonusDecision(String decision, int numberOfBonus)
	{
		Pattern bonusPattern = null;

		if (numberOfBonus == 1)
		{
			bonusPattern = Pattern.compile("[1-5]");
		}

		if (numberOfBonus == 2)
		{
			bonusPattern = Pattern.compile("[1-5]-[1-5]");
		}

		if (numberOfBonus == 3)
		{
			bonusPattern = Pattern.compile("[1-5]-[1-5]-[1-5]");
		}

		if (bonusPattern == null) return false;

		Matcher matcher = bonusPattern.matcher(decision);

		if (!matcher.find()) return false;

		Set<String> duplicates = new HashSet<>();

		duplicates.addAll(Arrays.asList(decision.split("-")));

		if (duplicates.size() == numberOfBonus) return true;

		return false;
	}
}
