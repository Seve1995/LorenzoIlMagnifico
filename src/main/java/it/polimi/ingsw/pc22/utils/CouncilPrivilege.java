package it.polimi.ingsw.pc22.utils;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.AssetType;

public class CouncilPrivilege {
	List<Asset> bonus1;
    List<Asset> bonus2;
    List<Asset> bonus3;
    List<Asset> bonus4;
    List<Asset> bonus5;
    
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
    
	@Override
    public String toString() {
    	String output = "";
    	if (bonus1!=null) output += "1) One stone & One wood" + '\n';
    	if (bonus2!=null) output += "2) Two servants" + '\n';
    	if (bonus3!=null) output += "3) Two coins" + '\n';
    	if (bonus4!=null) output += "4) Two military points" + '\n';
    	if (bonus5!=null) output += "5) Two faith points";
    	return output;
    }
}
