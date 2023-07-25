package net.dries007.tfc.compat.gregtech.material;

import gregtech.api.unification.material.properties.IMaterialProperty;
import gregtech.api.unification.material.properties.MaterialProperties;
import gregtech.api.unification.material.properties.PropertyKey;

public class HeatProperty implements IMaterialProperty {

    private final float meltTemp;
    private final float specificHeat;

    // Use unknown metal values from tfc
    public HeatProperty() {
        this.meltTemp = 0.5f;
        this.specificHeat = 1250f;
    }

    public HeatProperty(float meltTemp, float specificHeat) {
        this.meltTemp = meltTemp;
        this.specificHeat = specificHeat;
    }

    public float getMeltTemp() {
        return meltTemp;
    }

    public float getSpecificHeat() {
        return specificHeat;
    }

    @Override
    public void verifyProperty(MaterialProperties properties) {}
}
