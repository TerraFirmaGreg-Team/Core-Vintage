package su.terrafirmagreg.core.modules.ambiental.modifier;

import su.terrafirmagreg.core.modules.ambiental.capability.TemperatureCapability;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class TempModifier implements Comparable<TempModifier> {
	private String unlocalizedName;
	private float change = 0f;
	private float potency = 0f;
	private int count = 1;
	private float multiplier = 1f;

	public TempModifier(String unlocalizedName) {
		this.unlocalizedName = unlocalizedName;
	}

	public TempModifier(String unlocalizedName, float change, float potency) {
		this.unlocalizedName = unlocalizedName;
		this.change = change;
		this.potency = potency;
	}

	public static Optional<TempModifier> defined(String unlocalizedName, float change, float potency) {
		return Optional.of(new TempModifier(unlocalizedName, change, potency));
	}

	public static Optional<TempModifier> none() {
		return Optional.empty();
	}

	public float getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(float multiplier) {
		this.multiplier = multiplier;
	}

	public void addMultiplier(float multiplier) {
		this.setMultiplier(this.getMultiplier() * multiplier);
	}

	public float getChange() {
		return change * multiplier * 1; //(count == 1 ? 1f : TFGConfig.GENERAL.diminishedModifierMultiplier);
	}

	public void setChange(float change) {
		this.change = change;
	}

	public float getPotency() {
		return potency * multiplier * 1; //(count == 1 ? 1f : TFGConfig.GENERAL.diminishedModifierMultiplier);
	}

	public void setPotency(float potency) {
		this.potency = potency;
	}

	public void addCount() {
		count++;
	}

	public void absorb(TempModifier modifier) {
//        if(count >= TFGConfig.GENERAL.modifierCap) {
//            return;
//        }
		this.count += modifier.count;
		this.change += modifier.change;
		this.potency += modifier.potency;
		this.addMultiplier(modifier.getMultiplier());
	}

	public int getCount() {
		return count;
	}

	public String getUnlocalizedName() {
		return unlocalizedName;
	}

//    public String getDisplayName() {
//        return Util.translate(MOD_ID + ".modifier." + this.unlocalizedName);
//    }

	public void apply(TemperatureCapability temp) {
		// nothing;
	}

	public void cancel(TemperatureCapability temp) {
		// nothing;
	}

	@Override
	public int compareTo(@NotNull TempModifier o) {
		return Float.compare(this.change, o.change);
	}
}
