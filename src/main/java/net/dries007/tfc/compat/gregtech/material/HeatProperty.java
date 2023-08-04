package net.dries007.tfc.compat.gregtech.material;

import gregtech.api.unification.material.properties.IMaterialProperty;
import gregtech.api.unification.material.properties.MaterialProperties;

public class HeatProperty implements IMaterialProperty {

	private final float meltTemp;
	private final float specificHeat;
	private final int tier;

	// Use unknown metal values from tfc
	public HeatProperty() {
		this.meltTemp = 0.5f;
		this.specificHeat = 1250f;
		this.tier = 0;
	}

	public HeatProperty(float meltTemp, float specificHeat, int tier) {
		this.meltTemp = meltTemp;
		this.specificHeat = specificHeat;
		this.tier = tier;
	}

	public float getMeltTemp() {
		return meltTemp;
	}

	public float getSpecificHeat() {
		return specificHeat;
	}

	public int getTier() {
		return tier;
	}

	@Override
	public void verifyProperty(MaterialProperties properties) {
	}
}
