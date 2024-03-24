package su.terrafirmagreg.modules.core.api.capabilities.size;

import lombok.Getter;
import su.terrafirmagreg.modules.core.ModuleCoreConfig;

@Getter
public enum Weight {
	VERY_LIGHT("very_light", ModuleCoreConfig.MISC.WEIGHT.VERY_LIGHT),
	LIGHT("light", ModuleCoreConfig.MISC.WEIGHT.LIGHT),
	MEDIUM("medium", ModuleCoreConfig.MISC.WEIGHT.MEDIUM),
	HEAVY("heavy", ModuleCoreConfig.MISC.WEIGHT.HEAVY),
	VERY_HEAVY("very_heavy", ModuleCoreConfig.MISC.WEIGHT.VERY_HEAVY);

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
