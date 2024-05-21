package su.terrafirmagreg.api.capabilities.size;

import su.terrafirmagreg.modules.core.ModuleCoreConfig;


import lombok.Getter;

@Getter
public enum Weight {
    VERY_LIGHT("very_light", ModuleCoreConfig.MISC.WEIGHT.veryLight),
    LIGHT("light", ModuleCoreConfig.MISC.WEIGHT.light),
    MEDIUM("medium", ModuleCoreConfig.MISC.WEIGHT.medium),
    HEAVY("heavy", ModuleCoreConfig.MISC.WEIGHT.heavy),
    VERY_HEAVY("very_heavy", ModuleCoreConfig.MISC.WEIGHT.veryHeavy);

    public final int stackSize;
    public final String name;

    Weight(String name, int stackSize) {
        this.name = name;
        this.stackSize = stackSize;
    }

    public boolean isSmallerThan(Weight other) {
        return this.stackSize > other.stackSize;
    }
}
