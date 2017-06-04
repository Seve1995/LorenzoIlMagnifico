package it.polimi.ingsw.pc22.gamebox;

public class BonusTile {
	private Asset productionServantBonus;
	private Asset productionCoinsBonus;
	private Asset productionMilitaryPointsBonus;
	private int productionActivationValue;
	private Asset harvestServantBonus;
	private Asset harvestCoinsBonus;
	private Asset harvestMilitaryPointsBonus;
	private Asset harvestWoodsBonus;
	private Asset harvestStonesBonus;
	private int harvestActivationValue;

	public Asset getProductionServantBonus() {
		return productionServantBonus;
	}

	public void setProductionServantBonus(Asset productionServantBonus) {
		this.productionServantBonus = productionServantBonus;
	}

	public Asset getProductionCoinsBonus() {
		return productionCoinsBonus;
	}

	public void setProductionCoinsBonus(Asset productionCoinsBonus) {
		this.productionCoinsBonus = productionCoinsBonus;
	}

	public Asset getProductionMilitaryPointsBonus() {
		return productionMilitaryPointsBonus;
	}

	public void setProductionMilitaryPointsBonus(Asset productionMilitaryPointsBonus) {
		this.productionMilitaryPointsBonus = productionMilitaryPointsBonus;
	}

	public int getProductionActivationValue() {
		return productionActivationValue;
	}

	public void setProductionActivationValue(int productionActivationValue) {
		this.productionActivationValue = productionActivationValue;
	}

	public Asset getHarvestServantBonus() {
		return harvestServantBonus;
	}

	public void setHarvestServantBonus(Asset harvestServantBonus) {
		this.harvestServantBonus = harvestServantBonus;
	}

	public Asset getHarvestCoinsBonus() {
		return harvestCoinsBonus;
	}

	public void setHarvestCoinsBonus(Asset harvestCoinsBonus) {
		this.harvestCoinsBonus = harvestCoinsBonus;
	}

	public Asset getHarvestMilitaryPointsBonus() {
		return harvestMilitaryPointsBonus;
	}

	public void setHarvestMilitaryPointsBonus(Asset harvestMilitaryPointsBonus) {
		this.harvestMilitaryPointsBonus = harvestMilitaryPointsBonus;
	}

	public Asset getHarvestWoodsBonus() {
		return harvestWoodsBonus;
	}

	public void setHarvestWoodsBonus(Asset harvestWoodsBonus) {
		this.harvestWoodsBonus = harvestWoodsBonus;
	}

	public Asset getHarvestStonesBonus() {
		return harvestStonesBonus;
	}

	public void setHarvestStonesBonus(Asset harvestStonesBonus) {
		this.harvestStonesBonus = harvestStonesBonus;
	}

	public int getHarvestActivationValue() {
		return harvestActivationValue;
	}

	public void setHarvestActivationValue(int harvestActivationValue) {
		this.harvestActivationValue = harvestActivationValue;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		BonusTile bonusTile = (BonusTile) o;

		if (productionActivationValue != bonusTile.productionActivationValue) return false;
		if (harvestActivationValue != bonusTile.harvestActivationValue) return false;
		if (productionServantBonus != null ? !productionServantBonus.equals(bonusTile.productionServantBonus) : bonusTile.productionServantBonus != null)
			return false;
		if (productionCoinsBonus != null ? !productionCoinsBonus.equals(bonusTile.productionCoinsBonus) : bonusTile.productionCoinsBonus != null)
			return false;
		if (productionMilitaryPointsBonus != null ? !productionMilitaryPointsBonus.equals(bonusTile.productionMilitaryPointsBonus) : bonusTile.productionMilitaryPointsBonus != null)
			return false;
		if (harvestServantBonus != null ? !harvestServantBonus.equals(bonusTile.harvestServantBonus) : bonusTile.harvestServantBonus != null)
			return false;
		if (harvestCoinsBonus != null ? !harvestCoinsBonus.equals(bonusTile.harvestCoinsBonus) : bonusTile.harvestCoinsBonus != null)
			return false;
		if (harvestMilitaryPointsBonus != null ? !harvestMilitaryPointsBonus.equals(bonusTile.harvestMilitaryPointsBonus) : bonusTile.harvestMilitaryPointsBonus != null)
			return false;
		if (harvestWoodsBonus != null ? !harvestWoodsBonus.equals(bonusTile.harvestWoodsBonus) : bonusTile.harvestWoodsBonus != null)
			return false;
		return harvestStonesBonus != null ? harvestStonesBonus.equals(bonusTile.harvestStonesBonus) : bonusTile.harvestStonesBonus == null;
	}

	@Override
	public int hashCode() {
		int result = productionServantBonus != null ? productionServantBonus.hashCode() : 0;
		result = 31 * result + (productionCoinsBonus != null ? productionCoinsBonus.hashCode() : 0);
		result = 31 * result + (productionMilitaryPointsBonus != null ? productionMilitaryPointsBonus.hashCode() : 0);
		result = 31 * result + productionActivationValue;
		result = 31 * result + (harvestServantBonus != null ? harvestServantBonus.hashCode() : 0);
		result = 31 * result + (harvestCoinsBonus != null ? harvestCoinsBonus.hashCode() : 0);
		result = 31 * result + (harvestMilitaryPointsBonus != null ? harvestMilitaryPointsBonus.hashCode() : 0);
		result = 31 * result + (harvestWoodsBonus != null ? harvestWoodsBonus.hashCode() : 0);
		result = 31 * result + (harvestStonesBonus != null ? harvestStonesBonus.hashCode() : 0);
		result = 31 * result + harvestActivationValue;
		return result;
	}

	@Override
	public String toString() {
		return "BonusTile{" +
				"productionServantBonus=" + productionServantBonus +
				", productionCoinsBonus=" + productionCoinsBonus +
				", productionMilitaryPointsBonus=" + productionMilitaryPointsBonus +
				", productionActivationValue=" + productionActivationValue +
				", harvestServantBonus=" + harvestServantBonus +
				", harvestCoinsBonus=" + harvestCoinsBonus +
				", harvestMilitaryPointsBonus=" + harvestMilitaryPointsBonus +
				", harvestWoodsBonus=" + harvestWoodsBonus +
				", harvestStonesBonus=" + harvestStonesBonus +
				", harvestActivationValue=" + harvestActivationValue +
				'}';
	}
}